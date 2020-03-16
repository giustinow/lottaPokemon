package it.dstech.lottapokemon;

public class Pokemon {
	private int id;
	private String nome;
	private int hp;
	private double attacco;
	private double difesa;
	private double resistenza;
	private String evoluzione;
	private String proprietario;
	private String tipo;

	public Pokemon(int id, String nome, int hp, int attacco, double difesa, double resistenza, String evoluzione,
			String proprietario, String tipo) {
		super();
		this.id = id;
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

	public double getAttacco() {
		return attacco;
	}

	public void setAttacco(double attacco) {
		this.attacco = attacco;
	}

	public double getDifesa() {
		return difesa;
	}

	public void setDifesa(double difesa) {
		this.difesa = difesa;
	}

	public double getResistenza() {
		return resistenza;
	}

	public void setResistenza(double resistenza) {
		this.resistenza = resistenza;
	}

	public String getEvoluzione() {
		return evoluzione;
	}

	public void setEvoluzione(String evoluzione) {
		this.evoluzione = evoluzione;
	}
	public double attaccoForte() {
		return this.getAttacco() * 2;
	}
	public double attaccoNormale() {
		return this.getAttacco();
	}
	public double attaccoScarso() {
		return this.attacco/2;
	}
	public int difesaForte() {
		return (int) (((this.attacco * 0.5) - this.difesa) - ((this.resistenza * 0.01)*(this.attacco * 0.5) - this.difesa));
	}
	public int difesaNormale() {
		return (int) ((this.attacco - this.difesa) - ((this.resistenza * 0.01)*(this.attacco) - this.difesa));
	}
	public int difesaScarsa() {
		return (int) (((this.attacco * 2) - this.difesa) - ((this.resistenza * 0.01)*(this.attacco * 2) - this.difesa));
	}
	public boolean vantaggioTipoPokemon(Pokemon pokemon) {
		if(this.getTipo().equals("acqua")){
			if(pokemon.getTipo().equals("terra") || pokemon.getTipo().equals("fuoco")) {
				return true;
			}return false;
		}else if(this.getTipo().equals("fuoco")) {
			if(pokemon.getTipo().equals("terra") || pokemon.getTipo().equals("aria")) {
				return true;
			}return false;
		}else if(this.getTipo().equals("aria")) {
			if(pokemon.getTipo().equals("fuoco") || pokemon.getTipo().equals("acqua")) {
				return true;
			}return false;
		}else {
			if(pokemon.getTipo().equals("aria") || pokemon.getTipo().equals("acqua")) {
				return true;
			}return false;
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pokemon [nome=" + nome + ", hp=" + hp + ", attacco=" + attacco + ", difesa=" + difesa + ", resistenza="
				+ resistenza + ", evoluzione=" + evoluzione + ", proprietario=" + proprietario + ", tipo=" + tipo + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
