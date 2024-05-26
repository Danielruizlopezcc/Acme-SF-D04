
package acme.features.auditor.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	@Autowired
	private AuditorDashboardRepository dashboardRepository;


	@Override
	public void authorise() {
		boolean status;

		Principal principal = super.getRequest().getPrincipal();
		int id = principal.getAccountId();
		Auditor auditor = this.dashboardRepository.findAuditorById(id);
		status = auditor != null && principal.hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int auditorId;
		AuditorDashboard dashboard;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		Collection<Double> auditingRecordsPerAudit = this.dashboardRepository.auditingRecordsPerAudit(auditorId);

		double totalNumCodeAuditsStatic;
		double totalNumCodeAuditsDynamic;
		double averageNumAuditRecords;
		double deviationNumAuditRecords;
		int minNumAuditRecords;
		int maxNumAuditRecords;
		double averageAuditRecordsPeriodLength;
		double deviationAuditRecordsPeriodLength;
		double minAuditRecordsPeriodLength;
		double maxAuditRecordsPeriodLength;

		totalNumCodeAuditsStatic = this.dashboardRepository.totalAuditRecordsStatic(auditorId);
		totalNumCodeAuditsDynamic = this.dashboardRepository.totalAuditRecordsDynamic(auditorId);
		averageNumAuditRecords = this.dashboardRepository.averageAuditingRecords(auditorId);
		deviationNumAuditRecords = this.calDeviation(auditingRecordsPerAudit);
		minNumAuditRecords = this.dashboardRepository.minAuditingRecords(auditorId);
		maxNumAuditRecords = this.dashboardRepository.maxAuditingRecords(auditorId);
		averageAuditRecordsPeriodLength = this.dashboardRepository.averageRecordPeriod(auditorId);
		deviationAuditRecordsPeriodLength = this.dashboardRepository.deviationRecordPeriod(auditorId);
		minAuditRecordsPeriodLength = this.dashboardRepository.minimumRecordPeriod(auditorId);
		maxAuditRecordsPeriodLength = this.dashboardRepository.maximumRecordPeriod(auditorId);

		dashboard = new AuditorDashboard();
		dashboard.setTotalCodeAuditsStatic(totalNumCodeAuditsStatic);
		dashboard.setTotalCodeAuditsDynamic(totalNumCodeAuditsDynamic);

		if (dashboard.getTotalCodeAuditsStatic() != 0 || dashboard.getTotalCodeAuditsDynamic() != 0) {
			dashboard.setAverageNumAuditRecords(averageNumAuditRecords);
			dashboard.setDeviationNumAuditRecords(deviationNumAuditRecords);
			dashboard.setMinNumAuditRecords(minNumAuditRecords);
			dashboard.setMaxNumAuditRecords(maxNumAuditRecords);
			dashboard.setAverageAuditRecordsPeriodLength(averageAuditRecordsPeriodLength);
			dashboard.setDeviationAuditRecordsPeriodLength(deviationAuditRecordsPeriodLength);
			dashboard.setMinimumAuditRecordsPeriodLength(minAuditRecordsPeriodLength);
			dashboard.setMaximumAuditRecordsPeriodLength(maxAuditRecordsPeriodLength);
		} else {
			dashboard.setAverageNumAuditRecords(0.0);
			dashboard.setDeviationNumAuditRecords(0.0);
			dashboard.setMinNumAuditRecords(0);
			dashboard.setMaxNumAuditRecords(0);
			dashboard.setAverageAuditRecordsPeriodLength(0.0);
			dashboard.setDeviationAuditRecordsPeriodLength(0.0);
			dashboard.setMinimumAuditRecordsPeriodLength(0.0);
			dashboard.setMaximumAuditRecordsPeriodLength(0.0);
		}

		super.getBuffer().addData(dashboard);
	}

	public double calDeviation(final Collection<Double> values) {
		double res = 0.0;
		if (!values.isEmpty()) {
			double average = this.calAverage(values);
			double aux = 0.0;
			for (final Double value : values)
				aux += Math.pow(value - average, 2);
			res = Math.sqrt(aux / values.size());
		}
		return res;
	}

	private double calAverage(final Collection<Double> values) {
		double sum = 0.0;
		for (Double value : values)
			sum += value;
		return sum / values.size();
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		assert object != null;

		Dataset dataset;

		int totalCodeAuditsStatic = (int) object.getTotalCodeAuditsStatic();
		int totalCodeAuditsDynamic = (int) object.getTotalCodeAuditsDynamic();

		dataset = super.unbind(object, //
			"averageNumAuditRecords", "deviationNumAuditRecords", //
			"minNumAuditRecords", "maxNumAuditRecords", //
			"averageAuditRecordsPeriodLength", "deviationAuditRecordsPeriodLength", //
			"minimumAuditRecordsPeriodLength", "maximumAuditRecordsPeriodLength");

		dataset.put("totalCodeAuditsStatic", totalCodeAuditsStatic);
		dataset.put("totalCodeAuditsDynamic", totalCodeAuditsDynamic);

		super.getResponse().addData(dataset);
	}
}
