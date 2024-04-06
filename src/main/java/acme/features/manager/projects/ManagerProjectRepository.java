
package acme.features.manager.projects;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id = :managerId")
	List<Project> findAllProjectsByManagerId(int managerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findOneManagerById(int managerId);

	@Query("select p from Project p where p.code = :projectCode")
	Project findOneProjectByCode(String projectCode);

	@Query("select pus from ProjectUserStory pus where pus.project.id = :projectId")
	Collection<UserStory> findAllUserStoriesByProjectId(int projectId);

}
