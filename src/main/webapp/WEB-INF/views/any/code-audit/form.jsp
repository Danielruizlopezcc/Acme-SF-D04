<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.code-audit.form.label.code"
		path="code" />
	<acme:input-moment code="any.code-audit.form.label.execution"
		path="execution" />
	<acme:input-textbox code="any.code-audit.form.label.type"
		path="type" />
	<acme:input-textbox code="any.code-audit.form.label.proposed-corrective-actions"
		path="proposedCorrectiveActions" />
	<acme:input-url code="any.code-audit.form.label.link"
		path="link" />
	<acme:input-textbox code="any.code-audit.form.label.mark"
		path="mark" readonly="true"/>
	<acme:input-textbox code="any.code-audit.form.label.project"
		path="project" />
	<acme:button code="any.code-audit.form.button.audit-records" action="/any/audit-record/list?codeAuditId=${id}"/>
</acme:form>

