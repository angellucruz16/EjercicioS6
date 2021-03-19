package com.example.ejerciciosem6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private ImageFilterButton botonArriba, botonAbajo, botonIzquierda, botonDerecha,botonColor;
    private BufferedWriter bWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonArriba = findViewById(R.id.botonArriba);
        botonAbajo = findViewById(R.id.botonAbajo);
        botonIzquierda = findViewById(R.id.botonIzquierda);
        botonDerecha = findViewById(R.id.botonDerecha);
        botonColor = findViewById(R.id.botonColor);
        //Crear Botones
        botonArriba.setOnClickListener(
                v -> {
                    indicaciones("Arriba");
                }
        );
        botonDerecha.setOnClickListener(
                v -> {
                    indicaciones("Derecha");
                }
        );
        botonAbajo.setOnClickListener(
                v -> {
                    indicaciones("Abajo");
                }
        );
        botonIzquierda.setOnClickListener(
                v -> {
                    indicaciones("Izquierda");
                }
        );
        botonColor.setOnClickListener(
                v -> {
                    indicaciones("Color");
                }
        );

        //Conectar con eclipse (server)
        //Siempre lo hacemos en un hilo aparte
        //Conectamos con un Socket y siempre usamos Try y Catch
        new Thread(
                ()->{
                    try {
                        Socket socket = new Socket ("192.168.1.12" , 5000);

                        OutputStream os = socket.getOutputStream(); //Nos permite obtener el flujo de salida del cliente
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        bWriter = new BufferedWriter(osw); //Nos permite mandar los datos hacia el otro lado
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        ).start();

    }
    private void indicaciones (String mensaje){

        new Thread( //Siempre poner el hilo
                ()->{
                    try {
                        Gson gson = new Gson();
                        String mensajeFinal = gson.toJson(mensaje); //Serializando
                        bWriter.write(mensajeFinal + "\n"); //SIEMPRE poner el salto de línea
                        bWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
}