package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.Data;
import data.TrainingDataException;
import tree.RegressionTree;

class ServerOneClient extends Thread {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public ServerOneClient(Socket s) throws IOException { /*
															 * Costruttore di classe. Inizializza gli attributi socket,
															 * in e out. Avvia il thread.
															 */
		socket = s;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		start();
	}

	@Override
	public void run() { /*
						 * Riscrive il metodo run della superclasse Thread al fine di gestire le
						 * richieste del client in modo da rispondere alle richieste del client.
						 */
		RegressionTree tree = null;
		Data trainingSet = null;
		String tableName = null;
		try {
			if (in.readInt() == 0) {
				try {
					tableName = in.readUTF();
					trainingSet = new Data(tableName);
				} catch (TrainingDataException e) {
					out.writeUTF(e.toString());
				}
				
				if (in.readInt() == 1) {
					tree = new RegressionTree(trainingSet);
					try {
						tree.salva(tableName + ".dmp");
					} catch (IOException e) {
						System.out.println(e.toString());
					}
				}
				
				
			} else {
				try {
					tree = RegressionTree.carica(in.readUTF() + ".dmp");
				} catch (ClassNotFoundException | IOException e) {
					out.writeUTF(e.toString());
				}
			}
			tree.printRules();
			tree.printTree();
			out.writeUTF("OK");
			
			char risp = 'y';
			do {
				if (in.readInt() == 3) {
				try {
					out.writeUTF(tree.predictClass());
				} catch (UnknownValueException e) {

					System.out.println(e);
				}
				System.out.println("Would you repeat ? (y/n)");
				risp = Keyboard.readChar();
				}
			} while (Character.toUpperCase(risp) == 'Y');
			
		}
			
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
