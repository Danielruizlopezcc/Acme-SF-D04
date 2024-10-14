<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.audit-record.list.label.code" path="code" width="10%"/>
	<acme:list-column code="any.audit-record.list.label.audit-start-time" path="auditStartTime" width="10%"/>
	<acme:list-column code="any.audit-record.list.label.audit-end-time" path="auditEndTime" width="10%"/>
	<acme:list-column code="any.audit-record.list.label.mark" path="mark" width="10%"/>
	<acme:list-column code="any.audit-record.list.label.link" path="link" width="10%"/>		
</acme:list>