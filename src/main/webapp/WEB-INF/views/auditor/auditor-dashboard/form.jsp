<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-integer code="auditor.dashboard.form.label.totalNumberCodeAuditsTypeStatic" path="totalCodeAuditsStatic" readonly="true"/>
	<acme:input-integer code="auditor.dashboard.form.label.totalNumberCodeAuditsTypeDynamic" path="totalCodeAuditsDynamic" readonly="true"/>
	
	<acme:input-double code="auditor.dashboard.form.label.averageNumberRecords" path="averageNumAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-double code="auditor.dashboard.form.label.deviationNumberRecords" path="deviationNumAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.minimumNumberRecords" path="minNumAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.maximumNumberRecords" path="maxNumAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-double code="auditor.dashboard.form.label.averagePeriodLength" path="averageAuditRecordsPeriodLength" readonly="true" placeholder="--"/>
	<acme:input-double code="auditor.dashboard.form.label.deviationPeriodLength" path="deviationAuditRecordsPeriodLength" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.minimumPeriodLength" path="minimumAuditRecordsPeriodLength" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.maximumPeriodLength" path="maximumAuditRecordsPeriodLength" readonly="true" placeholder="--"/>
</acme:form>