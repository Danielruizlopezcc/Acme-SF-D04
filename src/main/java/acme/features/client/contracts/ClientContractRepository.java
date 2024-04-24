
package acme.features.client.contracts;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.progressLogs.ProgressLog;
import acme.entities.project.Project;
import acme.entities.systemconf.SystemConfiguration;
import acme.roles.clients.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.client.id = :clientId")
	List<Contract> findAllContractsByClientId(int clientId);

	@Query("select c from Contract c where c.id= :contractId")
	Contract findOneContractById(int contractId);

	@Query("select p from Project p where p.id= :projectId")
	Project findOneProjectById(int projectId);

	@Query("select pr from ProgressLog pr where pr.contract.id = :contractId")
	Collection<ProgressLog> findManyProgressLogByContractId(int contractId);

	@Query("select cl from Client cl where cl.id = :clientId")
	Client findOneClientById(int clientId);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select c.project from Contract c where c.id = :contractId")
	Collection<Project> findOneProjectByContractId(int contractId);

	@Query("select c from Contract c where c.code = :code")
	Contract findOneContractByCode(String code);

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findManyContractByProjectId(int projectId);

	@Query("select s from SystemConfiguration s")
	List<SystemConfiguration> findSystemConfiguration();

}
