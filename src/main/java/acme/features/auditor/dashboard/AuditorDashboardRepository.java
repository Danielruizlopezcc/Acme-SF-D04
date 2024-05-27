
package acme.features.auditor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select a from Auditor a where a.userAccount.id = :id")
	Auditor findAuditorById(int id);

	@Query("select (select count(ar) from AuditRecords ar where ar.codeAudits.id = a.id) from CodeAudits a where a.auditor.id = :id")
	Collection<Double> auditingRecordsPerAudit(int id);

	@Query("select avg(select count(ar) from AuditRecords ar where ar.codeAudits.id = a.id) from CodeAudits a where a.auditor.id = :id")
	Double averageAuditingRecords(int id);

	@Query("select max(select count(ar) from AuditRecords ar where ar.codeAudits.id = a.id) from CodeAudits a where a.auditor.id = :id")
	Integer maxAuditingRecords(int id);

	@Query("select min(select count(ar) from AuditRecords ar where ar.codeAudits.id = a.id) from CodeAudits a where a.auditor.id = :id")
	Integer minAuditingRecords(int id);

	@Query("select avg(timestampdiff(SECOND, ar.startPeriod, ar.endPeriod) / 3600.0) from AuditRecords ar where ar.codeAudits.auditor.id = :id")
	Double averageRecordPeriod(int id);

	@Query("select stddev(timestampdiff(SECOND, ar.startPeriod, ar.endPeriod) / 3600.0) from AuditRecords ar where ar.codeAudits.auditor.id = :id")
	Double deviationRecordPeriod(int id);

	@Query("select min(timestampdiff(SECOND, ar.startPeriod, ar.endPeriod) / 3600.0) from AuditRecords ar where ar.codeAudits.auditor.id = :id")
	Double minimumRecordPeriod(int id);

	@Query("select max(timestampdiff(SECOND, ar.startPeriod, ar.endPeriod) / 3600.0) from AuditRecords ar where ar.codeAudits.auditor.id = :id")
	Double maximumRecordPeriod(int id);

	@Query("select count(a) from CodeAudits a where a.auditor.id = :auditorId and a.type = acme.entities.codeAudits.CodeAuditsType.STATIC")
	Double totalAuditRecordsStatic(int auditorId);

	@Query("select count(a) from CodeAudits a where a.auditor.id = :auditorId and a.type = acme.entities.codeAudits.CodeAuditsType.DYNAMIC")
	Double totalAuditRecordsDynamic(int auditorId);

}
