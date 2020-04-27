package br.com.aquiaolado.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.util.List;

@Entity(name = "CATEGORIA")
public class Categoria extends PanacheEntityBase {

    private static final Logger LOG = Logger.getLogger(Categoria.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name = "DS_NOME")
    public String nome;

    @Column(name = "DS_COR")
    public String cor;

    @Column(name = "FL_IMAGEM_CATEGORIA")
    @Lob
    public String imagemCategoria;

    public static List<Categoria> listarTodas() {
        LOG.info("Listar todas categorias");
        return listAll();
    }
}
