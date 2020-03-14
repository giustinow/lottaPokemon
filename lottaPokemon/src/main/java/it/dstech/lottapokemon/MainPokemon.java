package it.dstech.lottapokemon;

import java.sql.SQLException;
import java.util.Scanner;

public class MainPokemon {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
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
				if (gestione.checkPersona(sceltaUtente)) {
					if (gestione.checkPokemon(nuovoPokemon)) {
						gestione.insertPokemon(nuovoPokemon, sceltaUtente);
					} else {
						System.out.println("Pokemon già presente");
					}
				}else {
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
				gestione.retrivePokemon();
				break;
			}
			case 6:{
				gestione.retriveUtente();
				break;
			}
			}
		}
	}
	
	public static Utente sceltaUtente(Scanner input, Gestione gestione) throws SQLException {
		System.out.println("Scegli l'utenti tra gli utenti registrati");
		gestione.retriveUtente();
		return new Utente(input.nextLine(), "");
	}

	public static Utente nuovoUtente(Scanner input) throws SQLException {
		System.out.println("Inserisci l'id dell'utente");
		String idUtente = input.nextLine();
		System.out.println("Inserisci il nome dell'utente");
		String nome = input.nextLine();
		return new Utente(idUtente, nome);
	}
	public static String scegliIdUtente(Scanner input,Gestione gestione) throws SQLException {
		System.out.println("Scegli l'ID dell'utente");
		gestione.retriveUtente();
		return input.nextLine();
	}
	public static int scegliIdPokemon(Scanner input, Gestione gestione) throws SQLException {
		System.out.println("Scegli l'ID del pokemon");
		gestione.retrivePokemon();
		return input.nextInt();

	}

	public static Pokemon nuovoPokemon(Scanner input) {
		System.out.println("Inserisci il NOME del tuo Pokemon");
		String nome = input.nextLine();
		int hp = randomHP(input);
		int attacco = randomAttacco(input);
		int difesa = randomDifesa(input);
		int resistenza = randomAttacco(input);
		System.out.println("Inserisci lo stadio di evoluzione del Pokemon");
		String evoluzione = input.nextLine();
		System.out.println("Inserisci l'ID del proprietario del Pokemon (idUtente)");
		String nomeProprietario = input.nextLine();
		System.out.println("Inserisci il tipo di Pokemon");
		String tipo = input.nextLine();
		return new Pokemon(nome, hp, attacco, difesa, resistenza, evoluzione, nomeProprietario, tipo);
	}

	public static int randomHP(Scanner input) {
		double max = 600.0;
		double min = 500.0;

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

	public static void menu() {
		System.out.println("Scegli cosa fare");
		System.out.println("************************************************************************");
		System.out.println("1. Registra utente");
		System.out.println("2. Inserisci un nuovo Pokemon da mettere al centro Pokemon");
		System.out.println("3. Rimuovi il Pokemon dal centro Pokemon");
		System.out.println("4. Stampa la lista di tutti i Pokemon");
		System.out.println("5. Crea un nuovo utente");
		System.out.println("6. Aggiungi Pokemon come sfidante");
		System.out.println("************************************************************************");

	}
}
