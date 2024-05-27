
package acme.features.auditor.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Controller
public class AuditorDashboardController extends AbstractController<Auditor, AuditorDashboard> {

	@Autowired
	private AuditorDashboardShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}
}
