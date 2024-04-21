
package acme.features.sponsor.invoice;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoice.Invoice;
import acme.entities.project.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.sponsor.id = :sponsorId")
	List<Sponsorship> findAllSponsorshipsBySponsorId(int sponsorId);

	@Query("select s from Sponsorship s where s.id = :sponsorshipId")
	Sponsorship findOneSponsorshipById(int sponsorshipId);

	@Query("select i from Invoice i where i.id = :invoiceId")
	Invoice findOneInvoiceById(int invoiceId);

	@Query("select s from Sponsor s where s.id = :sponsorId")
	Sponsor findOneSponsorById(int sponsorId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select i from Invoice i where i.code = :invoiceCode")
	Invoice findOneInvoiceByCode(String invoiceCode);

	@Query("select i from Invoice i where i.sponsorship.id = :sponsorshipId")
	Collection<Invoice> findAllInvoicesBySponsorshipId(int sponsorshipId);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select s from Sponsorship s where s.invoice.id = :invoiceId")
	Sponsorship findOneSponsorshipByInvoiceId(int invoiceId);

}
