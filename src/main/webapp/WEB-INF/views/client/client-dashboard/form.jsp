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
			<acme:message code="client.dashboard.form.label.progress-log-less-than-25"/>
		</th>
		<td>
			<acme:print value="${totalNumProgressLogLessThan25}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.progress-log-between-25-50"/>
		</th>
		<td>
			<acme:print value="${totalNumProgressLogBetween25And50}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.progress-log-between-50-75"/>
		</th>
		<td>
			<acme:print value="${totalNumProgressLogBetween50And75}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.progress-log-above-75"/>
		</th>
		<td>
			<acme:print value="${totalNumProgressLogAbove75}"/>
		</td>
	</tr>
	</table>

<jstl:forEach var="currency" items="${supportedCurrencies}">
    <h2>
        General indicators for published contracts with currency ${currency}
    </h2>

    <table class="table table-sm">
        <tr>
            <th scope="row">
                <acme:message code="client.dashboard.form.label.contract-average"/>
            </th>
            <td>
                <acme:print value="${averagePerCurrency[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="client.dashboard.form.label.contract-deviation"/>
            </th>
            <td>
                <acme:print value="${deviationPerCurrency[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="client.dashboard.form.label.contract-minimum"/>
            </th>
            <td>
                <acme:print value="${minimumPerCurrency[currency]}"/>
            </td>
        </tr>   
        <tr>
            <th scope="row">
                <acme:message code="client.dashboard.form.label.contract-maximum"/>
            </th>
            <td>
                <acme:print value="${maximumPerCurrency[currency]}"/>
            </td>
        </tr>
    </table>
</jstl:forEach>