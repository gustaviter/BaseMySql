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
public class Produto implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name = "IDPRODUTO", unique = true, nullable = false)
    private Long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="IDCATEGORIA", nullable=true)
	private Categoria categoria;
	
    private String nome;
    
    public Produto() {
        super();
    }

	public Produto(Categoria categoria, String nome) {
		super();
		this.categoria = categoria;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
