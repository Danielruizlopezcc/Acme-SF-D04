
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

	double						totalCodeAuditsStatic;
	double						totalCodeAuditsDynamic;
	double						averageNumAuditRecords;
	double						deviationNumAuditRecords;
	int							minNumAuditRecords;
	int							maxNumAuditRecords;
	double						averageAuditRecordsPeriodLength;
	double						deviationAuditRecordsPeriodLength;
	double						minimumAuditRecordsPeriodLength;
	double						maximumAuditRecordsPeriodLength;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
