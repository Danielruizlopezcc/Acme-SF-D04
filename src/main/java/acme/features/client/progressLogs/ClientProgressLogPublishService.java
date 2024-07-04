
package acme.features.client.progressLogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.progressLogs.ProgressLog;
import acme.roles.clients.Client;

@Service
public class ClientProgressLogPublishService extends AbstractService<Client, ProgressLog> {

	@Autowired
	private ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int progressLogId;
		Contract contract;

		progressLogId = super.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractByProgressLogId(progressLogId);
		ProgressLog pl = this.repository.findOneProgressLogById(progressLogId);

		int activeClientId = super.getRequest().getPrincipal().getActiveRoleId();
		Client activeClient = this.repository.findOneClientById(activeClientId);
		boolean clientOwnsPl = pl.getContract().getClient() == activeClient;

		status = pl.isDraftMode() && clientOwnsPl && contract != null && !contract.isDraftMode() && super.getRequest().getPrincipal().hasRole(contract.getClient());

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {

		ProgressLog object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProgressLogById(id);

		super.getBuffer().addData(object);

	}

	@Override
	public void bind(final ProgressLog object) {

		assert object != null;

		int progressLogId;

		progressLogId = super.getRequest().getData("id", int.class);
		Contract contract = this.repository.findOneContractByProgressLogId(progressLogId);

		super.bind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");
		object.setContract(contract);
	}

	@Override
	public void validate(final ProgressLog object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("recordId")) {
			ProgressLog existing;

			existing = this.repository.findOneProgressLogByRecordId(object.getRecordId());
			super.state(existing == null || existing.equals(object), "recordId", "client.progress-log.form.error.duplicated");

			if (!super.getBuffer().getErrors().hasErrors("completeness")) {
				Double existing2;
				existing2 = this.repository.findPublishedProgressLogWithMaxCompletenessPublished(object.getContract().getId()).orElse(0.);
				super.state(object.getCompleteness() > existing2, "completeness", "client.progress-log.form.error.completeness-too-low");
			}
			if (!super.getBuffer().getErrors().hasErrors("registrationMoment"))
				super.state(object.getRegistrationMoment().after(object.getContract().getInstantiationMoment()), "registrationMoment", "client.progress-log.form.error.registration-moment-must-be-later");

		}
	}

	@Override
	public void perform(final ProgressLog object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");

		dataset.put("masterId", object.getContract().getId());
		dataset.put("draftMode", object.isDraftMode());

		super.getResponse().addData(dataset);
	}

}
