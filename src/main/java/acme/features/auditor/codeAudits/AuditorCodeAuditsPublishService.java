
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

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "link", "project");
		object.setProject(project);

	}

	@Override
	public void validate(final CodeAudits object) {
		assert object != null;

		String markMode;

		Collection<Mark> marks = this.repository.findMarksByCodeAuditsId(object.getId());
		markMode = MarkMode.calculate(marks);

		if (!super.getBuffer().getErrors().hasErrors("markMode"))
			if (markMode != null) {
				boolean isMarkAtLeastC = markMode.equals("C") || markMode.equals("B") || markMode.equals("A") || markMode.equals("A_PLUS");
				super.state(isMarkAtLeastC, "markMode", "validation.code-audits.mark.minimun");
			} else
				super.state(false, "markMode", "validation.code-audits.mark.minimun");

		Collection<AuditRecords> auditRecords;

		auditRecords = this.repository.findAllAuditRecordsByCodeAuditsId(object.getId());

		super.state(auditRecords.stream().noneMatch(AuditRecords::getDraftMode), "*", "auditor.code-audits.error.draftMode");

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
		String markMode;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Collection<Mark> marks = this.repository.findMarksByCodeAuditsId(object.getId());
		markMode = MarkMode.calculate(marks);

		Dataset dataset;
		choices = SelectChoices.from(CodeAuditsType.class, object.getType());
		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link", "project");
		dataset.put("codeAuditsType", choices);
		dataset.put("markMode", markMode);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);

	}
}
