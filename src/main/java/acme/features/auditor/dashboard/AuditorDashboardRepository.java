
package acme.features.auditor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select count(ca) from CodeAudit ca where ca.type = acme.entities.codeAudits.CodeAuditType.Static and ca.auditor.id = :auditorId and ca.draftMode=false")
	int totalCodeAuditsStatic(int auditorId);

	@Query("select count(ca) from CodeAudit ca where ca.type = acme.entities.codeAudits.CodeAuditType.Dynamic and ca.auditor.id = :auditorId and ca.draftMode=false")
	int totalCodeAuditsDynamic(int auditorId);

	@Query("select (select count(ar) from AuditRecord ar where ar.codeAudit.id = ca.id and ar.draftMode=false) from CodeAudit ca where ca.auditor.id = :auditorId and ca.draftMode=false")
	Collection<Double> auditRecordsPerAudit(int auditorId);

	@Query("select avg(select count(ar) from AuditRecord ar where ar.codeAudit.id = ca.id and ar.draftMode=false) from CodeAudit ca where ca.auditor.id = :auditorId and ca.draftMode=false")
	Double auditRecordsAverage(int auditorId);

	@Query("select min(select count(ar) from AuditRecord ar where ar.codeAudit.id = ca.id and ar.draftMode=false) from CodeAudit ca where ca.auditor.id = :auditorId and ca.draftMode=false")
	Integer auditRecordsMinimum(int auditorId);

	@Query("select max(select count(ar) from AuditRecord ar where ar.codeAudit.id = ca.id and ar.draftMode=false) from CodeAudit ca where ca.auditor.id = :auditorId and ca.draftMode=false")
	Integer auditRecordsMaximum(int auditorId);

	@Query("select avg(time_to_sec(timediff(ar.auditEndTime, ar.auditStartTime)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId and ar.draftMode=false")
	Double periodAverageTime(int auditorId);

	@Query("select stddev(time_to_sec(timediff(ar.auditEndTime, ar.auditStartTime)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId and ar.draftMode=false")
	Double periodDeviationTime(int auditorId);

	@Query("select min(time_to_sec(timediff(ar.auditEndTime, ar.auditStartTime)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId and ar.draftMode=false")
	Double periodMinimumTime(int auditorId);

	@Query("select max(time_to_sec(timediff(ar.auditEndTime, ar.auditStartTime)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId and ar.draftMode=false")
	Double periodMaximumTime(int auditorId);

}
