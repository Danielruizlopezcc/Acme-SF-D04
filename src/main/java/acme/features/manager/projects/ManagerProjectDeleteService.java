
package acme.features.manager.projects;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.AbstractEntity;
import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.auditRecords.AuditRecords;
import acme.entities.codeAudits.CodeAudits;
import acme.entities.contract.Contract;
import acme.entities.invoice.Invoice;
import acme.entities.progressLogs.ProgressLog;
import acme.entities.project.Project;
import acme.entities.projectUserStories.ProjectUserStory;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		masterId = super.getRequest().getData("id", int.class);
		Project project = this.repository.findOneProjectById(masterId);
		Manager manager = project == null ? null : project.getManager();
		status = project != null && project.isDraftMode() && principal.hasRole(manager) && project.getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;
		super.bind(object, "code", "title", "abstractProject", "indication", "cost", "link");

	}

	@Override
	public void validate(final Project object) {
		assert object != null;
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		List<Contract> contracts;
		List<ProgressLog> progressLogs;

		List<Sponsorship> sponsorShips;
		List<Invoice> invoices;

		List<CodeAudits> codeAudits;
		List<AuditRecords> auditRecords;

		List<TrainingModule> trainingModule;
		List<TrainingSession> trainingSession;

		List<ProjectUserStory> projectUserStories;
		int id = object.getId();

		// SponsorShip e invoices
		sponsorShips = (List<Sponsorship>) this.repository.findManySponsorshipsByProjectId(id);
		if (sponsorShips != null) {
			Set<Integer> sponsorShipIds = sponsorShips.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			invoices = (List<Invoice>) this.repository.findManyInvoicesBySponsorshipIds(sponsorShipIds);
			this.repository.deleteAll(invoices);
			this.repository.deleteAll(sponsorShips);
		}

		// Contracts y progressLogs
		contracts = (List<Contract>) this.repository.findManyContractsByProjectId(id);
		if (contracts != null) {
			Set<Integer> contractIds = contracts.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			progressLogs = (List<ProgressLog>) this.repository.findManyProgressLogsByContractIds(contractIds);
			this.repository.deleteAll(progressLogs);
			this.repository.deleteAll(contracts);
		}
		//ProjectUserStories
		projectUserStories = (List<ProjectUserStory>) this.repository.findProjectUserStoriesByProjectId(id);

		//CodeAudtis y auditRecords
		codeAudits = (List<CodeAudits>) this.repository.findManyCodeAuditsByProjectId(id);
		if (codeAudits != null) {
			Set<Integer> codeAuditsIds = codeAudits.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			auditRecords = (List<AuditRecords>) this.repository.findManyAuditsRecordsByCodeAuditsId(codeAuditsIds);
			this.repository.deleteAll(auditRecords);
			this.repository.deleteAll(codeAudits);
		}

		//TrainingModule y TrainingSession 
		trainingModule = (List<TrainingModule>) this.repository.findManyTrainingModuleByProjectId(id);
		if (trainingModule != null) {
			Set<Integer> trainingModuleIds = trainingModule.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			trainingSession = (List<TrainingSession>) this.repository.findManyTrainingSessionByTrainingModuleId(trainingModuleIds);
			this.repository.deleteAll(trainingSession);
			this.repository.deleteAll(trainingModule);
		}

		this.repository.deleteAll(projectUserStories);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstractProject", "indication", "cost", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
