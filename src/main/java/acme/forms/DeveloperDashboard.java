
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	double						totalTrainingModulesWithUpdateMoment;
	double						totalTrainingSessionsWithLink;
	double						averageTrainingModulesTime;
	double						deviatonTrainingModulesTime;
	double						minimumTrainingModulesTime;
	double						maximumTrainingModulesTime;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
