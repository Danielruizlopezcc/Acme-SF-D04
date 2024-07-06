
package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeAudits.CodeAudits;
import acme.entities.codeAudits.CodeAuditsType;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditsUpdateService extends AbstractService<Auditor, CodeAudits> {

	@Autowired
	AuditorCodeAuditsRepository repository;


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

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudits existing;

			existing = this.repository.findOneCodeAuditsByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "auditor.code-audits.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("excutionDate"))
			super.state(MomentHelper.isPast(object.getExecutionDate()), "executionDate", "auditor.code-audits.form.error.execution-date-not-valid");

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode(), "draftMode", "validation.code-audits.published");
	}

	@Override
	public void perform(final CodeAudits object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudits object) {
		assert object != null;
		SelectChoices choices;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Dataset dataset;
		choices = SelectChoices.from(CodeAuditsType.class, object.getType());
		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link", "project");
		dataset.put("codeAuditsType", choices);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);

	}
}
