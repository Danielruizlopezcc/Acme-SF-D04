
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
public class AuditorAuditRecordsShowService extends AbstractService<Auditor, AuditRecords> {

	@Autowired
	AuditorAuditRecordsRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int auditRecordsId;
		CodeAudits codeAudits;

		auditRecordsId = super.getRequest().getData("id", int.class);
		codeAudits = this.repository.findOneCodeAuditsByAuditRecordsId(auditRecordsId);
		status = codeAudits != null && (!codeAudits.isDraftMode() || super.getRequest().getPrincipal().hasRole(codeAudits.getAuditor()));

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
		dataset.put("masterId", object.getCodeAudits().getId());
		dataset.put("mark", marks);

		super.getResponse().addData(dataset);
	}

}
