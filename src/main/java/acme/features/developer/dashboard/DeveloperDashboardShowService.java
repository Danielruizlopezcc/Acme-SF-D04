
package acme.features.developer.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {

	@Autowired
	private DeveloperDashboardRepository dashboardRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		DeveloperDashboard developerDashboard = new DeveloperDashboard();

		developerDashboard.setTotalTrainingSessionsWithLink(this.dashboardRepository.totalTrainingSessionsWithLink());
		developerDashboard.setTotalTrainingModulesWithUpdateMoment(this.dashboardRepository.totalTrainingModulesWithUpdateMoment());
		developerDashboard.setAverageTrainingModulesTime(this.dashboardRepository.averageTrainingModulesTime());
		developerDashboard.setDeviatonTrainingModulesTime(this.dashboardRepository.deviatonTrainingModulesTime());
		developerDashboard.setMinimumTrainingModulesTime(this.dashboardRepository.minimumTrainingModulesTime());
		developerDashboard.setMaximumTrainingModulesTime(this.dashboardRepository.maximumTrainingModulesTime());

		super.getBuffer().addData(developerDashboard);

	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalTrainingModulesWithUpdateMoment", "totalTrainingSessionsWithLink", "averageTrainingModulesTime", "deviatonTrainingModulesTime", "minimumTrainingModulesTime", "maximumTrainingModulesTime");

		super.getResponse().addData(dataset);
	}
}
