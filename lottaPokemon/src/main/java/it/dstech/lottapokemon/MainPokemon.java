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
			}
		}
	}

	public static Pokemon nuovoPokemon(Scanner input) {
		System.out.println("Inserisci il NOME del tuo Pokemon");
		String nome = input.nextLine();
		System.out.println("Inserisci l'HP del pokemon");
		int hp = input.nextInt();
		System.out.println("Inserisci il livello di ATTACCO del Pokemon");
		int attacco = input.nextInt();
		input.nextLine();
		System.out.println("Inserisci il livello di DIFESA del Pokemon");
		int difesa = input.nextInt();
		input.nextLine();
		System.out.println("Inserisci il livello di VELOCITA del Pokemon");
		int velocita = input.nextInt();
		input.nextLine();
		System.out.println("Inserisci lo stadio di evoluzione del Pokemon");
		String evoluzione = input.nextLine();
		System.out.println("Inserisci il nome del proprietario del Pokemon");
		String nomeProprietario = input.nextLine();
		System.out.println("Inserisci il tipo di Pokemon");
		String tipo = input.nextLine();
		return new Pokemon(nome, hp, attacco, difesa, velocita, evoluzione,nomeProprietario, tipo);
	}

	public static void menu() {
		System.out.println("Scegli cosa fare");
		System.out.println("************************************************************************");
		System.out.println("1. Inserisci un nuovo Pokemon da mettere al centro Pokemon");
		System.out.println("2. Rimuovi il Pokemon dal centro Pokemon");
		System.out.println("3. Stampa la lista di tutti i Pokemon");
		System.out.println("************************************************************************");

	}
}
