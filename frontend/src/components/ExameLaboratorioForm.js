import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { exameLaboratorioService } from '../services/api';

function ExameLaboratorioForm() {
  const navigate = useNavigate();
  const { id } = useParams();

  const [exame, setExame] = useState({
    descricao: '',
    posologia: ''
  });

  useEffect(() => {
    if (id) {
      exameLaboratorioService.buscar(id).then(res => setExame(res.data));
    }
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (id) {
        await exameLaboratorioService.atualizar(id, exame);
      } else {
        await exameLaboratorioService.criar(exame);
      }

      navigate('/exames-laboratorio');
    } catch (error) {
      console.error('Erro ao salvar exame:', error);
    }
  };

  return (
    <div>
      <h2>{id ? 'Editar Exame de Laboratório' : 'Novo Exame de Laboratório'}</h2>

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Descrição *</label>
          <input
            type="text"
            value={exame.descricao}
            required
            onChange={e => setExame({ ...exame, descricao: e.target.value })}
          />
        </div>

        <div className="form-group">
          <label>Posologia *</label>
          <input
            type="text"
            value={exame.posologia}
            required
            onChange={e => setExame({ ...exame, posologia: e.target.value })}
          />
        </div>

        <div className="actions">
          <button type="submit" className="btn btn-primary">Salvar</button>
          <button type="button" className="btn" onClick={() => navigate('/exames-laboratorio')}>Cancelar</button>
        </div>
      </form>
    </div>
  );
}

export default ExameLaboratorioForm;