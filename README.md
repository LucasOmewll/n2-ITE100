# **SalaFácil - Sistema de Reserva de Salas de Reunião**

## 📋 Sobre o Projeto

O **SalaFácil** é um aplicativo móvel desenvolvido para otimizar a gestão de salas de reunião em ambientes corporativos. Com o aumento das políticas de **Return to Office (RTO)** e modelos híbridos de trabalho, a aplicação resolve problemas comuns de conflitos de agendamento e subutilização de espaços físicos.

### 🎯 Objetivos
- Automatizar o processo de reserva de salas de reunião
- Oferecer interface intuitiva e acessível
- Garantir segurança e escalabilidade com Firebase
- Otimizar o uso de espaços corporativos

### 👥 Público-Alvo
Funcionários de empresas que adotaram modelos híbridos de trabalho e gestores responsáveis pela otimização de espaços corporativos.

## 🚀 Funcionalidades

### 👤 Para Usuários Autenticados
- **📋 Listar salas** disponíveis com filtros por data/horário
- **✅ Reservar salas** com verificação de conflitos
- **❌ Cancelar reservas** próprias
- **📊 Visualizar histórico** de reservas

### 👨‍💼 Para Administradores
- **➕ Cadastrar novas salas** com detalhes completos
- **✏️ Editar informações** das salas existentes
- **🗑️ Excluir salas** (com validação de reservas futuras)
- **📈 Gerar relatórios** de utilização

### 🔐 Gestão de Acesso
- **👥 Cadastro de usuários**
- **🔑 Sistema de login** seguro
- **🎯 Controle de permissões** por níveis (Guest, User, Admin)

## 🛠️ Tecnologias Utilizadas

### Front-End
- **🟣 Kotlin** - Linguagem principal
- **📱 Android Studio** - IDE de desenvolvimento
- **🎨 Material Design** - Diretrizes de interface

### Back-End & Banco de Dados
- **🔥 Firebase Authentication** - Autenticação de usuários
- **☁️ Cloud Firestore** - Banco de dados NoSQL
- **🔒 Firebase Security Rules** - Controle de acesso

## 📊 Casos de Uso Implementados

| UC | Nome | Descrição |
|----|------|-----------|
| UC01 | Cadastrar usuário | Registro de novos usuários |
| UC02 | Realizar login | Autenticação no sistema |
| UC03 | Listar salas | Visualização de salas disponíveis |
| UC04 | Reservar sala | Agendamento de salas |
| UC05 | Cancelar reserva | Cancelamento de agendamentos |
| UC06 | Visualizar histórico | Consulta de reservas passadas |
| UC07 | Cadastrar sala | Adição de novas salas (Admin) |
| UC08 | Editar sala | Modificação de salas (Admin) |
| UC09 | Excluir sala | Remoção de salas (Admin) |

## 👥 Equipe de Desenvolvimento

| Nome | Função | Responsabilidades |
|------|--------|------------------|
| Lucas Gomes | Desenvolvedor Front-end | UI/UX, Telas de usuário |
| Vitor Doja | Desenvolvedor Full-stack | Integração Firebase, Lógica de negócio |
| Gustavo Torres | Desenvolvedor Back-end | Modelagem de dados |

## 📄 Licença

Este projeto está sob licença AGPLv3. Veja o arquivo [LICENSE](LICENSE) para detalhes.
---
*Desenvolvido com ❤️ para otimizar o futuro do trabalho híbrido*
