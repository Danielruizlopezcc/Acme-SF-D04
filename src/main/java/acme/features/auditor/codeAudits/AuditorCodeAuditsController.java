
package acme.features.auditor.codeAudits;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.codeAudits.CodeAudits;
import acme.roles.Auditor;

@Controller
public class AuditorCodeAuditsController extends AbstractController<Auditor, CodeAudits> {

	@Autowired
	private AuditorCodeAuditsListMineService	listMineService;
	@Autowired
	private AuditorCodeAuditsShowService		showService;
	@Autowired
	private AuditorCodeAuditsDeleteService		deleteService;
	@Autowired
	private AuditorCodeAuditsCreateService		createService;
	@Autowired
	private AuditorCodeAuditsUpdateService		updateService;
	@Autowired
	private AuditorCodeAuditsPublishService		publishService;


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
