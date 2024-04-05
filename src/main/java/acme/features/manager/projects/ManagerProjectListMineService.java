
package acme.features.manager.projects;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectListMineService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Project> modules;
		Principal principal;
		String details;

		principal = super.getRequest().getPrincipal();
		modules = this.repository.findAllProjectsByManagerId(principal.getActiveRoleId());

		super.getBuffer().addData(modules);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstractProject", "indication", "cost", "link");

		super.getResponse().addData(dataset);
	}

}
