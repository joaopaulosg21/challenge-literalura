package projeto.desafio.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import projeto.desafio.literalura.model.Author;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseDTO(String title,
                          List<Author> authors,
                          List<String> languages,
                          Integer download_count) {
}
