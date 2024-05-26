
package acme.features.auditor.auditRecords;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecords;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudits;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordsPublishService extends AbstractService<Auditor, AuditRecords> {

	@Autowired
	AuditorAuditRecordsRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int auditRecordsId;
		CodeAudits codeAudits;

		auditRecordsId = super.getRequest().getData("id", int.class);
		codeAudits = this.repository.findOneCodeAuditsByAuditRecordsId(auditRecordsId);
		status = codeAudits != null && codeAudits.isDraftMode() && super.getRequest().getPrincipal().hasRole(codeAudits.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecords auditRecords;
		int id;

		id = super.getRequest().getData("id", int.class);
		auditRecords = this.repository.findOneAuditRecordsById(id);

		super.getBuffer().addData(auditRecords);
	}

	@Override
	public void bind(final AuditRecords object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "endPeriod", "mark", "link");
	}

	@Override
	public void validate(final AuditRecords object) {
		Collection<String> allCodes = this.repository.findAllCodeAuditsCode();
		int id = super.getRequest().getData("id", int.class);
		AuditRecords auditRecord = this.repository.findOneAuditRecordsById(id);
		boolean isCodeChanged = false;

		if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
			if (object.getEndPeriod() != null && object.getStartPeriod() != null)
				super.state(MomentHelper.isAfter(object.getEndPeriod(), object.getStartPeriod()), "startPeriod", "validation.audit-records.initialIsBefore");
		if (!super.getBuffer().getErrors().hasErrors("endPeriod"))
			if (object.getEndPeriod() != null && object.getStartPeriod() != null) {
				Date end;
				end = MomentHelper.deltaFromMoment(object.getStartPeriod(), 1, ChronoUnit.HOURS);
				super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), end), "endPeriod", "validation.audit-records.moment.minimun");
			}

		if (!super.getBuffer().getErrors().hasErrors("code"))
			isCodeChanged = !object.getCode().equals(auditRecord.getCode());
		super.state(!isCodeChanged || !allCodes.contains(object.getCode()), "code", "validation.audit-records.code.duplicate");

	}

	@Override
	public void perform(final AuditRecords object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecords object) {

		assert object != null;

		SelectChoices marks;
		SelectChoices codeAudits;
		Dataset dataset;

		Collection<CodeAudits> allCodeAudits = this.repository.findAllCodeAudits();
		codeAudits = SelectChoices.from(allCodeAudits, "code", object.getCodeAudits());
		marks = SelectChoices.from(Mark.class, object.getMark());
		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "mark", "link", "draftMode");
		dataset.put("codAudit", codeAudits.getSelected().getKey());
		dataset.put("codeaudits", codeAudits);
		dataset.put("mark", marks);

		super.getResponse().addData(dataset);
	}

}
