
package acme.features.any.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.Any;
import acme.client.repositories.AbstractRepository;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.codeAudits.CodeAudit;

@Repository
public interface AnyAuditRecordRepository extends AbstractRepository {

	@Query("select ar from AuditRecord ar where ar.codeAudit.id=:id")
	Collection<AuditRecord> findAuditRecordsByCodeAuditId(int id);

	@Query("select ar from AuditRecord ar where ar.id = :id")
	AuditRecord findOneAuditRecordById(int id);

	@Query("select a from Any a where a.id = :id")
	Any findOneAnyById(int id);

	@Query("select ar from AuditRecord ar where ar.code=:code")
	AuditRecord findOneAuditRecordByCode(String code);

	@Query("select ca from CodeAudit ca where ca.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("SELECT ar FROM AuditRecord ar WHERE ar.draftMode = false")
	Collection<AuditRecord> findPublishedAuditRecords();

}
