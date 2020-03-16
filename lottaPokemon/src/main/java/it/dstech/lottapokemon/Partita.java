package it.dstech.lottapokemon;

public class Partita {
	private int idPartita;
	private String idCreatore;
	private String idSfidante;
	private int pokemonCreatore1;
	private int pokemonCreatore2;
	private int pokemonCreatore3;
	private int pokemonSfidante1;
	private int pokemonSfidante2;
	private int pokemonSfidante3;

	public Partita(int idPartita, String idCreatore, String idSfidante, int pokemonCreatore1, int pokemonCreatore4,
			int pokemonCreatore3, int pokemonSfidante1, int pokemonSfidante2, int pokemonSfidante3) {
		super();
		this.idPartita = idPartita;
		this.idCreatore = idCreatore;
		this.idSfidante = idSfidante;
		this.pokemonCreatore1 = pokemonCreatore1;
		this.pokemonCreatore2 = pokemonCreatore4;
		this.pokemonCreatore3 = pokemonCreatore3;
		this.pokemonSfidante1 = pokemonSfidante1;
		this.pokemonSfidante2 = pokemonSfidante2;
		this.pokemonSfidante3 = pokemonSfidante3;
	}

	public int getIdPartita() {
		return idPartita;
	}

	public void setIdPartita(int idPartita) {
		this.idPartita = idPartita;
	}

	public String getIdCreatore() {
		return idCreatore;
	}

	public void setIdCreatore(String idCreatore) {
		this.idCreatore = idCreatore;
	}

	public String getIdSfidante() {
		return idSfidante;
	}

	public void setIdSfidante(String idSfidante) {
		this.idSfidante = idSfidante;
	}

	public int getPokemonCreatore1() {
		return pokemonCreatore1;
	}

	public void setPokemonCreatore1(int pokemonCreatore1) {
		this.pokemonCreatore1 = pokemonCreatore1;
	}

	public int getPokemonCreatore2() {
		return pokemonCreatore2;
	}

	public void setPokemonCreatore2(int pokemonCreatore4) {
		this.pokemonCreatore2 = pokemonCreatore4;
	}

	public int getPokemonCreatore3() {
		return pokemonCreatore3;
	}

	public void setPokemonCreatore3(int pokemonCreatore3) {
		this.pokemonCreatore3 = pokemonCreatore3;
	}

	public int getPokemonSfidante1() {
		return pokemonSfidante1;
	}

	public void setPokemonSfidante1(int pokemonSfidante1) {
		this.pokemonSfidante1 = pokemonSfidante1;
	}

	public int getPokemonSfidante2() {
		return pokemonSfidante2;
	}

	public void setPokemonSfidante2(int pokemonSfidante2) {
		this.pokemonSfidante2 = pokemonSfidante2;
	}

	public int getPokemonSfidante3() {
		return pokemonSfidante3;
	}

	public void setPokemonSfidante3(int pokemonSfidante3) {
		this.pokemonSfidante3 = pokemonSfidante3;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCreatore == null) ? 0 : idCreatore.hashCode());
		result = prime * result + idPartita;
		result = prime * result + ((idSfidante == null) ? 0 : idSfidante.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partita other = (Partita) obj;
		if (idCreatore == null) {
			if (other.idCreatore != null)
				return false;
		} else if (!idCreatore.equals(other.idCreatore))
			return false;
		if (idPartita != other.idPartita)
			return false;
		if (idSfidante == null) {
			if (other.idSfidante != null)
				return false;
		} else if (!idSfidante.equals(other.idSfidante))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Partita [idPartita=" + idPartita + ", idCreatore=" + idCreatore + ", idSfidante=" + idSfidante
				+ ", pokemonCreatore1=" + pokemonCreatore1 + ", pokemonCreatore4=" + pokemonCreatore2
				+ ", pokemonCreatore3=" + pokemonCreatore3 + ", pokemonSfidante1=" + pokemonSfidante1
				+ ", pokemonSfidante2=" + pokemonSfidante2 + ", pokemonSfidante3=" + pokemonSfidante3 + "]";
	}

}
