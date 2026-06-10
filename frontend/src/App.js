import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import ProfissionalSaudeList from './components/ProfissionalSaudeList';
import ProfissionalSaudeForm from './components/ProfissionalSaudeForm';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <nav className="navbar">
          <h1>Clinica de Saude</h1>
          <div className="nav-links">
            <Link to="/profissionais">Profissionais</Link>
          </div>
        </nav>

        <main className="container">
          <Routes>
            <Route path="/" element={<ProfissionalSaudeList />} />
            <Route path="/profissionais" element={<ProfissionalSaudeList />} />
            <Route path="/profissionais/novo" element={<ProfissionalSaudeForm />} />
            <Route path="/profissionais/editar/:id" element={<ProfissionalSaudeForm />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;