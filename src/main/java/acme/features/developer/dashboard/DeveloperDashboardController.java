
package acme.features.developer.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Controller
public class DeveloperDashboardController extends AbstractController<Developer, DeveloperDashboard> {

	@Autowired
	private DeveloperDashboardShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}

}
