
package acme.features.manager.projectUserStories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projectUserStories.ProjectUserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoriesDeleteService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoriesRepository repository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStory projectUserStory = this.repository.findProjectUserStoryById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = projectUserStory != null && projectUserStory.getProject().getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStory projectUserStories = this.repository.findProjectUserStoryById(id);

		super.getBuffer().addData(projectUserStories);
	}

	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;
		super.bind(object, "userStory");

	}

	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;
	}

	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "userStory");

		super.getResponse().addData(dataset);
	}

}
