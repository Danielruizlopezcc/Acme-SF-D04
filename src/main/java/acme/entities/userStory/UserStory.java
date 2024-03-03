
package acme.entities.userStory;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.accounts.UserAccount;
import acme.entities.project.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStory extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@Min(1)
	private Integer				estimadtedCost;

	@NotBlank
	@Length(max = 100)
	private Integer				acceptanceCriteria;

	@NotNull
	private Priority			priority;

	@URL
	private String				optionalLink;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private UserAccount			user;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

}
