package it.dstech.lottapokemon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Gestione {
	private String username;
	private String password;
	private String url;
	private Connection connessione;
	private Statement statement;

	public Gestione() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.username = "L4RZNtuhbB";
		this.password = "YxHvHv0NUT";
		this.url = "jdbc:mysql://remotemysql.com:3306/L4RZNtuhbB?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false";
		this.connessione = DriverManager.getConnection(url, username, password);
		this.statement = connessione.createStatement();
	}

	public void insertUtente(Utente utente) throws SQLException {
		String queryInserimentoPokemon = "INSERT INTO `L4RZNtuhbB`.`Utenti` (`idUtenti`, `nome`) VALUES (?, ?);";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoPokemon);
		prepareStatement.setString(1, utente.getId());
		prepareStatement.setString(2, utente.getNome());
		prepareStatement.execute();
	}

	public void insertPokemon(Pokemon pokemon, Utente utente) throws SQLException {
		String queryInserimentoPokemon = "INSERT INTO `L4RZNtuhbB`.`digimon` (`iddigimon`, `nome`, `HP`, `ATK`, `DEF`, `RES`, `EVO`, `tipo`, `idUtenti` ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoPokemon);
		prepareStatement.setInt(1, 2);
		prepareStatement.setString(2, pokemon.getNome());
		prepareStatement.setInt(3, pokemon.getHp());
		prepareStatement.setInt(4, pokemon.getAttacco());
		prepareStatement.setInt(5, pokemon.getDifesa());
		prepareStatement.setInt(6, pokemon.getResistenza());
		prepareStatement.setString(7, pokemon.getEvoluzione());
		prepareStatement.setString(8, pokemon.getTipo());
		prepareStatement.setString(9, utente.getId());
		prepareStatement.execute();
	}

	public boolean checkPersona(Utente utente) throws SQLException {
		PreparedStatement prepareStatement = connessione
				.prepareStatement("select idUtenti from L4RZNtuhbB.Utenti where idUtenti = ?");
		prepareStatement.setString(1, utente.getId());
		ResultSet risultato = prepareStatement.executeQuery();
		while (risultato.next()) {
			if (utente.getId().equals(risultato.getString("idUtenti"))) {
				return false;
			}
		}
		return true;
	}

	public boolean checkPokemon(Pokemon pokemon) throws SQLException {
		PreparedStatement prepareStatement = connessione.prepareStatement("select EVO from digimon where nome = ?");
		prepareStatement.setString(1, pokemon.getNome());
		ResultSet risultato = prepareStatement.executeQuery();
		while (risultato.next()) {
			if (pokemon.getEvoluzione().equals(risultato.getString("EVO"))) {
				return false;
			}
		}
		return true;

	}

	public void deleteUtente(String id) throws SQLException {
		String queryEliminaUtente = "DELETE FROM `L4RZNtuhbB`.`Utenti` WHERE idUtenti = ?;";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryEliminaUtente);
		prepareStatement.setString(1, id);
		prepareStatement.execute();
	}

	public void deletePokemon(int id) throws SQLException {
		String queryEliminaPokemon = "DELETE FROM `L4RZNtuhbB`.`digimon` WHERE iddigimon = ?;";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryEliminaPokemon);
		prepareStatement.setInt(1, id);
		prepareStatement.execute();
	}

	public void retriveUtente() throws SQLException {
		ResultSet risultatoQuery = statement.executeQuery("select * from Utenti");
		while (risultatoQuery.next()) {
			String id = risultatoQuery.getString("idUtenti");
			String nome = risultatoQuery.getString("nome");
			System.out.println(id + " " + nome);
		}
	}

	public void retrivePokemon() throws SQLException {
		ResultSet risultatoQuery = statement.executeQuery("select * from `L4RZNtuhbB`.`digimon`");
		System.out.println(" ID " + " " + "  nome  " + " " + " HP" + "" + " ATK " + "" + "DEF " + "" + "RES " + ""
				+ "EVO " + "" + "idUtente " + "" + "tipo");
		System.out.println("------------------------------------------");
		while (risultatoQuery.next()) {
			int autoIncKeyFromFunc = risultatoQuery.getInt(1);
			String nome = risultatoQuery.getString("nome");
			int hp = risultatoQuery.getInt("HP");
			int atk = risultatoQuery.getInt("ATK");
			int def = risultatoQuery.getInt("DEF");
			int res = risultatoQuery.getInt("RES");
			String evoluzione = risultatoQuery.getString("EVO");
			String tipo = risultatoQuery.getString("tipo");
			String owner = risultatoQuery.getString("idUtenti");
			System.out.println("[ " + autoIncKeyFromFunc + " ] " + nome + " " + hp + " " + atk + " " + def + " " + res
					+ " " + evoluzione + " " + tipo + " " + owner);
		}
	}

	public void sceltaPokemon(boolean x) {
		if (x) {
			String queryInserimentoPokemon = "INSERT INTO `L4RZNtuhbB`.`Partite` (dc1, dc2, dc3) SELECT `nome` FROM `L4RZNtuhbB`.`Partite` ";
		}
	}
}
