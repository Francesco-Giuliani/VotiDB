import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class TestDB {

	public static void main (String[] args) {
		String jdbcURL = "jdbc:mysql://localhost/voti?user=root";//IN xampp non devo mettere la password
																//la string di connessione al DB inizia sempre con jdbc: ma poi
															 	//la sintassi cambia a seconda che stiamo usando Oracle, mySQL, MariaDB ecc..
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			//il driver manager ha una factory di connessioni
			//Non voglio fare new perchè dovrei sapere il nome della classe che andrei a creare,
			//ma io non voglio dovrelo specificare. Delego al Driver manager scoprire cosa fare
			//i.e. che Classe particolare scegliere!
			
			Statement st = conn.createStatement(); //Immagino che le query SQL siano trasportate da delle navette
			//Statement è un'interfaccia : sto ancora facendo factoring
			
			String sql = "SELECT nome, voto " + 
					"FROM libretto " + 
					"ORDER BY voto DESC";
			
			ResultSet res = st.executeQuery(sql); //il ResultSet contiene un meccanismo per poter ottenere i risultati della query
													//perchè non è detto che mi interessino tutti i risultati
													//il resultSet imlementa un cursore sulla tabella risultati
													//il cursore è in una certa posizione sulla riga
													//resSet contiene due famiglie di metodi: una per spostarsi
													//una per leggere i dati relativi alla riga sulla quale mi trovo
							//quindi: mi sposto sula prima R e su questa invoco la seconda famiglia per leggere dalle varie
					//caselle sulle righe desiderate (seconda famiglia getDataType()
					//immagino che a ogni tabella di risultati ci siano due righe in più: una prima della prima riga (il curs all'inizio viene posizionato
					//qui. E una riga dopo l'ultima riga. Questo perchè non è detto che la la prima riga esista --> non voglio  generare una eccezione --> riga prima della prima
					//NOTA devo fare almeno un next() per andare sulla prima riga
			
			while(res.next()) {
				String nome = res.getString("nome");
				int voto = res.getInt("voto");
				System.out.format("Voto %d dell'esame %s\n", voto, nome);
				
			}
			st.close();
			
			conn.close();//SEMPRE CHIUDERE LE CONNESSIONI
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // mi connetto al DB
	
	}
	
}
