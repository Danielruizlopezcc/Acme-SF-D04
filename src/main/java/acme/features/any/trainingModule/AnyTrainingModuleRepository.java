
package acme.features.any.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.trainingModule.TrainingModule;

@Repository
public interface AnyTrainingModuleRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.draftMode = false")
	Collection<TrainingModule> findAllPublishedTrainingModules();

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findTrainingModuleById(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();
}
