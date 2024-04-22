
package acme.features.manager.projectUserStories;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
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
		final int projectId = super.getRequest().getData("projectId", int.class);
		Project project = this.repository.findProjectById(projectId);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = project != null && project.isDraftMode() && principal.hasRole(Manager.class) && project.getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
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
	public void validate(final ProjectUserStory object) {
		assert object != null;
		final int projectId = super.getRequest().getData("masterId", int.class);

		if (!super.getBuffer().getErrors().hasErrors("userStory")) {
			final boolean userStoriesDuplicated = this.repository.findProjectUserStoryByProjectId(projectId).stream().anyMatch(a -> a.getUserStory().equals(object.getUserStory()));

			super.state(!userStoriesDuplicated, "userStory", "manager.project.form.error.duplicated-user-story");
		}

	}

	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Dataset dataset;

		int id = super.getRequest().getPrincipal().getActiveRoleId();
		SelectChoices projectChoices;
		SelectChoices userStoriesChoices;

		Collection<Project> projects = this.repository.findProjectsByManagerId(id);
		Collection<UserStory> userStories = this.repository.findUserStoriesByManagerId(id);

		projectChoices = SelectChoices.from(projects, "code", object.getProject());
		userStoriesChoices = SelectChoices.from(userStories, "title", object.getUserStory());

		dataset = super.unbind(object, "project", "userStory");

		dataset.put("projects", projectChoices);
		dataset.put("userStories", userStoriesChoices);

		super.getResponse().addData(dataset);
	}
}
