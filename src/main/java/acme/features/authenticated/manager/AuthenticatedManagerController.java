
package acme.features.authenticated.manager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.roles.Manager;

@Controller
public class AuthenticatedManagerController extends AbstractController<Authenticated, Manager> {

	@Autowired
	private AuthenticatedManagerCreateService	createService;

	@Autowired
	private AuthenticatedManagerUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
	}

}
