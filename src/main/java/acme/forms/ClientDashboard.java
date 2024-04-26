
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// total number of progress logs with a completeness rate below 25%
	double						totalNumProgressLogLessThan25;

	// total number of progress logs with a completeness rate between 25% and 50%
	double						totalNumProgressLogBetween25And50;

	// total number of progress logs with a completeness rate between 50% and 75%
	double						totalNumProgressLogBetween50And75;

	// total number of progress logs with a completeness rate above 75%
	double						totalNumProgressLogAbove75;

	// average, deviation, minimum, and maximum budget of the contracts

	Map<String, Double>			averagePerCurrency;

	Map<String, Double>			deviationPerCurrency;

	Map<String, Double>			maximumPerCurrency;

	Map<String, Double>			minimumPerCurrency;

	String[]					supportedCurrencies;

}
