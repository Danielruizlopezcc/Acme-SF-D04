<%--
- list.jsp
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

<acme:list>
	<acme:list-column code="sponsor.invoice.list.label.code" path="code" width="10%"/>
	<acme:list-column code="sponsor.invoice.list.label.registration-time" path="registrationTime" width="10%"/>
	<acme:list-column code="sponsor.invoice.list.label.due-date" path="dueDate" width="10%"/>	
	<acme:list-column code="sponsor.invoice.list.label.quantity" path="quantity" width="10%"/>	
	<acme:list-column code="sponsor.invoice.list.label.tax" path="tax" width="10%"/>	
	<acme:list-column code="sponsor.invoice.list.label.link" path="link" width="10%"/>
	<acme:list-column code="sponsor.invoice.list.label.total-amount" path="totalAmount" width="10%"/>
	<acme:list-column code="sponsor.invoice.list.label.draftMode" path="draftMode" width="10%"/>
		
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="sponsor.invoice.list.button.create-form" action="/sponsor/invoice/create"/>
</jstl:if>