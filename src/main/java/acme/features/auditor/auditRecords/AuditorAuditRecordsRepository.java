
package acme.features.auditor.auditRecords;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditRecords.AuditRecords;
import acme.entities.codeAudits.CodeAudits;

@Repository
public interface AuditorAuditRecordsRepository extends AbstractRepository {

	@Query("select c from CodeAudits c where c.id = :codeAuditsId")
	CodeAudits findOneCodeAuditsById(int codeAuditsId);

	@Query("select a from AuditRecords a where a.code = :codeAuditRecords")
	AuditRecords findOneAuditRecordsByCode(String codeAuditRecords);

	@Query("Select c.code from CodeAudits c")
	Collection<String> findAllCodeAuditsCode();

	@Query("Select c from CodeAudits c")
	Collection<CodeAudits> findAllCodeAudits();

	@Query("select ca.codeAudits from AuditRecords ca where ca.id = :caId")
	CodeAudits findOneCodeAuditsByAuditRecordsId(int caId);

	@Query("select a from AuditRecords a where a.id = :auditRecordsId")
	AuditRecords findOneAuditRecordsById(int auditRecordsId);

	@Query("select ar from AuditRecords ar where ar.codeAudits.id = :caId")
	Collection<AuditRecords> findAllAuditRecordsByMasterId(int caId);

}
