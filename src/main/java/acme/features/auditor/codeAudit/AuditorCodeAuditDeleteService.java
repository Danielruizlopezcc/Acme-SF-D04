
package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.codeAudits.CodeAuditType;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditDeleteService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int auditorId;
		CodeAudit codeAudit;

		id = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(id);

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();

		status = auditorId == codeAudit.getAuditor().getId() && codeAudit.isDraftMode();

		super.getResponse().setAuthorised(status);
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
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "execution", "type", "proposedCorrectiveActions", "link", "project");
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		Collection<AuditRecord> publishedAuditRecord;

		publishedAuditRecord = this.repository.findPublishedAuditRecordsByCodeAuditId(object.getId());
		super.state(publishedAuditRecord.isEmpty(), "*", "auditor.code-audit.form.error.published-audit-records");

	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		Collection<AuditRecord> relations = this.repository.findRelationsByCodeAuditId(object.getId());

		this.repository.deleteAll(relations);

		this.repository.delete(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choices;
		Collection<Project> projects = this.repository.findPublishedProjects();
		SelectChoices choices2;
		List<Mark> marks;
		String projectCode;

		projectCode = object.getProject() != null ? object.getProject().getCode() : null;

		Project project = object.getProject() != null ? object.getProject() : (Project) projects.toArray()[0];

		marks = this.repository.findMarksByCodeAuditId(object.getId()).stream().toList();

		choices = SelectChoices.from(CodeAuditType.class, object.getType());
		choices2 = SelectChoices.from(projects, "code", project);

		dataset = super.unbind(object, "code", "execution", "type", "proposedCorrectiveActions", "link", "project", "draftMode");
		dataset.put("mark", this.repository.averageMark(marks));
		dataset.put("types", choices);
		dataset.put("projects", choices2);
		dataset.put("project", projectCode);

		super.getResponse().addData(dataset);
	}
}
