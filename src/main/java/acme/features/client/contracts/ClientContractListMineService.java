
package acme.features.client.contracts;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.roles.clients.Client;

@Service
public class ClientContractListMineService extends AbstractService<Client, Contract> {

	@Autowired
	ClientContractRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Contract> contracts;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		contracts = this.repository.findAllContractsByClientId(principal.getActiveRoleId());

		super.getBuffer().addData(contracts);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "project.code", "draftMode");

		super.getResponse().addData(dataset);
	}

}
