
package acme.entities.risk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.accounts.Administrator;
import acme.entities.project.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Risk extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^R-[0-9]{3}$")
	@Column(unique = true)
	private String				reference;

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	private Date				identificationDate;

	@NotNull
	@Min(0)
	private Double				impact;

	@NotNull
	@Min(0)
	private Double				probability;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@URL
	private String				optionalLink;

	// Derived attributes -----------------------------------------------------


	private Double value() {
		return this.impact * this.probability;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Administrator	admin;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project			project;

}
