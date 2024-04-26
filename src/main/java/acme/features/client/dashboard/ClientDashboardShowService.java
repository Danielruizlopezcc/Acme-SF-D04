
package acme.features.client.dashboard;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.progressLogs.ProgressLog;
import acme.entities.systemconf.SystemConfiguration;
import acme.forms.ClientDashboard;
import acme.roles.clients.Client;

@Service
public class ClientDashboardShowService extends AbstractService<Client, ClientDashboard> {

	@Autowired
	private ClientDashboardRepository repository;


	@Override
	public void authorise() {

		//habria que checkear que el rol es client??

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		int clientId = super.getRequest().getPrincipal().getActiveRoleId();

		ClientDashboard clientDashboard;

		clientDashboard = new ClientDashboard();

		Collection<ProgressLog> progressLogsPublished = this.repository.findAllProgressLogs().stream().filter(x -> !x.isDraftMode()).toList();
		Collection<Contract> myPublishedContracts = this.repository.findManyContractsByClientId(clientId).stream().filter(x -> !x.isDraftMode()).toList();
		Collection<Integer> myContractsIds = myPublishedContracts.stream().map(x -> x.getId()).toList();
		Collection<Money> myBudgets = this.repository.findManyBudgetsByClientId(clientId); //this only considers published contracts.
		Map<String, List<Money>> budgetsByCurrency = myBudgets.stream().collect(Collectors.groupingBy(Money::getCurrency));

		Map<String, Double> mediaPorCurrency = budgetsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMedia(entry.getValue()).getAmount()));

		Map<String, Double> maximoPorCurrency = budgetsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMaximo(entry.getValue()).getAmount()));

		Map<String, Double> minimoPorCurrency = budgetsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMinimo(entry.getValue()).getAmount()));

		Map<String, Double> desviacionPorCurrency = budgetsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularDesviacion(entry.getValue()).getAmount()));

		List<SystemConfiguration> systemConfiguration = this.repository.findSystemConfiguration();

		String[] supportedCurrencies = systemConfiguration.get(0).acceptedCurrencies.split(",");
		//my progress logs less than 25
		double totalNumProgressLogLessThan25 = progressLogsPublished.stream().filter(x -> myContractsIds.contains(x.getContract().getId())).filter(x -> x.getCompleteness() < 25.0).count();
		// PL between 25 and 50
		double totalNumProgressLogBetween25and50 = progressLogsPublished.stream().filter(x -> myContractsIds.contains(x.getContract().getId())).filter(x -> x.getCompleteness() >= 25.0 && x.getCompleteness() <= 50.0).count();
		//PL between 50 and 75
		double totalNumProgressLogBetween50and75 = progressLogsPublished.stream().filter(x -> myContractsIds.contains(x.getContract().getId())).filter(x -> x.getCompleteness() > 50.0 && x.getCompleteness() <= 75.0).count();
		// PL above 75
		double totalNumProgressLogAbove75 = progressLogsPublished.stream().filter(x -> myContractsIds.contains(x.getContract().getId())).filter(x -> x.getCompleteness() > 75.0).count();

		clientDashboard.setTotalNumProgressLogLessThan25(totalNumProgressLogLessThan25);
		clientDashboard.setTotalNumProgressLogBetween25And50(totalNumProgressLogBetween25and50);
		clientDashboard.setTotalNumProgressLogBetween50And75(totalNumProgressLogBetween50and75);
		clientDashboard.setTotalNumProgressLogAbove75(totalNumProgressLogAbove75);

		clientDashboard.setMaximumPerCurrency(maximoPorCurrency);
		clientDashboard.setMinimumPerCurrency(minimoPorCurrency);
		clientDashboard.setAveragePerCurrency(mediaPorCurrency);
		clientDashboard.setDeviationPerCurrency(desviacionPorCurrency);

		clientDashboard.setSupportedCurrencies(supportedCurrencies);
		super.getBuffer().addData(clientDashboard);

	}

	@Override
	public void unbind(final ClientDashboard object) {

		Dataset dataset;

		dataset = super.unbind(object, "totalNumProgressLogLessThan25", "totalNumProgressLogBetween25And50", //
			"totalNumProgressLogBetween50And75", "totalNumProgressLogAbove75", //
			"maximumPerCurrency", "minimumPerCurrency", "averagePerCurrency", "deviationPerCurrency", "supportedCurrencies");

		super.getResponse().addData(dataset);

	}

	private Money calcularMedia(final Collection<Money> budgets) {
		Money moneyFinal = new Money();
		moneyFinal.setCurrency("USD");
		moneyFinal.setAmount(budgets.stream().map(x -> x.getAmount()).mapToDouble(Double::doubleValue).average().orElse(Double.NaN));

		return moneyFinal;
	}

	private Money calcularMaximo(final Collection<Money> budgets) {
		Money moneyFinal = new Money();
		moneyFinal.setCurrency("USD");
		moneyFinal.setAmount(budgets.stream().map(x -> x.getAmount()).mapToDouble(Double::doubleValue).max().orElse(Double.NaN));
		return moneyFinal;
	}

	private Money calcularMinimo(final Collection<Money> budgets) {
		Money moneyFinal = new Money();
		moneyFinal.setCurrency("USD");
		moneyFinal.setAmount(budgets.stream().map(x -> x.getAmount()).mapToDouble(Double::doubleValue).min().orElse(Double.NaN));
		return moneyFinal;

	}

	private Money calcularDesviacion(final Collection<Money> budgets) {
		Money desviacion = new Money();
		desviacion.setCurrency("USD");

		// Calcular la media
		double media = budgets.stream().mapToDouble(Money::getAmount).average().orElse(Double.NaN);

		// Calcular la suma de las diferencias al cuadrado
		double sumaDiferenciasCuadradas = budgets.stream().mapToDouble(budget -> Math.pow(budget.getAmount() - media, 2)).sum();

		// Calcular la varianza
		double varianza = sumaDiferenciasCuadradas / budgets.size();

		// Calcular la desviación estándar como la raíz cuadrada de la varianza
		double desviacionEstandar = Math.sqrt(varianza);

		desviacion.setAmount(desviacionEstandar);

		return desviacion;
	}

}
