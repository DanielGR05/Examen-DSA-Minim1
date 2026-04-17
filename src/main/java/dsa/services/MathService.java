package dsa.services;

import dsa.MathManager;
import dsa.MathManagerImpl;
import dsa.PeticionOperacionDTO;
import dsa.exceptions.InstitutoNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import dsa.models.Instituto;
import dsa.models.OperacionMatematica;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/math", description = "Servicio REST de Operaciones Matematicas")
@Path("/math")
public class MathService {

    private MathManager manager;

    public MathService() {
        this.manager = MathManagerImpl.getInstance();

        if (manager.getInstitutosOrdenados().isEmpty()) {
            manager.addInstituto("INSTI-1", "EETAC");
            manager.addInstituto("INSTI-2", "FIB");
            manager.addAlumno("ALUM-1", "Tomeu", "INSTI-1");
            manager.addAlumno("ALUM-2", "Maria", "INSTI-2");
        }
    }

    @POST
    @Path("/operaciones")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Solicitar una nueva operacion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Operacion aceptada")
    })
    public Response solicitarOperacion(PeticionOperacionDTO peticion) {
        manager.solicitarOperacion(peticion.getExpresion(), peticion.getIdAlumno(), peticion.getIdInstituto());
        return Response.status(201).build();
    }

    @GET
    @Path("/operaciones/procesar")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Procesar la operacion mas antigua en la cola")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = OperacionMatematica.class),
            @ApiResponse(code = 404, message = "No hay operaciones en cola")
    })
    public Response procesarOperacion() {
        OperacionMatematica procesada = manager.procesarOperacion();
        if (procesada != null) {
            return Response.status(200).entity(procesada).build();
        } else {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/institutos/{id}/operaciones")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Obtener operaciones de un instituto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = OperacionMatematica.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Instituto no encontrado")
    })
    public Response getOperacionesInstituto(@PathParam("id") String id) {
        try {
            List<OperacionMatematica> lista = manager.getOperacionesInstituto(id);
            GenericEntity<List<OperacionMatematica>> entity = new GenericEntity<List<OperacionMatematica>>(lista) {};
            return Response.status(200).entity(entity).build();

        } catch (InstitutoNotFoundException e) {
            return Response.status(404).entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/alumnos/{id}/operaciones")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Obtener operaciones de un alumno")
    public Response getOperacionesAlumno(@PathParam("id") String id) {
        List<OperacionMatematica> lista = manager.getOperacionesAlumno(id);
        GenericEntity<List<OperacionMatematica>> entity = new GenericEntity<List<OperacionMatematica>>(lista) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @Path("/institutos/ordenados")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Obtener institutos ordenados por operaciones")
    public Response getInstitutosOrdenados() {
        List<Instituto> lista = manager.getInstitutosOrdenados();
        GenericEntity<List<Instituto>> entity = new GenericEntity<List<Instituto>>(lista) {};
        return Response.status(200).entity(entity).build();
    }
}