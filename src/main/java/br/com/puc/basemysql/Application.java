package br.com.puc.basemysql;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.puc.basemysql.domain.Categoria;
import br.com.puc.basemysql.domain.Operacao;
import br.com.puc.basemysql.domain.Pessoa;
import br.com.puc.basemysql.domain.Produto;
import br.com.puc.basemysql.domain.TipoOperacao;
import br.com.puc.basemysql.util.HibernateUtil;

public class Application {
	
	private Set<Pessoa> pessoas = new HashSet<Pessoa>();
	private Set<Produto> produtos = new HashSet<Produto>();
	private int qtdUF = 0;
	private int qtdVendas = 0;
	private int qtdCompas = 4;
	
	public static void main(String[] args) {
		long antes = System.currentTimeMillis();
		new Application().processarInformacoes();
		System.out.printf("O programa executou em %d milissegundos.%n", System.currentTimeMillis() - antes);
	}
	
	private void _carregaInformacoesIniciais() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		/** Categorias ---------------------------------------------------- */
		Categoria catInformatica = new Categoria();
		catInformatica.setDescricao("Informatica");
		session.save(catInformatica);

		Categoria catCelulares = new Categoria();
		catCelulares.setDescricao("Celulares");
		session.save(catCelulares);

		Categoria catJogos = new Categoria();
		catJogos.setDescricao("Jogos");
		session.save(catJogos);

		Categoria catLivros = new Categoria();
		catLivros.setDescricao("Livros");
		session.save(catLivros);

		/** Produtos ---------------------------------------------------- */
		Produto produto;
		for (int indiceProduto = 0; indiceProduto < 5000; indiceProduto++) {
			produto = new Produto();
			produto.setCategoria(catInformatica);
			produto.setNome("Mouse Modelo: " + (indiceProduto + 1));
			session.save(produto);
			produtos.add(produto);

			produto = new Produto();
			produto.setCategoria(catCelulares);
			produto.setNome("Aparelho Celular Modelo: " + (indiceProduto + 1));
			session.save(produto);
			produtos.add(produto);

			produto = new Produto();
			produto.setCategoria(catJogos);
			produto.setNome("Jogo de Aventura Versao: " + (indiceProduto + 1));
			session.save(produto);
			produtos.add(produto);

			produto = new Produto();
			produto.setCategoria(catLivros);
			produto.setNome("Livro de Informatica Edicao: "
					+ (indiceProduto + 1));
			session.save(produto);
			produtos.add(produto);
		}

		Random random = new Random(System.currentTimeMillis());

