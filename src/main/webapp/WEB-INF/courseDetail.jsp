<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Nuevo Proyecto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<div class="container">
    <header class="d-flex justify-content-between align-items-center">
        <h1>Curso: ${courseFound.getCourseName()}</h1>
        <a class="btn btn-danger" href="/logout" role="button">Cerrar Sesión</a>
    </header>

    <div class="card" style="width: 30rem;">
        <ul class="list-group list-group-flush">
            <li class="list-group-item">Instructor: ${courseFound.getInstructor()}</li>
            <li class="list-group-item">Sing Ups: ${courseFound.getAssignedUsers().size()}</li>
            <li class="list-group-item">Capacity: ${courseFound.getCapacitySignups()}</li>
        </ul>
    </div>

    <div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Nombre</th>
                <th>Fecha de Inscripción</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${courseFound.getAssignedUsers()}" var="user">
                <tr>
                    <td>${user.getName()}</td>
                    <td>${courseFound.getActualSignup()}</td>
                    <c:if test="${user.getId() == userSession.getId()}">
                        <td>
                            <a href="/courses/leave/${courseFound.getId()}" class="btn btn-dark">Darse de baja</a>
                        </td>
                    </c:if>
                    <c:if test="${user.getId() == userSession.getId()  && courseFound.getAssignedUsers().size() < courseFound.getCapacitySignups()}">
                        <td><span class="fw-bold">Already Added</span></td>
                    </c:if>
                    <c:if test="${user.getId()  != userSession.getId() &&  courseFound.getAssignedUsers().size() >= courseFound.getCapacitySignups() }">
                        <td><span class="fw-bold">FULL</span></td>
                    </c:if>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <hr>
    <div class="col-6 d-flex justify-content-between align-items-center">
        <c:if test="${courseFound.getOwner().getId() == userSession.getId()}">
            <form action="/courses/delete/${courseFound.getId()}" method="POST">
                <input type="hidden" name="_method" value="DELETE"/>
                <input type="submit" value="Eliminar" class="btn btn-danger"/>
            </form>
            <a href="/courses/edit/${courseFound.getId()}" class="btn btn-warning">Editar</a>
        </c:if>

        <c:if test="${!myAssignedCoursesList.contains(courseFound) && courseFound.getAssignedUsers().size() < courseFound.getCapacitySignups()}">
            <td><a href="/courses/join/${courseFound.getId()}" class="btn btn-info">Unirse</a></td>
        </c:if>

        <a href="/courses" class="btn btn-dark">Volver atrás</a>
    </div>

</div>

</body>
</html>