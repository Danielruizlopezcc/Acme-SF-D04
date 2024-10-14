<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="auditor.dashboard.form.title.code-audits-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.total-code-audits-static"/>
		</th>
		<td>
			<acme:print value="${totalCodeAuditsStatic}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.total-code-audits-dynamic"/>
		</th>
		<td>
			<acme:print value="${totalCodeAuditsDynamic}"/>
		</td>
	</tr>
</table>

<h2>
	<acme:message code="auditor.dashboard.form.title.audit-records-indicators"/>
</h2>
<table class="table table-sm">
	<tr>
	<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-records-average"/>
		</th>
		<td>
			<acme:print value="${auditRecordsAverage}"/>
		</td>
	</tr>
	<tr>
	<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-records-deviation"/>
		</th>
		<td>
			<acme:print value="${auditRecordsDeviation}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-records-minimum"/>
		</th>
		<td>
			<acme:print value="${auditRecordsMinimum}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-records-maximum"/>
		</th>
		<td>
			<acme:print value="${auditRecordsMaximum}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.period-average-time"/>
		</th>
		<td>
			<acme:print value="${periodAverageTime}"/>
		</td>
	</tr>
	<tr>
	<th scope="row">
			<acme:message code="auditor.dashboard.form.label.period-deviation-time"/>
		</th>
		<td>
			<acme:print value="${periodDeviationTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.period-minimum-time"/>
		</th>
		<td>
			<acme:print value="${periodMinimumTime}"/>
		</td>
	</tr>
	<tr>
	<th scope="row">
			<acme:message code="auditor.dashboard.form.label.period-maximum-time"/>
		</th>
		<td>
			<acme:print value="${periodMaximumTime}"/>
		</td>
	</tr>

</table>
<acme:return/>