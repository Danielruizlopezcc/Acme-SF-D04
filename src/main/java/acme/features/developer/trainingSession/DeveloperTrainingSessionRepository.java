
package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.id = :tmId")
	TrainingModule findOneTMById(int tmId);

	@Query("select ts from TrainingSession ts where ts.code = :tsCode")
	TrainingSession findOneTSByCode(String tsCode);

	@Query("select ts.trainingModule from TrainingSession ts where ts.id = :tsId")
	TrainingModule findOneTMByTSId(int tsId);

	@Query("select ts from TrainingSession ts where ts.id = :tsId")
	TrainingSession findOneTSById(int tsId);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :tmId")
	Collection<TrainingSession> findAllTSByMasterId(int tmId);
}
