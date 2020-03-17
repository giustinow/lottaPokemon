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
		this.url = "jdbc:mysql://remotemysql.com:3306/L4RZNtuhbB?useUnicode=true&useLegacyDatetime"
				+ "Code=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false";
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
		prepareStatement.setInt(1, pokemon.getId());
		prepareStatement.setString(2, pokemon.getNome());
		prepareStatement.setInt(3, pokemon.getHp());
		prepareStatement.setDouble(4, pokemon.getAttacco());
		prepareStatement.setDouble(5, pokemon.getDifesa());
		prepareStatement.setDouble(6, pokemon.getResistenza());
		prepareStatement.setString(7, pokemon.getEvoluzione());
		prepareStatement.setString(8, pokemon.getTipo());
		prepareStatement.setString(9, utente.getId());
		prepareStatement.execute();
	}

	public void insertCreaPartita(Partita partita) throws SQLException {
		String queryInserimentoPartita = "INSERT INTO `L4RZNtuhbB`.`Partite` (`idpartita`, `idcreatore`, `dc1`, `dc2`, `dc3`) VALUES (?, ?, ?, ?, ?);";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoPartita);
		prepareStatement.setInt(1, partita.getIdPartita());
		prepareStatement.setString(2, partita.getIdCreatore());
		prepareStatement.setInt(3, partita.getPokemonCreatore1());
		prepareStatement.setInt(4, partita.getPokemonCreatore2());
		prepareStatement.setInt(5, partita.getPokemonCreatore3());
		prepareStatement.execute();
	}

	public void insertPartecipaPartita(Partita partita) throws SQLException {
		String queryPartecipaPartita = "UPDATE `L4RZNtuhbB`.`Partite` SET `idsfidante` = ?, `ds1` = ?, `ds2` = ?, `ds3` = ? WHERE (`idpartita` = ?);";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryPartecipaPartita);
		prepareStatement.setString(1, partita.getIdCreatore());
		prepareStatement.setInt(2, partita.getPokemonCreatore1());
		prepareStatement.setInt(3, partita.getPokemonCreatore2());
		prepareStatement.setInt(4, partita.getPokemonCreatore3());
		prepareStatement.setInt(5, partita.getIdPartita());
		prepareStatement.execute();
	}

	public void duplicateRow(int idPartita) throws SQLException {
		String queryInserimentoScontro = "INSERT INTO L4RZNtuhbB.Scontro_JustineProva (OWNER, digimonA, digimonD, idpartita, HPdigimonA, HPdigimonD)\n"
				+ "SELECT OWNER, digimonA, digimonD, idpartita, HPdigimonA, HPdigimonD FROM L4RZNtuhbB.Scontro  ORDER BY idmossa DESC LIMIT 1;";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoScontro);
		prepareStatement.execute();
	}

	public void creazioneNuovoRound(int idPartita, Pokemon pokemonCreatore, Pokemon pokemonSfidante)
			throws SQLException {
		String queryInserimentoScontro = "INSERT INTO L4RZNtuhbB.Scontro_JustineProva (`OWNER`, `digimonA`, `digimonD`, `idpartita`, `HPdigimonA`, `HPdigimonD`) VALUES (?,?,?,?,?,?);";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoScontro);
		prepareStatement.setString(1, retrieveOggettoPartita(idPartita).getIdCreatore());
		prepareStatement.setString(2, pokemonCreatore.getNome());
		prepareStatement.setString(3, pokemonSfidante.getNome());
		prepareStatement.setInt(4, idPartita);
		prepareStatement.setInt(5, pokemonCreatore.getHp());
		prepareStatement.setInt(6, pokemonSfidante.getHp());
		prepareStatement.execute();
	}

	public void iniziaNuovoScontro(int idPartita) throws SQLException, InterruptedException {
		switch (1) {
		case 1: {
			Pokemon retrievePokemonPartitaCreatore = retrievePokemonPartitaCreatore(
					retrieveOggettoPartita(idPartita).getPokemonCreatore1());
			Pokemon retrievePokemonPartitaSfidante = retrievePokemonPartitaSfidante(
					retrieveOggettoPartita(idPartita).getPokemonSfidante1());
			creazioneNuovoRound(idPartita, retrievePokemonPartitaCreatore, retrievePokemonPartitaSfidante);
// al posto del for, un do while senza il break, per permettere di passare al round successivo
			for (int i = 0; i < 5; i++) {
				duplicateRow(idPartita);
				aggiornaVitaCreatore(idPartita, retrievePokemonPartitaCreatore, retrievePokemonPartitaSfidante);
				if (!aggiornaVitaSfidante(idPartita, retrievePokemonPartitaCreatore, retrievePokemonPartitaSfidante)) {
					System.out.println("Giustino ha vinto");
				}
				System.out.println("Turno di Giustino");
				Thread.sleep(200);
				duplicateRow(idPartita);
				aggiornaVitaSfidante(idPartita, retrievePokemonPartitaCreatore, retrievePokemonPartitaSfidante);
				if (!aggiornaVitaCreatore(idPartita, retrievePokemonPartitaCreatore, retrievePokemonPartitaSfidante)) {
					System.out.println("Giustino ha perso");
				}
				System.out.println("Turno dell'avversario");
				Thread.sleep(200);
			}
			break;
		}

		case 2: {
			creazioneNuovoRound(idPartita,
					retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore2()),
					retrievePokemonPartitaSfidante(retrieveOggettoPartita(idPartita).getPokemonSfidante2()));

		}
		case 3: {
			creazioneNuovoRound(idPartita,
					retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore3()),
					retrievePokemonPartitaSfidante(retrieveOggettoPartita(idPartita).getPokemonSfidante3()));
			break;
		}
		}

	}

	public int attaccaPokemonDiAvversario(Pokemon pokemonCreatore, Pokemon pokemonAvversario) {
		if (pokemonCreatore.getTipo().equals(pokemonAvversario.getTipo())) {
			return (int) pokemonCreatore.difesaNormale(pokemonAvversario);
		} else if (pokemonCreatore.vantaggioTipoPokemon(pokemonAvversario)) {
			return (int) pokemonCreatore.difesaForte(pokemonAvversario);
		}
		return (int) pokemonCreatore.difesaScarsa(pokemonAvversario);
	}

	public boolean aggiornaVitaSfidante(int idPartita, Pokemon pokemonCreatore, Pokemon pokemonSfidante)
			throws SQLException {
		String queryInserimentoScontro = "UPDATE L4RZNtuhbB.Scontro_JustineProva SET OWNER = ?, digimonA = ?, digimonD = ?, idpartita = ?, HPdigimonA = HPdigimonD, HPdigimonD = HPdigimonD - ?  ORDER BY idmossa DESC LIMIT 1;";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoScontro);
		if (verificaHpPokemonDifesa() >= 0) {
			prepareStatement.setString(1, retrieveOggettoPartita(idPartita).getIdSfidante());
			prepareStatement.setString(2, pokemonCreatore.getNome());
			prepareStatement.setString(3, pokemonSfidante.getNome());
			prepareStatement.setInt(4, idPartita);
			prepareStatement.setInt(5, attaccaPokemonDiAvversario(pokemonCreatore, pokemonSfidante));
			prepareStatement.execute();
			return true;
		}
		return false;
	}

	public void evolviPokemon(int idPokemon) {

	}

	public boolean aggiornaVitaCreatore(int idPartita, Pokemon pokemonCreatore, Pokemon pokemonSfidante)
			throws SQLException {
		String queryInserimentoScontro = "UPDATE L4RZNtuhbB.Scontro_JustineProva SET OWNER = ?, digimonA = ?, digimonD = ?, idpartita = ?, HPdigimonA = HpdigimonD, HPdigimonD =  HPdigimonA - ? ORDER BY idmossa DESC LIMIT 1;";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoScontro);
		if (verificaHpPokemonDifesa() >= 0) {
			prepareStatement.setString(1, retrieveOggettoPartita(idPartita).getIdCreatore());
			prepareStatement.setString(2, pokemonSfidante.getNome());
			prepareStatement.setString(3, pokemonCreatore.getNome());
			prepareStatement.setInt(4, idPartita);
			prepareStatement.setInt(5, attaccaPokemonDiAvversario(pokemonSfidante, pokemonCreatore));
			prepareStatement.execute();
			return true;
		}
		return false;
	}

	public int verificaHpPokemonDifesa() throws SQLException {// verifica degli hp rimanenti dei pokemon in lotta
		ResultSet risultatoQueryCreatore = statement
				.executeQuery("SELECT HPdigimonD FROM L4RZNtuhbB.Scontro_JustineProva order by idmossa desc limit 1;");
		while (risultatoQueryCreatore.next()) {
			return risultatoQueryCreatore.getInt("HPdigimonD");
		}
		return 0;
	}

	public Pokemon retrievePokemonPartitaCreatore(int idPokemon) throws SQLException {// con la correzione di ieri mi
																						// dava comunque un nullpointer
																						// exception
		ResultSet risultatoQueryCreatore = statement
				.executeQuery("select * from L4RZNtuhbB.digimon where iddigimon = \"" + idPokemon + "\"");
		int id = 0;
		String nome = "";
		int hp = 0;
		int atk = 0;
		int def = 0;
		int res = 0;
		String evo = "";
		String tipo = "";
		String idUtenti = "";
		while (risultatoQueryCreatore.next()) {
			id = risultatoQueryCreatore.getInt("iddigimon");
			nome = risultatoQueryCreatore.getString("nome");
			hp = risultatoQueryCreatore.getInt("HP");
			atk = risultatoQueryCreatore.getInt("ATK");
			def = risultatoQueryCreatore.getInt("DEF");
			res = risultatoQueryCreatore.getInt("RES");
			evo = risultatoQueryCreatore.getString("EVO");
			tipo = risultatoQueryCreatore.getString("tipo");
			idUtenti = risultatoQueryCreatore.getString("idUtenti");
		}
		return new Pokemon(id, nome, hp, atk, def, res, evo, tipo, idUtenti);

	}

	public Pokemon retrievePokemonPartitaSfidante(int idPokemon) throws SQLException {// con la correzione di ieri mi
																						// dava comunque un nullpointer
																						// exception
		ResultSet risultatoQueryCreatore = statement
				.executeQuery("select * from L4RZNtuhbB.digimon_JustineProva where iddigimon = \"" + idPokemon + "\"");
		int id = 0;
		String nome = "";
		int hp = 0;
		int atk = 0;
		int def = 0;
		int res = 0;
		String evo = "";
		String tipo = "";
		String idUtenti = "";
		while (risultatoQueryCreatore.next()) {
			id = risultatoQueryCreatore.getInt("iddigimon");
			nome = risultatoQueryCreatore.getString("nome");
			hp = risultatoQueryCreatore.getInt("HP");
			atk = risultatoQueryCreatore.getInt("ATK");
			def = risultatoQueryCreatore.getInt("DEF");
			res = risultatoQueryCreatore.getInt("RES");
			evo = risultatoQueryCreatore.getString("EVO");
			tipo = risultatoQueryCreatore.getString("tipo");
			idUtenti = risultatoQueryCreatore.getString("idUtenti");
		}
		return new Pokemon(id, nome, hp, atk, def, res, evo, tipo, idUtenti);
	}

	public Partita retrieveOggettoPartita(int idPartita) throws SQLException {// con la correzione di ieri mi dava
																				// comunque un nullpointer exception
		ResultSet risultatoQueryCreatore = statement.executeQuery(
				"select idcreatore,idsfidante, dc1, dc2, dc3, ds1, ds2, ds3 from L4RZNtuhbB.Partite where idpartita = \""
						+ idPartita + "\"");
		String idCreatore = "";
		int digimonCreatore1 = 0;
		int digimonCreatore2 = 0;
		int digimonCreatore3 = 0;
		String idSfidante = "";
		int digimonSfidante1 = 0;
		int digimonSfidante2 = 0;
		int digimonSfidante3 = 0;
		while (risultatoQueryCreatore.next()) {
			idCreatore = risultatoQueryCreatore.getString("idcreatore");
			digimonCreatore1 = risultatoQueryCreatore.getInt("dc1");
			digimonCreatore2 = risultatoQueryCreatore.getInt("dc2");
			digimonCreatore3 = risultatoQueryCreatore.getInt("dc3");
			idSfidante = risultatoQueryCreatore.getString("idsfidante");
			digimonSfidante1 = risultatoQueryCreatore.getInt("ds1");
			digimonSfidante2 = risultatoQueryCreatore.getInt("ds2");
			digimonSfidante3 = risultatoQueryCreatore.getInt("ds3");
		}
		return new Partita(idPartita, idCreatore, idSfidante, digimonCreatore1, digimonCreatore2, digimonCreatore3,
				digimonSfidante1, digimonSfidante2, digimonSfidante3);
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

	public void retrieveUtente() throws SQLException {
		ResultSet risultatoQuery = statement.executeQuery("select * from Utenti");
		System.out.println(" ID " + "  " + "  nome");
		System.out.println("--------------------");
		while (risultatoQuery.next()) {
			String id = risultatoQuery.getString("idUtenti");
			String nome = risultatoQuery.getString("nome");
			System.out.println("[ " + id + " ]" + " " + nome);
		}
	}

	public void retrievePartita() throws SQLException {
		ResultSet risultatoQuery = statement.executeQuery("select * from `L4RZNtuhbB`.`Partite`");
		while (risultatoQuery.next()) {
			int idPartite = risultatoQuery.getInt("idpartita");
			String idCreatore = risultatoQuery.getString("idcreatore");
			String idSfidante = risultatoQuery.getString("idsfidante");
			int dc1 = risultatoQuery.getInt("dc1");
			int dc2 = risultatoQuery.getInt("dc2");
			int dc3 = risultatoQuery.getInt("dc3");
			int ds1 = risultatoQuery.getInt("ds1");
			int ds2 = risultatoQuery.getInt("ds2");
			int ds3 = risultatoQuery.getInt("ds3");
			System.out.println("[ " + idPartite + " ] " + idCreatore + " " + idSfidante + " " + dc1 + " " + dc2 + " "
					+ dc3 + " " + ds1 + " " + ds2 + " " + ds3);
		}
	}

	public void retrievePokemonUtente(Utente idUtente) throws SQLException {
		PreparedStatement prepareStatement = connessione
				.prepareStatement("select * from `L4RZNtuhbB`.`digimon` where idUtenti = ?");
		prepareStatement.setString(1, idUtente.getId());
		ResultSet risultato = prepareStatement.executeQuery();
		System.out.println(" ID " + " " + "  nome  " + " " + " HP" + "" + " ATK " + "" + "DEF " + "" + "RES " + ""
				+ "EVO " + "" + "tipo" + "" + "idUtente ");
		System.out.println("------------------------------------------");
		while (risultato.next()) {
			int autoIncKeyFromFunc = risultato.getInt(1);
			String nome = risultato.getString("nome");
			int hp = risultato.getInt("HP");
			int atk = risultato.getInt("ATK");
			int def = risultato.getInt("DEF");
			int res = risultato.getInt("RES");
			String evoluzione = risultato.getString("EVO");
			String tipo = risultato.getString("tipo");
			String owner = risultato.getString("idUtenti");
			System.out.println("[ " + autoIncKeyFromFunc + " ] " + nome + " " + hp + " " + atk + " " + def + " " + res
					+ " " + evoluzione + " " + tipo + " " + owner);
		}
	}

	public void retrievePokemon() throws SQLException {
		ResultSet risultatoQuery = statement.executeQuery("select * from `L4RZNtuhbB`.`digimon`");
		System.out.println(" ID " + " " + "  nome  " + " " + " HP" + "" + " ATK " + "" + "DEF " + "" + "RES " + ""
				+ "EVO " + "" + "tipo" + "" + "idUtente ");
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
}
