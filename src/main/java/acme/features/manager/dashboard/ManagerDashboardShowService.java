
package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.userStory.StoryPriority;
import acme.entities.userStory.UserStory;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	@Autowired
	private ManagerDashboardRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Manager.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ManagerDashboard dashboard;
		int managerId;
		// the information I want to show in the dashboard

		// getting the manager whose data I want to show

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		dashboard = new ManagerDashboard();

		Collection<Project> projects = this.repository.findAllProjectsByManagerId(managerId);
		Collection<UserStory> userStories = this.repository.findAllUserStoriesByManagerId(managerId);

		dashboard.setTotalUserStoriesWithPriorityMust(0);
		dashboard.setTotalUserStoriesWithPriorityShould(0);
		dashboard.setTotalUserStoriesWithPriorityCould(0);
		dashboard.setTotalUserStoriesWithPriorityWont(0);
		dashboard.setAverageUserStoriesEstimatedCost(Double.NaN);
		dashboard.setDeviationUserStoriesEstimatedCost(Double.NaN);
		dashboard.setMinimumUserStoriesEstimatedCost(Double.NaN);
		dashboard.setMaximumUserStoriesEstimatedCost(Double.NaN);
		dashboard.setAverageProjectCost(Double.NaN);
		dashboard.setDeviationProjectCost(Double.NaN);
		dashboard.setMinimumProjectCost(Double.NaN);
		dashboard.setMaximumProjectCost(Double.NaN);

		if (!userStories.isEmpty()) {
			dashboard.setTotalUserStoriesWithPriorityMust(this.repository.totalUserStoriesByPriorityByManagerId(managerId, StoryPriority.MUST));
			dashboard.setTotalUserStoriesWithPriorityShould(this.repository.totalUserStoriesByPriorityByManagerId(managerId, StoryPriority.SHOULD));
			dashboard.setTotalUserStoriesWithPriorityCould(this.repository.totalUserStoriesByPriorityByManagerId(managerId, StoryPriority.COULD));
			dashboard.setTotalUserStoriesWithPriorityWont(this.repository.totalUserStoriesByPriorityByManagerId(managerId, StoryPriority.WONT));
			dashboard.setAverageUserStoriesEstimatedCost(this.repository.averageUserStoriesEstimatedCostByManagerId(managerId));
			dashboard.setDeviationUserStoriesEstimatedCost(this.repository.deviationUserStoriesEstimatedCostByManagerId(managerId));
			dashboard.setMinimumUserStoriesEstimatedCost(this.repository.minimumUserStoriesEstimatedCostByManagerId(managerId));
			dashboard.setMaximumUserStoriesEstimatedCost(this.repository.maximumUserStoriesEstimatedCostByManagerId(managerId));

		}

		if (!projects.isEmpty()) {
			dashboard.setAverageProjectCost(this.repository.averageProjectCostByManagerId(managerId));
			dashboard.setDeviationProjectCost(this.repository.deviationProjectCostByManagerId(managerId));
			dashboard.setMinimumProjectCost(this.repository.minimumProjectCostByManagerId(managerId));
			dashboard.setMaximumProjectCost(this.repository.maximumProjectCostByManagerId(managerId));
		}

		super.getBuffer().addData(dashboard);

	}
	@Override
	public void unbind(final ManagerDashboard object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "totalUserStoriesWithPriorityMust", "totalUserStoriesWithPriorityShould", "totalUserStoriesWithPriorityCould", "totalUserStoriesWithPriorityWont", "averageUserStoriesEstimatedCost",
			"deviationUserStoriesEstimatedCost", "minimumUserStoriesEstimatedCost", "maximumUserStoriesEstimatedCost", "averageProjectCost", "deviationProjectCost", "minimumProjectCost", "maximumProjectCost");
		super.getResponse().addData(dataset);
	}
}
