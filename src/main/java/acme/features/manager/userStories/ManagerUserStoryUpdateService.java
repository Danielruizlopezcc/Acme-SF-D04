
package acme.features.manager.userStories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.userStory.StoryPriority;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryUpdateService extends AbstractService<Manager, UserStory> {

	@Autowired
	private ManagerUserStoryRepository repository;


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		UserStory us;
		Manager manager;

		masterId = super.getRequest().getData("id", int.class);
		us = this.repository.findUserStoryById(masterId);
		manager = us == null ? null : us.getManager();
		status = us != null && us.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;
		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");

	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("estimatedCost"))
			super.state(object.getEstimatedCost() > 0, "estimatedCost", "manager.user-story.form.error.negative-cost");

	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		SelectChoices choices = SelectChoices.from(StoryPriority.class, object.getPriority());

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draftMode");

		dataset.put("priority", choices);
		super.getResponse().addData(dataset);
	}
}
