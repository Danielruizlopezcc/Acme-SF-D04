
package acme.features.manager.dashboard;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.systemconf.SystemConfiguration;
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
		boolean isManager = super.getRequest().getPrincipal().hasRole(Manager.class);
		super.getResponse().setAuthorised(isManager);
	}

	@Override
	public void load() {
		int managerId = super.getRequest().getPrincipal().getActiveRoleId();

		ManagerDashboard managerDashboard = new ManagerDashboard();

		Collection<UserStory> userStories = this.repository.findManyUserStoriesByManagerId(managerId).stream().filter(x -> !x.isDraftMode()).toList();
		Collection<Project> projects = this.repository.findManyProjectsByManagerId(managerId).stream().filter(x -> !x.isDraftMode()).toList();
		Collection<Money> projectCosts = projects.stream().map(Project::getCost).toList();
		Map<String, List<Money>> projectCostsByCurrency = projectCosts.stream().collect(Collectors.groupingBy(Money::getCurrency));

		Map<String, Double> averageProjectCostPerCurrency = projectCostsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMedia(entry.getValue(), entry.getKey()).getAmount()));

		Map<String, Double> maxProjectCostPerCurrency = projectCostsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMaximo(entry.getValue(), entry.getKey()).getAmount()));

		Map<String, Double> minProjectCostPerCurrency = projectCostsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMinimo(entry.getValue(), entry.getKey()).getAmount()));

		Map<String, Double> deviationProjectCostPerCurrency = projectCostsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularDesviacion(entry.getValue(), entry.getKey()).getAmount()));

		List<SystemConfiguration> systemConfiguration = this.repository.findSystemConfiguration();
		String[] supportedCurrencies = systemConfiguration.get(0).acceptedCurrencies.split(",");

		double totalUserStoriesWithPriorityMust = userStories.stream().filter(x -> x.getPriority() == StoryPriority.MUST).count();
		double totalUserStoriesWithPriorityShould = userStories.stream().filter(x -> x.getPriority() == StoryPriority.SHOULD).count();
		double totalUserStoriesWithPriorityCould = userStories.stream().filter(x -> x.getPriority() == StoryPriority.COULD).count();
		double totalUserStoriesWithPriorityWont = userStories.stream().filter(x -> x.getPriority() == StoryPriority.WONT).count();
		double averageUserStoriesEstimatedCost = userStories.stream().mapToInt(UserStory::getEstimatedCost).average().orElse(Double.NaN);
		double deviationUserStoriesEstimatedCost = this.calcularDesviacionEstimacion(userStories);
		double minUserStoriesEstimatedCost = userStories.stream().mapToInt(UserStory::getEstimatedCost).min().orElse(0);
		double maxUserStoriesEstimatedCost = userStories.stream().mapToInt(UserStory::getEstimatedCost).max().orElse(0);

		managerDashboard.setTotalUserStoriesWithPriorityMust(totalUserStoriesWithPriorityMust);
		managerDashboard.setTotalUserStoriesWithPriorityShould(totalUserStoriesWithPriorityShould);
		managerDashboard.setTotalUserStoriesWithPriorityCould(totalUserStoriesWithPriorityCould);
		managerDashboard.setTotalUserStoriesWithPriorityWont(totalUserStoriesWithPriorityWont);
		managerDashboard.setAverageUserStoriesEstimatedCost(averageUserStoriesEstimatedCost);
		managerDashboard.setDeviationUserStoriesEstimatedCost(deviationUserStoriesEstimatedCost);
		managerDashboard.setMinimumUserStoriesEstimatedCost(minUserStoriesEstimatedCost);
		managerDashboard.setMaximumUserStoriesEstimatedCost(maxUserStoriesEstimatedCost);

		managerDashboard.setAverageProjectCostPerCurrency(averageProjectCostPerCurrency);
		managerDashboard.setDeviationProjectCostPerCurrency(deviationProjectCostPerCurrency);
		managerDashboard.setMinimumProjectCostPerCurrency(minProjectCostPerCurrency);
		managerDashboard.setMaximumProjectCostPerCurrency(maxProjectCostPerCurrency);
		managerDashboard.setSupportedCurrencies(supportedCurrencies);

		super.getBuffer().addData(managerDashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset = super.unbind(object, "totalUserStoriesWithPriorityMust", "totalUserStoriesWithPriorityShould", //
			"totalUserStoriesWithPriorityCould", "totalUserStoriesWithPriorityWont", //
			"averageUserStoriesEstimatedCost", "deviationUserStoriesEstimatedCost", "minimumUserStoriesEstimatedCost", "maximumUserStoriesEstimatedCost", //
			"averageProjectCostPerCurrency", "deviationProjectCostPerCurrency", "minimumProjectCostPerCurrency", "maximumProjectCostPerCurrency", "supportedCurrencies");

		super.getResponse().addData(dataset);
	}

	private Money calcularMedia(final Collection<Money> budgets, final String currency) {
		Money moneyFinal = new Money();
		moneyFinal.setCurrency(currency);
		moneyFinal.setAmount(budgets.stream().mapToDouble(Money::getAmount).average().orElse(Double.NaN));
		return moneyFinal;
	}

	private Money calcularMaximo(final Collection<Money> budgets, final String currency) {
		Money moneyFinal = new Money();
		moneyFinal.setCurrency(currency);
		moneyFinal.setAmount(budgets.stream().mapToDouble(Money::getAmount).max().orElse(Double.NaN));
		return moneyFinal;
	}

	private Money calcularMinimo(final Collection<Money> budgets, final String currency) {
		Money moneyFinal = new Money();
		moneyFinal.setCurrency(currency);
		moneyFinal.setAmount(budgets.stream().mapToDouble(Money::getAmount).min().orElse(Double.NaN));
		return moneyFinal;
	}

	private Money calcularDesviacion(final Collection<Money> budgets, final String currency) {
		Money desviacion = new Money();
		desviacion.setCurrency(currency);
		double media = budgets.stream().mapToDouble(Money::getAmount).average().orElse(Double.NaN);
		double sumaDiferenciasCuadradas = budgets.stream().mapToDouble(budget -> Math.pow(budget.getAmount() - media, 2)).sum();
		double varianza = sumaDiferenciasCuadradas / budgets.size();
		double desviacionEstandar = Math.sqrt(varianza);
		desviacion.setAmount(desviacionEstandar);
		return desviacion;
	}

	private double calcularDesviacionEstimacion(final Collection<UserStory> userStories) {
		double media = userStories.stream().mapToInt(UserStory::getEstimatedCost).average().orElse(Double.NaN);
		double sumaDiferenciasCuadradas = userStories.stream().mapToDouble(story -> Math.pow(story.getEstimatedCost() - media, 2)).sum();
		double varianza = sumaDiferenciasCuadradas / userStories.size();
		return Math.sqrt(varianza);
	}
}
