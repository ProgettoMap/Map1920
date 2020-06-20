import java.io.FileNotFoundException;

import data.Data;
import data.TrainingDataException;
import tree.RegressionTree;
import tree.UnknownValueException;
import utility.Keyboard;
/*
 * TODO: ricontrollare tutte le visibilità di attributi, metodi e classi
 * TODO: Ricontrollare tutte le possibili eccezioni
 */

class MainTest extends Keyboard {

    public static void main(String[] args) throws TrainingDataException, UnknownValueException, FileNotFoundException {

	System.out.println("Training set: ");

	String fileName = Keyboard.readString();
	try {

	    Data trainingSet = new Data(fileName);
	    RegressionTree tree = new RegressionTree(trainingSet);
	    tree.printRules();
	    tree.printTree();
	    String response;

	    boolean responseValid = false;
	    System.out.println("Starting prediction phase!");

	    do {
		try {
		    System.out.println(tree.predictClass());
		} catch (UnknownValueException e) {
		    System.out.println(e);
		}
		do {
		    System.out.println("Would you want to repeat? (y/n):\n");
		    response = Keyboard.readString();

		    responseValid = response.length() == 1 && isValidResponse(response.charAt(0));
		    if (!responseValid)
			System.out.println("Character not valid. Retry please");
		} while (!responseValid);

	    } while (response.equals("y"));

	    System.out.println("Shutting down the program, and leaving the control to the operative system...");

	} catch (TrainingDataException e) {
	    System.out.println(e);
	}

    }

    private static boolean isValidResponse(Character response) {
	return (response == 'y' || response == 'n');
    }

}
