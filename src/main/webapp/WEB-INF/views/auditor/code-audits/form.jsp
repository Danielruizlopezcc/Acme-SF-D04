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
	<acme:input-textbox code="auditor.code-audits.form.label.code" path="code" placeholder="ABC-000" />
	<acme:input-moment code="auditor.code-audits.form.label.execution-date" path="executionDate"/>
	<acme:input-select code="auditor.code-audits.form.label.type" path="type" choices="${codeAuditsType}"/>	
	<acme:input-textbox code="auditor.code-audits.form.label.corrective-actions" path="correctiveActions"/>	
	<acme:input-select code="auditor.code-audits.form.label.mark" path="mark" choices="${mark}"/>
	<acme:input-url code="auditor.code-audits.form.label.link" path="link"/>	
	<acme:input-select code="auditor.code-audits.form.label.project" path="project" choices="${projects}"/>	
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="auditor.code-audits.form.button.audit-records" action="/auditor/audit-records/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="auditor.code-audits.form.button.update" action="/auditor/code-audits/update"/>
			<acme:submit code="auditor.code-audits.form.button.delete" action="/auditor/code-audits/delete"/>
			<acme:submit code="auditor.code-audits.form.button.publish" action="/auditor/code-audits/publish"/>
			<acme:button code="auditor.code-audits.form.button.audit-records" action="/auditor/audit-records/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.code-audits.form.button.create-code-audits" action="/auditor/code-audits/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>