
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.sponsorship.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		masterId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = super.getRequest().getPrincipal().hasRole(sponsor) || sponsorship != null && !sponsorship.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		SelectChoices choices;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Dataset dataset;
		choices = SelectChoices.from(SponsorshipType.class, object.getType());
		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "moment", "durationStart", "durationEnd", "amount", "type", "emailContact", "link", "draftMode", "project");

		dataset.put("sponsorshipType", choices);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);

	}
}
