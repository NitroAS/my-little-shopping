package api.mylittleshopping.services;

import api.mylittleshopping.Categoria;
import api.mylittleshopping.interfaces.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria salvarCategoria(Categoria categoria) {
        // Aqui você pode adicionar lógica de validação, transformação, etc.
        return categoriaRepository.save(categoria);
    }
}
