
package acme.features.any.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;

@Repository
public interface AnyProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllPublishedProjects();

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

}
