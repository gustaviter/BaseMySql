package br.com.puc.basemysql.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import br.com.puc.basemysql.util.HibernateUtil;

@Entity
@Table
public class TipoOperacao implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name = "IDTIPOOPERACAO", unique = true, nullable = false)
    private Long id;
     
    private String descricao;
     
    public TipoOperacao() {
        super();
    }

	public TipoOperacao(String descricao) {
		super();
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public static TipoOperacao getTipoOperacaoById(Long idTipoOperacaouser) {
	    Session session = null;
	    TipoOperacao tipoOperacao = null;
	    try {
	        session = HibernateUtil.getSessionFactory().openSession();
	        tipoOperacao = (TipoOperacao)session.load(TipoOperacao.class, idTipoOperacaouser);
	        Hibernate.initialize(tipoOperacao);
	    } catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
	    }
	    return tipoOperacao;
	}
}
