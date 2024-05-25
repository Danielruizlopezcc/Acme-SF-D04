
package acme.features.manager.userStories;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListByProyectService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerUserStoryRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Manager.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<UserStory> userStories;

		final int projectId = super.getRequest().getData("masterId", int.class);
		userStories = this.repository.findUserStoryByProjectId(projectId);

		super.getBuffer().addData(userStories);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		final Dataset dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");

		int masterId = super.getRequest().getData("masterId", int.class);

		super.getResponse().addGlobal("masterId", masterId);

		super.getResponse().addData(dataset);
	}

}
