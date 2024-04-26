
package acme.features.any.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.project.Project;

@Controller
public class AnyProjectController extends AbstractController<Any, Project> {

	@Autowired
	private AnyProjectListService	listService;
	@Autowired
	private AnyProjectShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
