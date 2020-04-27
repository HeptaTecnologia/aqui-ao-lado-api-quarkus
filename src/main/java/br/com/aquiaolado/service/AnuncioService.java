package br.com.aquiaolado.service;

import br.com.aquiaolado.constantes.Mensagens;
import br.com.aquiaolado.dto.AnuncioDTO;
import br.com.aquiaolado.dto.ImagemDTO;
import br.com.aquiaolado.entity.*;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

public class AnuncioService {

    private AnuncioService() {
    }

    private static final Logger LOG = Logger.getLogger(AnuncioService.class);

    public static String salvarAnuncio(AnuncioDTO dto, InputStream imagemCapa) throws IOException {
        LOG.info("service salvar anuncio");

        try {
            dto.imagemCapa = imagemCapa.readAllBytes();

            Anuncio novoAnuncio = new Anuncio(dto.titulo, dto.descricao, dto.imagemCapa,
                    dto.anunciante, dto.localAnuncio, dto.contato, dto.whatsapp, dto.instagram);

            novoAnuncio.uf = Estado.findById(dto.uf.id);
            novoAnuncio.cidade = Cidade.findById(dto.cidade.id);

            HashSet<Bairro> locais = new HashSet<>();
            dto.locaisEntrega.forEach(local -> {
                locais.add(Bairro.findById(local.id));
            });
            novoAnuncio.locaisEntrega = locais;

            HashSet<Categoria> categorias = new HashSet<>();
            dto.categorias.forEach(categoria -> {
                categorias.add(Categoria.findById(categoria.id));
            });
            novoAnuncio.categorias = categorias;

            Anuncio.salvarAnuncio(novoAnuncio);
            return String.valueOf(novoAnuncio.id);
        }catch (IOException e){
            LOG.warn(e.getMessage());
            throw e; //new exception file
        } catch (Exception e) {
            LOG.warn(e.getMessage());
            throw e; 
        }
    }

    public static String salvarAnunciante(String anunciante, Long id){
        LOG.info("service salvar anunciante");
        try {
            Anuncio.salvarAnunciante(anunciante, id);
        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return Mensagens.ERRO_SALVAR_ANUNCIANTE;
        }
        return Mensagens.SUCESSO_SALVAR_ANUNCIANTE;
    }

    public static Anuncio detalhesAnuncio(long id) {
        LOG.info("detalhes anuncio id:"+id);
        Anuncio anuncio = null;
        try{
             anuncio = Anuncio.detalhesAnuncio(id);
        }catch (Exception e){
            LOG.warn(e.getMessage());
            throw e;
        }
        return anuncio;
    }

    public static String atualizarImagem(ImagemDTO form) {
        LOG.info("service salvar imagem");
        try {
            byte[] imagem = form.imagem.readAllBytes();
            Long anuncioId = Long.parseLong(form.anuncioId);
            Anuncio.atualizarImagem(anuncioId, imagem);
        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return Mensagens.ERRO_SALVAR_IMAGEM;
        }
        return Mensagens.SUCESSO_SALVAR_IMAGEM;
    }
}
