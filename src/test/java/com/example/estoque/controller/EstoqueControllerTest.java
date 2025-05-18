package com.example.estoque.controller;

import com.example.estoque.domain.Produto;
import com.example.estoque.service.ProdutoService;
import com.example.estoque.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EstoqueControllerTest {

    @InjectMocks
    EstoqueController controller;

    @Mock
    ProdutoService service;

    final String PATH = "/estoque";

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void deveCadastrarProdutoComSucesso() throws Exception {
        //Cenário
        Produto produto = new Produto();
        produto.setNome("Livro");
        produto.setQtd(15);

        //Execução e validação
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(produto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cadastrado com Sucesso"))
                .andDo(print());

        Mockito.verify(service).cadastrarProduto(Mockito.any(Produto.class));

    }

    @Test
    void deveListarTodosProdutosComSucesso() throws Exception {
        //Cenário
        List<Produto> produtos = new ArrayList();
        produtos.add(new Produto("Livro", "Descrição 1", 54.5, 20));
        produtos.add(new Produto("Camiseta", "Descrição 2", 60.6, 25));

        Mockito.when(service.encontrarTodos()).thenReturn(produtos);

        //Execução e Validação
        mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().string(TestUtils.asJsonString(produtos)))
                .andDo(print());

    }


}
