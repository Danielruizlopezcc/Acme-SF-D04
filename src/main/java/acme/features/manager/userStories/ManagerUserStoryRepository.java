
package acme.features.manager.userStories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.projectUserStories.ProjectUserStory;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("SELECT a FROM ProjectUserStory a WHERE a.project.id = :id")
	Collection<ProjectUserStory> findProjectUserStoryByProjectId(int id);

	@Query("SELECT a FROM ProjectUserStory a WHERE a.userStory.id = :id")
	Collection<ProjectUserStory> findProjectUserStoryByUserStoryById(int id);

	@Query("SELECT us FROM UserStory us WHERE us.id = :id")
	UserStory findUserStoryById(int id);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT us FROM UserStory us WHERE us.manager.userAccount.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findManagerById(int id);

	@Query("SELECT a FROM ProjectUserStory a WHERE a.project.id = :projectId and a.userStory.id = :usId")
	Collection<ProjectUserStory> findProjectUserStoryByProjectIdAndUserStoryId(int projectId, int usId);

}
