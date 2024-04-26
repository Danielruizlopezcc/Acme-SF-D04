
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.sponsorship.SponsorshipType;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	@Autowired
	private AnySponsorshipRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Sponsorship> publishedSponsorships;

		publishedSponsorships = this.repository.findAllPublishedSponsorships();

		super.getBuffer().addData(publishedSponsorships);
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
		dataset = super.unbind(object, "code", "moment", "durationStart", "durationEnd", "amount", "type", "emailContact", "link", "project");

		dataset.put("sponsorshipType", choices);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);
	}
}
