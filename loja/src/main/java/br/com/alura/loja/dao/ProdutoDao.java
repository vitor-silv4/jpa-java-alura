package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

	public List<Produto> buscarTodos() {
		String jpql = "SELECT p FROM Produto p";
		return em.createQuery(jpql, Produto.class).getResultList();
	}

	public List<Produto> buscarPorNome(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome"; // ou ?1
		return em.createQuery(jpql, Produto.class).setParameter("nome", nome) // ai colocaria 1
				.getResultList();
	}

	public List<Produto> buscarPorNomeDaCategoria(String nome) {
		return em.createNamedQuery("Produto.produtosPorCategoria", Produto.class).setParameter("nome", nome) // ai
																												// colocaria
																												// 1
				.getResultList();
	}

	public BigDecimal buscarDoProdutoComNome(String nome) {
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome"; // ou ?1
		return em.createQuery(jpql, BigDecimal.class).setParameter("nome", nome) // ai colocaria 1
				.getSingleResult();
	}

	public List<Produto> bucarPorParametros(String nome, BigDecimal preco, LocalDate dataDeCadastro){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);
		
		Predicate filtros = builder.and();
		if(nome != null && !nome.trim().isEmpty()) {
			filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
		}
		if(preco != null) {
			filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
		}
		if(dataDeCadastro != null) {
			filtros = builder.and(filtros, builder.equal(from.get("dataDeCadastro"), dataDeCadastro));
		}
		query.where(filtros);

		return em.createQuery(query).getResultList();
	}

}
