
package acme.features.manager.projectUserStories;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.projectUserStories.ProjectUserStory;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoriesListMineService extends AbstractService<Manager, ProjectUserStory> {

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

		int managerId = super.getRequest().getPrincipal().getActiveRoleId();
		Collection<ProjectUserStory> projectUserStories = this.repository.findProjectUserStoriesByManagerId(managerId);

		super.getBuffer().addData(projectUserStories);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Project project;
		UserStory userStory;
		int projectUserStoryId;
		Dataset dataset;

		projectUserStoryId = object.getId();
		project = this.repository.findOneProjectByProjectUserStoryId(projectUserStoryId);
		userStory = this.repository.findOneUserStoryByProjectUserStoryId(projectUserStoryId);

		dataset = super.unbind(object, "userStory", "project");
		dataset.put("project", project.getCode());
		dataset.put("userStory", userStory.getTitle());

		super.getResponse().addData(dataset);
	}

}
