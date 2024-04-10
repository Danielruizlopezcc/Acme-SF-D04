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
	<acme:input-textbox code="client.contract.form.label.code" path="code" placeholder="client.contract.form.code.placeholder"/>
	<acme:input-moment code="client.contract.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="client.contract.form.label.providerName" path="providerName" placeholder="client.contract.form.provider.placeholder"/>	
	<acme:input-textbox code="client.contract.form.label.customerName" path="customerName" placeholder="client.contract.form.customer.placeholder"/>	
	<acme:input-textarea code="client.contract.form.label.goals" path="goals" placeholder="client.contract.form.goals.placeholder"/>
	<acme:input-money code="client.contract.form.label.budget" path="budget"/>
	<acme:input-select code="client.contract.form.label.project" path="project" choices="${projects}"/>
	
	<jstl:choose>	
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="client.contract.form.button.progressLogs" action="/client/progress-log/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:submit code="client.contract.form.button.update" action="/client/contract/update"/>
			<acme:submit code="client.contract.form.button.delete" action="/client/contract/delete"/>
			<acme:submit code="client.contract.form.button.publish" action="/client/contract/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.contract.form.button.create-contract" action="/client/contract/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>