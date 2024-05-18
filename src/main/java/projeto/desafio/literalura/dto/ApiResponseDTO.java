package projeto.desafio.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResponseDTO(List<ResponseDTO> results) {
}
