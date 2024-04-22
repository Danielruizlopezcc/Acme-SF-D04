
package acme.features.manager.projectUserStories;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projectUserStories.ProjectUserStory;
import acme.roles.Manager;

@Controller
public class ManagerProjectUserStoriesController extends AbstractController<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerProjectUserStoriesListMineService	listService;

	@Autowired
	protected ManagerProjectUserStoriesShowService		showService;

	@Autowired
	protected ManagerProjectUserStoriesCreateService	createService;

	@Autowired
	protected ManagerProjectUserStoriesDeleteService	deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
	}

}
