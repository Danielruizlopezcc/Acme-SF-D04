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
	<acme:input-textbox code="any.contract.form.label.code" path="code"/>
	<acme:input-moment code="any.contract.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="any.contract.form.label.providerName" path="providerName"/>	
	<acme:input-textbox code="any.contract.form.label.customerName" path="customerName"/>	
	<acme:input-textarea code="any.contract.form.label.goals" path="goals" />
	<acme:input-money code="any.contract.form.label.budget" path="budget"/>
	<acme:input-select code="any.contract.form.label.project" path="project" choices="${projects}"/>
	
	<acme:button code="any.contract.form.button.progress-logs" action="/any/progress-log/list?masterId=${id}"/>
	
</acme:form>