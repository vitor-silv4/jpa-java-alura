package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDao {
	
	private EntityManager em;
	
	public ProdutoDao(EntityManager em) {
		super();
		this.em = em;
	}

	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}
	
	public Produto buscarPorId(Long id) {
		return em.find(Produto.class, id);
	}
	
	public List<Produto> buscarTodos(){
		String jpql = "SELECT p FROM Produto p";
		return em.createQuery(jpql, Produto.class).getResultList();
	}
	
	public List<Produto> buscarPorNome(String nome){
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome"; //ou  ?1
		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", nome)							  //ai colocaria 1
				.getResultList();
	}
	
	public List<Produto> buscarPorNomeDaCategoria(String nome){
		String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome"; //ou  ?1
		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", nome)							  //ai colocaria 1
				.getResultList();
	}
	
	public BigDecimal buscarDoProdutoComNome(String nome){
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome"; //ou  ?1
		return em.createQuery(jpql, BigDecimal.class)
				.setParameter("nome", nome)							  //ai colocaria 1
				.getSingleResult();
	}
	
}
