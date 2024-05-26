
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.trainingModule.Difficulty;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleDeleteService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	private DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		TrainingModule tm;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		tm = this.repository.findOneTMById(masterId);
		developer = tm == null ? null : tm.getDeveloper();
		status = tm != null && tm.isDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTMById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "estimatedTotalTime", "project");
		object.setProject(project);

	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		Collection<TrainingSession> sessions;
		boolean canBeDeleted;

		sessions = this.repository.findAllTSByTMId(object.getId());
		canBeDeleted = sessions.stream().anyMatch(ts -> ts.isDraftMode() == true);
		if (!canBeDeleted && sessions.size() > 0)
			super.state(canBeDeleted, "*", "developer.training-module.form.error.can't-delete-published-training-session");
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		Collection<TrainingSession> sessions;

		sessions = this.repository.findAllTSByTMId(object.getId());
		this.repository.deleteAll(sessions);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		SelectChoices choices;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Dataset dataset;
		choices = SelectChoices.from(Difficulty.class, object.getDifficultyLevel());
		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "estimatedTotalTime", "draftMode", "project");
		dataset.put("difficulty", choices);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);

	}

}
