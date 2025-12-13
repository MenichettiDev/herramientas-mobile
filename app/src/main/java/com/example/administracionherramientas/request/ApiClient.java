package com.example.administracionherramientas.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administracionherramientas.model_response.CountResponse;
import com.example.administracionherramientas.model_response.HerramientaApiResponse;
import com.example.administracionherramientas.model_response.LoginResponse;
import com.example.administracionherramientas.model_response.PagedResponse;
import com.example.administracionherramientas.model_response.ProveedorResponse;
import com.example.administracionherramientas.model_response.UsuarioResponse;
import com.example.administracionherramientas.models.Cliente;
import com.example.administracionherramientas.models.EstadoDisponibilidad;
import com.example.administracionherramientas.models.EstadoFisicoHerramienta;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.models.Obra;
import com.example.administracionherramientas.models.Proveedor;
import com.example.administracionherramientas.models.Rol;
import com.example.administracionherramientas.models.Usuario;
import com.example.administracionherramientas.models_request.DevolucionRequest;
import com.example.administracionherramientas.models_request.PrestamoRequest;
import com.example.administracionherramientas.models_request.ReparacionRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class ApiClient {
    private static final String BASE_URL="http://147.93.32.147:4000/api/";
    private static ApiClient apiClient;
    private final ApiServicio apiServicio;

    private ApiClient() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiServicio = retrofit.create(ApiServicio.class);
    }

    public static ApiClient getInstance() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public ApiServicio getApiClient() {
        return apiServicio;
    }


    // Clase para el cuerpo de la solicitud de login
    public static class LoginRequest {
        final String legajo;
        final String password;

        public LoginRequest(String legajo, String password) {
            this.legajo = legajo;
            this.password = password;
        }
    }

    public interface ApiServicio {

        //Auth
        @POST("auth/login")
        Call<LoginResponse> loginForm(@Body LoginRequest loginRequest);
        //Alertas
        @GET("Alerta/count-alertas-vencidas")
        Call<CountResponse> getCountAlertasVencidas(@Header("Authorization") String token);
        //Herramientas
        @GET("Herramienta/count-herramientas-disponibles")
        Call<CountResponse> getCountHerramientasDisponibles(@Header("Authorization") String token);
        @GET("Herramienta/count-herramientas-en-prestamo")
        Call<CountResponse> getCountHerramientasPrestamo(@Header("Authorization") String token);
        @GET("Herramienta/count-herramientas-en-reparacion")
        Call<CountResponse> getCountHerramientasReparacion(@Header("Authorization") String token);
        @GET
        Call<HerramientaApiResponse<PagedResponse<Herramienta>>> getHerramientasPaged(@Url String url, @Header("Authorization") String token);
        @GET("Herramienta/disponibilidad/{id}")
        Call<HerramientaApiResponse<List<Herramienta>>> getHerramientasByDisponibilidad(
                @Header("Authorization") String token,
                @Path("id") int disponibilidadId,
                @Query("search") String search
        );

        @GET("Herramienta/prestamo/usuario/{idUsuarioResponsable}")
        Call<HerramientaApiResponse<List<Herramienta>>> getHerramientasPrestadasByUsuario(
                @Header("Authorization") String token,
                @Path("idUsuarioResponsable") int idUsuarioResponsable
        );

        @GET("Herramienta/reparacion/proveedor/{idProveedor}")
        Call<HerramientaApiResponse<List<Herramienta>>> getHerramientasPrestadasByProveedor(
                @Header("Authorization") String token,
                @Path("idProveedor") int idProveedor
        );

        //EstadoDisponibilidad
        @GET("EstadoDisponibilidad")
        Call<HerramientaApiResponse<List<EstadoDisponibilidad>>> getEstadosDisponibilidad(@Header("Authorization") String token);
        //Rol
        @GET("Rol")
        Call<List<Rol>> getRoles(@Header("Authorization") String token);
        //Proveedor
        @GET("Proveedor/getProveedoresCombo")
        Call<ProveedorResponse> getProveedores(
                @Header("Authorization") String token,
                @Query("search") String search);

        //Usuario
        @GET("Usuario")
        Call<UsuarioResponse> getUsuarios(
                @Header("Authorization") String token,
                @Query("nombre") String nombre
        );
        //Cliente
        @GET("Cliente/getClientesCombo")
        Call<HerramientaApiResponse<List<Cliente>>> getClientes(
                @Header("Authorization") String token,
                @Query("search") String search);
        //Estado fisico Herramienta
        @GET("EstadoFisicoHerramienta")
        Call<HerramientaApiResponse<List<EstadoFisicoHerramienta>>> getEstadoFisicoHerramienta(
                @Header("Authorization") String token);

        //Obra
        @GET("Obra/getObrasCombo")
        Call<HerramientaApiResponse<List<Obra>>> getObras(
                @Header("Authorization") String token,
                @Query("idCliente") int idCliente,
                @Query("search") String search
        );
        //Movimientos
        @POST("MovimientoHerramienta")
        Call<HerramientaApiResponse<Object>> guardarPrestamo(
                @Header("Authorization") String token,
                @Body PrestamoRequest prestamoRequest
        );
        @POST("MovimientoHerramienta")
        Call<HerramientaApiResponse<Object>> guardarReparacion(
                @Header("Authorization") String token,
                @Body ReparacionRequest reparacionRequest
        );

        @POST("MovimientoHerramienta")
        Call<HerramientaApiResponse<Object>> guardarDevolucion(
                @Header("Authorization") String token,
                @Body DevolucionRequest devolucionRequest
        );

    }

    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

    //Guardar idUsuario
    public static void guardarIdUsuario(Context context, int idUsuario) {
        SharedPreferences sp = context.getSharedPreferences("idUsuario.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("idUsuario", idUsuario);
        editor.apply();
    }

    //Leer usuario
    public static int leerIdUsuario(Context context) {
        SharedPreferences sp = context.getSharedPreferences("idUsuario.xml", Context.MODE_PRIVATE);
        return sp.getInt("idUsuario", -1);
    }
}
