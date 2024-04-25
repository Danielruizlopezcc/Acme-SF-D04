
package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeAudits.CodeAudits;
import acme.entities.codeAudits.CodeAuditsType;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditsCreateService extends AbstractService<Auditor, CodeAudits> {

	@Autowired
	AuditorCodeAuditsRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CodeAudits object;
		Auditor auditor;

		Principal principal = super.getRequest().getPrincipal();
		auditor = this.repository.findOneAuditorById(principal.getActiveRoleId());
		object = new CodeAudits();
		object.setDraftMode(true);
		object.setAuditor(auditor);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudits object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "mark", "link", "draftMode", "project", "auditor");

		object.setProject(project);

	}

	@Override
	public void validate(final CodeAudits object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudits existing;

			existing = this.repository.findOneCodeAuditsByCode(object.getCode());
			super.state(existing == null, "code", "auditor.code-audits.form.error.duplicated");
		}

	}

	@Override
	public void perform(final CodeAudits object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudits object) {
		assert object != null;
		SelectChoices types;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Dataset dataset;

		types = SelectChoices.from(CodeAuditsType.class, object.getType());
		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "mark", "link", "draftMode", "project", "auditor");

		dataset.put("type", types);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);

		super.getResponse().addData(dataset);

	}

}
