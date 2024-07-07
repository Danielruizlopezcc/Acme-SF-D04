
package acme.features.auditor.auditRecords;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecords;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudits;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordsDeleteService extends AbstractService<Auditor, AuditRecords> {

	@Autowired
	private AuditorAuditRecordsRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int auditRecordsId;
		CodeAudits module;

		auditRecordsId = super.getRequest().getData("id", int.class);
		module = this.repository.findOneCodeAuditsByAuditRecordsId(auditRecordsId);
		status = module != null && module.isDraftMode() && super.getRequest().getPrincipal().hasRole(module.getAuditor());

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
		;
	}

	@Override
	public void validate(final AuditRecords object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.getDraftMode(), "draftMode", "validation.audit-records.draftMode");
	}

	@Override
	public void perform(final AuditRecords object) {
		assert object != null;

		this.repository.delete(object);
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
		dataset.put("masterId", object.getCodeAudits().getId());
		dataset.put("codAudit", codeAudits.getSelected().getKey());
		dataset.put("codeaudits", codeAudits);
		dataset.put("mark", marks);

		super.getResponse().addData(dataset);
	}

}
