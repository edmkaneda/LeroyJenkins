<%-- 
    Document   : anadir
    Created on : 04-may-2017, 12:23:40
    Author     : Desarrollo Web
--%>

<%@page import="Almacen.Producto" %>
<%@page import="java.io.*" %>
<%@page import="Almacen.Fichero" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <%
       String respuesta="";
       
       
       try {
           int id=Integer.parseInt(request.getParameter("id"));
           String nombre=request.getParameter("nombre");
           String descripcion=request.getParameter("descripcion");
           double precio=Double.parseDouble(request.getParameter("precio"));
           
           Fichero file = new Fichero("almacen.obj","ab");
           if (request.getParameter("id")==null || nombre==null 
                   || descripcion == null 
                   || request.getParameter("precio")==null) {
               respuesta="Alguno de los campos quedó sin rellenar";
           } 
           else {
               file.escribirObjeto(new Producto(id,nombre,descripcion,precio));
               respuesta="Se insertó con éxito";
           }
           file.close();
       } catch (NumberFormatException nfe){
              respuesta="Error en los numeros";
       }
    %>
    
    <body>
        <h1>Añadir productos</h1>
        
        <form method="post" action="anadir.jsp" >
            Referencia:  <input type="number" name="id"><br>
            Nombre:      <input type="text" name="nombre"><br>
            Descripcion: <input type="text" name="descripcion"><br>
            Precio:      <input type="number" name="precio"><br>
            <input type="submit" name="Enviar">      
            <input type="reset" name="Borrar" value="Borrar"><br>
            
            
        </form>
    </body>
</html>
