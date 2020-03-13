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

	public void insertPokemon(Pokemon pokemon) throws SQLException {
		String queryInserimentoPokemon = "INSERT INTO `L4RZNtuhbB`.`digimon` (`nome`, `HP`, `ATK`, `DEF`, `RES`, `EVO`, `owner`, `tipo` ) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoPokemon);
		prepareStatement.setString(1, pokemon.getNome());
		prepareStatement.setInt(2, pokemon.getHp());
		prepareStatement.setInt(3, pokemon.getAttacco());
		prepareStatement.setInt(4, pokemon.getDifesa());
		prepareStatement.setInt(5, pokemon.getVelocita());
		prepareStatement.setString(6, pokemon.getEvoluzione());
		prepareStatement.setString(7, pokemon.getProprietario());
		prepareStatement.setString(8, pokemon.getTipo());
		prepareStatement.execute();
	}

	public void deletePokemon(int id) throws SQLException {
		String queryEliminaPokemon = "DELETE FROM `L4RZNtuhbB`.`digimon` WHERE id = ?;";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryEliminaPokemon);
		prepareStatement.setInt(1, id);
		prepareStatement.execute();
	}

	public void retrivePokemon() throws SQLException {
		ResultSet risultatoQuery = statement.executeQuery("select * from `pokemon`.`pokemon`");
		System.out.println(
				" ID " + " " + "  nome  " + " " + " HP" + "" + " ATK " + "" + "DEF " + "" + "RES " + "" + "EVO " + "" + "owner " + "" + "tipo");
		System.out.println("------------------------------------------");
		while (risultatoQuery.next()) {
			int autoIncKeyFromFunc = risultatoQuery.getInt(1);
			String nome = risultatoQuery.getString("nome");
			int hp = risultatoQuery.getInt("HP");
			int atk = risultatoQuery.getInt("ATK");
			int def = risultatoQuery.getInt("DEF");
			int res = risultatoQuery.getInt("RES");
			String evoluzione = risultatoQuery.getString("evoluzione");
			String owner = risultatoQuery.getString("owner");
			String tipo = risultatoQuery.getString("tipo");
			System.out.println("[ " + autoIncKeyFromFunc + " ] " + nome + " " + hp + " " + atk + " " + def + " " + res
					+ " " + evoluzione + " " + owner + " " + tipo);
		}
	}
}
