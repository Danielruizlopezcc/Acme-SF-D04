
package acme.features.developer.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select 1.0 * count(tm) from TrainingModule tm where tm.updateMoment != null and tm.developer.userAccount.id = :id")
	double totalTrainingModulesWithUpdateMoment(int id);

	@Query("select 1.0 * count(ts) from TrainingSession ts where ts.link is not null and ts.link not like '' and ts.trainingModule.developer.userAccount.id = :id")
	double totalTrainingSessionsWithLink(int id);

	@Query("select avg(tm.estimatedTotalTime) from TrainingModule tm where tm.developer.userAccount.id = :id")
	double averageTrainingModulesTime(int id);

	@Query("SELECT SQRT((SUM(tm.estimatedTotalTime * tm.estimatedTotalTime) / COUNT(tm.estimatedTotalTime)) - (AVG(tm.estimatedTotalTime) * AVG(tm.estimatedTotalTime))) FROM TrainingModule tm where tm.developer.userAccount.id = :id")
	double deviatonTrainingModulesTime(int id);

	@Query("select min(tm.estimatedTotalTime) from TrainingModule tm where tm.developer.userAccount.id = :id")
	double minimumTrainingModulesTime(int id);

	@Query("select max(tm.estimatedTotalTime) from TrainingModule tm where tm.developer.userAccount.id = :id")
	double maximumTrainingModulesTime(int id);

	@Query("select d from Developer d where d.userAccount.id = :id")
	Developer findDeveloperById(int id);

	@Query("select tm from TrainingModule tm where tm.developer.userAccount.id = :id")
	Collection<TrainingModule> findAllTMByDeveloperId(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule.developer.userAccount.id = :id")
	Collection<TrainingSession> findAllTSByDeveloperId(int id);

}
