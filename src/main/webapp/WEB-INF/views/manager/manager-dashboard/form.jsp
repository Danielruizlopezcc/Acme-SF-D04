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
	<acme:message code="client.dashboard.form.title.general-indicators"/>
</h2>

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
	</table>

<jstl:forEach var="currency" items="${supportedCurrencies}">
    <h2>
        <acme:message code="manager.dashboard.form.label.project-indicators"/>
        <acme:message code="${currency}"/>
    </h2>

    <table class="table table-sm">
        <tr>
            <th scope="row">
                <acme:message code="manager.dashboard.form.label.project-average"/>
            </th>
            <td>
                <acme:print value="${averageProjectCostPerCurrency[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="manager.dashboard.form.label.project-deviation"/>
            </th>
            <td>
                <acme:print value="${deviationProjectCostPerCurrency[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="manager.dashboard.form.label.project-minimum"/>
            </th>
            <td>
                <acme:print value="${minimumProjectCostPerCurrency[currency]}"/>
            </td>
        </tr>   
        <tr>
            <th scope="row">
                <acme:message code="manager.dashboard.form.label.project-maximum"/>
            </th>
            <td>
                <acme:print value="${maximumProjectCostPerCurrency[currency]}"/>
            </td>
        </tr>
    </table>
</jstl:forEach>