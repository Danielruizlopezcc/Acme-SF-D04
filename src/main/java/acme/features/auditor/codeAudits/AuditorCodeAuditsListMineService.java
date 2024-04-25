
package acme.features.auditor.codeAudits;

import java.util.Collection;
import java.util.Locale;

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
public class AuditorCodeAuditsListMineService extends AbstractService<Auditor, CodeAudits> {

	@Autowired
	AuditorCodeAuditsRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudits> codeAudits;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		codeAudits = this.repository.findAllCodeAuditsByAuditorId(principal.getActiveRoleId());

		super.getBuffer().addData(codeAudits);
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
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "mark", "link", "draftMode", "project", "auditor");

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		dataset.put("codeAuditType", choices);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);
	}

}
