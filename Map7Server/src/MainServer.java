import java.io.IOException;
import java.util.Scanner;

import server.MultiServer;

class MainServer {
	/**
	 * @checked
	 */
	static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean connectionError;

		System.out.println("Regression Tree Learner\n");
		do {
			connectionError = false;
			System.out.println("Insert the port on which to create the connection: ");
			int port = scanner.nextInt();
			try {
				new MultiServer(port);
			} catch (IOException e) {
				System.out.println(
						"[!] Error [!] Cannot initialize the connection on the port " + port + ". Detail error: " + e);
				System.out.println("Do you want insert a new PORT?");
				String risposta;
				do {
					System.out.println("Y/N");
					risposta = scanner.nextLine().toUpperCase();

					if (risposta == "Y") {
						connectionError = true;
					}
				} while (risposta != "Y" && risposta != "N");
			}
		} while (connectionError);
		scanner.close();
	}

}