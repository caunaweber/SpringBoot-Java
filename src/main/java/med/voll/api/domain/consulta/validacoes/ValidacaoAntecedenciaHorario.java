package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacaoAntecedenciaHorario implements ValidadorDeConsulta{

    public void validar(DadosAgendamentoConsulta dados) {

        var diferencaMinutos = Duration.between(LocalDateTime.now(), dados.data()).toMinutes();
        if (diferencaMinutos < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com o mínimo 30 minutos de antecedência ");
        }
    }
}
