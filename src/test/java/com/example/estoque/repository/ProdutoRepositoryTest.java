package com.example.estoque.repository;

import com.example.estoque.entity.ProdutoEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProdutoRepositoryTest {

    @Autowired
    ProdutoRepository repository;

    @Test
    void deveSalvarUmProdutoComSucesso() {
        //Cenário
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome("Mesa");
        produto.setDescricao("Descrição 1");
        produto.setPreco(20.0);
        produto.setQtd(30);

        //Execução
        ProdutoEntity produtoSalvo = repository.save(produto);

        //Validação
        Assertions.assertNotNull(produtoSalvo);
        Assertions.assertNotNull(produtoSalvo.getId());
        Assertions.assertEquals("Mesa", produtoSalvo.getNome());

    }

    @Test
    void deveBuscarProdutoPorNomeComSucesso() {
        //Cenário
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome("Cadeira");
        produto.setDescricao("Descrição 2");
        produto.setPreco(32.6);
        produto.setQtd(50);

        repository.save(produto);

        //Execução
        ProdutoEntity produtoEncontrado = repository.findByNome("Cadeira");

        //Validação
        Assertions.assertNotNull(produtoEncontrado);
        Assertions.assertEquals("Cadeira", produtoEncontrado.getNome());
        Assertions.assertEquals(32.6, produtoEncontrado.getPreco());
        Assertions.assertEquals(50, produtoEncontrado.getQtd());
    }

    @Test
    void deveDeletarUmProdutoComSucesso() {

        //Cenário
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome("Livro");
        repository.save(produto);

        //Execução
        repository.deleteById(produto.getId());


        //Validação
        Optional<ProdutoEntity> produtoEncontrado = repository.findById(produto.getId());
        Assertions.assertTrue(produtoEncontrado.isEmpty());
    }

}
