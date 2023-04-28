package br.com.alura.loja.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categoria {

	@EmbeddedId
	private CategoriaId id;
	
	public Categoria() {
	}

	public Categoria(String nome, String tipo) {
		this.id = new CategoriaId(nome, tipo);
	}

	public String getNome() {
		return this.id.getNome();
	}

}
