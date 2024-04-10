
package acme.features.client.contracts;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.contract.Contract;
import acme.roles.clients.Client;

@Controller
public class ClientContractController extends AbstractController<Client, Contract> {

	@Autowired
	private ClientContractListMineService	listMineService;
	@Autowired
	private ClientContractShowService		showService;
	@Autowired
	private ClientContractDeleteService		deleteService;
	@Autowired
	private ClientContractCreateService		createService;
	@Autowired
	private ClientContractUpdateService		updateService;
	@Autowired
	private ClientContractPublishService	publishService;


	@PostConstruct
	protected void initialise() {

		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);

		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);

	}

}
