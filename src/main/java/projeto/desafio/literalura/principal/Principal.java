package projeto.desafio.literalura.principal;

import projeto.desafio.literalura.dto.ApiResponseDTO;
import projeto.desafio.literalura.model.Author;
import projeto.desafio.literalura.model.Book;
import projeto.desafio.literalura.repository.AuthorRepository;
import projeto.desafio.literalura.repository.BookRepository;
import projeto.desafio.literalura.service.BuscaApi;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private BuscaApi buscaApi = new BuscaApi();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private BookRepository repository;

    private AuthorRepository authorRepository;

    public Principal(BookRepository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    public void exibeMenu() {
        int opcao = -1;
        String menu = """
              Escolha uma opção valida:
              1 - Buscar livro pelo titulo
              2 - Listar livros registrados
              3 - Listar autores registrados
              4 - Listar autores vivos em um determinado ano
              5 - Listar livros em um determinado idioma
              6 - Top 10 mais baixados
              0 - sair
                """;

        while(opcao != 0) {
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();
            switch (opcao) {
                case 1:
                    this.buscarLivro();
                    break;
                case 2:
                    buscarTodosOsLivros();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    buscarAutoresPorData();
                    break;
                case 5:
                    buscarLivrosPeloIdioma();
                    break;
                case 6:
                    top10MaisBaixados();
                default:
                    System.out.println("Opcao invalida");
                    break;
            }
        }
    }

    private void buscarLivro() {
        System.out.print("Digite o nome do livro: ");
        String nomeLivro = leitura.nextLine();
        nomeLivro = nomeLivro.replaceAll(" ","%20").trim().toLowerCase();
        ApiResponseDTO response = buscaApi.obterDados(ENDERECO+nomeLivro, ApiResponseDTO.class);
        Book book = new Book(response.results().get(0));
        buscarAutorPeloNome(book);
        System.out.println("Livro " + book.getTitle() + " salvo com sucesso");
    }

    private void buscarTodosOsLivros() {
        System.out.println("*** LIVROS REGISTRADOS NO SISTEMA ***");
        repository.findAll().forEach(l -> {
            String response = """
                    Titulo: %s,
                    Autor: %s,
                    Idioma: %s,
                    Numero de downloads: %d
                    """.formatted(l.getTitle(), l.getAuthor().getName(),l.getLanguage(),l.getDownloadCount());
            System.out.println(response);
        });
    }

    private void buscarAutores() {
        System.out.println("*** AUTORES REGISTRADOS NO SISTEMA ***");
        authorRepository.findAll().forEach(a -> {
            String response = """
                    Nome: %s,
                    Ano do nascimento: %d,
                    Ano da morte: %d,
                    Livros: %s
                    """.formatted(a.getName(),a.getBirthYear(),a.getDeathYear(),a.getBooks());
            System.out.println(response);
        });
    }

    private void buscarAutorPeloNome(Book book) {
        Optional<Author> optionalAuthor = authorRepository.buscarAuthorPeloNome(book.getAuthor().getName());

        if(optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            book.setAuthor(author);
            repository.save(book);
        }else {
            repository.save(book);
        }
    }

    private void buscarAutoresPorData() {
        System.out.println("Insira o ano que deseja pesquisar");
        int ano = leitura.nextInt();
        leitura.nextLine();

        authorRepository.buscarPorDeterminadoAno(ano).forEach(a -> {
            String response = """
                    Nome: %s,
                    Ano do nascimento: %d,
                    Ano da morte: %d,
                    Livros: %s
                    """.formatted(a.getName(),a.getBirthYear(),a.getDeathYear(),a.getBooks());
            System.out.println(response);
        });
    }

    private void buscarLivrosPeloIdioma(){
        System.out.println("""
                es- espanhol,
                en- ingles
                fr - frances
                pt- portugues
                """);
        System.out.print("Digite o idioma para a busca:");
        String idioma = leitura.nextLine();

        List<Book> books = repository.buscarLivrosPeloIdioma(idioma);

        if(books.isEmpty()) {
            System.out.println("\nNao existem livros registrados nesse idioma\n");

        }
        books.forEach(l -> {
            String response = """
                    Titulo: %s,
                    Autor: %s,
                    Idioma: %s,
                    Numero de downloads: %d
                    """.formatted(l.getTitle(), l.getAuthor().getName(),l.getLanguage(),l.getDownloadCount());
            System.out.println(response);
        });
    }

    private void top10MaisBaixados() {
        System.out.println("*** TOP 10 LIVROS MAIS BAIXADOS***");
        repository.top10MaisBaixados().forEach(l -> {
            String response = """
                    Titulo: %s,
                    Autor: %s,
                    Idioma: %s,
                    Numero de downloads: %d
                    """.formatted(l.getTitle(), l.getAuthor().getName(),l.getLanguage(),l.getDownloadCount());
            System.out.println(response);
        });

    }
}
