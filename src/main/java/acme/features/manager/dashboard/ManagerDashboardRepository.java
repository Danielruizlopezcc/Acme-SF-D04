
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select m from Manager m WHERE m.id = :id")
	Manager findManagerById(int id);
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority='MUST' ")
	Integer totalUserStoriesWithPriorityMustByManagerId(int managerId);
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority='SHOULD' ")
	Integer totalUserStoriesWithPriorityShouldByManagerId(int managerId);
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority='COULD' ")
	Integer totalUserStoriesWithPriorityCouldByManagerId(int managerId);
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority='WONT' ")
	Integer totalUserStoriesWithPriorityWontByManagerId(int managerId);
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
}
