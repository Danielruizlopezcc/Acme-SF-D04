
package acme.features.authenticated.sponsor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.roles.Sponsor;

@Controller
public class AuthenticatedSponsorController extends AbstractController<Authenticated, Sponsor> {

	@Autowired
	private AuthenticatedSponsorCreateService	createService;

	@Autowired
	private AuthenticatedSponsorUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
	}

}
