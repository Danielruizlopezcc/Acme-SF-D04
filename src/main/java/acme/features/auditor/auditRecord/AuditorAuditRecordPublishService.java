
package acme.features.auditor.auditRecord;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.auditRecords.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordPublishService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int auditorId;
		AuditRecord auditRecord;

		id = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findOneAuditRecordById(id);

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();

		status = auditorId == auditRecord.getAuditor().getId() && auditRecord.isDraftMode();

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
	public void bind(final AuditRecord object) {
		assert object != null;

		super.bind(object, "code", "auditStartTime", "auditEndTime", "mark", "link", "project");
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {

			AuditRecord auditRecordSameCode = this.repository.findOneAuditRecordByCode(object.getCode());

			if (auditRecordSameCode != null)
				super.state(auditRecordSameCode.getId() == object.getId(), "code", "auditor.audit-record.form.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("auditEndTime")) {
			Date auditStartTime;
			Date auditEndTime;

			auditStartTime = object.getAuditStartTime();
			auditEndTime = object.getAuditEndTime();

			if (auditStartTime != null && auditEndTime != null)
				super.state(MomentHelper.isLongEnough(auditStartTime, auditEndTime, 1, ChronoUnit.HOURS) && auditEndTime.after(auditStartTime), "auditEndTime", "auditor.audit-record.form.error.audit-end-time");
		}

		if (!super.getBuffer().getErrors().hasErrors("publishedCodeAudit"))
			super.state(object.getCodeAudit().isDraftMode(), "*", "auditor.audit-record.form.error.published-code-audit");

		if (!super.getBuffer().getErrors().hasErrors("auditStartTime")) {

			Date auditRecordDate = object.getAuditStartTime();
			Date minimumDate = object.getCodeAudit().getExecution();

			Boolean isAfter = auditRecordDate.after(minimumDate);
			super.state(isAfter, "auditStartTime", "auditor.audit-record.form.error.auditStartTime");
		}
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choices;

		choices = SelectChoices.from(Mark.class, object.getMark());

		dataset = super.unbind(object, "code", "auditStartTime", "auditEndTime", "mark", "link", "draftMode");
		dataset.put("marks", choices);

		super.getResponse().addData(dataset);
	}
}
