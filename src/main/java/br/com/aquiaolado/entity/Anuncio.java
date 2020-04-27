package br.com.aquiaolado.entity;

import br.com.aquiaolado.util.LongLocalDateTimeDeserializer;
import br.com.aquiaolado.util.LongLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "ANUNCIO")
public class Anuncio extends PanacheEntityBase {

    private static final Logger LOG = Logger.getLogger(Anuncio.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name = "DS_TITULO")
    public String titulo;

    @Column(name = "DS_DESCRICAO")
    public String descricao;

    @Column(name = "FL_IMAGEM_CAPA")
    @Lob
    public byte[] imagemCapa;

    @Column(name = "DS_EMAIL_ANUNCIANTE")
    public String anunciante;

    @JsonDeserialize(using = LongLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LongLocalDateTimeSerializer.class)
    @Column(name = "DT_CRIACAO_ANUNCIO")
    public LocalDateTime dataAnuncio;

    @Column(name = "DS_ENDERECO")
    public String localAnuncio;

    @Column(name = "DS_CONTATO")
    public  String contato;
    
    @Column(name = "DS_WHATSAPP")
    public  String whatsapp;
    
    @Column(name = "DS_INSTAGRAM")
    public  String instagram;
    
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.ALL})
    @JoinColumn(name = "FK_UF", referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_CONSTRAINT_ANUNCIO_UF"))
    public Estado uf;

    @ManyToOne
    @JoinColumn(name = "FK_CIDADE", referencedColumnName="id" ,foreignKey = @ForeignKey(name = "FK_CONSTRAINT_ANUNCIO_CIDADE"))
    public Cidade cidade;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.ALL})
    @JoinTable(name = "ANUNCIO_BAIRRO",
            joinColumns = {@JoinColumn(name = "FK_ANUNCIO", referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_CONSTRAINT_ANUNCIO_BAIRRO")) }
            ,inverseJoinColumns = @JoinColumn(name = "FK_BAIRRO", foreignKey = @ForeignKey(name = "FK_CONSTRAINT_BAIRRO_ANUNCIO"))
    )
    public Set<Bairro> locaisEntrega = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.ALL})
    @JoinTable(name = "ANUNCIO_CATEGORIA",
            joinColumns = {@JoinColumn(name = "FK_ANUNCIO", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_CONSTRAINT_ANUNCIO_CATEGORIA")) }
            ,inverseJoinColumns = @JoinColumn(name = "FK_CATEGORIA", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_CONSTRAINT_CATEGORIA_ANUNCIO"))
    )
    public Set<Categoria> categorias = new HashSet<>();

    public Anuncio() {
    }


    public Anuncio(String titulo, String descricao, byte[] imagemCapa, String anunciante,
                   String localAnuncio, String contato, String whatsapp, String instagram) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagemCapa = imagemCapa;
        this.anunciante = anunciante;
        this.dataAnuncio = LocalDateTime.now();
        this.localAnuncio = localAnuncio;
        this.contato = contato;
        this.whatsapp = whatsapp;
        this.instagram = instagram;
    }

    //TODO devolver DTO
    public static List<Anuncio> listarTodos() {
        return listAll();
    }

    public static Anuncio detalhesAnuncio(long id) {
        return findById(id);
    }

    public static void salvarAnunciante(String anunciante, Long id){
        Anuncio.update("update from ANUNCIO set DS_EMAIL_ANUNCIANTE = ?1 where id = ?2",anunciante,id);
    }

    public static void salvarAnuncio(Anuncio anuncio) {
        anuncio.persist();
    }
    //TODO devolver DTO
    public static List<Anuncio> listarTodosPorFiltro(String valor, Long cidade, Long categoria, List<Long> bairros, Integer pageIndex, Integer pageSize) {

    	Map<String, Object> params = new HashMap<>(); 
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT a FROM ANUNCIO a ");
        sql.append(" JOIN a.locaisEntrega le ");
        sql.append(" JOIN a.categorias cat ");
        sql.append(" WHERE 1=1");
        
        if(valor != null && !valor.isEmpty()) {
        	sql.append(" AND (a.titulo like :valor");
        	sql.append(" OR a.descricao like :valor)");
        	params.put("valor", "%" + valor + "%");
        }
        
        sql.append(" AND le.id IN (:bairros)");
        params.put("bairros", bairros);
        
        sql.append(" AND a.cidade.id = :cidade");
        params.put("cidade", cidade);

        if(categoria != null) {
            sql.append(" AND cat.id = :categoria");
            params.put("categoria", categoria);
        }
       
        LOG.info(sql.toString());
        
    return find(sql.toString(), params).page(pageIndex, pageSize).list();

    	
    }

    public static List<Anuncio> listarTodosPorFiltro(String valor, Long cidade, Long categoria, Integer pageIndex, Integer pageSize) {

    	Map<String, Object> params = new HashMap<>(); 
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT a FROM ANUNCIO a");
        sql.append(" JOIN a.categorias cat ");
        sql.append(" WHERE 1=1");

        if(valor != null && !valor.isEmpty()) {
        	sql.append(" AND (a.titulo like :valor");
        	sql.append(" OR a.descricao like :valor)");
        	params.put("valor", "%" + valor + "%");
        }

        sql.append(" AND a.cidade.id = :cidade");
        params.put("cidade", cidade);

        if(categoria != null) {
            sql.append(" AND cat.id = :categoria");
            params.put("categoria", categoria);
        }

    return find(sql.toString(), params).page(pageIndex, pageSize).list();
    }


    public static void atualizarImagem(Long anuncioId, byte[] imagem) {
        Anuncio.update("update ANUNCIO set FL_IMAGEM_CAPA = ?1 where id = ?2",imagem,anuncioId);
    }
}
