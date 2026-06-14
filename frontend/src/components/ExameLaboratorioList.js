import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { exameLaboratorioService } from '../services/api';

function ExameLaboratorioList() {
  const [exames, setExames] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    carregarExames();
  }, []);

  const carregarExames = async () => {
    try {
      const response = await exameLaboratorioService.listar();
      setExames(response.data);
    } catch (error) {
      console.error('Erro ao carregar exames:', error);
    } finally {
      setLoading(false);
    }
  };

  const deletarExame = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este exame?')) {
      try {
        await exameLaboratorioService.deletar(id);
        carregarExames();
      } catch (error) {
        console.error('Erro ao deletar exame:', error);
      }
    }
  };

  if (loading) return <p>Carregando...</p>;

  return (
    <div>
      <div className="top-actions">
        <h2>Exames de Laboratório</h2>
        <Link to="/exames-laboratorio/novo" className="btn btn-primary">+ Novo Exame</Link>
      </div>

      <table>
        <thead>
          <tr>
            <th>Descrição</th>
            <th>Posologia</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {exames.map(exame => (
            <tr key={exame.id}>
              <td>{exame.descricao}</td>
              <td>{exame.posologia}</td>
              <td>
                <Link to={`/exames-laboratorio/editar/${exame.id}`} className="btn btn-sm">Editar</Link>
                <button onClick={() => deletarExame(exame.id)} className="btn btn-danger btn-sm">Excluir</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {exames.length === 0 && <p>Nenhum exame cadastrado.</p>}
    </div>
  );
}

export default ExameLaboratorioList;