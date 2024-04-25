
package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecords;
import acme.entities.codeAudits.CodeAudits;
import acme.entities.codeAudits.CodeAuditsType;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditsDeleteService extends AbstractService<Auditor, CodeAudits> {

	@Autowired
	private AuditorCodeAuditsRepository repository;


	@Override
	public void authorise() {
		boolean status;
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

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "mark", "link", "draftMode", "project", "auditor");

	}

	@Override
	public void validate(final CodeAudits object) {
		assert object != null;
	}

	@Override
	public void perform(final CodeAudits object) {
		assert object != null;

		Collection<AuditRecords> auditRecords;

		auditRecords = this.repository.findAllAuditRecordsByCodeAuditsId(object.getId());
		this.repository.deleteAll(auditRecords);
		this.repository.delete(object);
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
