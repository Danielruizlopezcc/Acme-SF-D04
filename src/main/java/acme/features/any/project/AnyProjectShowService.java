
package acme.features.any.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;

@Service
public class AnyProjectShowService extends AbstractService<Any, Project> {

	@Autowired
	private AnyProjectRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int id;
		Project project;

		id = super.getRequest().getData("id", int.class);
		project = this.repository.findOneProjectById(id);

		status = project != null && !project.isDraftMode();

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

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstractProject", "indication", "cost", "link");

		super.getResponse().addData(dataset);
	}

}
