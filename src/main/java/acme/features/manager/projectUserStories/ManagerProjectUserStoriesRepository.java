
package acme.features.manager.projectUserStories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.projectUserStories.ProjectUserStory;
import acme.entities.userStory.UserStory;

@Repository
public interface ManagerProjectUserStoriesRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT us FROM UserStory us WHERE us.id = :id")
	UserStory findUserStoryById(int id);

	@Query("SELECT pus FROM ProjectUserStory pus WHERE pus.id = :id")
	ProjectUserStory findProjectUserStoryById(int id);

	@Query("SELECT pus FROM ProjectUserStory pus WHERE pus.id = :id")
	Collection<ProjectUserStory> findProjectUserStoriesById(int id);

	@Query("SELECT pr FROM Project pr WHERE pr.manager.userAccount.id = :id")
	Collection<Project> findProjectsByManagerId(int id);

	@Query("SELECT us FROM UserStory us WHERE us.manager.userAccount.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

	@Query("SELECT pus FROM ProjectUserStory pus WHERE pus.project.id = :id")
	Collection<ProjectUserStory> findProjectUserStoryByProjectId(int id);

	@Query("SELECT pus FROM ProjectUserStory pus WHERE pus.project.id = :projectId AND pus.userStory.id = :userStoryId")
	Collection<ProjectUserStory> findProjectUserStoryByProjectIdAndUserStoryId(int projectId, int userStoryId);

}
