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
	<acme:input-textbox code="developer.training-session.form.label.code" path="code"/>
	<acme:input-moment code="developer.training-session.form.label.session-start" path="sessionStart"/>
	<acme:input-moment code="developer.training-session.form.label.session-end" path="sessionEnd"/>
	<acme:input-textbox code="developer.training-session.form.label.location" path="location"/>	
	<acme:input-textbox code="developer.training-session.form.label.instructor" path="instructor"/>	
	<acme:input-email code="developer.training-session.form.label.contact-email" path="contactEmail"/>
	<acme:input-url code="developer.training-session.form.label.link" path="link"/>	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="developer.training-session.form.button.update" action="/developer/training-session/update"/>
			<acme:submit code="developer.training-session.form.button.delete" action="/developer/training-session/delete"/>
			<acme:submit code="developer.training-session.form.button.publish" action="/developer/training-session/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.training-session.form.button.create-training-session" action="/developer/training-session/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>