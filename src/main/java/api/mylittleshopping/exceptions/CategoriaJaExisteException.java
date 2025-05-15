package api.mylittleshopping.exceptions;


public class CategoriaJaExisteException extends RuntimeException {
    public CategoriaJaExisteException(String nome) {
        super("Categoria já existe: " + nome);
    }
}
