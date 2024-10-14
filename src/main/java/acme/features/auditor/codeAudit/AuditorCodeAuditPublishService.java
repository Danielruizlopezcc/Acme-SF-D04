
package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.codeAudits.CodeAuditType;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {

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

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(!object.getProject().isDraftMode(), "project", "auditor.code-audit.form.error.project");

		if (!super.getBuffer().getErrors().hasErrors("code")) {

			CodeAudit codeAuditSameCode = this.repository.findOneCodeAuditByCode(object.getCode());

			if (codeAuditSameCode != null)
				super.state(codeAuditSameCode.getId() == object.getId(), "code", "auditor.code-audit.form.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("mark")) {
			List<Mark> marks;

			marks = this.repository.findMarksByCodeAuditId(object.getId()).stream().toList();
			Mark nota = this.repository.averageMark(marks);

			super.state(nota == Mark.C || nota == Mark.B || nota == Mark.A || nota == Mark.AA, "mark", "auditor.code-audit.form.error.mark");
		}

		if (!super.getBuffer().getErrors().hasErrors("unpublishedAuditRecords")) {

			Collection<AuditRecord> unpublishedAuditRecords;

			unpublishedAuditRecords = this.repository.findUnpublishedAuditRecordsByCodeAuditId(object.getId());

			super.state(unpublishedAuditRecords.isEmpty(), "*", "auditor.code-audit.form.error.unpublished-audit-records");
		}

		if (!super.getBuffer().getErrors().hasErrors("execution")) {

			Date codeAuditDate = object.getExecution();
			Date minimumDate = MomentHelper.parse("1999-12-31 23:59", "yyyy-MM-dd HH:mm");

			Boolean isAfter = codeAuditDate.after(minimumDate);
			super.state(isAfter, "execution", "auditor.code-audit.form.error.execution");
		}

		if (!super.getBuffer().getErrors().hasErrors("execution")) {
			AuditRecord earliestAuditRecord;
			Boolean validExecution;
			Date execution = object.getExecution();

			earliestAuditRecord = this.repository.findAuditRecordWithEarliestDateByCodeAuditId(object.getId()).stream().findFirst().orElse(null);

			if (earliestAuditRecord != null) {
				validExecution = execution.before(earliestAuditRecord.getAuditStartTime());
				super.state(validExecution, "execution", "auditor.code-audit.form.error.execution-ar");
			}
		}
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
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
