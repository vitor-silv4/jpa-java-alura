package br.com.alura.loja.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class PerformanceConsultas {

    public static void main(String[] args) {
        cadastrarProduto();
        EntityManager em = JPAUtil.getEntityManager();
        
        PedidoDao pedidoDao = new PedidoDao(em);
        Pedido pedido = pedidoDao.buscarPedidoComCliente(1l); //buscarPedidoComCliente query criada pois pedido com cliente está lazy
        
        em.close();
//        Para faxer a consulta depois de fechar o em, como temos nos relacionamentos o lazy temos que criar uma consuta específica
        System.out.println(pedido.getCliente().getNome());
    }

    private static void cadastrarProduto() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);

        Categoria notebooks = new Categoria("INFORMÁTICA");
        Produto notebook = new Produto("Mac Book", "Rapido", new BigDecimal("18000"), notebooks);

        Categoria games = new Categoria("VIDEO GAME");
        Produto game = new Produto("Play Station 5", "Performance", new BigDecimal("5000"), games);
        
        Cliente cliente = new Cliente("Arthur da Silva", "123.456.549.10");

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, celular));
        pedido.adicionarItem(new ItemPedido(40, pedido, game));
        
        Pedido pedido2 = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(2, pedido2, notebook));
        

        EntityManager em = JPAUtil.getEntityManager();
        PedidoDao pedidoDao = new PedidoDao(em);
        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        em.getTransaction().begin();

        clienteDao.cadastrar(cliente);
        pedidoDao.cadastrar(pedido);
        pedidoDao.cadastrar(pedido2);

        categoriaDao.cadastrar(celulares);
        produtoDao.cadastrar(celular);

        categoriaDao.cadastrar(notebooks);
        produtoDao.cadastrar(notebook);

        categoriaDao.cadastrar(games);
        produtoDao.cadastrar(game);

        em.getTransaction().commit();
        em.close();
    }

}