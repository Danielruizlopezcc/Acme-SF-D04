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
	<acme:input-textbox code="developer.training-module.form.label.code" path="code"/>
	<acme:input-moment code="developer.training-module.form.label.creation-moment" path="creationMoment"/>
	<acme:input-textbox code="developer.training-module.form.label.details" path="details"/>	
	<acme:input-select code="developer.training-module.form.label.difficulty-level" path="difficultyLevel" choices="${difficulty}"/>	
	<acme:input-moment code="developer.training-module.form.label.update-moment" path="updateMoment"/>
	<acme:input-url code="developer.training-module.form.label.link" path="link"/>	
	<acme:input-integer code="developer.training-module.form.label.estimated-total-time" path="estimatedTotalTime"/>
	<acme:input-select code="developer.training-module.form.label.project" path="project" choices="${projects}"/>	
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="developer.training-module.form.button.sessions" action="/developer/training-session/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="developer.training-module.form.button.update" action="/developer/training-module/update"/>
			<acme:submit code="developer.training-module.form.button.delete" action="/developer/training-module/delete"/>
			<acme:submit code="developer.training-module.form.button.publish" action="/developer/training-module/publish"/>
			<acme:button code="developer.training-module.form.button.sessions" action="/developer/training-session/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.training-module.form.button.create-training-module" action="/developer/training-module/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>