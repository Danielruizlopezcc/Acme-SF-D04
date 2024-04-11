
package acme.features.authenticated.client;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.roles.clients.Client;

@Repository
public interface AuthenticatedClientRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id= :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select c from Client c where c.userAccount.id = :id")
	Client findOneClientByUserAccountId(int id);

	@Query("select c from Client c where c.identification = :identification")
	Client findOneClientByIdentification(String identification);

}
