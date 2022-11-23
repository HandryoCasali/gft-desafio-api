# ***Projeto GFT Noticias***

## Middleware para consumir api de noticias
**🔧 Tecnologias utilizadas 🔧**
- Java 17;
- Spring Boot 2.7.5;
- Lombok;
- MySql;
- Vs code;
- [API de noticias](https://apinoticias.tedk.com.br/)
---

## 🙍‍♂️ Usuários previamente cadastrados:
- **ADM**:
    - **login**: adms@email.com
    - **senha**: admin123
- **Usuario**:
    - **login**: user@email.com 
    - **senha**: user123
    
    É possível cadastrar novos usuários e ADM's.
    (Para cadastrar é necessário estar logado como adm).
---

## 🎲 Banco de dados
- Sistema já está previamente populado de exemplos.
---


## 🌎 Endpoints
- Cadastro de adm; 
- Cadastro de usuario;
- Endpoint para login;
- Cadastro de etiqueta (somente o usuario pode cadastrar suas etiquetas);
- Histórico das etiquetas acessadas (adm via id usuario e o proprio usuario);
- Ranking das etiquetas mais acessadas (somente o adm pode visualizar);
- Acesso as noticias (somente usuario);
- Envio de email com as noticias para todos usuarios;
---

## 🎁 ***Exceeds***

- [x] Autenticação com jwt 
- [x] Swagger
- [x] Projeto no insomnia
- [x] Requisições simultaneas no consumo da api de noticias
- [x] Filtrar data das noticias
- [x] Usuario atualizar a própria senha
- [x] Usuario desinscrever em alguma etiqueta
- [x] Envio de email para usuário no ato do cadastro
- [x] Endpoint envio de email com noticias da data corrente para usuarios (somente adm)
- [/] Testes unitários - Alguns testes apenas

---
## 🔧 ***Futuras Melhorias***

- [ ] Endpoint para pesquisar uma unica etiqueta mesmo sem estar inscrito na etiqueta;
