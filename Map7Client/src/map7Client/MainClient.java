package map7Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import utility.Keyboard;
/**
 *
 * Main del client
 *
 */
public class MainClient {

	private static Socket socket = null;
	private static ObjectOutputStream out = null;
	private static ObjectInputStream in = null;
	public static void main(String[] args) {	

		ShutDownTask shutDownTask = new ShutDownTask();
		Runtime.getRuntime().addShutdownHook(shutDownTask);

		System.out.println("Regression Tree Learner\n");

		// Validazione parametri in input
		if(args.length == 2) {
			if(!args[0].matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) { // Formato ip non valido
				System.err.println("[!] Error [!] The IP that you've entered isn't correct. Please, start again the program and insert a valid ip.");
				return;
			}
			if(!args[1].matches("^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$")) { // Formato porta non valido
				System.err.println("[!] Error [!] The port that you've entered isn't correct. Please, start again the program and insert a valid port (value between 1 and 65535).");
				return;
			}
		} else { // Numero parametri insufficiente
			System.err.println("[!] Error [!] You haven't entered the necessary parameters for starting correctly the Regression Tree Learner.\nMind that you have to launch the program in the following mode: ./RegressionTreeLearner.bat <ip address> <port>");
			return;
		}

		InetAddress addr;
		try {
			addr = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.out.println(e.toString());
			return;
		}
	
		try {
			System.out.println("Trying to connect to the server " + addr + "...");
			socket = new Socket(addr, new Integer(args[1]).intValue());
			System.out.println(socket + "\n");

			// stream con richieste del client
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("[!] Error [!] Cannot initialize the connection with the server. Detail error: " + e.toString());
			return;
		}

		String answer = "";

		int decision = 0;
		System.out.println("MENU ");
		System.out.println(" - [1] Learn Regression Tree from data");
		System.out.println(" - [2] Load Regression Tree from archive");
		do {
			System.out.print("-> ");
			decision = Keyboard.readInt();
		} while (!(decision == 1) && !(decision == 2));

		System.out.println("Table/file name:");
		System.out.print("-> ");
		String tableName = Keyboard.readString();
		try {
			if (decision == 1) { // Learn regression tree
				System.out.println("Starting data acquisition phase!");

				out.writeObject(0);
				out.writeObject(tableName);
				answer = in.readObject().toString();
				if (!answer.equals("OK")) {
					System.err.println(answer); // C'è stato qualche errore
					return;
				}
				System.out.println("Starting learning phase!");
				out.writeObject(1);

			} else { // Load tree from archive
				out.writeObject(2);
				out.writeObject(tableName);
				answer = in.readObject().toString();
				if (!answer.equals("OK")) {
					System.err.println(answer); // C'è stato qualche errore
					return;
				}
			}

			while (!(answer = in.readObject().toString()).equals("FINISH")) {
				if(answer.toLowerCase().contains("error"))
					System.err.println(answer);
				else
					System.out.println(answer); // Reading rules
			}

			while (!(answer = in.readObject().toString()).equals("FINISH")) {
				if(answer.toLowerCase().contains("error"))
					System.err.println(answer);
				else
					System.out.println(answer); // Reading rules
			}

			answer = in.readObject().toString();
			if (!answer.equals("OK")) {
				System.err.println(answer);
				return;
			}

			char risp = 'y';
			do {
				out.writeObject(3);
				System.out.println("Starting prediction phase!");
				answer = in.readObject().toString(); // Reading tree.predictClass()

				while (answer.equals("QUERY")) {
					// Formulating query, reading answer
					answer = in.readObject().toString(); // Read trees
					System.out.println(answer);
					System.out.print("-> ");
					int path = Keyboard.readInt();
					out.writeObject(path);
					answer = in.readObject().toString();
				}

				if (answer.equals("OK")) { // Reading prediction
					answer = in.readObject().toString();
					System.out.println("Predicted class:" + answer);
				} else // Printing error message
					System.err.println(answer);

				do {
					System.out.println("Do you want to repeat? (y/n)");
					risp = Keyboard.readChar();
				} while (Character.toUpperCase(risp) != 'Y' && Character.toUpperCase(risp) != 'N');

			} while (Character.toUpperCase(risp) == 'Y');

			// Aggiunta stampa per far capire al server che l'esecuzione del client vuole terminare
			out.writeObject(0);
			System.out.println("Thank you for having used this Regression Tree Learner! See you soon...");

	    } catch (IOException | ClassNotFoundException e) {
			System.out.println(e.toString());
		}
	}

	private static class ShutDownTask extends Thread {
 
		@Override
		public void run() {
			System.out.println("Closing the socket...");
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
					if(in != null)
						in.close();
					if(out != null)
						out.close();
				} catch (IOException e) {
					System.err.println("[!] Error [!] Socket has not been closed correctly.");
				}
			} 
		}
	}

}
