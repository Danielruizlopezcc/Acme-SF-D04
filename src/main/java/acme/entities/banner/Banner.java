
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.accounts.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Past
	private Date				instantiationMoment;

	@NotNull
	@FutureOrPresent
	private Date				displayPeriodStart;

	@NotNull
	@FutureOrPresent
	private Date				displayPeriodEnd;

	@URL
	@NotBlank
	private String				picture;

	@NotBlank
	@Length(max = 75)
	private String				slogan;

	@URL
	@NotBlank
	private String				link;

	// Derived attributes -----------------------------------------------------


	private Date displayPeriod() {
		Long difference = this.displayPeriodEnd.getTime() - this.displayPeriodStart.getTime();
		return new Date(difference);
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private UserAccount user;

}
