package api.mylittleshopping.controllers;


import api.mylittleshopping.Categoria;
import api.mylittleshopping.Uf;
import api.mylittleshopping.interfaces.UfRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/uf")
@Validated
public class UfController {
    @Autowired
    private UfRepository ufRepository;


    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<Uf> ufs = ufRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (ufs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há dados para retornar");
        }
        return ResponseEntity.ok(ufs);
    }

}
