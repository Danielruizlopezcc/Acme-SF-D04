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
	<acme:message code="sponsor.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.invoices-with-tax-less-or-equal-to-21"/>
		</th>
		<td>
			<acme:print value="${totalNumInvoicesWithTaxLessOrEqualTo21}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.invoices-with-link"/>
		</th>
		<td>
			<acme:print value="${totalNumInvoicesWithLink}"/>
		</td>
	</tr>
	</table>

<jstl:forEach var="currency" items="${supportedCurrencies}">
    <h2>
        <acme:message code="sponsor.dashboard.form.label.sponsorship-general-indicators"/>
        <acme:message code="${currency}"/>
    </h2>

    <table class="table table-sm">
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.sponsorship-amount-average"/>
            </th>
            <td>
                <acme:print value="${averageSponsorshipsAmount[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.sponsorship-amount-deviation"/>
            </th>
            <td>
                <acme:print value="${deviationSponsorshipsAmount[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.sponsorship-amount-minimum"/>
            </th>
            <td>
                <acme:print value="${minimumSponsorshipsAmount[currency]}"/>
            </td>
        </tr>   
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.sponsorship-amount-maximum"/>
            </th>
            <td>
                <acme:print value="${maximumSponsorshipsAmount[currency]}"/>
            </td>
        </tr>
    </table>
</jstl:forEach>

<jstl:forEach var="currency" items="${supportedCurrencies}">
    <h2>
        <acme:message code="sponsor.dashboard.form.label.invoice-general-indicators"/>
        <acme:message code="${currency}"/>
    </h2>

    <table class="table table-sm">
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.invoice-quantity-average"/>
            </th>
            <td>
                <acme:print value="${averageInvoicesQuantity[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.invoice-quantity-deviation"/>
            </th>
            <td>
                <acme:print value="${deviationInvoicesQuantity[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.invoice-quantity-minimum"/>
            </th>
            <td>
                <acme:print value="${minimumInvoicesQuantity[currency]}"/>
            </td>
        </tr>   
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.invoice-quantity-maximum"/>
            </th>
            <td>
                <acme:print value="${maximumInvoicesQuantity[currency]}"/>
            </td>
        </tr>
    </table>
</jstl:forEach>