
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
public class DeveloperTrainingSessionPublishService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int sessionId;
		TrainingModule module;

		sessionId = super.getRequest().getData("id", int.class);
		module = this.repository.findOneTMByTSId(sessionId);
		status = module != null && module.isDraftMode() && super.getRequest().getPrincipal().hasRole(module.getDeveloper());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession session;
		int id;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findOneTSById(id);

		super.getBuffer().addData(session);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "sessionStart", "sessionEnd", "location", "instructor", "contactEmail", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findOneTSByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-session.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("sessionStart")) {
			TrainingModule module;
			int id;

			id = super.getRequest().getData("id", int.class);
			module = this.repository.findOneTMByTSId(id);
			super.state(MomentHelper.isAfter(object.getSessionStart(), module.getCreationMoment()), "sessionStart", "developer.training-session.form.error.creation-moment-invalid");
		}

		if (!super.getBuffer().getErrors().hasErrors("sessionEnd")) {
			Date minimumEnd;

			minimumEnd = MomentHelper.deltaFromMoment(object.getSessionStart(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getSessionEnd(), minimumEnd), "sessionEnd", "developer.training-session.form.error.too-close");
		}

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "sessionStart", "sessionEnd", "location", "instructor", "contactEmail", "link", "draftMode");

		super.getResponse().addData(dataset);
	}
}
