<%-- 
    Document   : lista
    Created on : 04-may-2017, 12:29:15
    Author     : Desarrollo Web
--%>
<%@page import="java.io.EOFException" %>
<%@page import="Almacen.Producto" %>
<%@page import="java.io.*" %>
<%@page import="Almacen.Fichero" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de productos</title>
    </head>
    <body>
        <h1>Lista de productos</h1>
        <table>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripcion</th>
                <th>Precio</th>
            </tr>
        <%
            ObjectInputStream ois;
            String respuesta="";
            
           
                Fichero file = new Fichero("almacen.obj","rb");
                Producto aux = (Producto) file.leerObjeto();
                while(aux!=null){
                    
                    respuesta+="<tr>"
                            + "<td>"+aux.getId()+"</td>"
                            + "<td>"+aux.getNombre()+"</td>"
                            + "<td>"+aux.getDescripcion()+"</td>"
                            + "<td>"+aux.getPrecio()+"</td>"
                            + "</tr>";
                    //respuesta+=aux.toString();
                   
                    
                    aux = (Producto) file.leerObjeto();
                }
            
            file.close();
        %>
        <%=respuesta%>
       </table>
    </body>
</html>
