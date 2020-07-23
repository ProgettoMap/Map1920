package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class MultiServer {

	private int PORT = 8080;

	public MultiServer(int port) {
		if (port != PORT) {
			this.PORT = port;

		}
		run();
		// Costruttore di classe. Inizializza la porta ed invoca run()
	}

	private void run() {
		/*
		 * Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di
		 * crichiesta di connessioni da parte del client. Ad ogni nuova richiesta
		 * connessione si istanzia ServerOneClient.
		 */

		try {
			ServerSocket srvSck = new ServerSocket(PORT);
			Socket socket = srvSck.accept();
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
			//TODO CACCIA L'eccezione
		}
	}

}
