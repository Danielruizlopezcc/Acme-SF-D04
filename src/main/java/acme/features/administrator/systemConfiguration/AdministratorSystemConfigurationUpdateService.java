
package acme.features.administrator.systemConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.systemconf.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationUpdateService extends AbstractService<Administrator, SystemConfiguration> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorSystemConfigurationRepository repository;


	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final SystemConfiguration systemConfiguration = this.repository.findSystemConfigurationsById(id);

		super.getBuffer().addData(systemConfiguration);
	}

	@Override
	public void bind(final SystemConfiguration object) {
		assert object != null;

		super.bind(object, "systemCurrency", "acceptedCurrencies");
	}

	@Override
	public void validate(final SystemConfiguration object) {
		assert object != null;

		List<SystemConfiguration> sc = this.repository.findAllSystemConfigurations();
		final List<String> currencies = Stream.of(sc.get(0).acceptedCurrencies.split(",")).toList();

		if (!super.getBuffer().getErrors().hasErrors("systemCurrency"))
			super.state(currencies.contains(object.getSystemCurrency()), "systemCurrency", "system.form.error.currency-not-suported");
		super.state(this.checkUniqueElements(object.systemCurrency), "systemCurrency", "system.form.error.duplicated-currency");

		if (!super.getBuffer().getErrors().hasErrors("acceptedCurrencies")) {

			Set<String> inputedCurrencies = this.convertToList(object.getAcceptedCurrencies());

			super.state(inputedCurrencies.containsAll(currencies), "acceptedCurrencies", "system.form.error.can-not-delete-currencies");
			super.state(this.checkUniqueElements(object.acceptedCurrencies), "acceptedCurrencies", "system.form.error.duplicated-currency");
		}

	}

	private Set<String> convertToList(final String acceptedCurrencies) {
		String[] newCurrenciesArray = acceptedCurrencies.split(",");
		Set<String> newCurrencySet = new HashSet<>();
		for (String currency : newCurrenciesArray)
			newCurrencySet.add(currency.trim());

		return newCurrencySet;
	}

	private boolean checkUniqueElements(final String string) {
		String[] elements = string.split(",");
		Set<String> uniqueElements = new HashSet<>();

		for (String e : elements)
			if (!uniqueElements.add(e.trim()))
				return false;

		return true;

	}

	@Override
	public void perform(final SystemConfiguration object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "systemCurrency", "acceptedCurrencies");

		super.getResponse().addData(dataset);
	}

}
