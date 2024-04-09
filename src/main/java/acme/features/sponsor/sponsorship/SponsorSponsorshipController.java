
package acme.features.sponsor.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Controller
public class SponsorSponsorshipController extends AbstractController<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipCreateService		createService;

	@Autowired
	private SponsorSponsorshipDeleteService		deleteService;

	@Autowired
	private SponsorSponsorshipUpdateService		updateService;

	@Autowired
	private SponsorSponsorshipListMineService	listMineService;

	@Autowired
	private SponsorSponsorshipPublishService	publishService;

	@Autowired
	private SponsorSponsorshipShowService		showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addBasicCommand("show", this.showService);
	}
}
