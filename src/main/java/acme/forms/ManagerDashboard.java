
package acme.forms;

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
	double						averageProjectCost;
	double						deviationProjectCost;
	double						minimumProjectCost;
	double						maximumProjectCost;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
