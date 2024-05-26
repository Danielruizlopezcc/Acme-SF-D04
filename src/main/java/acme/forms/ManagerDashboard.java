
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	double						totalUserStoriesWithPriorityMust;
	double						totalUserStoriesWithPriorityShould;
	double						totalUserStoriesWithPriorityCould;
	double						totalUserStoriesWithPriorityWont;
	double						averageUserStoriesEstimatedCost;
	double						deviationUserStoriesEstimatedCost;
	double						minimumUserStoriesEstimatedCost;
	double						maximumUserStoriesEstimatedCost;

	Map<String, Double>			averageProjectCostPerCurrency;
	Map<String, Double>			deviationProjectCostPerCurrency;
	Map<String, Double>			minimumProjectCostPerCurrency;
	Map<String, Double>			maximumProjectCostPerCurrency;
	String[]					supportedCurrencies;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
