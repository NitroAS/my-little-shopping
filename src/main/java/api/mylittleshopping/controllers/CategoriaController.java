package api.mylittleshopping.controllers;

import api.mylittleshopping.Categoria;
import api.mylittleshopping.Uf;
import api.mylittleshopping.dtos.ResultadoCriacaoCategoriasDTO;
import api.mylittleshopping.exceptions.CategoriaJaExisteException;
import api.mylittleshopping.exceptions.CategoriaNaoEncontradaException;
import api.mylittleshopping.interfaces.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categoria")
@Validated
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    public Categoria criar(@RequestBody @Valid Categoria categoria) {
        if (categoriaRepository.existsByNomeIgnoreCase(categoria.getNome())) {
            throw new CategoriaJaExisteException(categoria.getNome());
        }
        return categoriaRepository.save(categoria);
    }

    @PostMapping("/lote")
    @Transactional
    public ResponseEntity<ResultadoCriacaoCategoriasDTO> criarVarias(@RequestBody List<@Valid Categoria> categorias) {
        // Eliminar duplicatas da lista recebida (por nome, ignorando maiúsculas/minúsculas)
        Map<String, Categoria> categoriasUnicasMap = categorias.stream()
                .collect(Collectors.toMap(
                        c -> c.getNome().toLowerCase(),
                        c -> c,
                        (c1, c2) -> c1 // mantém o primeiro
                ));

        List<String> nomesRecebidos = new ArrayList<>(categoriasUnicasMap.keySet());

        // Filtrar os nomes que já existem no banco
        List<String> nomesExistentes = nomesRecebidos.stream()
                .filter(nome -> categoriaRepository.existsByNomeIgnoreCase(nome))
                .toList();

        // Filtrar apenas as categorias novas
        List<Categoria> novasCategorias = categoriasUnicasMap.entrySet().stream()
                .filter(entry -> !nomesExistentes.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .toList();

        // Salvar as novas categorias
        categoriaRepository.saveAll(novasCategorias);

        // Preparar resposta
        List<String> nomesIncluidos = novasCategorias.stream()
                .map(Categoria::getNome)
                .toList();

        ResultadoCriacaoCategoriasDTO resposta = new ResultadoCriacaoCategoriasDTO(nomesIncluidos, nomesExistentes);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }
    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<Categoria> categorias = categoriaRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (categorias.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há dados para retornar");
        }
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarCategoria(@PathVariable Long id, @RequestBody @Valid Categoria categoriaAtualizada) {
        // Verifica se a categoria com o ID fornecido existe
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada"));

        // Verifica se o nome da categoria já existe no banco (ignorando maiúsculas e minúsculas)
        if (categoriaRepository.existsByNomeIgnoreCase(categoriaAtualizada.getNome())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("O nome da categoria '" + categoriaAtualizada.getNome() + "' já existe. Escolha outro nome.");
        }

        // Atualiza os dados da categoria existente
        categoriaExistente.setNome(categoriaAtualizada.getNome());

        // Salva a categoria atualizada no banco de dados
        categoriaRepository.save(categoriaExistente);

        // Retorna a mensagem de sucesso
        return ResponseEntity.ok("Categoria com ID " + id + " foi alterada com sucesso.");
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirCategoria(@PathVariable Long id) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada"));

        categoriaRepository.delete(categoriaExistente);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Categoria excluída com sucesso.");
    }
}
