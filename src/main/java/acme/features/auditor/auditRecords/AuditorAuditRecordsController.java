
package acme.features.auditor.auditRecords;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.auditRecords.AuditRecords;
import acme.roles.Auditor;

@Controller
public class AuditorAuditRecordsController extends AbstractController<Auditor, AuditRecords> {

	@Autowired
	private AuditorAuditRecordsCreateService	createService;

	@Autowired
	private AuditorAuditRecordsDeleteService	deleteService;

	@Autowired
	private AuditorAuditRecordsUpdateService	updateService;

	@Autowired
	private AuditorAuditRecordsListService		listService;

	@Autowired
	private AuditorAuditRecordsPublishService	publishService;

	@Autowired
	private AuditorAuditRecordsShowService		showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("list", this.listService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addBasicCommand("show", this.showService);
	}
}
