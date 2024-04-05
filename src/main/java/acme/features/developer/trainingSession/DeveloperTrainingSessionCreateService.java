
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule trainingModule;

		masterId = super.getRequest().getData("masterId", int.class);
		trainingModule = this.repository.findOneTMById(masterId);
		status = trainingModule != null && trainingModule.isDraftMode() && super.getRequest().getPrincipal().hasRole(trainingModule.getDeveloper());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession session;
		int masterId;
		TrainingModule module;

		masterId = super.getRequest().getData("master", int.class);
		module = this.repository.findOneTMById(masterId);

		session = new TrainingSession();
		session.setTrainingModule(module);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "creationMoment", "sessionStart", "sessionEnd", "location", "instructor", "contactEmail", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findOneTSByCode(object.getCode());
			super.state(existing == null, "code", "developer.training-session.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("sessionStart")) {
			TrainingModule module;
			Date minimumStart;

			module = this.repository.findOneTMById(super.getRequest().getData("masterId", int.class));
			minimumStart = MomentHelper.deltaFromMoment(object.getSessionStart(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(module.getCreationMoment(), minimumStart), "session-start", "developer.training-session.form.error.too-close");
		}

		if (!super.getBuffer().getErrors().hasErrors("sessionEnd")) {
			Date minimumEnd;

			minimumEnd = MomentHelper.deltaFromMoment(object.getSessionStart(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getSessionEnd(), minimumEnd), "session-end", "developer.training-session.form.error.too-short");
		}

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "creationMoment", "sessionStart", "sessionEnd", "location", "instructor", "contactEmail", "link");
		dataset.put("masterId", object.getTrainingModule().getId());
		dataset.put("draftMode", object.getTrainingModule().isDraftMode());

		super.getResponse().addData(dataset);
	}

}
