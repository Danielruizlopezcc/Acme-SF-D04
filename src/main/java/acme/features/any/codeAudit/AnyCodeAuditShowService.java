
package acme.features.any.codeAudit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudit;

@Service
public class AnyCodeAuditShowService extends AbstractService<Any, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int id;
		CodeAudit codeAudit;

		id = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(id);

		super.getResponse().setAuthorised(!codeAudit.isDraftMode());
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;
		List<Mark> marks;
		String projectCode;

		projectCode = object.getProject() != null ? object.getProject().getCode() : null;

		marks = this.repository.findMarksByCodeAuditId(object.getId()).stream().toList();

		dataset = super.unbind(object, "code", "execution", "type", "proposedCorrectiveActions", "link", "project", "draftMode");
		dataset.put("mark", this.repository.averageMark(marks));
		dataset.put("project", projectCode);

		super.getResponse().addData(dataset);
	}

}
