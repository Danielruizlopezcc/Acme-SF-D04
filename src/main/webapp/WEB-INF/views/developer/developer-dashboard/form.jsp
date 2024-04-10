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

<h2>
	<acme:message code="developer.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.total-training-modules-with-update-moment"/>
		</th>
		<td>
			<acme:print value="${totalTrainingModulesWithUpdateMoment}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.total-training-sessions-with-link"/>
		</th>
		<td>
			<acme:print value="${totalTrainingSessionsWithLink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.average-training-modules-time"/>
		</th>
		<td>
			<acme:print value="${averageTrainingModulesTime}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.deviation-training-modules-time"/>
		</th>
		<td>
			<acme:print value="${deviatonTrainingModulesTime}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.minimum-training-modules-time"/>
		</th>
		<td>
			<acme:print value="${minimumTrainingModulesTime}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.maximum-training-modules-time"/>
		</th>
		<td>
			<acme:print value="${maximumTrainingModulesTime}"/>
		</td>
	</tr>
</table>