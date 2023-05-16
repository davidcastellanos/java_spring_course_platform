<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cursos Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<div class="container">
    <header class="d-flex justify-content-between align-items-center">
        <h1>Bienvenido ${userSession.name}</h1>
        <a class="btn btn-success" href="/courses/new" role="button">Nuevo Curso</a>
        <a class="btn btn-danger" href="/logout" role="button">Cerrar Sesión</a>
    </header>
    <br><br>

    <div class="row">
        <h2>Lista de Cursos</h2>
        <div class="card">
            <div class="card-body">
                <blockquote class="blockquote">
                    <p>¡Ten en cuenta!</p>
                </blockquote>
                <ul>
                    <li class="list-group-item list-group-item-light">Si eres el creador de un curso y estás unido a él, puedes: <span class="fw-bold">&nbsp Editarla , Eliminarlo y/o darte de baja</span></li>
                    <li class="list-group-item list-group-item-light">Si eres el creador de un curso y <span class="fw-bold">No</span> estás unido a él, puedes: <span class="fw-bold">&nbsp Editarla, Eliminarla, y/o Unirte</span></li>
                    <li class="list-group-item list-group-item-light">Si <span class="fw-bold">No</span> eres el creador de un curso, puedes: <span class="fw-bold">&nbsp Unirte y darte de Baja</span></li>
                    <li class="list-group-item list-group-item-light">Unirte a un curso depende de: <span class="fw-bold">&nbsp La cantidad de cupo disponible y de que ya no te estés unido a él </span></li>

                </ul>
            </div>
            <br>
        </div>

        <div class="d-grid gap-2 d-md-flex justify-content-md-end">

            <a class="btn btn-outline-success" href="/courses/mayorAmenor" role="button">Ordenar Capacidad: Mayor a Menor</a>
            <a class="btn btn-outline-success" href="/courses/menorAmayor" role="button">Ordenar Capacidad: Menor a Mayor</a>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Curso</th>
                <th>Instructor</th>
                <th>Inscripciones</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allCoursesList}" var="course">
                <tr>
                    <td><a href="/courses/show/${course.getId()}">${course.getCourseName()}</a></td>
                    <td>${course.getInstructor()}</td>
                    <td>${course.getAssignedUsers().size()} / ${course.getCapacitySignups()}</td>
                    <td>
                    <a href="/courses/show/${course.getId()}" class="btn btn-info">Detalle</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>


</div>



</body>
</html>