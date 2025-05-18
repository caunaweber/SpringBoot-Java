package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCancelamentoConsulta(
        @NotNull Long id,
        @NotNull MotivoCancelamento motivo,
        LocalDateTime data) {
}
