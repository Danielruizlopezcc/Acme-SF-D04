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
    <acme:input-select code="manager.link.form.label.project" path="project" choices="${projects}"/>
    <acme:input-select code="manager.link.form.label.userStory" path="userStory" choices="${userStories}"/>

    <jstl:choose>
        <jstl:when test="${acme:anyOf(_command, 'show|delete|update')}">
            <acme:submit code="manager.link.form.button.delete" action="/manager/project-user-stories/delete"/>
            <acme:submit code="manager.link.form.button.update" action="/manager/project-user-stories/update"/>
        </jstl:when>
        <jstl:when test="${_command=='create'}">
            <acme:submit code="manager.link.form.button.create" action="/manager/project-user-stories/create"/>
        </jstl:when>
    </jstl:choose>
</acme:form>