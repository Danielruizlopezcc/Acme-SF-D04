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
	<acme:list-column code="any.contract.list.label.code" path="code" width="10%"/>
	<acme:list-column code="any.contract.list.label.instantiationMoment" path="instantiationMoment" width="10%"/>
	<acme:list-column code="any.contract.list.label.providerName" path="providerName" width="10%"/>	
	<acme:list-column code="any.contract.list.label.customerName" path="customerName" width="10%"/>	
	<acme:list-column code="any.contract.list.label.budget" path="budget" width="10%"/>
	<acme:list-column code="any.contract.list.label.project" path="project.code" width="10%"/>
	<acme:list-payload path="payload"/>
</acme:list>