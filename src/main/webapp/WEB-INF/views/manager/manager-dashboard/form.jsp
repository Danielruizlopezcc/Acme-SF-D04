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


<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-must-priority-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalUserStoriesWithPriorityMust}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-should-priority-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalUserStoriesWithPriorityShould}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-could-priority-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalUserStoriesWithPriorityCould}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-wont-priority-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalUserStoriesWithPriorityWont}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimum-user-story-cost"/>
		</th>
		<td>
			<acme:print value="${minimumUserStoriesEstimatedCost}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.maximum-user-story-cost"/>
		</th>
		<td>
			<acme:print value="${maximumUserStoriesEstimatedCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.average-user-story-cost"/>
		</th>
		<td>
			<acme:print value="${averageUserStoriesEstimatedCost}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviation-user-story-cost"/>
		</th>
		<td>
			<acme:print value="${deviationUserStoriesEstimatedCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimum-project-cost"/>
		</th>
		<td>
			<acme:print value="${minimumProjectCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.maximum-project-cost"/>
		</th>
		<td>
			<acme:print value="${maximumProjectCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.average-project-cost"/>
		</th>
		<td>
			<acme:print value="${averageProjectCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviation-project-cost"/>
		</th>
		<td>
			<acme:print value="${deviationProjectCost}"/>
		</td>
	</tr>
	
</table>
<acme:return/>