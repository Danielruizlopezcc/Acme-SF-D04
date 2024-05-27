<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit-records.list.label.code" path="code"/>
	<acme:list-column code="auditor.audit-records.list.label.startPeriod" path="startPeriod"/>
	<acme:list-column code="auditor.audit-records.list.label.link" path="link"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="auditor.audit-records.list.button.create" action="/auditor/audit-records/create?masterId=${masterId}"/>
</jstl:if>