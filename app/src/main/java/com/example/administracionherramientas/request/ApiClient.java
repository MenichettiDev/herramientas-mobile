package com.example.administracionherramientas.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administracionherramientas.model_response.LoginResponse;
import com.example.administracionherramientas.models.Cliente;
import com.example.administracionherramientas.models.EstadoDisponibilidad;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.models.Obra;
import com.example.administracionherramientas.models.Proveedor;
import com.example.administracionherramientas.models.Rol;
import com.example.administracionherramientas.models.Usuario;
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

public class ApiClient {
    public final static String BASE_URL="http://147.93.32.147:4000/api/";



    public static InmoServicio getInmoServicio(){
        Gson gson = new GsonBuilder().setLenient().create();
         Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmoServicio.class);
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

    public interface InmoServicio{

        //Auth
        @POST("auth/login")
        Call<LoginResponse> loginForm(@Body LoginRequest loginRequest);
        //Alertas
        @GET("Alerta/count-alertas-vencidas")
        Call<Integer> getCountAlertasVencidas(@Header("Authorization") String token);
        //Herramientas
        @GET("Herramienta/count-herramientas-disponibles")
        Call<Integer> getCountHerramientasDisponibles(@Header("Authorization") String token);
        @GET("Herramienta/count-herramientas-en-prestamo")
        Call<Integer> getCountHerramientasPrestamo(@Header("Authorization") String token);
        @GET("Herramienta/count-herramientas-en-reparacion")
        Call<Integer> getCountHerramientasReparacion(@Header("Authorization") String token);
        @GET("Herramienta/paged")
        Call<List<Herramienta>> getHerramientasPaged(@Header("Authorization") String token);
        @GET("Herramienta/disponibilidad") //query params disponibilidad multiple
        Call<List<Herramienta>> getHerramientasByDisponibilidad(@Header("Authorization") String token);

        //EstadoDisponibilidad
        @GET("EstadoDisponibilidad")
        Call<List<EstadoDisponibilidad>> getEstadoDisponibilidad(@Header("Authorization") String token);
        //Rol
        @GET("Rol")
        Call<List<Rol>> getRoles(@Header("Authorization") String token);
        //Proveedor
        @GET("Proveedor")
        Call<List<Proveedor>> getProveedores(@Header("Authorization") String token);
        //Obra
        @GET("Obra")
        Call<List<Obra>> getObras(@Header("Authorization") String token);
        //Usuario
        @GET("Usuario")
        Call<List<Usuario>> getUsuarios(@Header("Authorization") String token);
        //Cliente
        @GET("Cliente")
        Call<List<Cliente>> getClientes(@Header("Authorization") String token);
//
//        @GET("api/Propietarios")
//        Call<Propietario> getPropietario(@Header("Authorization") String token);
//
//        @PUT("api/Propietarios/actualizar")
//        Call<Propietario> editPropietario(@Header("Authorization") String token,
//                                          @Body Propietario propietario);
//
//        @GET("api/Inmuebles")
//        Call<List<Inmueble>> getInmueble(@Header("Authorization") String token);
//
//        @GET("api/Inmuebles/GetContratoVigente")
//        Call<List<Inmueble>> getInmuebleContratoVigente(@Header("Authorization") String token);
//
//        @GET("api/contratos/inmueble/{id}")
//        Call<Contrato> getContratoByInmueble(@Header("Authorization") String token,
//                                             @Path("id") int idInmueble);
//
//        @PUT("api/Inmuebles/actualizar")
//        Call<Inmueble> updateInmueble(@Header("Authorization") String token,
//                                      @Body Inmueble inmueble);
//
//        @Multipart
//        @POST("api/Inmuebles/cargar")
//        Call<Inmueble> CargarInmueble(@Header("Authorization") String token,
//                                      @Part MultipartBody.Part imagen,
//                                      @Part("inmueble") RequestBody inmuebleBody);
//
//        @GET("api/pagos/contrato/{id}")
//        Call<List<Pago>> getPagosByContrato(@Header("Authorization") String token, @retrofit2.http.Path("id") int idContrato);


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


}
