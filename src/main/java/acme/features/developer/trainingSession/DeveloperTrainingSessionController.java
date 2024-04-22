
package acme.features.developer.trainingSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingSessionController extends AbstractController<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionCreateService	createService;

	@Autowired
	private DeveloperTrainingSessionDeleteService	deleteService;

	@Autowired
	private DeveloperTrainingSessionUpdateService	updateService;

	@Autowired
	private DeveloperTrainingSessionListService		listService;

	@Autowired
	private DeveloperTrainingSessionShowService		showService;

	@Autowired
	private DeveloperTrainingSessionPublishService	publishService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
