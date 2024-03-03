
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Developer extends AbstractRole {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	private String				company;

	@NotBlank
	private String				sector;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
