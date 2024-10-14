
package acme.features.auditor.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int auditorId;
		AuditorDashboard dashboard;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		Collection<Double> auditRecordsPerAudit = this.repository.auditRecordsPerAudit(auditorId);

		int totalCodeAuditsStatic;
		int totalCodeAuditsDynamic;
		Double auditRecordsAverage;
		Double auditRecordsDeviation;
		Integer auditRecordsMinimum;
		Integer auditRecordsMaximum;
		Double periodAverageTime;
		Double periodDeviationTime;
		Double periodMinimumTime;
		Double periodMaximumTime;

		totalCodeAuditsStatic = this.repository.totalCodeAuditsStatic(auditorId);
		totalCodeAuditsDynamic = this.repository.totalCodeAuditsDynamic(auditorId);
		auditRecordsAverage = this.repository.auditRecordsAverage(auditorId);
		auditRecordsDeviation = this.deviation(auditRecordsPerAudit);
		auditRecordsMinimum = this.repository.auditRecordsMinimum(auditorId);
		auditRecordsMaximum = this.repository.auditRecordsMaximum(auditorId);
		periodAverageTime = this.repository.periodAverageTime(auditorId);
		periodDeviationTime = this.repository.periodDeviationTime(auditorId);
		periodMinimumTime = this.repository.periodMinimumTime(auditorId);
		periodMaximumTime = this.repository.periodMaximumTime(auditorId);

		dashboard = new AuditorDashboard();
		dashboard.setTotalCodeAuditsStatic(totalCodeAuditsStatic);
		dashboard.setTotalCodeAuditsDynamic(totalCodeAuditsDynamic);
		dashboard.setAuditRecordsAverage(auditRecordsAverage);
		dashboard.setAuditRecordsDeviation(auditRecordsDeviation);
		dashboard.setAuditRecordsMinimum(auditRecordsMinimum);
		dashboard.setAuditRecordsMaximum(auditRecordsMaximum);
		dashboard.setPeriodAverageTime(periodAverageTime);
		dashboard.setPeriodDeviationTime(periodDeviationTime);
		dashboard.setPeriodMinimumTime(periodMinimumTime);
		dashboard.setPeriodMaximumTime(periodMaximumTime);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "totalCodeAuditsStatic", "totalCodeAuditsDynamic", "auditRecordsAverage", "auditRecordsDeviation", "auditRecordsMinimum", "auditRecordsMaximum", "periodAverageTime", "periodDeviationTime", "periodMinimumTime",
			"periodMaximumTime");

		super.getResponse().addData(dataset);
	}

	public Double deviation(final Collection<Double> values) {
		Double res;
		Double aux;
		res = 0.0;
		if (values.isEmpty())
			return null;

		if (!values.isEmpty()) {
			Double average = this.average(values);
			aux = 0.0;
			for (final Double value : values)
				aux += Math.pow(value + average, 2);
			res = Math.sqrt(aux / values.size());
		}
		return res;
	}

	private Double average(final Collection<Double> values) {
		double sum = 0.0;
		for (Double value : values)
			sum += value;
		return sum / values.size();
	}

}
