
package acme.features.client.progressLogs;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.progressLogs.ProgressLog;
import acme.roles.clients.Client;

@Repository
public interface ClientProgressLogRepository extends AbstractRepository {

	@Query("select c from Contract c where c.id= :contractId")
	Contract findOneContractById(int contractId);

	@Query("select pl from ProgressLog pl where pl.contract.id = :contractId")
	Collection<ProgressLog> findManyProgressLogByContractId(int contractId);

	@Query("select pl.contract from ProgressLog pl where pl.id = :id")
	Contract findOneContractByProgressLogId(int id);

	@Query("select pl from ProgressLog pl where pl.id = :id")
	ProgressLog findOneProgressLogById(int id);

	@Query("select pl from ProgressLog pl where pl.recordId = :recordId")
	ProgressLog findOneProgressLogByRecordId(String recordId);

	@Query("select cl from Client cl where cl.id = :clientId")
	Client findOneClientById(int clientId);

	@Query("select max(pl.completeness) from ProgressLog pl where pl.contract.id = :contractId and pl.draftMode = false")
	Optional<Double> findPublishedProgressLogWithMaxCompletenessPublished(int contractId);
}
