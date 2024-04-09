
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Sponsorship object;
		Sponsor sponsor;

		Principal principal = super.getRequest().getPrincipal();
		sponsor = this.repository.findOneSponsorById(principal.getActiveRoleId());
		object = new Sponsorship();
		object.setDraftMode(true);
		object.setSponsor(sponsor);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "moment", "durationStart", "durationEnd", "amount", "type", "emailContact", "link");
		object.setProject(project);

	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.sponsorship.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("durationStart"))
			super.state(MomentHelper.isAfter(object.getDurationStart(), object.getMoment()), "durationStart", "sponsor.sponsorship.form.error.duration-start-date-not-valid");

		if (!super.getBuffer().getErrors().hasErrors("durationEnd")) {
			Date durationStart;
			Date durationEnd;
			boolean isMinimumDuration;
			boolean durationEndIsAfterStart;

			durationStart = object.getDurationStart();
			durationEnd = object.getDurationEnd();
			isMinimumDuration = MomentHelper.isLongEnough(durationStart, durationEnd, 1, ChronoUnit.MONTHS);
			durationEndIsAfterStart = MomentHelper.isAfter(durationEnd, durationStart);

			super.state(isMinimumDuration && durationEndIsAfterStart, "durationStart", "sponsor.sponsorhip.form.error.duration-not-valid");
		}
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "moment", "durationStart", "durationEnd", "amount", "type", "emailContact", "link");

		super.getResponse().addData(dataset);

	}
}
