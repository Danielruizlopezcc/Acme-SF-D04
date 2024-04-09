
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
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	@Autowired
	ClientContractRepository clientContractRepository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;
		Client client;

		masterId = super.getRequest().getData("id", int.class);
		contract = this.clientContractRepository.findOneContractById(masterId);
		client = contract == null ? null : contract.getClient();
		status = contract != null && contract.isDraftMode() && super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract contract;
		int id;

		id = super.getRequest().getData("id", int.class);

		contract = this.clientContractRepository.findOneContractById(id);

		super.getBuffer().addData(contract);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.clientContractRepository.findOneProjectById(projectId);

		super.bind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");
		object.setProject(project);
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		//tenemos que validar que la suma de los proyects no lo supere!!! :)))

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.clientContractRepository.findOneContractByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "client.contract.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget"))
			super.state(this.checkContractsAmountsLessThanProjectCost(object), "budget", "client.contract.form.error.excededBudget");
	}

	private Boolean checkContractsAmountsLessThanProjectCost(final Contract object) {

		Collection<Contract> contratos = this.clientContractRepository.findManyContractByProjectId(object.getProject().getId());

		Double budgetTotal = contratos.stream().filter(contract -> contract.isDraftMode() == false).mapToDouble(contract -> contract.getBudget().getAmount()).sum();

		//Integer suma = sumContractsBudgetByProjectId(obect.project.getId)
		Double projectCost = object.getProject().getCost().getAmount();
		// Project project = repository.findProjectbyId(object.project())

		//return project.cost >= suma;

		return projectCost >= budgetTotal + object.getBudget().getAmount();
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;
		object.setDraftMode(false);
		this.clientContractRepository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;

		projects = this.clientContractRepository.findAllProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());

		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
