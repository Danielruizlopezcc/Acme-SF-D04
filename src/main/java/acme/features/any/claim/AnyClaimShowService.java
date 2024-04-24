
package acme.features.any.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.claim.Claim;

@Service
public class AnyClaimShowService extends AbstractService<Any, Claim> {

	@Autowired
	protected AnyClaimRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Claim claim = this.repository.findClaimById(id);

		super.getBuffer().addData(claim);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;

		final Dataset dataset = super.unbind(object, "code", "instantiationMoment", "heading", "description", "department", "emailAddress", "link");

		super.getResponse().addData(dataset);
	}

}
