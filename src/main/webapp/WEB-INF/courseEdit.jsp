<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Editar Curso: ${courseToEdit.getCourseName()}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <header class="d-flex justify-content-between align-items-center">
        <h1>Editar Curso: ${courseToEdit.getCourseName()}</h1>
        <a class="btn btn-danger" href="/logout" role="button">Cerrar Sesión</a>
    </header>

    <form:form action="/courses/edit/${courseToEdit.getId()}" method="post" modelAttribute="courseToEdit">
        <input type="hidden" name="_method" value="put"/>
        <div class="form-group">
            <form:label path="courseName">Course:</form:label>
            <form:input path="courseName" class="form-control" />
            <form:errors path="courseName" class="text-danger" />
        </div>

        <div class="form-group">
            <form:label path="instructor">Instructor</form:label>
            <form:input path="instructor" class="form-control" />
            <form:errors path="instructor" class="text-danger" />

        </div>


        <div class="form-group">
            <form:label path="capacitySignups">Capacity</form:label>
            <form:input path="capacitySignups" type="number" min="0" max="100" step="1" value="1"/>
            <form:errors path="capacitySignups" class="text-danger" />
        </div>


        <form:hidden path="owner" value="${userSession.id}" />

        <input type="submit" value="Editar" class="btn btn-success" />


    </form:form>


</div>



</body>
</html>