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

Usuarios:
    
    v1/usuarios

- **Get "/"** Listar usuarios cadastrados (somente adm);
- **Get "/historico"** Mostrar historico do proprio usuario (somente user);
- **Get "/historico/{id}"** Mostrar historico de um usuario (somente adm);
- **Get "/detalhes"** Mostrar detalhes do proprio usuario (somente user);
- **Get "/detalhes/{id}"** Mostrar detalhes de um usuario (somente adm);
- **Post "/"** Cadastro de usuario, atributo *"perfil"* especifica a role (somente adm);
- **Put "/nova-senha"** Atualiza senha do usuario (adm e user);
- **Delete "/"** Excluir o proprio usuario (adm e user);

Etiquetas:

    v1/etiquetas

- **Get "/"** Ranking etiquetas acessadas (somente adm);
- **Post "/"** Cadastrar etiqueta no usuario (somente o user);
- **Delete "/"** "Desinscrever" etiqueta no usuario (somente o usuario);


Noticias:

    v1/noticias

- **Get "/"** Buscar noticias do usuario (somente user);
- **Get "/email/enviar"** Enviar noticias nos emails dos usuarios (somente adm);

Autenticacao:

    v1/auth

- **Post "/"** Autenticar para receber o token jwt (adm e user);
---

## 🎁 ***Exceeds***

- [x] Autenticação com jwt;
- [x] Swagger;
- [x] Projeto no insomnia;
- [x] Requisições simultaneas no consumo da api de noticias;
- [x] Filtrar data das noticias;
- [x] Usuario atualizar a própria senha;
- [x] Usuario desinscrever em alguma etiqueta;
- [x] Envio de email para usuário no ato do cadastro;
- [x] Endpoint envio de email com noticias da data corrente para usuarios (somente adm);
- [x] Listar usuarios cadastrados (somente adm);
- [x] Detalhes usuarios (proprio usuario ou adm via id);
- [/] Testes unitários - Alguns testes apenas;

---
## 🔧 ***Futuras Melhorias***

- [ ] Endpoint para pesquisar uma unica etiqueta mesmo sem estar inscrito na etiqueta;
- [ ] Atualizar Email Usuario;
- [ ] Usuario decidir se quer receber email com noticias;
