
package acme.features.client.dashboard;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.progressLogs.ProgressLog;
import acme.entities.systemconf.SystemConfiguration;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	@Query("select pl from ProgressLog pl")
	Collection<ProgressLog> findAllProgressLogs();

	@Query("select c from Contract c")
	Collection<Contract> findAllContracts();

	@Query("select c from Contract c where c.client.id = :clientId")
	Collection<Contract> findManyContractsByClientId(int clientId);

	/*
	 * @Query("select avg(c.budget) from Contract c where c.client.id = :clientId")
	 * Money averageBudgetByClientId(int clientId);
	 * 
	 * @Query("select max(c.budget) from Contract c where c.client.id = :clientId")
	 * Money maxBudgetByClientId(int clientId);
	 * 
	 * @Query("select min(c.budget) from Contract c where c.client.id = :clientId")
	 * Money minBudgetByClientId(int clientId);
	 */

	@Query("select c.budget from Contract c where c.client.id = :clientId and c.draftMode = false")
	Collection<Money> findManyBudgetsByClientId(int clientId);

	@Query("select s from SystemConfiguration s")
	List<SystemConfiguration> findSystemConfiguration();
}
