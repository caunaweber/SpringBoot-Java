package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidadorDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorDeConsulta> validador;

    public void agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente não existe.");
        }
        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do médico não existe.");
        }

        validador.forEach(v ->  v.validar(dados));

        var medico = escolherMedico(dados);
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dados.data());

        consultaRepository.save(consulta);
    }

    public void cancelarConsulta(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        var consulta = consultaRepository.getReferenceById(dados.id());

        if(consulta.getData().minusHours(24).isBefore(LocalDateTime.now())){
            throw new ValidacaoException("A consulta só pode ser cancelada com no mínimo  24 horas de antecedência");
        }

        consultaRepository.delete(consulta);
    }

    private Medico escolherMedico(@Valid DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
            return medicoRepository.getById(dados.idMedico());
        }
        if(dados.especialidade() != null){
            throw new ValidacaoException("Especialidade é obrigatória quando não for selecionado um médico específico.");
        }

        return medicoRepository.escolherMedicoAleatorioLivre(dados.especialidade(), dados.data());
    }
}