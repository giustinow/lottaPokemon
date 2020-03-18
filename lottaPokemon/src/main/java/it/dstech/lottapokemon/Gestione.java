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
		String queryInserimentoPokemon = "INSERT INTO L4RZNtuhbB.digimon_JustineProva (`iddigimon`, `nome`, `HP`, `ATK`, `DEF`, `RES`, `EVO`, `tipo`, `idUtenti` ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
		String queryInserimentoPartita = "INSERT INTO `L4RZNtuhbB`.`Partite_JustineProva` (`idpartita`, `idcreatore`, `dc1`, `dc2`, `dc3`) VALUES (?, ?, ?, ?, ?);";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoPartita);
		prepareStatement.setInt(1, partita.getIdPartita());
		prepareStatement.setString(2, partita.getIdCreatore());
		prepareStatement.setInt(3, partita.getPokemonCreatore1());
		prepareStatement.setInt(4, partita.getPokemonCreatore2());
		prepareStatement.setInt(5, partita.getPokemonCreatore3());
		prepareStatement.execute();
	}

	public void insertPartecipaPartita(Partita partita) throws SQLException {
		String queryPartecipaPartita = "UPDATE `L4RZNtuhbB`.`Partite_JustineProva` SET `idsfidante` = ?, `ds1` = ?, `ds2` = ?, `ds3` = ? WHERE (`idpartita` = ?);";
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
				+ "SELECT OWNER, digimonA, digimonD, idpartita, HPdigimonA, HPdigimonD FROM L4RZNtuhbB.Scontro_JustineProva  ORDER BY idmossa DESC LIMIT 1;";
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

	public boolean iniziaNuovoScontro(int idPartita) throws SQLException, InterruptedException {
		int contaVinciteGiustino = 0;
		System.out.println("          Inizio del primo round ");

		insertHpInizialiPrimoRound(idPartita);
		if (nuovoRound(idPartita,
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore1()),
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonSfidante1()))) {
			contaVinciteGiustino++;
		}
		System.out.println("          Inizio del secondo round");
		emptyRow();
		insertHpInizialiSecondoRound(idPartita);
		if (nuovoRound(idPartita,
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore2()),
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonSfidante2()))) {
			contaVinciteGiustino++;
		}
		System.out.println("          Inizio dell'ultimo round");
		emptyRow();
		insertHpInizialiTerzoRound(idPartita);
		if (nuovoRound(idPartita,
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore3()),
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonSfidante3()))) {
			contaVinciteGiustino++;
		}
		System.out.println("Fine del torneo");
		if (contaVinciteGiustino > 1) {
			return true;
		} else {
		}
		return false;

	}

	private void insertHpInizialiTerzoRound(int idPartita) throws SQLException {
		creazioneNuovoRound(idPartita,
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore3()),
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonSfidante3()));
		creazioneNuovoRound(idPartita,
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore3()),
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonSfidante3()));
	}

	private void insertHpInizialiSecondoRound(int idPartita) throws SQLException {
		creazioneNuovoRound(idPartita,
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore2()),
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonSfidante2()));

		creazioneNuovoRound(idPartita,
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore2()),
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonSfidante2()));
	}

	private void insertHpInizialiPrimoRound(int idPartita) throws SQLException {
		creazioneNuovoRound(idPartita,
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore1()),
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonSfidante1()));
		creazioneNuovoRound(idPartita,
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore1()),
				retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonSfidante1()));
	}

	public boolean nuovoRound(int idPartita, Pokemon retrievePokemonCreatorePrimoRound,
			Pokemon retrievePokemonSfidantePrimoRound) throws SQLException, InterruptedException {
		do {
			updateVitaSfidante(idPartita, retrievePokemonCreatorePrimoRound, retrievePokemonSfidantePrimoRound);
			System.out.println("Danni inflitti dal pokemon di Giustino: "
					+ attaccaPokemonDiAvversario(retrievePokemonCreatorePrimoRound, retrievePokemonSfidantePrimoRound));
			System.out.println("Danni inflitta dal pokemon di Giustino: "
					+ attaccaPokemon(retrievePokemonCreatorePrimoRound, retrievePokemonCreatorePrimoRound));
			if (!checkHpPokemonDifesa()) {
				System.out.println("Giustino ha vinto");
				return true;
			}
			duplicateRow(idPartita);
			System.out.println("Turno di Giustino");
			Thread.sleep(200);
			updateVitaCreatore(idPartita, retrievePokemonCreatorePrimoRound, retrievePokemonSfidantePrimoRound);
			System.out.println("Danni inflitti dal pokemon dell'avversario: "
					+ attaccaPokemonDiAvversario(retrievePokemonSfidantePrimoRound, retrievePokemonCreatorePrimoRound));
			System.out.println("Danni inflitta dal pokemon dell'avversaio: "
					+ attaccaPokemon(retrievePokemonSfidantePrimoRound, retrievePokemonCreatorePrimoRound));
			if (!checkHpPokemonAttacco()) {
				System.out.println("Giustino ha perso");
				return false;
			}
			duplicateRow(idPartita);
			System.out.println("Turno dell'avversario");
			Thread.sleep(200);
		} while (true);
	}

	public int attaccaPokemon(Pokemon pokemonCreatore, Pokemon pokemonSfidante) {
		if (pokemonCreatore.vantaggioTipoPokemon(pokemonSfidante)) {
			return (int) pokemonCreatore.attaccoForte();
		} else if (pokemonCreatore.uguaglianzaTipo(pokemonSfidante)) {
			return (int) pokemonCreatore.attaccoNormale();
		}
		return (int) pokemonCreatore.attaccoScarso();
	}

	public int attaccaPokemonDiAvversario(Pokemon pokemonCreatore, Pokemon pokemonSfidante) throws SQLException {
		if (pokemonCreatore.uguaglianzaTipo(pokemonSfidante)) {
			return pokemonSfidante.difesaPokemon((int) pokemonCreatore.attaccoNormale());
		} else if (pokemonCreatore.vantaggioTipoPokemon(pokemonSfidante)) {
			return pokemonSfidante.difesaPokemon((int) pokemonCreatore.attaccoForte());
		}
		return pokemonSfidante.difesaPokemon((int) pokemonCreatore.attaccoScarso());

	}

	public void emptyRow() throws SQLException {
		String queryInserimentoScontro = "INSERT INTO L4RZNtuhbB.Scontro_JustineProva (`OWNER`, `digimonA`, `digimonD`, `idpartita`, `HPdigimonA`, `HPdigimonD`) VALUES (?,?,?,?,?,?);";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoScontro);
		prepareStatement.setString(1, " ");
		prepareStatement.setString(2, " ");
		prepareStatement.setString(3, " ");
		prepareStatement.setInt(4, 0);
		prepareStatement.setInt(5, 0);
		prepareStatement.setInt(6, 0);
		prepareStatement.execute();
	}


	public void evolviPokemon(int idPokemon) throws SQLException {
		String queryInserimentoScontro = "UPDATE L4RZNtuhbB.digimon_JustineProva SET HP = HP + HP * 0.1, ATK = ATK + ATK * 0.1, DEF = DEF + DEF * 0.1, RES = RES + RES * 0.1, EVO = ? where iddigimon = ?";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoScontro);
		if (checkEvoluzione(idPokemon)) {
			prepareStatement.setString(1, "Seconda");
			prepareStatement.setInt(2, idPokemon);
			prepareStatement.execute();
			return;
		}
		prepareStatement.setString(1, "ultima");
		prepareStatement.setInt(2, idPokemon);
		prepareStatement.execute();
		return;
	}

	public boolean checkEvoluzione(int idPokemon) throws SQLException {
		if (retrievePokemonPartitaCreatore(idPokemon).getEvoluzione().equals("prima")) {
			return true;
		}
		return false;
	}

	public boolean checkHpPokemonDifesa() throws SQLException {// verifica degli hp rimanenti dei pokemon in lotta
		ResultSet risultatoQueryCreatore = statement
				.executeQuery("SELECT HPdigimonD FROM L4RZNtuhbB.Scontro_JustineProva order by idmossa desc limit 1;");
		while (risultatoQueryCreatore.next()) {
			if (risultatoQueryCreatore.getInt("HPdigimonD") < 0) {
				return false;
			}
		}
		return true;
	}

	public boolean checkHpPokemonAttacco() throws SQLException {// verifica degli hp rimanenti dei pokemon in lotta
		ResultSet risultatoQueryCreatore = statement
				.executeQuery("SELECT HPdigimonA FROM L4RZNtuhbB.Scontro_JustineProva order by idmossa desc limit 1;");
		while (risultatoQueryCreatore.next()) {
			if (risultatoQueryCreatore.getInt("HPdigimonA") < 0) {
				return false;
			}
		}
		return true;
	}
	public boolean checkEsistenzaIdPartita(int idPartita) throws SQLException {
		PreparedStatement prepareStatement = connessione
				.prepareStatement("select idpartita from L4RZNtuhbB.Partite_JustineProva where idpartita = ?");
		prepareStatement.setInt(1, idPartita);
		ResultSet risultato = prepareStatement.executeQuery();
		Integer partita = idPartita;
		while (risultato.next()) {
			if(partita.equals(risultato.getInt("idpartita"))) {
				return false;
			}	
		}return true;
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
		PreparedStatement prepareStatement = connessione
				.prepareStatement("select EVO from digimon_JustineProva where nome = ?");
		prepareStatement.setString(1, pokemon.getNome());
		ResultSet risultato = prepareStatement.executeQuery();
		while (risultato.next()) {
			if (pokemon.getEvoluzione().equals(risultato.getString("EVO"))) {
				return false;
			}
		}
		return true;

	}
	public void updateVitaSfidante(int idPartita, Pokemon pokemonCreatore, Pokemon pokemonSfidante)
			throws SQLException {
		String queryInserimentoScontro = "UPDATE L4RZNtuhbB.Scontro_JustineProva SET OWNER = ?, digimonA = ?, digimonD = ?, idpartita = ?, HPdigimonA = HPdigimonA- ?, HPdigimonD = HPdigimonD  ORDER BY idmossa DESC LIMIT 1;";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoScontro);
		prepareStatement.setString(1, retrieveOggettoPartita(idPartita).getIdSfidante());
		prepareStatement.setString(2, pokemonCreatore.getNome());
		prepareStatement.setString(3, pokemonSfidante.getNome());
		prepareStatement.setInt(4, idPartita);
		prepareStatement.setInt(5, attaccaPokemonDiAvversario(pokemonSfidante, pokemonCreatore));
		prepareStatement.execute();
	}

	public void updateVitaCreatore(int idPartita, Pokemon pokemonCreatore, Pokemon pokemonSfidante)
			throws SQLException {
		String queryInserimentoScontro = "UPDATE L4RZNtuhbB.Scontro_JustineProva SET OWNER = ?, digimonA = ?, digimonD = ?, idpartita = ?, HPdigimonA = HPdigimonA  , HPdigimonD = HPdigimonD - ? ORDER BY idmossa DESC LIMIT 1;";
		PreparedStatement prepareStatement = connessione.prepareStatement(queryInserimentoScontro);

		prepareStatement.setString(1, retrieveOggettoPartita(idPartita).getIdCreatore());
		prepareStatement.setString(3, pokemonSfidante.getNome());
		prepareStatement.setString(2, pokemonCreatore.getNome());
		prepareStatement.setInt(4, idPartita);
		prepareStatement.setInt(5, attaccaPokemonDiAvversario(pokemonCreatore, pokemonSfidante));
		prepareStatement.execute();
	}


	public Pokemon retrievePokemonPartitaCreatore(int idPokemon) throws SQLException {// con la correzione di ieri mi
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

	public void retrieveListaPokemonPartita(int idPartita) throws SQLException {
		System.out.println(retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore1()));
		System.out.println(retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore2()));
		System.out.println(retrievePokemonPartitaCreatore(retrieveOggettoPartita(idPartita).getPokemonCreatore3()));
	}

	public void retrievePartita() throws SQLException {
		ResultSet risultatoQuery = statement.executeQuery("select * from `L4RZNtuhbB`.`Partite_JustineProva`");
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
				.prepareStatement("select * from L4RZNtuhbB.digimon_JustineProva where idUtenti = ?");
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
		ResultSet risultatoQuery = statement.executeQuery("select * from L4RZNtuhbB.digimon_JustineProva");
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
}
