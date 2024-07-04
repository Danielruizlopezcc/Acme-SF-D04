
package acme.features.manager.projects;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectShowService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;
		Manager manager1;
		Manager manager2;
		int managerId;

		projectId = super.getRequest().getData("id", int.class);
		project = this.repository.findOneProjectById(projectId);

		Principal principal = super.getRequest().getPrincipal();
		managerId = principal.getActiveRoleId();
		manager2 = this.repository.findOneManagerById(managerId);

		manager1 = project == null ? null : project.getManager();
		status = project != null && manager1.equals(manager2);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;
		int id = super.getRequest().getData("id", int.class);

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstractProject", "indication", "cost", "link", "draftMode");

		if (object.isIndication()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("indication", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("indication", "No");

		super.getResponse().addGlobal("masterId", id);
		super.getResponse().addData(dataset);

	}

}
