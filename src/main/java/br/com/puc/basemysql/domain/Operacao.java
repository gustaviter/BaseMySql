package br.com.puc.basemysql.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Operacao implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name = "IDOPERACAO", unique = true, nullable = false)
    private Long id;
    
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="IDPRODUTO", nullable=true)
    private Produto produto;
    
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="IDPESSOA", nullable=true)
    private Pessoa pessoa;
    
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="IDTIPOOPERACAO", nullable=true)
    private TipoOperacao tipoOperacao;
	
    private String descricao;
     
    public Operacao() {
        super();
    }

	public Operacao(String descricao,Produto produto, Pessoa pessoa, TipoOperacao tipoOperacao) {
		super();
		this.descricao = descricao;
		this.produto = produto;
		this.pessoa = pessoa;
		this.tipoOperacao = tipoOperacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
