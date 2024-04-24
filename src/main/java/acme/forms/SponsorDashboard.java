
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Atributes --------------------------------------------------------------

	double						totalNumInvoicesWithTaxLessOrEqualTo21;
	double						totalNumInvoicesWithLink;
	Map<String, Double>			averageSponsorshipsAmount;
	Map<String, Double>			deviationSponsorshipsAmount;
	Map<String, Double>			minimumSponsorshipsAmount;
	Map<String, Double>			maximumSponsorshipsAmount;
	Map<String, Double>			averageInvoicesQuantity;
	Map<String, Double>			deviationInvoicesQuantity;
	Map<String, Double>			minimumInvoicesQuantity;
	Map<String, Double>			maximumInvoicesQuantity;

	String[]					supportedCurrencies;
}
