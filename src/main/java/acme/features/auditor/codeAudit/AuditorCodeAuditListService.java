
package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditListService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;
		int auditorId;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();

		objects = this.repository.findCodeAuditsByAuditorId(auditorId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;
		List<Mark> marks;

		marks = this.repository.findMarksByCodeAuditId(object.getId()).stream().toList();

		dataset = super.unbind(object, "code", "execution", "type", "proposedCorrectiveActions", "link");
		dataset.put("mark", this.repository.averageMark(marks));
		dataset.put("project", object.getProject().getCode());

		super.getResponse().addData(dataset);
	}
}
