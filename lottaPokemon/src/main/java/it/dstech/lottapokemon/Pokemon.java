package it.dstech.lottapokemon;

public class Pokemon {

	private String nome;
	private int hp;
	private int attacco;
	private int difesa;
	private int resistenza;
	private String evoluzione;
	private String proprietario;
	private String tipo;

	public Pokemon(String nome, int hp, int attacco, int difesa, int resistenza, String evoluzione, String proprietario,
			String tipo) {
		super();
		this.nome = nome;
		this.hp = hp;
		this.attacco = attacco;
		this.difesa = difesa;
		this.resistenza = resistenza;
		this.evoluzione = evoluzione;
		this.proprietario = proprietario;
		this.tipo = tipo;
	}

	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAttacco() {
		return attacco;
	}

	public void setAttacco(int attacco) {
		this.attacco = attacco;
	}

	public int getDifesa() {
		return difesa;
	}

	public void setDifesa(int difesa) {
		this.difesa = difesa;
	}

	public int getResistenza() {
		return resistenza;
	}

	public void setResistenza(int resistenza) {
		this.resistenza = resistenza;
	}

	public String getEvoluzione() {
		return evoluzione;
	}

	public void setEvoluzione(String evoluzione) {
		this.evoluzione = evoluzione;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((proprietario == null) ? 0 : proprietario.hashCode());
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
		Pokemon other = (Pokemon) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (proprietario == null) {
			if (other.proprietario != null)
				return false;
		} else if (!proprietario.equals(other.proprietario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pokemon [nome=" + nome + ", hp=" + hp + ", attacco=" + attacco + ", difesa=" + difesa + ", resistenza="
				+ resistenza + ", evoluzione=" + evoluzione + ", proprietario=" + proprietario + ", tipo=" + tipo + "]";
	}



}
