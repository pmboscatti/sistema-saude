import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { atendimentoService, profissionalSaudeService } from '../services/api';

function AtendimentoForm() {
  const navigate = useNavigate();
  const { id } = useParams();
  
  // Estado inicial atualizado com os novos atributos
  const [atendimento, setAtendimento] = useState({
    titulo: '', data: '', horario: '', link: '', tipoReceita: '', receitaDetalhada: '', profissionalSaude: null
  });
  const [profissionais, setProfissionais] = useState([]);

  useEffect(() => {
    // Carrega a lista de profissionais para preencher o Select
    profissionalSaudeService.listar().then(res => setProfissionais(res.data));
    
    if (id) {
      atendimentoService.buscar(id).then(res => {
        // Se a API retornar null para o tipoReceita, ajustamos para string vazia pro Select não quebrar
        const dados = res.data;
        if (!dados.tipoReceita) dados.tipoReceita = '';
        setAtendimento(dados);
      });
    }
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (id) {
        await atendimentoService.atualizar(id, atendimento);
      } else {
        await atendimentoService.criar(atendimento);
      }
      navigate('/atendimentos');
    } catch (error) {
      console.error('Erro ao salvar atendimento:', error);
    }
  };

  return (
    <div>
      <h2>{id ? 'Editar Atendimento' : 'Novo Atendimento'}</h2>
      <form onSubmit={handleSubmit}>
        
        <div className="form-group">
          <label>Título *</label>
          <input type="text" value={atendimento.titulo} required
            onChange={e => setAtendimento({...atendimento, titulo: e.target.value})} />
        </div>
        
        <div className="form-group">
          <label>Data *</label>
          <input type="date" value={atendimento.data} required
            onChange={e => setAtendimento({...atendimento, data: e.target.value})} />
        </div>
        
        <div className="form-group">
          <label>Horário *</label>
          <input type="time" value={atendimento.horario} required
            onChange={e => setAtendimento({...atendimento, horario: e.target.value})} />
        </div>

        <div className="form-group">
          <label>Link (Videoconferência)</label>
          <input type="url" value={atendimento.link || ''} placeholder="https://..."
            onChange={e => setAtendimento({...atendimento, link: e.target.value})} />
        </div>

        <div className="form-group">
          <label>Tipo de Prescrição</label>
          <select value={atendimento.tipoReceita}
            onChange={e => setAtendimento({...atendimento, tipoReceita: e.target.value})}>
            <option value="">Nenhuma prescrição</option>
            <option value="REMEDIO">Remédio (Médico)</option>
            <option value="ATIVIDADE_FISICA">Atividade Física (Fisioterapeuta)</option>
            <option value="ATIVIDADE_MENTAL">Atividade Mental (Psicólogo)</option>
          </select>
        </div>

        <div className="form-group">
          <label>Detalhes da Prescrição / Evolução</label>
          <textarea value={atendimento.receitaDetalhada || ''} rows="3"
            onChange={e => setAtendimento({...atendimento, receitaDetalhada: e.target.value})} />
        </div>

        <div className="form-group">
          <label>Profissional de Saúde Responsável</label>
          <select value={atendimento.profissionalSaude?.id || ''} required
            onChange={e => setAtendimento({...atendimento,
              profissionalSaude: e.target.value ? {id: parseInt(e.target.value)} : null})}>
            <option value="">Selecione um profissional</option>
            {profissionais.map(p => (
              <option key={p.id} value={p.id}>{p.nome}</option>
            ))}
          </select>
        </div>

        <div className="actions">
          <button type="submit" className="btn btn-primary">Salvar</button>
          <button type="button" className="btn" onClick={() => navigate('/atendimentos')}>Cancelar</button>
        </div>
      </form>
    </div>
  );
}

export default AtendimentoForm;