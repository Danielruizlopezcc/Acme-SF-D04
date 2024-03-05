
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
	private Integer				totalNumProgressLogLessThan25;

	// total number of progress logs with a completeness rate between 25% and 50%
	private Integer				totalNumProgressLogLessBetween25And50;

	// total number of progress logs with a completeness rate between 50% and 75%
	private Integer				totalNumProgressLogLessBetween50And75;

	// total number of progress logs with a completeness rate above 75%
	private Integer				totalNumProgressLogAbove75;

	// average, deviation, minimum, and maximum budget of the contracts

	private Double				average;

	private Double				deviation;

	private Double				minimum;

	private Double				maximum;

}
