
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Double						totalCodeAuditsStatic;
	Double						totalCodeAuditsDynamic;
	Double						averageCodeAuditsPeriodLenght;
	Double						deviationCodeAuditsPeriodLenght;
	Double						minimumCodeAuditsPeriodLenght;
	Double						maximumCodeAuditsPeriodLenght;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
