
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

	Double						totalUserStoriesWithPriorityMust;
	Double						totalUserStoriesWithPriorityShould;
	Double						totalUserStoriesWithPriorityCould;
	Double						totalUserStoriesWithPriorityWont;
	Double						averageUserStoriesEstimatedCost;
	Double						deviationUserStoriesEstimatedCost;
	Double						minimumUserStoriesEstimatedCost;
	Double						maximumUserStoriesEstimatedCost;
	Double						averageProjectCost;
	Double						deviationProjectCost;
	Double						minimumProjectCost;
	Double						maximumProjectCost;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
