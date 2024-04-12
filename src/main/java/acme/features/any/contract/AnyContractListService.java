
package acme.features.any.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;

@Service
public class AnyContractListService extends AbstractService<Any, Contract> {

	@Autowired
	private AnyContractRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Contract> publishedContracts;

		publishedContracts = this.repository.findAllPublishedContracts();

		super.getBuffer().addData(publishedContracts);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "project.code");

		payload = String.format(//
			"%s;", //
			object.getClient().getIdentification());
		dataset.put(payload, payload);

		super.getResponse().addData(dataset);
	}

}
