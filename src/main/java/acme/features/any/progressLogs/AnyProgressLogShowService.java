
package acme.features.any.progressLogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.progressLogs.ProgressLog;

@Service
public class AnyProgressLogShowService extends AbstractService<Any, ProgressLog> {

	@Autowired
	private AnyProgressLogRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int masterId;
		Contract contract;

		masterId = super.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractByProgressLogId(masterId);

		status = contract != null && !contract.isDraftMode();

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
	public void unbind(final ProgressLog object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");

		super.getResponse().addData(dataset);
	}

}
