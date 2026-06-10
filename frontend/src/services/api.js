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

export default api;