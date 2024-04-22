
package acme.features.developer.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {

	@Autowired
	private DeveloperDashboardRepository dashboardRepository;


	@Override
	public void authorise() {
		boolean status;

		Principal principal = super.getRequest().getPrincipal();
		int id = principal.getAccountId();
		Developer developer = this.dashboardRepository.findDeveloperById(id);
		status = developer != null && principal.hasRole(Developer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();
		DeveloperDashboard developerDashboard = new DeveloperDashboard();
		Collection<TrainingModule> modules = this.dashboardRepository.findAllTMByDeveloperId(userAccountId);
		Collection<TrainingSession> sessions = this.dashboardRepository.findAllTSByDeveloperId(userAccountId);

		developerDashboard.setTotalTrainingSessionsWithLink(Double.NaN);
		developerDashboard.setTotalTrainingModulesWithUpdateMoment(Double.NaN);
		developerDashboard.setAverageTrainingModulesTime(Double.NaN);
		developerDashboard.setDeviatonTrainingModulesTime(Double.NaN);
		developerDashboard.setMinimumTrainingModulesTime(Double.NaN);
		developerDashboard.setMaximumTrainingModulesTime(Double.NaN);

		if (!modules.isEmpty()) {
			developerDashboard.setTotalTrainingModulesWithUpdateMoment(this.dashboardRepository.totalTrainingModulesWithUpdateMoment(userAccountId));
			developerDashboard.setAverageTrainingModulesTime(this.dashboardRepository.averageTrainingModulesTime(userAccountId));
			developerDashboard.setDeviatonTrainingModulesTime(this.dashboardRepository.deviatonTrainingModulesTime(userAccountId));
			developerDashboard.setMinimumTrainingModulesTime(this.dashboardRepository.minimumTrainingModulesTime(userAccountId));
			developerDashboard.setMaximumTrainingModulesTime(this.dashboardRepository.maximumTrainingModulesTime(userAccountId));
		}

		if (!sessions.isEmpty())
			developerDashboard.setTotalTrainingSessionsWithLink(this.dashboardRepository.totalTrainingSessionsWithLink(userAccountId));

		super.getBuffer().addData(developerDashboard);

	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalTrainingModulesWithUpdateMoment", "totalTrainingSessionsWithLink", "averageTrainingModulesTime", "deviatonTrainingModulesTime", "minimumTrainingModulesTime", "maximumTrainingModulesTime");

		super.getResponse().addData(dataset);
	}
}
