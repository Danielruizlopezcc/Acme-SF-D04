
package acme.features.manager.projects;

import java.util.Collection;
import java.util.Locale;
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
		int projectId;
		Manager manager;
		Project project;

		projectId = super.getRequest().getData("id", int.class);
		project = this.repository.findOneProjectById(projectId);

		Principal principal = super.getRequest().getPrincipal();
		manager = this.repository.findOneManagerById(principal.getActiveRoleId());

		status = project != null && project.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

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
		Collection<Contract> contracts;
		Collection<ProgressLog> progressLogs;

		Collection<Sponsorship> sponsorShips;
		Collection<Invoice> invoices;

		Collection<CodeAudits> codeAudits;
		Collection<AuditRecords> auditRecords;

		Collection<TrainingModule> trainingModule;
		Collection<TrainingSession> trainingSession;

		Collection<ProjectUserStory> projectUserStories;
		int id = object.getId();

		// SponsorShip e invoices
		sponsorShips = this.repository.findManySponsorshipsByProjectId(id);
		if (sponsorShips != null) {
			Set<Integer> sponsorShipIds = sponsorShips.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			invoices = this.repository.findManyInvoicesBySponsorshipIds(sponsorShipIds);
			this.repository.deleteAll(invoices);
			this.repository.deleteAll(sponsorShips);
		}

		// Contracts y progressLogs
		contracts = this.repository.findManyContractsByProjectId(id);
		if (contracts != null) {
			Set<Integer> contractIds = contracts.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			progressLogs = this.repository.findManyProgressLogsByContractIds(contractIds);
			this.repository.deleteAll(progressLogs);
			this.repository.deleteAll(contracts);
		}
		//ProjectUserStories
		projectUserStories = this.repository.findProjectUserStoriesByProjectId(id);

		//CodeAudtis y auditRecords
		codeAudits = this.repository.findManyCodeAuditsByProjectId(id);
		if (codeAudits != null) {
			Set<Integer> codeAuditsIds = codeAudits.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			auditRecords = this.repository.findManyAuditsRecordsByCodeAuditsId(codeAuditsIds);
			this.repository.deleteAll(auditRecords);
			this.repository.deleteAll(codeAudits);
		}

		//TrainingModule y TrainingSession 
		trainingModule = this.repository.findManyTrainingModuleByProjectId(id);
		if (trainingModule != null) {
			Set<Integer> trainingModuleIds = trainingModule.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			trainingSession = this.repository.findManyTrainingSessionByTrainingModuleId(trainingModuleIds);
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
		if (object.isIndication()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("indication", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("indication", "No");
		super.getResponse().addData(dataset);
	}

}
