
package acme.features.developer;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.trainingModule.TrainingModule;
import acme.roles.Developer;

@Repository
public interface DeveloperRepository extends AbstractRepository {

	@Query("select tm from TrainingModule where tm.developer.id = :id")
	List<TrainingModule> findAllTMByDevId(int id);

	@Query("select tm.details from TrainingModule tm where tm.developer.id = :id")
	List<String> findAllTMDetailsByDevId(int id);

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findOneTMById(int id);

	@Query("select d from Developer d where d.id = :id")
	Developer findOneDeveloperById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select tm from TrainingModule tm where tm.code = :code")
	TrainingModule findOneTMByCode(String code);

}
