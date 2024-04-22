
package acme.features.manager.projects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.projectUserStories.ProjectUserStory;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		Project pr;
		Manager manager;

		masterId = super.getRequest().getData("id", int.class);
		pr = this.repository.findOneProjectById(masterId);
		manager = pr == null ? null : pr.getManager();
		status = pr != null && pr.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;
		super.bind(object, "code", "title", "abstractProject", "indication", "cost", "link");

	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project existing;

			existing = this.repository.findOneProjectByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-module.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("indication"))
			super.state(object.isIndication() == false, "indication", "manager.project-module-form.error.existing-fatal-errors");

		if (!super.getBuffer().getErrors().hasErrors("cost"))
			super.state(object.getCost().getAmount() > 0, "cost", "manager.project.form.error.negative-cost");

		int masterId = super.getRequest().getData("id", int.class);
		List<UserStory> ls = this.repository.findProjectUserStoriesByProjectId(masterId).stream().map(ProjectUserStory::getUserStory).toList();
		final boolean draftUserStory = ls.stream().anyMatch(us -> us.isDraftMode());
		final boolean noUserStories = ls.isEmpty();
		super.state(!noUserStories, "*", "manager.project.form.error.userStories-empty");
		super.state(!draftUserStory, "*", "manager.project.form.error.userStories-draft");

	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstractProject", "indication", "cost", "link", "draftMode");

		super.getResponse().addData(dataset);

	}
}
