
package acme.features.manager.userStories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.projectUserStories.ProjectUserStory;
import acme.entities.userStory.StoryPriority;
import acme.entities.userStory.UserStory;
import acme.features.manager.projectUserStories.ManagerProjectUserStoriesRepository;
import acme.roles.Manager;

@Service
public class ManagerUserStoryCreateInProjectService extends AbstractService<Manager, UserStory> {

	private static final String					PROJECT_ID	= "masterId";

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository			repository;

	@Autowired
	private ManagerProjectUserStoriesRepository	managerProjectUserStoryRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;
		Manager manager;

		projectId = super.getRequest().getData(ManagerUserStoryCreateInProjectService.PROJECT_ID, int.class);
		project = this.repository.findProjectById(projectId);
		manager = this.repository.findManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		status = project != null && project.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		Manager manager;

		manager = this.repository.findManagerById(super.getRequest().getPrincipal().getActiveRoleId());

		object = new UserStory();
		object.setDraftMode(true);
		object.setManager(manager);

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
			super.state(object.getEstimatedCost() > 0., "estimatedCost", "manager.userstory.form.error.not-positive-estimatedcost");
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		ProjectUserStory pus = new ProjectUserStory();
		int projectId;
		Project project;

		projectId = super.getRequest().getData(ManagerUserStoryCreateInProjectService.PROJECT_ID, int.class);
		project = this.repository.findProjectById(projectId);

		pus.setProject(project);
		pus.setUserStory(object);

		this.repository.save(object);
		this.managerProjectUserStoryRepository.save(pus);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choices;

		int projectId;

		choices = SelectChoices.from(StoryPriority.class, object.getPriority());

		projectId = super.getRequest().getData(ManagerUserStoryCreateInProjectService.PROJECT_ID, int.class);
		Project project = this.repository.findProjectById(projectId);

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
		dataset.put(ManagerUserStoryCreateInProjectService.PROJECT_ID, super.getRequest().getData(ManagerUserStoryCreateInProjectService.PROJECT_ID, int.class));
		dataset.put("priority", choices);

		super.getResponse().addGlobal("masterId", projectId);
		super.getResponse().addData(dataset);
	}
}
