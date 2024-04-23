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
	<acme:input-textbox code="authenticated.manager.form.label.degree" path="degree"/>
	<acme:input-textarea code="authenticated.manager.form.label.overview" path="overview"/>
	<acme:input-textbox code="authenticated.manager.form.label.certifications" path="certifications"/>
	<acme:input-url code="authenticated.manager.form.label.optionalLink" path="optionalLink"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.manager.form.button.create" action="/authenticated/manager/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.manager.form.button.update" action="/authenticated/manager/update"/>
	
</acme:form>