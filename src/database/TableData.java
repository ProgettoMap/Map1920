package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TableData {

	private DbAccess db;

	public TableData(DbAccess db) {
		this.db = db;
	}

	/**
	 * Ricava lo schema della tabella con nome table. Esegue una interrogazione per
	 * estrarre le tuple distinte da tale tabella. Per ogni tupla del resultset, si
	 * crea un oggetto istanza della classe Example, il cui riferimento va incluso
	 * nella lista da restituire. In particolare, per la tupla corrente nel
	 * resultset, si estraggono i valori dei singoli campi (usando getFloat() o
	 * getString()), e li si aggiungono all’oggetto istanza della classe Example che
	 * si sta costruendo.
	 *
	 * @param table Nome della tabella nel database
	 * @return Lista di transazioni memorizzate nella tabella
	 * @throws SQLException
	 * @throws EmptySetException
	 */
	public List<Example> getTransazioni(String table) throws SQLException, EmptySetException {
		LinkedList<Example> transSet = new LinkedList<Example>();
		Statement statement;
		TableSchema tSchema = new TableSchema(db, table);

		String query = "SELECT ";

		int countAttributes =  tSchema.getNumberOfAttributes();
		for (int i = 0; i < countAttributes; i++) {
			Column c = tSchema.getColumn(i);
			if (i > 0)
				query += ",";
			query += c.getColumnName();
		}
		if (countAttributes == 0)
			throw new SQLException("[!] Errore! [!] La tabella " + table + "ha " + countAttributes
			+ " colonne, non necessarie per effettuare l'apprendimento dell'albero.");
		query += (" FROM " + table);

		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		boolean empty = true;
		while (rs.next()) { // Per ogni riga
			empty = false;
			Example currentTuple = new Example();
			for (int i = 0; i < countAttributes; i++)
				if (tSchema.getColumn(i).isNumber())
					currentTuple.add(rs.getDouble(i + 1));
				else
					currentTuple.add(rs.getString(i + 1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();
		if (empty)
			throw new EmptySetException("La tabella " + table
					+ " ha 0 righe, pertanto è vuota. Inserisci prima dei dati al suo interno!");

		return transSet;
	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre i valori distinti
	 * ordinati di column e popolare un insieme da restituire
	 *
	 * @param table  Nome della tabella dalla quale si vogliono ottenere i valori
	 *               distinti
	 * @param column Nome della colonna dalla quale si vogliono ottenere i valori
	 *               distinti
	 * @return Insieme di valori distinti ordinati in modalità ascendente che
	 *         l’attributo identificato da nome column assume nella tabella
	 *         identificata dal nome table
	 * @throws SQLException
	 */
	public Set<String> getDistinctColumnValues(String table, Column column) throws SQLException { // TODO: cambiato il set da object a string, chiedere se il set di object è fatto apposta per essere modificato

		Set<String> discreteValues = new TreeSet<String>();

		ResultSet r = db.getConnection().createStatement().executeQuery("SELECT DISTINCT " + column.getColumnName() + " FROM " + table + " ORDER BY " //TODO: Teoricamente se usiamo un treeset, l'ordinamento dovrebbe avvenire automaticamnete
				+ column.getColumnName() + " ASC");
		while (r.next()) {
			discreteValues.add(r.getString(1));
		}
		return discreteValues;
	}

	public enum QUERY_TYPE {
		MIN, MAX
	}

}
