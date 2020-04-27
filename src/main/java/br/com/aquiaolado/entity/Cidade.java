package br.com.aquiaolado.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(name = "CIDADE")
public class Cidade extends PanacheEntityBase {

    private static final Logger LOG = Logger.getLogger(Cidade.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_UF", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_CONSTRAINT_CIDADE_UF"))
    @Schema(hidden = true)
    public Estado uf;

    @Column(name = "DS_NOME")
    public String nome;

    public Cidade() {
    }

    public Cidade(Estado uf, String nome) {
        this.uf = uf;
        this.nome = nome;
    }

    //TODO devolver os dtos, arrumar a query n+1
    public static List<Cidade> listarCidadePorUf(long ufId) {
        LOG.info("db - listar cidades por uf id:"+ufId);
        PanacheEntityBase uf = Estado.findById(ufId);
        return list("uf", Sort.by("nome"), uf);
    }

	public static List<Cidade> listarCidadeComAnuncioPorUf(long ufId) {
        LOG.info("db - listar todas as Ufs com anuncios");
        Map<String, Object> params = new HashMap<>(); 
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM CIDADE c");
        sql.append(" WHERE 1=1");
        sql.append(" AND c.id in (SELECT DISTINCT a.cidade.id FROM ANUNCIO a WHERE a.uf.id = :uf)");
        sql.append(" ORDER BY c.nome ASC ");
        params.put("uf", ufId);

        return find(sql.toString(), params).list();
	}

}
