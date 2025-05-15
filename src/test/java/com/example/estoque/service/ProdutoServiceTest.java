package com.example.estoque.service;

import com.example.estoque.domain.Produto;
import com.example.estoque.entity.ProdutoEntity;
import com.example.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

class ProdutoServiceTest {

    ProdutoService service;
    ProdutoRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ProdutoRepository.class);
        service = new ProdutoService(repository);
    }

    @Test
    void deveCadastrarProdutoSeNaoExistirNoEstoque() {
        //Cenário
        Produto produto = umProduto();

        Mockito.when(repository.findByNome(produto.getNome())).thenReturn(null);

        //Execução
        service.cadastrarProduto(produto);

        //Validação
        Mockito.verify(repository, Mockito.times(1)).findByNome(produto.getNome());
        ArgumentCaptor<ProdutoEntity> captor = ArgumentCaptor.forClass(ProdutoEntity.class);
        Mockito.verify(repository, Mockito.times(1)).save(captor.capture());

        ProdutoEntity novoProdutoSalvo = captor.getValue();
        Assertions.assertEquals(produto.getNome(), novoProdutoSalvo.getNome());
        Assertions.assertEquals(produto.getQtd(), novoProdutoSalvo.getQtd());
    }

    @Test
    void deveAtualizarQuantidadeSeProdutoExistirNoEstoque() {
        //Cenário
        Produto produto = umProduto();

        ProdutoEntity produtoSalvo = new ProdutoEntity();
        produtoSalvo.setNome(produto.getNome());
        produtoSalvo.setId(1L);
        produtoSalvo.setQtd(10);

        Mockito.when(repository.findByNome(produto.getNome())).thenReturn(produtoSalvo);
        //Execução
        service.cadastrarProduto(produto);

        //Validação
        Mockito.verify(repository, Mockito.times(1)).findByNome(produto.getNome());
        Mockito.verify(repository, Mockito.times(1)).save(produtoSalvo);

        Assertions.assertEquals(3, produtoSalvo.getQtd());
    }

    @Test
    void deveRetornarListaComTodosOsProdutosEmEstoque(){

        //Cenário
        ProdutoEntity produto1 = umProdutoEntity("Livro", 4);
        ProdutoEntity produto2 = umProdutoEntity("Camiseta", 7);
        ProdutoEntity produto3 = umProdutoEntity("Ferramenta", 8);
        List<ProdutoEntity> produtos = Arrays.asList(produto1, produto2, produto3);

        Mockito.when(repository.findAll()).thenReturn(produtos);

        //Execução
        List<Produto> produtosRetornados = service.encontrarTodos();

        //Validação]
        Mockito.verify(repository, Mockito.times(1)).findAll();
        Assertions.assertEquals(3, produtosRetornados.size());
        Assertions.assertEquals("Livro", produtosRetornados.get(0).getNome());
        Assertions.assertEquals(8, produtosRetornados.get(2).getQtd());
    }

    private Produto umProduto(){
        Produto produto = new Produto();
        produto.setNome("produtoTeste");
        produto.setQtd(3);
        return produto;
    }

    private ProdutoEntity umProdutoEntity(String nome, int qtd) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome(nome);
        produtoEntity.setQtd(qtd);
        return produtoEntity;
    }
}
