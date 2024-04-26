
package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecords;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudits;
import acme.entities.codeAudits.CodeAuditsType;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditsPublishService extends AbstractService<Auditor, CodeAudits> {

	@Autowired
	private AuditorCodeAuditsRepository repository;


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		CodeAudits codeAudits;
		Auditor auditor;

		masterId = super.getRequest().getData("id", int.class);
		codeAudits = this.repository.findOneCodeAuditsById(masterId);
		auditor = codeAudits == null ? null : codeAudits.getAuditor();
		status = codeAudits != null && codeAudits.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudits object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditsById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudits object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "mark", "link", "project");
		object.setProject(project);

	}

	@Override
	public void validate(final CodeAudits object) {
		assert object != null;

		Collection<String> allCodes = this.repository.findAllCodeAudits();
		boolean isCodeChanged = false;
		int id = super.getRequest().getData("id", int.class);
		CodeAudits codeAudit = this.repository.findOneCodeAuditsById(id);
		Collection<AuditRecords> auditRecords = this.repository.findAllAuditRecordsByCodeAuditsId(object.getId());

		for (AuditRecords ar : auditRecords)
			super.state(!ar.getDraftMode(), "code", "auditor.codeaudit.error.draftMode");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			isCodeChanged = !object.getCode().equals(codeAudit.getCode());
			super.state(!isCodeChanged || !allCodes.contains(object.getCode()), "code", "auditor.codeaudit.error.duplicated-code");
		}
		if (!super.getBuffer().getErrors().hasErrors("mark")) {
			Mark mark = object.getMark();
			super.state(mark == Mark.A || mark == Mark.A_PLUS || mark == Mark.B || mark == Mark.C, "mark", "validation.codeAudit.mark.minimun");
		}
	}

	@Override
	public void perform(final CodeAudits object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudits object) {
		assert object != null;
		SelectChoices choices;
		SelectChoices marks;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Dataset dataset;
		choices = SelectChoices.from(CodeAuditsType.class, object.getType());
		marks = SelectChoices.from(Mark.class, object.getMark());
		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "mark", "link", "project");
		dataset.put("codeAuditsType", choices);
		dataset.put("mark", marks);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);

	}
}
