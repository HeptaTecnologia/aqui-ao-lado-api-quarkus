package br.com.aquiaolado.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.util.List;

@Entity(name = "UF")
public class Estado extends PanacheEntityBase {

    private static final Logger LOG = Logger.getLogger(Estado.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name = "DS_UF")
    public String sigla;

    @Column(name = "DS_NOME")
    public String nome;

    public Estado() {
    }

    public Estado(String sigla, String nome) {
        this.sigla = sigla;
        this.nome = nome;
    }

    public static List<Estado> listarTodos() {
        LOG.info("db - listar todas as Ufs");
        return listAll(Sort.by("nome"));
    }
    
	public static List<Estado> listarTodosComAnuncio() {
        LOG.info("db - listar todas as Ufs com anuncios");
        StringBuilder sql = new StringBuilder();
        sql.append("FROM UF e");
        sql.append(" WHERE 1=1");
        sql.append(" AND e.id in (SELECT DISTINCT a.uf.id FROM ANUNCIO a)");
        sql.append(" ORDER BY e.sigla ASC ");

        return find(sql.toString()).list();
	}
}
