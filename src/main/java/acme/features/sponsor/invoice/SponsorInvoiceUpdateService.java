
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.systemconf.SystemConfiguration;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int invoiceId;
		Sponsorship sponsorship;
		Invoice invoice;

		invoiceId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipByInvoiceId(invoiceId);
		invoice = this.repository.findOneInvoiceById(invoiceId);
		status = sponsorship != null && sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor()) && invoice.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "project");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
		Collection<Invoice> invoices;

		invoices = this.repository.findAllInvoicesByMasterId(object.getSponsorship().getId());

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.invoice.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("registrationTime")) {
			Date moment;
			Sponsorship sponsorship;
			boolean momentBeforeRegistrationTime;
			Date registrationTime;

			sponsorship = object.getSponsorship();
			moment = sponsorship.getMoment();
			registrationTime = object.getRegistrationTime();
			momentBeforeRegistrationTime = MomentHelper.isAfter(registrationTime, moment);
			super.state(momentBeforeRegistrationTime, "registrationTime", "sponsor.invoice.form.error.registration-time-not-valid");

		}

		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date registrationTime;
			Date dueDate;
			boolean isMinimumDuration;
			boolean dueDateIsAfterRegistrationTime;

			registrationTime = object.getRegistrationTime();
			dueDate = object.getDueDate();
			isMinimumDuration = registrationTime == null ? false : MomentHelper.isLongEnough(registrationTime, dueDate, 1, ChronoUnit.MONTHS);
			dueDateIsAfterRegistrationTime = registrationTime == null ? false : MomentHelper.isAfter(dueDate, registrationTime);

			super.state(isMinimumDuration && dueDateIsAfterRegistrationTime, "dueDate", "sponsor.invoice.form.error.due-date-not-valid");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			super.state(object.getQuantity().getAmount() > 0, "quantity", "sponsor.invoice.form.error.quantity-must-be-positive");
			List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
			final boolean foundCurrency = Stream.of(sc.get(0).acceptedCurrencies.split(",")).anyMatch(c -> c.equals(object.getQuantity().getCurrency()));

			super.state(foundCurrency, "quantity", "sponsor.invoice.form.error.currency-not-supported");

			boolean isSameCurrency;
			isSameCurrency = object.getQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency());
			super.state(isSameCurrency, "quantity", "sponsor.invoice.form.error.currency-not-valid");

			double cantidadAnterior = this.repository.findOneInvoiceById(object.getId()).totalAmount().getAmount();
			double cantidadTotalActual = object.totalAmount().getAmount() - cantidadAnterior;
			for (Invoice i : invoices)
				cantidadTotalActual += i.totalAmount().getAmount();
			super.state(cantidadTotalActual <= object.getSponsorship().getAmount().getAmount(), "*", "sponsor.invoice.form.error.quantity-not-valid");
		}
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode");
		dataset.put("masterId", object.getSponsorship().getId());

		super.getResponse().addData(dataset);

	}
}
