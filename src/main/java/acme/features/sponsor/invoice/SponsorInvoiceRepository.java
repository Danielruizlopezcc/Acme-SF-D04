
package acme.features.sponsor.invoice;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.systemconf.SystemConfiguration;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.id = :sponsorshipId")
	Sponsorship findOneSponsorshipById(int sponsorshipId);

	@Query("select i from Invoice i where i.id = :invoiceId")
	Invoice findOneInvoiceById(int invoiceId);

	@Query("select i from Invoice i where i.code = :invoiceCode")
	Invoice findOneInvoiceByCode(String invoiceCode);

	@Query("select i from Invoice i where i.sponsorship.id = :sponsorshipId")
	Collection<Invoice> findAllInvoicesByMasterId(int sponsorshipId);

	@Query("select i.sponsorship from Invoice i where i.id = :invoiceId")
	Sponsorship findOneSponsorshipByInvoiceId(int invoiceId);

	@Query("select s from SystemConfiguration s")
	List<SystemConfiguration> findSystemConfiguration();

}
