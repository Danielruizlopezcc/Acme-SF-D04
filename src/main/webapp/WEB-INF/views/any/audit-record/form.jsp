
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.audit-record.form.label.code"
		path="code" />
	<acme:input-moment code="any.audit-record.form.label.audit-start-time"
		path="auditStartTime" />
	<acme:input-moment code="any.audit-record.form.label.audit-end-time"
		path="auditEndTime" />
	<acme:input-textbox code="any.audit-record.form.label.mark"
		path="mark" />
	<acme:input-url code="any.audit-record.form.label.link"
		path="link" />
</acme:form>
