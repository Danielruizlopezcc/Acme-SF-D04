
package acme.entities.sponsorship;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.project.Project;
import acme.roles.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$")
	@Column(unique = true)
	private String				code;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				moment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				durationStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				durationEnd;

	@NotNull
	@Valid
	private Money				amount;

	@NotNull
	private SponsorshipType		type;

	@Email
	private String				emailContact;

	@URL
	private String				link;

	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsor				sponsor;
}
