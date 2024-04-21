
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListMineService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsorship sponsorship;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		status = sponsorship != null && (!sponsorship.isDraftMode() || super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Invoice> invoices;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		invoices = this.repository.findAllInvoicesBySponsorshipId(masterId);

		super.getBuffer().addData(invoices);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "totalAmount", "draftMode");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<Invoice> objects) {
		assert objects != null;

		int masterId;
		Sponsorship sponsorship;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		showCreate = sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().addGlobal("masterId", masterId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
