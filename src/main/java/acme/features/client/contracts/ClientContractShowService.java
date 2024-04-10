
package acme.features.client.contracts;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.project.Project;
import acme.roles.clients.Client;

@Service
public class ClientContractShowService extends AbstractService<Client, Contract> {

	@Autowired
	ClientContractRepository repository;


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		Contract c;
		Client client;

		masterId = super.getRequest().getData("id", int.class);
		c = this.repository.findOneContractById(masterId);
		client = c == null ? null : c.getClient();
		status = c != null && c.isDraftMode() || super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneContractById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		int contractId;
		Collection<Project> projects;
		SelectChoices choices;

		if (object.isDraftMode())
			projects = this.repository.findAllProjects();
		else {
			contractId = super.getRequest().getData("id", int.class);
			projects = this.repository.findOneProjectByContractId(contractId);

		}

		choices = SelectChoices.from(projects, "code", object.getProject());

		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
