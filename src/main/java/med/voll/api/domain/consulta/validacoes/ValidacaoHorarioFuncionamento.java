package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacaoHorarioFuncionamento implements ValidadorDeConsulta{

    public void validar(DadosAgendamentoConsulta dados){

        var domingo = dados.data().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioValidoAbertura = dados.data().getHour() < 7;
        var horarioValidoFechamento = dados.data().getHour() > 18;

        if(domingo || horarioValidoAbertura || horarioValidoFechamento){
            throw new ValidacaoException("Consulta fora do horário de funcionamento");
        }
    }
}
