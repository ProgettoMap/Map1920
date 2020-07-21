package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {

	private int PORT = 8080;
	private ServerSocket srvSck;

	public MultiServer(int port) throws IOException {
		if (port != PORT) { // La assegno solo se differente dalla default
			this.PORT = port;
		}
		// Costruttore di classe. Inizializza la porta ed invoca run()
		run();
	}

	/*
	 * Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di
	 * richiesta di connessioni da parte del client.
	 *
	 * Ad ogni nuova richiesta connessione si istanzia ServerOneClient.
	 */
	private void run() throws IOException {

		int nThread = 0;
		srvSck = new ServerSocket(PORT);

		if (nThread == 0) {
			System.out.println("Waiting for a connection...");
		}

		while (true) {
			try {
		Socket socket = srvSck.accept();
			
		nThread++;
			
		System.out.println("New client has started the connection. Clients actually connected: " + nThread);
			

					ServerSocket srvSck = new ServerSocket(PORT);
					try {
					new ServerOneClient(socket);
					} catch (IOException e) {
						// Se fallisce chiude il socket,
						// altrimenti il thread la chiuderà:
						socket.close();
					}	finally {
						srvSck.close();
					}
				}	catch (IOException e) { 
}}}}
