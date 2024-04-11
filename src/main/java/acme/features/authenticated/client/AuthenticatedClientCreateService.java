
package acme.features.authenticated.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.accounts.UserAccount;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.roles.clients.Client;
import acme.roles.clients.ClientRoleType;

@Service
public class AuthenticatedClientCreateService extends AbstractService<Authenticated, Client> {

	@Autowired
	private AuthenticatedClientRepository repository;


	@Override
	public void authorise() {

		boolean status;

		status = !this.getRequest().getPrincipal().hasRole(Client.class);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {

		Client object;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = this.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		object = new Client();
		object.setUserAccount(userAccount);

		super.getBuffer().addData(object);

	}

	@Override
	public void bind(final Client object) {

		assert object != null;

		super.bind(object, "identification", "companyName", "type", "email", "link");

	}

	@Override
	public void validate(final Client object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("identification")) {
			Client existing;

			existing = this.repository.findOneClientByIdentification(object.getIdentification());
			super.state(existing == null, "identification", "authenticated.client.form.error.duplicated");
		}
	}

	@Override
	public void perform(final Client object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Client object) {
		assert object != null;

		//Collection<ClientRoleType> types;
		SelectChoices choices;

		choices = SelectChoices.from(ClientRoleType.class, object.getType());
		Dataset dataset;

		dataset = super.unbind(object, "identification", "companyName", "type", "email");
		dataset.put("type", choices.getSelected().getKey());
		dataset.put("types", choices);

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
