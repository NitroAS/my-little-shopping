package api.mylittleshopping.handlers;

import api.mylittleshopping.exceptions.CategoriaJaExisteException;
import api.mylittleshopping.exceptions.CategoriaNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Tratamento para CategoriaJaExisteException
    @ExceptionHandler(CategoriaJaExisteException.class)
    public ResponseEntity<String> handleCategoriaJaExiste(CategoriaJaExisteException ex) {
        // Retorna a mensagem da exceção com o status 400 Bad Request
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<String> handleCategoriaNaoEncontrada(CategoriaNaoEncontradaException ex) {
        // Retorna a mensagem da exceção com o status 404 Not Found
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}





