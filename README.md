# Sistema de Clínica de Saúde

Trabalho da disciplina de Engenharia de Software. Sistema web para gerenciamento de uma clínica de saúde, desenvolvido em trio.

**Tecnologias:** Java + Spring Boot · React 18 · PostgreSQL · Docker

---

## Como rodar o projeto

A única coisa que você precisa ter instalado é o **Docker**. Não precisa instalar Java, Maven, Node ou PostgreSQL na sua máquina.

### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd sistema-saude
```

### 2. Suba os containers

```bash
docker-compose up --build -d
```

Esse comando vai baixar as imagens necessárias, compilar o backend e o frontend, e subir três containers:

- **postgres** — banco de dados na porta 5432
- **backend** — API Spring Boot na porta 8080
- **frontend** — React servido pelo Nginx na porta 3000

Na primeira vez demora alguns minutos porque precisa baixar tudo. Nas próximas vezes é bem mais rápido por causa do cache do Docker.

### 3. Acesse o sistema

Abra no navegador: **http://localhost:3000**

### Para derrubar os containers

```bash
docker-compose down
```

---

## Estrutura do projeto

```
sistema-saude/
  backend/   -> API REST em Java Spring Boot
  frontend/  -> Interface em React
  docs/      -> Documentação e requisitos
  docker-compose.yml
```

---

## Módulos

| Módulo | Responsável | Branch |
|---|---|---|
| ProfissionalSaude | Pedro | feature/profissional-saude |
| Atendimento | - | feature/atendimento |
| ExameLaboratorio | - | feature/exame-laboratorio |