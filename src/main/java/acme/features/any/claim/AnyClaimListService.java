
package acme.features.any.claim;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.claim.Claim;

@Service
public class AnyClaimListService extends AbstractService<Any, Claim> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyClaimRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Claim> claims = this.repository.findAllClaims();

		super.getBuffer().addData(claims);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;

		final Dataset dataset = super.unbind(object, "code", "heading");

		super.getResponse().addData(dataset);
	}

}
