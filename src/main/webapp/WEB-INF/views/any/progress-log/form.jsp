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
	<acme:input-textbox code="any.progress-log.form.label.recordId" path="recordId"/>
	<acme:input-double code="any.progress-log.form.label.completeness" path="completeness"/>
	<acme:input-textarea code="any.progress-log.form.label.comment" path="comment"/>
	<acme:input-moment code="any.progress-log.form.label.registrationMoment" path="registrationMoment" />
	<acme:input-textbox code="any.progress-log.form.label.responsiblePerson" path="responsiblePerson"/>
</acme:form>