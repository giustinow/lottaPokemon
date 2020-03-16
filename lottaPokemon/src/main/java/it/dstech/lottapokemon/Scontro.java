package it.dstech.lottapokemon;

public class Scontro {
private int idPartita ;
private String idUtente;
private String pokemonCreatore;
private String pokemonSfidante;

public Scontro(int idPartita, String idUtente, String pokemonCreatore, String pokemonSfidante) {
	super();
	this.idPartita = idPartita;
	this.idUtente = idUtente;
	this.pokemonCreatore = pokemonCreatore;
	this.pokemonSfidante = pokemonSfidante;
}

public int getIdPartita() {
	return idPartita;
}
public void setIdPartita(int idPartita) {
	this.idPartita = idPartita;
}
public String getIdUtente() {
	return idUtente;
}
public void setIdUtente(String idUtente) {
	this.idUtente = idUtente;
}
public String getPokemonCreatore() {
	return pokemonCreatore;
}
public void setPokemonCreatore(String pokemonCreatore) {
	this.pokemonCreatore = pokemonCreatore;
}
public String getPokemonSfidante() {
	return pokemonSfidante;
}
public void setPokemonSfidante(String pokemonSfidante) {
	this.pokemonSfidante = pokemonSfidante;
}

}
