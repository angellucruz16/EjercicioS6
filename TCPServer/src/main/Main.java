package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import processing.core.PApplet;

public class Main extends PApplet {

	int xBolita = 100;
	int yBolita = 100;
	String mensaje;
	int r = 0;
	int g = 0;
	int b = 255;

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	// 
	public void settings() {
		size(500, 500);

	}

	// 
	public void setup() {


		new Thread(() -> {
			try {
				Gson gson = new Gson();
				ServerSocket server = new ServerSocket(5000);
				System.out.println("Esperando cliente...");
				Socket socket = server.accept();
				System.out.println("Cliente esta conectado");

				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				// Hacer que el objeto is tenga la capacidad de leer Strings completos
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader breader = new BufferedReader(isr);

				while (true) {
					// Esperando mensaje
					System.out.println("Esperando mensaje...");
					String mensajeRecibido = breader.readLine(); //BW::X::Y::ALTO::ANCHO
					System.out.println(mensajeRecibido);
					mensaje = gson.fromJson(mensajeRecibido, String.class); //Des serializando
					instruccionesBolita(mensaje);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}


	public void draw() {
		background(255,255,255);
		fill(r,g,b);
		noStroke();
		ellipse(xBolita, yBolita, 50, 50);
	}
	public void instruccionesBolita (String mensaje) {
		switch (mensaje) {

		case "Arriba":
			yBolita -= 5;
			break;

		case "Derecha":
			xBolita += 5;
			break;
			
		case "Abajo":
			yBolita += 5;
			break;
			
		case "Izquierda":
			xBolita -= 5;
			break;
			
		case "Color":
			r = (int)random (0,255);
			g = (int)random (0,255);
			b = (int)random (0,255);
			break;

		default:
			break;
		}
	}
}
