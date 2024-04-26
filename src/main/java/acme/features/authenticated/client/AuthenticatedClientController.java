
package acme.features.authenticated.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.roles.clients.Client;

@Controller
public class AuthenticatedClientController extends AbstractController<Authenticated, Client> {

	@Autowired
	private AuthenticatedClientCreateService	createService;
	@Autowired
	private AuthenticatedClientUpdateService	updateService;


	@PostConstruct
	protected void intialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
	}

}
