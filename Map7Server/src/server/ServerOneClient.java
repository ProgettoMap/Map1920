package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.Data;
import data.TrainingDataException;
import tree.RegressionTree;

public class ServerOneClient extends Thread {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	/**
	 * Costruttore di classe. Inizializza gli attributi socket, in e out. Avvia il
	 * thread.
	 * @param s Socket
	 * @throws IOException quando l'input dato presenta errori
	 */
	public ServerOneClient(Socket s) throws IOException {
		socket = s;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		start();
	}

	/**
	 * Riscrive il metodo run della superclasse Thread al fine di gestire le
	 * richieste del client in modo da rispondere alle richieste del client.
	 */
	@Override
	public void run() {
		RegressionTree tree = null;
		Data trainingSet = null;
		String tableName = null;
		try {
			try {
				if (((int) in.readObject()) == 0) { // Acquisition phase
					try {
						tableName = (String) in.readObject();
						trainingSet = new Data(tableName);
					} catch (TrainingDataException e) {
						out.writeObject(e);
					}
					out.writeObject("OK");

					// Starting learning phase
					if (((int) in.readObject()) == 1) { // The client has choose to learn the regression tree
						tree = new RegressionTree(trainingSet);
						try {
							tree.salva(tableName + ".dmp");
						} catch (IOException e) {
							out.writeObject("[!] Error [!] Cannot save the learned Regression Tree on the server. Detail Error: " + e);
						}
						//TODO: Ricontrollare se eccezione gestita correttamente, controllare ripresa esecuzione
					}
				} else { // Load from archive
					try {
						tree = RegressionTree.carica(in.readObject().toString() + ".dmp");
					} catch (ClassNotFoundException | IOException e) {
						out.writeObject("[!] Error [!] Cannot load the Regression Tree saved on the server. Detail Error: " + e);
					}
				}

				// Abbiamo deciso di passare come nelle esercitazioni precedenti la
				// visualizzazione dell'albero appreso e delle regole
				// Client expect response with rules and tree
				tree.printRules(out);
				out.writeObject("FINISH");
				tree.printTree(out);
				out.writeObject("FINISH");

				out.writeObject("OK");

				char risp = 'y';
				do { // TODO: sostituire numeri interi con risposte in costanti
					if (((int) in.readObject()) == 3) {
						try {
							out.writeObject(tree.predictClass(this).toString());
						} catch (UnknownValueException e) {
							out.writeObject(e);
						}
					}
				} while (Character.toUpperCase(risp) == 'Y');
			} catch (ClassNotFoundException e1) {
				System.out.println(
						"[!] Error [!] Cannot convert a type of the data inserted in another one. Detail error:" + e1);
			}
		} catch (IOException e) {
			System.out.println("[!] Error [!] There was a problem with the input/output. Detail error: " + e);
		}
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}
}
