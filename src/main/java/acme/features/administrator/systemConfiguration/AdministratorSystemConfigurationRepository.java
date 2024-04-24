
package acme.features.administrator.systemConfiguration;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.systemconf.SystemConfiguration;

@Repository
public interface AdministratorSystemConfigurationRepository extends AbstractRepository {

	@Query("SELECT s FROM SystemConfiguration s")
	List<SystemConfiguration> findAllSystemConfigurations();

	@Query("SELECT s FROM SystemConfiguration s WHERE s.id = :id")
	SystemConfiguration findSystemConfigurationsById(int id);

}
