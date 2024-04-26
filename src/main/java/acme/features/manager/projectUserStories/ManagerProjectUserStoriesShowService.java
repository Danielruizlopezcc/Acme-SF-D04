
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
public class ManagerProjectUserStoriesShowService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoriesRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int projectUserStoryId;
		ProjectUserStory projectUserStory;

		projectUserStoryId = super.getRequest().getData("id", int.class);
		projectUserStory = this.repository.findProjectUserStoryById(projectUserStoryId);

		status = projectUserStory != null && super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectUserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProjectUserStoryById(id);

		super.getBuffer().addData(object);
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

		userStories = this.repository.findUserStoriesByManagerId(managerId);
		choicesUserStories = SelectChoices.from(userStories, "title", object.getUserStory());

		projects = this.repository.findProjectsByManagerId(managerId);
		choicesProjects = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "userStory", "project");
		dataset.put("userStory", choicesUserStories.getSelected().getKey());
		dataset.put("userStories", choicesUserStories);
		dataset.put("project", choicesProjects.getSelected().getKey());
		dataset.put("projects", choicesProjects);

		super.getResponse().addData(dataset);
	}

}
