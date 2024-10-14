
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

	int							totalCodeAuditsStatic;
	int							totalCodeAuditsDynamic;
	Double						auditRecordsAverage;
	Double						auditRecordsDeviation;
	Integer						auditRecordsMinimum;
	Integer						auditRecordsMaximum;
	Double						periodAverageTime;
	Double						periodDeviationTime;
	Double						periodMinimumTime;
	Double						periodMaximumTime;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
