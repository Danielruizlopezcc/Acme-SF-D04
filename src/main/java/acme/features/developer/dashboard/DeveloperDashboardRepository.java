
package acme.features.developer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select 1.0 * count(tm) from TrainingModule tm where tm.updateMoment != null && tm.developer")
	double totalTrainingModulesWithUpdateMoment();

	@Query("select 1.0 * count(ts) from TrainingSession ts where ts.link != null")
	double totalTrainingSessionsWithLink();

	@Query("select avg(tm.estimatedTotalTime) from TrainingModule tm")
	double averageTrainingModulesTime();

	@Query("SELECT SQRT((SUM(tm.estimatedTotalTime * tm.estimatedTotalTime) / COUNT(tm.estimatedTotalTime)) - (AVG(tm.estimatedTotalTime) * AVG(tm.estimatedTotalTime))) FROM TrainingModule tm")
	double deviatonTrainingModulesTime();

	@Query("select min(tm.estimatedTotalTime) from TrainingModule tm")
	double minimumTrainingModulesTime();

	@Query("select max(tm.estimatedTotalTime) from TrainingModule tm")
	double maximumTrainingModulesTime();

}
