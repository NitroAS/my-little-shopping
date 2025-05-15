package api.mylittleshopping.interfaces;

import api.mylittleshopping.Categoria;
import api.mylittleshopping.Uf;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface UfRepository extends JpaRepository<Uf, Long> {
}
