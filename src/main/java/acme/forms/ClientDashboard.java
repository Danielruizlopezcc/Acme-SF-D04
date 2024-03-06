
package acme.forms;

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
	Integer						totalNumProgressLogLessThan25;

	// total number of progress logs with a completeness rate between 25% and 50%
	Integer						totalNumProgressLogLessBetween25And50;

	// total number of progress logs with a completeness rate between 50% and 75%
	Integer						totalNumProgressLogLessBetween50And75;

	// total number of progress logs with a completeness rate above 75%
	Integer						totalNumProgressLogAbove75;

	// average, deviation, minimum, and maximum budget of the contracts

	Double						averageBudget;

	Double						deviationBudget;

	Double						minimumBudget;

	Double						maximumBudget;

}
