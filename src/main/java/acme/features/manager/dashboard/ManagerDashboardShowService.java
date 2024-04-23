
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
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
		int totalUserStoriesWithPriorityMust;
		int totalUserStoriesWithPriorityShould;
		int totalUserStoriesWithPriorityCould;
		int totalUserStoriesWithPriorityWont;

		Double averageUserStoriesEstimatedCost;
		Double deviationUserStoriesEstimatedCost;
		double minimumUserStoriesEstimatedCost;
		double maximumUserStoriesEstimatedCost;

		Double averageProjectCost;
		Double deviationProjectCost;
		double minimumProjectCost;
		double maximumProjectCost;
		// getting the manager whose data I want to show

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		dashboard = new ManagerDashboard();

		totalUserStoriesWithPriorityMust = this.repository.totalUserStoriesWithPriorityMustByManagerId(managerId);
		totalUserStoriesWithPriorityShould = this.repository.totalUserStoriesWithPriorityShouldByManagerId(managerId);
		totalUserStoriesWithPriorityCould = this.repository.totalUserStoriesWithPriorityCouldByManagerId(managerId);
		totalUserStoriesWithPriorityWont = this.repository.totalUserStoriesWithPriorityWontByManagerId(managerId);
		averageUserStoriesEstimatedCost = this.repository.averageUserStoriesEstimatedCostByManagerId(managerId);
		deviationUserStoriesEstimatedCost = this.repository.deviationUserStoriesEstimatedCostByManagerId(managerId);
		minimumUserStoriesEstimatedCost = this.repository.minimumUserStoriesEstimatedCostByManagerId(managerId);
		maximumUserStoriesEstimatedCost = this.repository.maximumUserStoriesEstimatedCostByManagerId(managerId);
		averageProjectCost = this.repository.averageProjectCostByManagerId(managerId);
		deviationProjectCost = this.repository.deviationProjectCostByManagerId(managerId);
		minimumProjectCost = this.repository.minimumProjectCostByManagerId(managerId);
		maximumProjectCost = this.repository.maximumProjectCostByManagerId(managerId);
		// Saving the data
		dashboard.setTotalUserStoriesWithPriorityMust(totalUserStoriesWithPriorityMust);
		dashboard.setTotalUserStoriesWithPriorityShould(totalUserStoriesWithPriorityShould);
		dashboard.setTotalUserStoriesWithPriorityCould(totalUserStoriesWithPriorityCould);
		dashboard.setTotalUserStoriesWithPriorityWont(totalUserStoriesWithPriorityWont);
		dashboard.setAverageUserStoriesEstimatedCost(averageUserStoriesEstimatedCost);
		dashboard.setDeviationUserStoriesEstimatedCost(deviationUserStoriesEstimatedCost);
		dashboard.setMinimumUserStoriesEstimatedCost(minimumUserStoriesEstimatedCost);
		dashboard.setMaximumUserStoriesEstimatedCost(maximumUserStoriesEstimatedCost);
		dashboard.setAverageProjectCost(averageProjectCost);
		dashboard.setDeviationProjectCost(deviationProjectCost);
		dashboard.setMinimumProjectCost(minimumProjectCost);
		dashboard.setMaximumProjectCost(maximumProjectCost);

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
