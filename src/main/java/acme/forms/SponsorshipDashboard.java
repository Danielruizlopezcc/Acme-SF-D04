
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

	double						totalNumInvoicesWithTaxLessOrEqualTo21;
	double						totalNumInvoicesWithLink;
	double						averageSponsorshipsAmount;
	double						deviationSponsorshipsAmount;
	double						minimumSponsorshipsAmount;
	double						maximunSponsorshipsAmount;
	double						averageInvoicesQuantity;
	double						deviationInvoicesQuantity;
	double						minimumInvoicesQuantity;
	double						maximunInvoicesQuantity;
}
