package br.com.aquiaolado.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(name = "BAIRRO")
public class Bairro extends PanacheEntityBase {

    private static final Logger LOG = Logger.getLogger(Bairro.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "FK_CIDADE", referencedColumnName = "id" ,foreignKey = @ForeignKey(name = "FK_CONSTRAINT_BAIRRO_CIDADE"))
    @Schema(hidden = true)
    public Cidade cidade;

    @Column(name = "DS_NOME")
    public String nome;

    public Bairro() {
    }

    public Bairro(Cidade c, String nome) {
        this.cidade = c;
        this.nome = nome;
    }
    
    public Bairro(Long id, String nome) {
        this.id = id;
        this.nome = nome;
        this.cidade = null;
    }


	public static List<Bairro> listarBairrosPorCidade(long cidadeId) {
        LOG.info("Listar Bairros por cidade: "+cidadeId);
		Map<String, Object> params = new HashMap<>(); 
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new br.com.aquiaolado.entity.Bairro(b.id, b.nome) FROM BAIRRO b");
        sql.append(" WHERE 1=1");
        sql.append(" AND b.cidade.id = :cidade");
        sql.append(" ORDER BY b.nome ASC ");
        params.put("cidade", cidadeId);

        return find(sql.toString(), params).list();
	}
}
