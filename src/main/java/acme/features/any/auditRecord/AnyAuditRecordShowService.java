
package acme.features.any.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.auditRecords.AuditRecord;

@Service
public class AnyAuditRecordShowService extends AbstractService<Any, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		AuditRecord auditRecord;

		id = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findOneAuditRecordById(id);

		status = auditRecord != null && !auditRecord.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditRecordById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "auditStartTime", "auditEndTime", "mark", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
