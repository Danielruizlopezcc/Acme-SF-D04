
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {

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
		final int id = super.getRequest().getData("id", int.class);
		final Banner banner = this.repository.findBannerById(id);

		super.getBuffer().addData(banner);
	}
	@Override
	public void bind(final Banner object) {
		assert object != null;
		//Not sure if necessary
		super.bind(object, "startDisplayPeriod", "endDisplayPeriod", "slogan", "link", "picture");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "startDisplayPeriod", "endDisplayPeriod", "slogan", "picture", "link");

		super.getResponse().addData(dataset);
	}

}
