package projeto.desafio.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.desafio.literalura.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
