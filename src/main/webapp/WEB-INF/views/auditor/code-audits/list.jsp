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
	<acme:list-column code="auditor.code-audits.list.label.code" path="code" width="10%"/>
	<acme:list-column code="auditor.code-audits.list.label.execution-date" path="executionDate" width="10%"/>
	<acme:list-column code="auditor.code-audits.list.label.type" path="type" width="10%"/>	
	<acme:list-column code="auditor.code-audits.list.label.corrective-actions" path="correctiveActions" width="10%"/>	
	<acme:list-column code="auditor.code-audits.list.label.markMode" path="markMode" width="10%"/>
	<acme:list-column code="auditor.code-audits.list.label.link" path="link" width="10%"/>
	<acme:list-column code="auditor.code-audits.list.label.draft-mode" path="draftMode" width="10%"/>
	<acme:list-column code="auditor.code-audits.list.label.project" path="project" width="10%"/>
 </acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="auditor.code-audits.list.button.create-code-audits" action="/auditor/code-audits/create"/>
</jstl:if>