<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="any.training-module.form.label.code" path="code" readonly="true"/>
	<acme:input-moment code="any.training-module.form.label.creation-moment" path="creationMoment" readonly="true"/>
	<acme:input-textbox code="any.training-module.form.label.details" path="details" readonly="true"/>
	<acme:input-textbox code="any.training-module.form.label.difficulty-level" path="defficultyLevel" readonly="true"/>
	<acme:input-moment code="any.training-module.form.label.update-moment" path="updateMoment" readonly="true"/>
	<acme:input-url code="any.training-module.form.label.link" path="link" readonly="true"/>
	<acme:input-textbox code="any.training-module.form.label.estimated-total-time" path="estimatedTotalTime" readonly="true"/>
</acme:form>