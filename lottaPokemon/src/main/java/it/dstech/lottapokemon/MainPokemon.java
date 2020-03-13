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
				gestione.insertPokemon(nuovoPokemon(input));
				break;
			}
			case 2: {
				gestione.deletePokemon(scegliId(input, gestione));
				break;
			}
			case 3: {
				gestione.retrivePokemon();
				break;
			}
			case 4: {
				nuovoUtente(input, gestione);
				break;
			}
			}
		}
	}

	public static void nuovoUtente(Scanner input, Gestione gestione) throws SQLException {
		System.out.println("Inserisci l'id dell'utente");
		String idUtente = input.nextLine();
		System.out.println("Inserisci il nome dell'utente");
		String nome = input.nextLine();
		gestione.insertUtente(idUtente, nome);
	}

	public static int scegliId(Scanner input, Gestione gestione) throws SQLException {
		gestione.retrivePokemon();
		System.out.println("Scegli l'ID del pokemon");
		int scelta = input.nextInt();
		input.nextLine();
		return scelta;
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
		System.out.println("Inserisci il nome del proprietario del Pokemon");
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
		System.out.println("1. Inserisci un nuovo Pokemon da mettere al centro Pokemon");
		System.out.println("2. Rimuovi il Pokemon dal centro Pokemon");
		System.out.println("3. Stampa la lista di tutti i Pokemon");
		System.out.println("4. Aggiungi Pokemon come creatore");
		System.out.println("5. Aggiungi Pokemon come sfidante");
		System.out.println("************************************************************************");

	}
}
