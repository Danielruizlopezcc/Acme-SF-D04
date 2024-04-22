
package acme.features.manager.projectUserStories;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projectUserStories.ProjectUserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoriesListMineService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoriesRepository repository;


	@Override
	public void authorise() {

		final Principal principal = super.getRequest().getPrincipal();
		final boolean authorise = principal.hasRole(Manager.class);
		super.getResponse().setAuthorised(authorise);
	}
	@Override
	public void load() {
		//		final int managerId = super.getRequest().getPrincipal().getAccountId();
		//		Collection<Project> projects = this.repository.findProjectsByManagerId(managerId);
		//		Collection<UserStory> userStories = this.repository.findUserStoriesByManagerId(managerId);
		int id = super.getRequest().getData("id", int.class);

		Collection<ProjectUserStory> projectUserStories = this.repository.findProjectUserStoriesById(id);

		super.getBuffer().addData(projectUserStories);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;
		Dataset dataset = super.unbind(object, "project", "userStory");
		super.getResponse().addData(dataset);
	}

}
