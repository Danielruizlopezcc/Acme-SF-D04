
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Banner banner = new Banner();

		super.getBuffer().addData(banner);
	}
	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "startDisplayPeriod", "endDisplayPeriod", "slogan", "picture", "link");

	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		final String PERIOD_START = "startDisplayPeriod";
		final String PERIOD_END = "endDisplayPeriod";
		final String INSTANTIATION = "instatiationMoment";
		if (object.getInstantiationMoment() != null && object.getStartDisplayPeriod() != null && object.getEndDisplayPeriod() != null && !super.getBuffer().getErrors().hasErrors(PERIOD_START) && !super.getBuffer().getErrors().hasErrors(INSTANTIATION)) {
			final boolean startAfterInstantiation = MomentHelper.isAfter(object.getStartDisplayPeriod(), object.getInstantiationMoment());
			super.state(startAfterInstantiation, PERIOD_START, "administrator.banner.form.error.start-before-instantiation");
		}

		if (object.getInstantiationMoment() != null && object.getStartDisplayPeriod() != null && object.getEndDisplayPeriod() != null && !super.getBuffer().getErrors().hasErrors(PERIOD_END) && !super.getBuffer().getErrors().hasErrors(INSTANTIATION)) {
			final boolean startBeforeEnd = MomentHelper.isAfter(object.getEndDisplayPeriod(), object.getStartDisplayPeriod());
			super.state(startBeforeEnd, PERIOD_END, "administrator.banner.form.error.end-before-start");

			if (startBeforeEnd) {
				final boolean startOneWeekBeforeEndMinimum = MomentHelper.isLongEnough(object.getStartDisplayPeriod(), object.getEndDisplayPeriod(), 7, ChronoUnit.DAYS);

				super.state(startOneWeekBeforeEndMinimum, PERIOD_END, "administrator.banner.form.error.small-display-period");
			}
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "startDisplayPeriod", "endDisplayPeriod", "slogan", "picture", "link");

		super.getResponse().addData(dataset);
	}

}
