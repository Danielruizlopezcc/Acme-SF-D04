
package acme.features.client.progressLogs;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.progressLogs.ProgressLog;
import acme.roles.clients.Client;

@Controller
public class ClientProgressLogController extends AbstractController<Client, ProgressLog> {

	@Autowired
	private ClientProgressLogListService	listService;
	@Autowired
	private ClientProgressLogShowService	showService;
	@Autowired
	private ClientProgressLogUpdateService	updateService;
	@Autowired
	private ClientProgressLogDeleteService	deleteService;
	@Autowired
	private ClientProgressLogPublishService	publishService;
	@Autowired
	private ClientProgressLogCreateService	createService;


	@PostConstruct
	protected void intialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("create", this.createService);

		super.addCustomCommand("publish", "update", this.publishService);
	}

}
