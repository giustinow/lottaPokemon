package it.dstech.lottapokemon;

import java.sql.SQLException;
import java.util.Scanner;

public class MainPokemon {

	public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
		Scanner input = new Scanner(System.in);
		Gestione gestione = new Gestione();
		while (true) {
			menu();
			int scelta = input.nextInt();
			input.nextLine();
			switch (scelta) {
			case 1: {
				Utente nuovoUtente = nuovoUtente(input);
				if (gestione.checkPersona(nuovoUtente)) {
					gestione.insertUtente(nuovoUtente);
				} else {
					System.out.println("Utente già registrato");
				}
				break;
			}
			case 2: {
				Utente sceltaUtente = sceltaUtente(input, gestione);
				Pokemon nuovoPokemon = nuovoPokemon(input);
				if (!gestione.checkPersona(sceltaUtente)) {
					if (gestione.checkPokemon(nuovoPokemon)) {
						gestione.insertPokemon(nuovoPokemon, sceltaUtente);
					} else {
						System.out.println("Pokemon già presente");
					}
				} else {
					System.out.println("Id inesistente");
				}
				break;
			}
			case 3: {
				gestione.deletePokemon(scegliIdPokemon(input, gestione));
				break;
			}
			case 4: {
				gestione.deleteUtente(scegliIdUtente(input, gestione));
				break;
			}
			case 5: {
				gestione.retrievePokemon();
				break;
			}
			case 6: {
				gestione.retrieveUtente();
				break;
			}
			case 7: {
				gestione.insertCreaPartita(creaPartita(input, gestione));
				break;
			}
			case 8: {
				gestione.insertPartecipaPartita(partecipaPartita(input, gestione));
				break;
			}
			case 9: {
				System.out.println("Scegli l'ID della partita");
				gestione.retrievePartita();
				int idPartita = input.nextInt();
				input.nextLine();  
				gestione.creaSfidaPokemon(idPartita);
				break;
			}
			}
		}
	}

	private static Partita partecipaPartita(Scanner input, Gestione gestione) throws SQLException {
		System.out.println("Inserisci l'id della partita in cui vuoi partecipare");
		int idPartita = input.nextInt();
		Utente sceltaUtente = sceltaUtente(input, gestione);
		gestione.retrievePokemonUtente(sceltaUtente);
		System.out.println("Inserisci il 1°");
		int ds1 = input.nextInt();
		input.nextLine();
		System.out.println("Inserisci il 2°");
		int ds2 = input.nextInt();
		input.nextLine();
		System.out.println("Inserisci il 3°");
		int ds3 = input.nextInt();
		input.nextLine();
		return new Partita(idPartita, sceltaUtente.getId(), "", ds1, ds2, ds3, 0,0, 0) ;
	}

	public static Partita creaPartita(Scanner input, Gestione gestione) throws SQLException {
		System.out.println("Inserisci l'ID della partita");// id partita e id creatore a cosa corrispondono?
		int idPartita = input.nextInt();
		Utente sceltaUtente = sceltaUtente(input, gestione);
		System.out.println("Scegli i 3 pokemon che devono combattere");
		gestione.retrievePokemonUtente(sceltaUtente);
		System.out.println("Inserisci il 1°");
		int dc1 = input.nextInt();
		System.out.println("Inserisci il 2°");
		int dc2 = input.nextInt();
		System.out.println("Inserisci il 3°");
		int dc3 = input.nextInt();
		return new Partita(idPartita, sceltaUtente.getId(), "", dc1, dc2, dc3, 0,0, 0);
	}

	public static Utente sceltaUtente(Scanner input, Gestione gestione) throws SQLException {
		System.out.println("Scegli l'utente tra gli utenti registrati");
		gestione.retrieveUtente();
		return new Utente(input.nextLine(), "");
	}

	public static Utente nuovoUtente(Scanner input) throws SQLException {
		System.out.println("Inserisci l'id dell'utente");
		String idUtente = input.nextLine();
		System.out.println("Inserisci il nome dell'utente");
		String nome = input.nextLine();
		return new Utente(idUtente, nome);
	}

	public static String scegliIdUtente(Scanner input, Gestione gestione) throws SQLException {
		System.out.println("Scegli l'ID dell'utente");
		gestione.retrieveUtente();
		return input.nextLine();
	}

	public static int scegliIdPokemon(Scanner input, Gestione gestione) throws SQLException {
		System.out.println("Scegli l'ID del pokemon");
		gestione.retrievePokemon();
		return input.nextInt();

	}

	public static Pokemon nuovoPokemon(Scanner input) {
		System.out.println("Inserisci l'ID del tuo Pokemon");
		int idPokemon = input.nextInt();
		input.nextLine();
		System.out.println("Inserisci il NOME del tuo Pokemon");
		String nome = input.nextLine();
		int hp = randomHP(input);
		int attacco = randomAttacco(input);
		int difesa = randomDifesa(input);
		int resistenza = randomResistenza(input);
		System.out.println("Inserisci lo stadio di evoluzione del Pokemon");
		String evoluzione = input.nextLine();
		System.out.println("Inserisci l'ID del proprietario del Pokemon (idUtente)");
		String nomeProprietario = input.nextLine();
		String tipoPokemon = tipoPokemon(input);
		return new Pokemon(idPokemon,nome, hp, attacco, difesa, resistenza, evoluzione, nomeProprietario, tipoPokemon);
	}

	public static String tipoPokemon(Scanner input) {
		menuTipo();
		int scelta = input.nextInt();
		input.nextLine();
		if (scelta == 1) {
			return "acqua";
		} else if (scelta == 2) {
			return "fuoco";
		} else if (scelta == 3) {
			return "aria";
		}
		return "terra";
	}

	public static int randomHP(Scanner input) {
		double max = 600.0;
		double min = 500.0;

		int x = (int) ((int) (Math.random() * ((max - min) + 1)) + min);
		return x;
	}

	public static int randomResistenza(Scanner input) {
		double max = 5.0;
		double min = 10.0;

		int x = (int) ((int) (Math.random() * ((max - min) + 1)) + min);

		return x;
	}

	public static int randomAttacco(Scanner input) {
		double max = 150.0;
		double min = 100.0;
		int x = (int) ((int) (Math.random() * ((max - min) + 1)) + min);
		return x;
	}

	public static int randomDifesa(Scanner input) {
		double max = 30.0;
		double min = 10.0;
		int x = (int) ((int) (Math.random() * ((max - min) + 1)) + min);
		return x;
	}

	public static void menuTipo() {
		System.out.println("-----------------------");
		System.out.println("Scegli il tipo di pokemon");
		System.out.println("1. Acqua");
		System.out.println("2. Fuoco");
		System.out.println("3. Aria");
		System.out.println("4.Terra");
		System.out.println("-----------------------");
	}

	public static void menu() {
		System.out.println("************************************************************************");
		System.out.println("Scegli cosa fare");
		System.out.println("1. Registra utente");
		System.out.println("2. Inserisci un nuovo Pokemon da mettere al centro Pokemon");
		System.out.println("3. Rimuovi il Pokemon dal centro Pokemon");
		System.out.println("4. Rimuovi utente registrato");
		System.out.println("5. Stampa la lista dei Pokemon");
		System.out.println("6. Stampa la lista dei Pokemon");
		System.out.println("7. Crea una nuova partita");
		System.out.println("8. Partecipa ad una partita");
		System.out.println("9. Crea la partita");
		System.out.println("************************************************************************");

	}
}
