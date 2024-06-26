package projeto.desafio.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projeto.desafio.literalura.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE :ano BETWEEN a.birthYear AND a.deathYear")
    List<Author> buscarPorDeterminadoAno(int ano);

    @Query("SELECT a FROM Author a WHERE LOWER(a.name) = LOWER(:nome)")
    Optional<Author> buscarAuthorPeloNome(String nome);
}
