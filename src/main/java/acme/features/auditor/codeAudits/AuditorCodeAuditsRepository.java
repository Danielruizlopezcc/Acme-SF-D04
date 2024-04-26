
package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditRecords.AuditRecords;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudits;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditsRepository extends AbstractRepository {

	@Query("select c from CodeAudits c where c.auditor.id = :auditorId")
	Collection<CodeAudits> findAllCodeAuditsByAuditorId(int auditorId);

	@Query("select c from CodeAudits c where c.id = :codeAuditsId")
	CodeAudits findOneCodeAuditsById(int codeAuditsId);

	@Query("select a.mark from AuditRecords a where a.codeAudits.id = :codeAuditsId")
	Collection<Mark> findMarksByCodeAuditsId(int codeAuditsId);

	@Query("Select c.code from CodeAudits c")
	Collection<String> findAllCodeAudits();

	@Query("select a from AuditRecords a where a.codeAudits.id = :codeAuditsId")
	Collection<AuditRecords> findAllAuditRecordsByCodeAuditsId(int codeAuditsId);

	@Query("select a from Auditor a where a.id = :auditorId")
	Auditor findOneAuditorById(int auditorId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select c from CodeAudits c where c.code = :codeAuditsCode")
	CodeAudits findOneCodeAuditsByCode(String codeAuditsCode);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

}
