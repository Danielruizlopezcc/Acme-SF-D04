
package acme.features.client.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.ClientDashboard;
import acme.roles.clients.Client;

@Controller
public class ClientDashboardController extends AbstractController<Client, ClientDashboard> {

	@Autowired
	private ClientDashboardShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}

}
