
package acme.features.auditor.auditRecords;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.auditRecords.AuditRecords;
import acme.entities.codeAudits.CodeAudits;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordsListService extends AbstractService<Auditor, AuditRecords> {

	@Autowired
	AuditorAuditRecordsRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudits codeAudits;

		masterId = super.getRequest().getData("masterId", int.class);
		codeAudits = this.repository.findOneCodeAuditsById(masterId);
		status = codeAudits != null && (!codeAudits.isDraftMode() || super.getRequest().getPrincipal().hasRole(codeAudits.getAuditor()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditRecords> auditRecords;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		auditRecords = this.repository.findAllAuditRecordsByMasterId(masterId);

		super.getBuffer().addData(auditRecords);
	}

	@Override
	public void unbind(final AuditRecords object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "mark", "link", "draftMode");

		if (object.getDraftMode()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<AuditRecords> objects) {
		assert objects != null;

		int masterId;
		CodeAudits codeAudits;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		codeAudits = this.repository.findOneCodeAuditsById(masterId);
		showCreate = codeAudits.isDraftMode() && super.getRequest().getPrincipal().hasRole(codeAudits.getAuditor());

		super.getResponse().addGlobal("masterId", masterId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
