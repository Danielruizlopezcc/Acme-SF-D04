
package acme.features.manager.projectUserStories;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.projectUserStories.ProjectUserStory;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoriesCreateService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoriesRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectUserStory object = new ProjectUserStory();
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;
		super.bind(object, "project", "userStory");

	}

	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;
		Project project;
		UserStory userStory;

		project = object.getProject();
		userStory = object.getUserStory();
		int managerId = super.getRequest().getPrincipal().getActiveRoleId();
		Manager manager = this.repository.findManagerById(managerId);

		super.state(object.getProject() != null, "*", "manager.project-user-story.create.error.null-project");
		super.state(object.getUserStory() != null, "*", "manager.project-user-story.create.error.null-user-story");

		if (!super.getBuffer().getErrors().hasErrors("project") && !super.getBuffer().getErrors().hasErrors("userStory")) {
			ProjectUserStory existing;
			existing = this.repository.findOneProjectUserStoryByProjectIdAndUserStoryId(project.getId(), userStory.getId());
			super.state(project.getManager().equals(manager) && userStory.getManager().equals(manager), "*", "manager.link.form.error.wrong-manager");
			super.state(existing == null, "*", "manager.link.form.error.existing-project-assignment");
			super.state(project.isDraftMode() || !userStory.isDraftMode(), "project", "manager.link.form.error.published-project");
		}

	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Collection<UserStory> userStories;
		Collection<Project> projects;
		SelectChoices choicesUserStories;
		SelectChoices choicesProjects;
		Dataset dataset;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		userStories = this.repository.findPublishedUserStoriesByManagerId(managerId, false);
		choicesUserStories = SelectChoices.from(userStories, "title", object.getUserStory());

		projects = this.repository.findNotPublishedProjectsByManagerId(managerId, true);
		choicesProjects = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "userStory", "project");
		dataset.put("userStory", choicesUserStories.getSelected().getKey());
		dataset.put("userStories", choicesUserStories);
		dataset.put("project", choicesProjects.getSelected().getKey());
		dataset.put("projects", choicesProjects);

		super.getResponse().addData(dataset);
	}

}
