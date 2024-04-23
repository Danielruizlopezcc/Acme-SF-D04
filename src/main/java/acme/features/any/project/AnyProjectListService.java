
package acme.features.any.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;

@Service
public class AnyProjectListService extends AbstractService<Any, Project> {

	@Autowired
	private AnyProjectRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Project> publishedProjects;

		publishedProjects = this.repository.findAllPublishedProjects();

		super.getBuffer().addData(publishedProjects);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "code", "title", "abstractProject", "indication", "cost", "link");

		payload = String.format(//
			"%s;", //
			object.getManager().getDegree());
		dataset.put(payload, payload);

		super.getResponse().addData(dataset);
	}

}
