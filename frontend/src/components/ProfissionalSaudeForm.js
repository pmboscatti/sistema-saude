import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { profissionalSaudeService } from '../services/api';

function ProfissionalSaudeForm() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [profissional, setProfissional] = useState({
    nome: '', email: '', telefone: '', endereco: '', categoria: ''
  });

  useEffect(() => {
    if (id) {
      profissionalSaudeService.buscar(id).then(res => setProfissional(res.data));
    }
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (id) {
        await profissionalSaudeService.atualizar(id, profissional);
      } else {
        await profissionalSaudeService.criar(profissional);
      }
      navigate('/profissionais');
    } catch (error) {
      console.error('Erro ao salvar profissional:', error);
    }
  };

  return (
    <div>
      <h2>{id ? 'Editar Profissional' : 'Novo Profissional'}</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Nome *</label>
          <input type="text" value={profissional.nome} required
            onChange={e => setProfissional({...profissional, nome: e.target.value})} />
        </div>
        <div className="form-group">
          <label>Email</label>
          <input type="email" value={profissional.email}
            onChange={e => setProfissional({...profissional, email: e.target.value})} />
        </div>
        <div className="form-group">
          <label>Telefone</label>
          <input type="text" value={profissional.telefone}
            onChange={e => setProfissional({...profissional, telefone: e.target.value})} />
        </div>
        <div className="form-group">
          <label>Endereço</label>
          <input type="text" value={profissional.endereco}
            onChange={e => setProfissional({...profissional, endereco: e.target.value})} />
        </div>
        <div className="form-group">
          <label>Categoria</label>
          <select value={profissional.categoria}
            onChange={e => setProfissional({...profissional, categoria: e.target.value})}>
            <option value="">Selecione...</option>
            <option value="PSICOLOGO">Psicólogo</option>
            <option value="FISIOTERAPEUTA">Fisioterapeuta</option>
            <option value="MEDICO">Médico</option>
          </select>
        </div>
        <div className="actions">
          <button type="submit" className="btn btn-primary">Salvar</button>
          <button type="button" className="btn" onClick={() => navigate('/profissionais')}>Cancelar</button>
        </div>
      </form>
    </div>
  );
}

export default ProfissionalSaudeForm;