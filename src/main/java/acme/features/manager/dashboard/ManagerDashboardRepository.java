
package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.userStory.StoryPriority;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select m from Manager m WHERE m.id = :id")
	Manager findManagerById(int id);
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority=:p")
	Integer totalUserStoriesByPriorityByManagerId(int managerId, StoryPriority p);
	@Query("select avg(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	Double averageUserStoriesEstimatedCostByManagerId(int managerId);
	@Query("select stddev(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	Double deviationUserStoriesEstimatedCostByManagerId(int managerId);
	@Query("select min(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	Double minimumUserStoriesEstimatedCostByManagerId(int managerId);
	@Query("select max(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	Double maximumUserStoriesEstimatedCostByManagerId(int managerId);
	@Query("select avg(p.cost.amount) from Project p where p.manager.id = :managerId")
	Double averageProjectCostByManagerId(int managerId);
	@Query("select stddev(p.cost.amount) from Project p where p.manager.id = :managerId")
	Double deviationProjectCostByManagerId(int managerId);
	@Query("select min(p.cost.amount) from Project p where p.manager.id = :managerId")
	Double minimumProjectCostByManagerId(int managerId);
	@Query("select max(p.cost.amount) from Project p where p.manager.id = :managerId")
	Double maximumProjectCostByManagerId(int managerId);

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findAllProjectsByManagerId(int managerId);

	@Query("select us from UserStory us where us.manager.id = :managerId")
	Collection<UserStory> findAllUserStoriesByManagerId(int managerId);
}
