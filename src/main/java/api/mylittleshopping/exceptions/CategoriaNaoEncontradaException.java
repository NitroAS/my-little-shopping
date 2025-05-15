package api.mylittleshopping.exceptions;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
