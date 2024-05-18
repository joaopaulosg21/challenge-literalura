package projeto.desafio.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projeto.desafio.literalura.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("SELECT b FROM Book b JOIN b.author a WHERE a.name = :nomeAuthor")
    Optional<Book> buscarAuthorPeloNome(String nomeAuthor);

    @Query("SELECT b FROM Book b WHERE b.language = :idioma")
    List<Book> buscarLivrosPeloIdioma(String idioma);
}
