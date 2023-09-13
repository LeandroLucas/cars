# CARS

Sistema para criação de carros e usuários com autenticação.

## SOLUÇÃO


## BUILD

### Requisitos

- Java x
- Maven x.x

## MODELO DE DADOS

### User

| Nome      | Tipo   | Descrição                     |
| --------- | ------ | ----------------------------- |
| firstName | String | Nome do usuário               |
| lastName  | String | Sobrenome do usuário          |
| email     | String | E-mail do usuário             |
| birthday  | Date   | Data de nascimento do usuário |
| login     | String | Login do usuário              |
| password  | String | Senha do usuário              |
| phone     | String | Telefone do usuário           |
| cars      | List   | Lista de carros do usuário    |

### Car

| Nome         | Tipo   | Descrição                          |
| ------------ | ------ | ---------------------------------- |
| year         | Int    | Ano de fabricação do carro         |
| licensePlate | String | Placa do carro                     |
| model        | String | Modelo do carro                    |
| color        | String | Cor predominante do carro          |
| usageCounter | Int    | Contador de utilizações do veículo |

## ESTÓRIAS DE USUÁRIO

**1 - Planejar estórias do backend e preparar README.md**

O README.md do projeto deverá ter uma seção ESTÓRIAS DE USUÁRIO com a lista numerada de estórias de
usuário que foram concebidas para a implementação do desafio. A primeira linha de cada commit do repositório
deve utilizar a descrição da estória de usuário associada

### Backend

**2 - Criar projeto spring boot**
```
Principais Dependencias:
    Maven
    spring web
    spring validator
    java 8+
    banco de dados h2
    spring data
```

**3 - Configurar banco de dados**
```
Requisitos:
    Preparar conexão com o banco de dados H2 da apliação.
```

**4 - Implementar API de usuário (não requer autenticação)**

| Rota            | Descrição                    | Erros possíveis |
| --------------- | ---------------------------- | --------------- |
| /api/users      | Listar todos os usuários     |                 |
| /api/users      | Cadastrar um novo usuário    | 2,3,4,5         |
| /api/users/{id} | Buscar um usuário pelo id    |                 |
| /api/users/{id} | Remover um usuário pelo id   |                 |
| /api/users/{id} | Atualizar um usuário pelo id | 2,3,4,5         |
```
Requisitos:
    Senha do usuário deve ser criptografada;
    testes unitários;

Erros possíveis:

1. E-mail já existente: retornar um erro com a mensagem “Email already exists”;
2. Login já existente: retornar um erro com a mensagem “Login already exists”;
3. Campos inválidos: retornar um erro com a mensagem “Invalid fields”;
4. Campos não preenchidos: retornar um erro com a mensagem “Missing fields”.
```
Exemplo de JSON para criação de usuário:
``` json
{
    "firstName": "Hello",
    "lastName": "World",
    "email": "hello@world.com",
    "birthday": "1990-05-01",
    "login": "hello.world",
    "password": "h3ll0",
    "phone": "988888888",
    "cars": [
        {
            "year": 2018,
            "licensePlate": "PDV-0625",
            "model": "Audi",
            "color": "White"
        }
    ]
}
```
**5 - Implementar autenticação do usuário**
| Rota        | Descrição                                                                                                                                       | Erros possíveis |
| ----------- | ----------------------------------------------------------------------------------------------------------------------------------------------- | --------------- |
| /api/signin | Esta rota espera um objeto com os campos login e password, e deve retornar o token de acesso da API (JWT) com as informações do usuário logado. | 1               |
```
Requisitos:
    jwt token;
    Senha deve ser criptografada;
    testes unitários;

Erros possíveis:

1. Login inexistente ou senha inválida: retornar um erro com a mensagem “Invalid login or password”;

```

**5 - Implementar API de dados do usuário autenticado**

| Rota    | Descrição                                                                                                                                                                                                    | Erros possíveis |
| ------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | --------------- |
| /api/me | Retornar as informações do usuário logado (firstName, lastName, email, birthday, login, phone, cars) + createdAt (data da criação do usuário) + lastLogin (data da última vez que o usuário realizou login). | 1,2             |

```
Requisitos:
    Todas as rotas devem esoerar o token de acesso da API (JWT) via header Authorization
    testes unitários;

Erros possíveis:
    1. Token não enviado: retornar um erro com a mensagem “Unauthorized”;
    2. Token expirado: retornar um erro com a mensagem “Unauthorized - invalid session”;
```
**6 - Implementar API de carros**

Todas as rotas abaixo esperam o token de acesso da API (JWT) via header Authorization

**7 - Documentar Testes, Build e Deploy**

O README.md do projeto deve ser claro e mostrar tudo que precisa ser feito para executar o build do projeto,
deploy, testes, etc.


**8 - Documentar Justificativa da solução**

O README.md do projeto deverá ter uma seção SOLUÇÃO com as justificativas e defesa técnica da solução que
está sendo entregue.

**9 - Criar rota para upload da fotografia do usuário e do carro**

**10 - Documentação da API com Swagger**

**11 - Integrar com SonarQube**
____
**Observações:**

- A aplicação deve aceitar e responder apenas em JSON;
- O id do usuário e o id do carro podem ser sequenciais gerados pelo banco ou identificadores únicos;
- Todas as rotas devem responder o código de status HTTP apropriado;
- Espera-se que as mensagens de erro tenham o seguinte formato:
    ``` json
    { "message": "Error message", "errorCode": 123 }
    ```
____
### Frontend

**12 - Definir telas do frontend**

**13 - Definir estórias do frontend** 
