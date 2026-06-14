import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
  headers: { 'Content-Type': 'application/json' }
});

export const profissionalSaudeService = {
  listar: () => api.get('/profissionais'),
  buscar: (id) => api.get(`/profissionais/${id}`),
  buscarPorNome: (nome) => api.get('/profissionais/buscar', { params: { nome } }),
  buscarPorCategoria: (categoria) => api.get(`/profissionais/categoria/${categoria}`),
  criar: (profissional) => api.post('/profissionais', profissional),
  atualizar: (id, profissional) => api.put(`/profissionais/${id}`, profissional),
  deletar: (id) => api.delete(`/profissionais/${id}`)
};

export const atendimentoService = {
  listar: () => api.get('/atendimentos'),
  buscar: (id) => api.get(`/atendimentos/${id}`),
  buscarPorTitulo: (titulo) => api.get('/atendimentos/buscar', { params: { titulo } }),
  buscarPorData: (data) => api.get(`/atendimentos/data/${data}`),
  buscarPorHorario: (horario) => api.get(`/atendimentos/horario/${horario}`),
  criar: (atendimento) => api.post('/atendimentos', atendimento),
  atualizar: (id, atendimento) => api.put(`/atendimentos/${id}`, atendimento),
  deletar: (id) => api.delete(`/atendimentos/${id}`)
};
export const exameLaboratorioService = {
  listar: () => api.get('/exames-laboratorio'),
  buscar: (id) => api.get(`/exames-laboratorio/${id}`),
  buscarPorDescricao: (descricao) => api.get('/exames-laboratorio/buscar', { params: { descricao } }),
  buscarPorPosologia: (posologia) => api.get('/exames-laboratorio/posologia', { params: { posologia } }),
  criar: (exame) => api.post('/exames-laboratorio', exame),
  atualizar: (id, exame) => api.put(`/exames-laboratorio/${id}`, exame),
  deletar: (id) => api.delete(`/exames-laboratorio/${id}`)
};
export default api;