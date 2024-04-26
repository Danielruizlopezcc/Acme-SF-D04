
package acme.features.manager.projects;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
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

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id = :managerId")
	List<Project> findAllProjectsByManagerId(int managerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findOneManagerById(int managerId);

	@Query("select p from Project p where p.code = :projectCode")
	Project findOneProjectByCode(String projectCode);

	@Query("select pus from ProjectUserStory pus where pus.project.id = :projectId")
	Collection<ProjectUserStory> findProjectUserStoriesByProjectId(int projectId);

	@Query("select c from Contract c where c.project.id = :id")
	Collection<Contract> findManyContractsByProjectId(int id);
	@Query("select pl from ProgressLog pl where pl.contract.id IN :ids")
	Collection<ProgressLog> findManyProgressLogsByContractIds(Set<Integer> ids);

	@Query("select ss from Sponsorship ss where ss.project.id = :id")
	Collection<Sponsorship> findManySponsorshipsByProjectId(int id);
	@Query("select i from Invoice i where i.sponsorship.id IN :ids")
	Collection<Invoice> findManyInvoicesBySponsorshipIds(Set<Integer> ids);

	@Query("select ca from CodeAudits ca where ca.project.id = :id")
	Collection<CodeAudits> findManyCodeAuditsByProjectId(int id);
	@Query("select ar from AuditRecords ar where ar.codeAudit.id IN :id")
	Collection<AuditRecords> findManyAuditsRecordsByCodeAuditsId(Set<Integer> id);

	@Query("select tm from TrainingModule tm where tm.project.id = :id")
	Collection<TrainingModule> findManyTrainingModuleByProjectId(int id);
	@Query("select ts from TrainingSession ts where ts.trainingModule.id IN :id")
	Collection<TrainingSession> findManyTrainingSessionByTrainingModuleId(Set<Integer> id);

	//	@Query("select s from SystemConfiguration s")
	//	Collection<SystemConfiguration> findSystemConfiguration();

}
