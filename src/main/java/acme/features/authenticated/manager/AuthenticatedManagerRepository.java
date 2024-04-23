
package acme.features.authenticated.manager;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.roles.Manager;

@Repository
public interface AuthenticatedManagerRepository extends AbstractRepository {

	@Query("select m from Manager m where m.userAccount.id = :id")
	Manager findOneManagerByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

}
