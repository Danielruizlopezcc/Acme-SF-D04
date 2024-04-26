
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface AnySponsorshipRepository extends AbstractRepository {

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select s from Sponsorship s where s.draftMode = false")
	Collection<Sponsorship> findAllPublishedSponsorships();

	@Query("select s from Sponsorship s where s.id = :sponsorshipId")
	Sponsorship findSponsorshipById(int sponsorshipId);

}
