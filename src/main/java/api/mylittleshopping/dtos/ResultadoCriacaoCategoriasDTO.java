package api.mylittleshopping.dtos;

import java.util.List;

public class ResultadoCriacaoCategoriasDTO {
    private List<String> categoriasIncluidas;
    private List<String> categoriasIgnoradas;

    public ResultadoCriacaoCategoriasDTO(List<String> categoriasIncluidas, List<String> categoriasIgnoradas) {
        this.categoriasIncluidas = categoriasIncluidas;
        this.categoriasIgnoradas = categoriasIgnoradas;
    }

    public List<String> getCategoriasIncluidas() {
        return categoriasIncluidas;
    }

    public void setCategoriasIncluidas(List<String> categoriasIncluidas) {
        this.categoriasIncluidas = categoriasIncluidas;
    }

    public List<String> getCategoriasIgnoradas() {
        return categoriasIgnoradas;
    }

    public void setCategoriasIgnoradas(List<String> categoriasIgnoradas) {
        this.categoriasIgnoradas = categoriasIgnoradas;
    }
}
