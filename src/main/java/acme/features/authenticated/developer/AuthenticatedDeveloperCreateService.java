
package acme.features.authenticated.developer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.accounts.UserAccount;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.roles.Developer;

@Service
public class AuthenticatedDeveloperCreateService extends AbstractService<Authenticated, Developer> {

	@Autowired
	private AuthenticatedDeveloperRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRole(Developer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Developer object;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		object = new Developer();
		object.setUserAccount(userAccount);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Developer object) {
		assert object != null;

		super.bind(object, "degree", "specialisation", "skills", "email", "link");
	}

	@Override
	public void validate(final Developer object) {
		assert object != null;
	}

	@Override
	public void perform(final Developer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Developer object) {
		Dataset dataset;

		dataset = super.unbind(object, "degree", "specialisation", "skills", "email", "link");

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
