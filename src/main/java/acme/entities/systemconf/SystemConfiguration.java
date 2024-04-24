
package acme.entities.systemconf;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}$")
	public String				systemCurrency;

	@NotBlank
	@Pattern(regexp = "^([A-Z]{3}(?:,|,\s))*[A-Z]{3}$")
	public String				acceptedCurrencies;

}
