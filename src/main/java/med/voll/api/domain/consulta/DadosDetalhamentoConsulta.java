package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
        Long Id,
        Long IdMedico,
        Long IdPaciente,
        LocalDateTime data) {
}