		/**
		 * Pessoas (Compradores e Vendedores)
		 * -------------------------------------------
		 */
		Pessoa pessoa;
		for (int indicePessoa = 0; indicePessoa < 1500; indicePessoa++) {

			String[] nomes = new String[] { "Sergio", "Felipe", "Vanessa",
					"Rudson", "Erika", "Everton", "Carina", "Carlos", "Rafael",
					"Alberto", "Alessandra", "Patracia", "Monica", "Fernanda",
					"Marcia" };

			String[] sobreNomes = new String[] { "Silva", "Barbosa", "Santos",
					"Dutra", "Oliveira", "Pinheiro", "Soares", "Carvalho",
					"Andrade", "Dias" };

			String nomePessoa = nomes[random.nextInt(nomes.length)] + " "
					+ sobreNomes[random.nextInt(sobreNomes.length)];
			String emailPessoa = nomePessoa.toLowerCase().replace(" ", ".")
					+ "@pucminas.com.br";
			String UF = getUf();

			pessoa = new Pessoa();
			pessoa.setNome(nomePessoa);
			pessoa.setEmail(emailPessoa);
			pessoa.setUF(UF);
			session.save(pessoa);
			pessoas.add(pessoa);
		}
		session.getTransaction().commit();
		session.close();
	}
	
	private void _carregaInformacoes(int qtdOperacoes) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		TipoOperacao compra = TipoOperacao.getTipoOperacaoById(1L);
		TipoOperacao venda = TipoOperacao.getTipoOperacaoById(2L);
		
		/** Operacoes de compra e venda */
		for (long indiceOperacao = 0; indiceOperacao < qtdOperacoes; indiceOperacao++) {

			/** Operacao de Compra */
			Pessoa comprador = selecionaPessoaAleatorio(pessoas);
			
				int qtdeCompras = getQtdCompras();
				for (int indiceCompra = 0; indiceCompra < qtdeCompras; indiceCompra++) {
					Operacao operacao = new Operacao("Operacao de compra (" + (indiceCompra + 1) + ")", selecionaProdutoAleatorio(produtos), comprador, compra);
					session.save(operacao);
				}

			/** Operacao de Venda */
			Pessoa vendedor = selecionaPessoaAleatorio(pessoas);

				int qtdeVendas = getQtdVendas();
				for (int indiceVenda = 0; indiceVenda < qtdeVendas; indiceVenda++) {
					Operacao operacao = new Operacao("Operacao de venda (" + indiceVenda + ")", selecionaProdutoAleatorio(produtos), vendedor, venda);
					session.save(operacao);
				}
		}
		session.getTransaction().commit();
		session.close();
	}
	

	/**
	 * Seleciona produto aleatoriamente
	 * 
	 * @param produtos
	 * @return
	 */
	private Produto selecionaProdutoAleatorio(Set<Produto> produtos) {
		Random random = new Random(System.currentTimeMillis());
		return (Produto) produtos.toArray()[random.nextInt(produtos.size())];
	}

	/**
	 * Seleciona pessoa aleatoriamente
	 * 
	 * @param pessoas
	 * @return
	 */
	private Pessoa selecionaPessoaAleatorio(Set<Pessoa> pessoas) {
		Random random = new Random(System.currentTimeMillis());
		return (Pessoa) pessoas.toArray()[random.nextInt(pessoas.size())];
	}
	
	private void processarInformacoes() {
		for(int i = 1; i<= 5; i++){
			System.out.printf(">>>>>>>>>>INICIO EXECUCAO %d° TESTE<<<<<<<<<<%n", i);
			_processarInformacoes();
			HibernateUtil.shutdown();
			limparInformacoes();
			System.out.printf(">>>>>>>>>>FIM EXECUCAO %d° TESTE<<<<<<<<<<%n", i);
			HibernateUtil.shutdown();
		}
	}
	
	private void _processarInformacoes() {
		int qtdOperacoes = 2500;
		int qtdAuxConsulta = 0;
		_carregaInformacoesIniciais();
		long antes = System.currentTimeMillis();
		while(qtdOperacoes <= 40000){
			_carregaInformacoes(qtdOperacoes);
			System.out.printf("Tempo inserçao %d operacoes: %d milissegundos.%n", qtdOperacoes, System.currentTimeMillis() - antes);
			antes = System.currentTimeMillis();
			HibernateUtil.shutdown();
			consultarInformacoes();
			System.out.printf("Tempo consulta %d operacoes: %d milissegundos.%n", qtdOperacoes+qtdAuxConsulta, System.currentTimeMillis() - antes);
			antes = System.currentTimeMillis();
			qtdAuxConsulta += qtdOperacoes;
			qtdOperacoes += qtdOperacoes;
		}
	}
	
	private String getUf() {
		String[] UFs = new String[] { "SP", "RJ", "PR", "MG" };
		String uf = UFs[qtdUF++];
		if(qtdUF == UFs.length){
			qtdUF = 0;
		}
		return uf;
	}
	
	private int getQtdVendas() {
		int qtdVendas = this.qtdVendas++;
		if(this.qtdVendas > 4){
			this.qtdVendas = 0;
		}
		return qtdVendas;
	}
	
	private int getQtdCompras() {
		int qtdCompas = this.qtdCompas--;
		if(this.qtdCompas < 0){
			this.qtdCompas = 4;
		}
		return qtdCompas;
	}
	
	public void consultarInformacoes() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query q = session.createSQLQuery("SELECT PE.nome, PE.email, PE.UF, CA.descricao, Count(*) "
				+ "FROM Pessoa AS PE INNER JOIN Operacao AS OP ON PE.idPessoa = OP.idPessoa "
				+ "INNER JOIN TipoOperacao AS CO ON OP.idTipoOperacao = CO.idTipoOperacao "
				+ "INNER JOIN Produto AS PR ON OP.idProduto = PR.idProduto "
				+ "INNER JOIN Categoria AS CA ON PR.idCategoria = CA.idCategoria "
				+ "WHERE CO.idTipoOperacao = 1 AND CA.descricao = 'Livros' AND PE.UF = 'MG' "
				+ "AND EXISTS ( SELECT 0 FROM Pessoa AS PE_V INNER JOIN Operacao AS OP_V ON PE_V.idPessoa = OP_V.idPessoa "
				+ "INNER JOIN TipoOperacao AS CO_V ON OP_V.idTipoOperacao = CO_V.idTipoOperacao "
				+ "WHERE CO_V.idTipoOperacao = 2 AND PE_V.idPessoa = PE.idPessoa) GROUP BY PE.nome, PE.email, PE.UF, CA.descricao;");
        List resultList = q.list();
        
		session.getTransaction().commit();
		session.close();
	}
	
	/*public void consultarInformacoes() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query q = session.createSQLQuery("SELECT Count(*) "
				+ "FROM Pessoa AS PE "
				+ "INNER JOIN Operacao AS OP ON PE.idPessoa = OP.idPessoa "
				+ "INNER JOIN TipoOperacao AS CO ON OP.idTipoOperacao = CO.idTipoOperacao "
				+ "INNER JOIN Produto AS PR ON OP.idProduto = PR.idProduto "
				+ "INNER JOIN Categoria AS CA ON PR.idCategoria = CA.idCategoria "
				+ "WHERE CO.idTipoOperacao = 1 AND CA.descricao = 'Celulares' AND PE.nome like 'Alessandra Soares';");
        List resultList = q.list();
        
		session.getTransaction().commit();
		session.close();
	}
	
	public void consultarInformacoes() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query q = session.createSQLQuery("SELECT PE.nome, PE.email, PE.UF, CA.descricao, Count(*) "
				+ "FROM Pessoa AS PE INNER JOIN Operacao AS OP ON PE.idPessoa = OP.idPessoa "
				+ "INNER JOIN TipoOperacao AS CO ON OP.idTipoOperacao = CO.idTipoOperacao "
				+ "INNER JOIN Produto AS PR ON OP.idProduto = PR.idProduto "
				+ "INNER JOIN Categoria AS CA ON PR.idCategoria = CA.idCategoria "
				+ "WHERE CO.idTipoOperacao = 2 AND CA.descricao = 'Celulares' AND PE.UF = 'RJ"
				+ " AND EXISTS ( 	SELECT 0 FROM Pessoa AS PE_V INNER JOIN Operacao AS OP_V ON PE_V.idPessoa = OP_V.idPessoa "
				+ "INNER JOIN TipoOperacao AS CO_V ON OP_V.idTipoOperacao = CO_V.idTipoOperacao "
				+ "INNER JOIN Produto AS PR_V ON OP_V.idProduto = PR_V.idProduto "
				+ "INNER JOIN Categoria AS CA_V ON PR_V.idCategoria = CA_V.idCategoria "
				+ "WHERE CO_V.idTipoOperacao = 2 AND CA.descricao = 'Jogos' AND PE_V.idPessoa = PE.idPessoa) "
				+ "GROUP BY PE.nome, PE.email, PE.UF, CA.descricao;");
        List resultList = q.list();
        
		session.getTransaction().commit();
		session.close();
	}*/
	
	private void limparInformacoes() {
		limparBase();
		pessoas = new HashSet<Pessoa>();
		produtos = new HashSet<Produto>();
	}
	
	private void limparBase() {
		long antes = System.currentTimeMillis();
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		session.beginTransaction();

		session.createSQLQuery("DELETE FROM Operacao").executeUpdate();
		session.createSQLQuery("DELETE FROM Pessoa").executeUpdate();
		session.createSQLQuery("DELETE FROM Produto").executeUpdate();
		session.createSQLQuery("DELETE FROM Categoria").executeUpdate();
        
		session.getTransaction().commit();
		session.close();
		System.out.printf("Tempo remoçao: %d milissegundos.%n", System.currentTimeMillis() - antes);
	}
}
