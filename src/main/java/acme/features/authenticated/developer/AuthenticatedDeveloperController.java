
package acme.features.authenticated.developer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.roles.Developer;

@Controller
public class AuthenticatedDeveloperController extends AbstractController<Authenticated, Developer> {

	@Autowired
	private AuthenticatedDeveloperCreateService	createService;

	@Autowired
	private AuthenticatedDeveloperUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
	}

}
