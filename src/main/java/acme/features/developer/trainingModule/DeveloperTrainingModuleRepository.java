
package acme.features.developer.trainingModule;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.developer.id = :developerId")
	List<TrainingModule> findAllTMByDevId(int developerId);

	@Query("select t.details from TrainingModule t where t.developer.id = :developerId")
	List<String> findAllTMDetailsByDevId(int developerId);

	@Query("select t from TrainingModule t where t.id = :tmId")
	TrainingModule findOneTMById(int tmId);

	@Query("select d from Developer d where d.id = :developerId")
	Developer findOneDeveloperById(int developerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select t from TrainingModule t where t.code = :tmCode")
	TrainingModule findOneTMByCode(String tmCode);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :tmId")
	Collection<TrainingSession> findAllTSByTMId(int tmId);

}
