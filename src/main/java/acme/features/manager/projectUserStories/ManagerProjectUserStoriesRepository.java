
package acme.features.manager.projectUserStories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.projectUserStories.ProjectUserStory;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

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

	@Query("SELECT pr FROM Project pr WHERE pr.manager.id = :id")
	Collection<Project> findProjectsByManagerId(int id);

	@Query("SELECT us FROM UserStory us WHERE us.manager.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

	@Query("SELECT pus FROM ProjectUserStory pus WHERE pus.project.id = :id")
	Collection<ProjectUserStory> findProjectUserStoryByProjectId(int id);

	@Query("select pus from ProjectUserStory pus where pus.project.manager.id = :id and pus.userStory.manager.id = :id")
	Collection<ProjectUserStory> findProjectUserStoriesByManagerId(int id);

	@Query("SELECT pus FROM ProjectUserStory pus WHERE pus.project.id = :projectId AND pus.userStory.id = :userStoryId")
	ProjectUserStory findOneProjectUserStoryByProjectIdAndUserStoryId(int projectId, int userStoryId);

	@Query("SELECT pus.project FROM ProjectUserStory pus WHERE pus.id = :id")
	Project findOneProjectByProjectUserStoryId(int id);

	@Query("SELECT pus.userStory FROM ProjectUserStory pus WHERE pus.id = :id")
	UserStory findOneUserStoryByProjectUserStoryId(int id);

	@Query("SELECT pus.project.manager FROM ProjectUserStory pus WHERE pus.id = :id")
	Manager findOneManagerByProjectUserStoryId(int id);

}
