
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorshipDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Atributes --------------------------------------------------------------

	Double						totalNumInvoicesWithTaxLessOrEqualTo21;
	Double						totalNumInvoicesWithLink;
	Double						averageSponsorshipsAmount;
	Double						deviationSponsorshipsAmount;
	Double						minimumSponsorshipsAmount;
	Double						maximunSponsorshipsAmount;
	Double						averageInvoicesQuantity;
	Double						deviationInvoicesQuantity;
	Double						minimumInvoicesQuantity;
	Double						maximunInvoicesQuantity;
}
