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
	<acme:input-textbox code="authenticated.sponsor.form.label.name" path="name"/>
	<acme:input-textarea code="authenticated.sponsor.form.label.expected-benefits" path="expectedBenefits"/>
	<acme:input-url code="authenticated.sponsor.form.label.web-page" path="webPage"/>
	<acme:input-email code="authenticated.sponsor.form.label.email-contact" path="emailContact"/>

	<acme:submit test="${_command == 'create'}" code="authenticated.sponsor.form.button.create" action="/authenticated/manager/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.sponsor.form.button.update" action="/authenticated/sponsor/update"/>
</acme:form>