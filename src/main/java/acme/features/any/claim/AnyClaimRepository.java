
package acme.features.any.claim;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.claim.Claim;

@Repository
public interface AnyClaimRepository extends AbstractRepository {

	@Query("SELECT c FROM Claim c")
	Collection<Claim> findAllClaims();

	@Query("SELECT c FROM Claim c WHERE c.id = :id")
	Claim findClaimById(int id);
}
