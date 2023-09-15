# CARS

Sistema para criação de usuários de carros com autenticação.

## SOLUÇÃO


## TEST, BUILD

- [cars-api](./cars-api/README.md)

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

- [X] **1 - Planejar estórias do backend e preparar README.md**

O README.md do projeto deverá ter uma seção ESTÓRIAS DE USUÁRIO com a lista numerada de estórias de
usuário que foram concebidas para a implementação do desafio. A primeira linha de cada commit do repositório
deve utilizar a descrição da estória de usuário associada

### Backend

- [X] **2 - Criar projeto spring boot**
```
Principais Dependencias:
    Maven
    spring web
    spring validator
    java 8+
    banco de dados h2
    spring data
```

- [X] **3 - Configurar banco de dados**
```
Requisitos:
    Preparar conexão com o banco de dados H2 da aplicação.
```

- [X] **4 - Implementar API de usuário (não requer autenticação)**

| Método HTTP | Rota            | Descrição                    | Erros possíveis |
| ----------- | --------------- | ---------------------------- | --------------- |
| GET         | /api/users      | Listar todos os usuários     |                 |
| POST        | /api/users      | Cadastrar um novo usuário    | 2,3,4,5         |
| GET         | /api/users/{id} | Buscar um usuário pelo id    |                 |
| DELETE      | /api/users/{id} | Remover um usuário pelo id   |                 |
| PUT         | /api/users/{id} | Atualizar um usuário pelo id | 2,3,4,5         |
```
Requisitos:
    testes unitários;

Erros possíveis:
    2. E-mail já existente: retornar um erro com a mensagem “Email already exists”;
    3. Login já existente: retornar um erro com a mensagem “Login already exists”;
    4. Campos inválidos: retornar um erro com a mensagem “Invalid fields”;
    5. Campos não preenchidos: retornar um erro com a mensagem “Missing fields”.
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
- [X] **5 - Implementar autenticação do usuário**
| Método HTTP | Rota         | Descrição                                                                                                                                       | Erros possíveis |
| ----------- | ------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- | --------------- |
| POST        | /api/signin  | Esta rota espera um objeto com os campos login e password, e deve retornar o token de acesso da API (JWT) com as informações do usuário logado. | 1               |
| DELETE      | /api/signout | Deslogar usuário                                                                                                                                | 1, 2, 3         |
```
Requisitos:
    jwt token;
    Senha deve ser criptografada;
    testes unitários;

Erros possíveis:

    1. Login inexistente ou senha inválida: retornar um erro com a mensagem “Invalid login or password”;
    2. Token não enviado: retornar um erro com a mensagem “Unauthorized”;
    3. Token expirado: retornar um erro com a mensagem “Unauthorized - invalid session”;

```

- [X] **5 - Implementar API de dados do usuário autenticado**

| Método HTTP | Rota    | Descrição                                                                                                                                                                                                    | Erros possíveis |
| ----------- | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | --------------- |
| GET         | /api/me | Retornar as informações do usuário logado (firstName, lastName, email, birthday, login, phone, cars) + createdAt (data da criação do usuário) + lastLogin (data da última vez que o usuário realizou login). | 1,2             |

```
Requisitos:
    Todas as rotas devem esperar o token de acesso da API (JWT) via header Authorization
    testes unitários;

Erros possíveis:
    1. Token não enviado: retornar um erro com a mensagem “Unauthorized”;
    2. Token expirado: retornar um erro com a mensagem “Unauthorized - invalid session”;
```
- [x] **6 - Implementar API de carros**

| Método HTTP | Rota           | Descrição                                     | Erros possíveis |
| ----------- | -------------- | --------------------------------------------- | --------------- |
| GET         | /api/cars      | Listar todos os carros do usuário logado      | 1,2             |
| POST        | /api/cars      | Cadastrar um novo carro para o usuário logado | 1,2,3,4,5       |
| GET         | /api/cars/{id} | Buscar um carro do usuário logado pelo id     | 1,2             |
| DELETE      | /api/cars/{id} | Remover um carro do usuário logado pelo id    | 1,2             |
| PUT         | /api/cars/{id} | Atualizar um carro do usuário logado pelo id  | 1,2,3,4,5       |

```
Requisitos:
    Todas as rotas devem esperar o token de acesso da API (JWT) via header Authorization
    testes unitários;

Erros possíveis:
    1. Token não enviado: retornar um erro com a mensagem “Unauthorized”;
    2. Token expirado: retornar um erro com a mensagem “Unauthorized - invalid session”;
    3. Placa já existente: retornar um erro com a mensagem “License plate already exists”;
    4. Campos inválidos: retornar um erro com a mensagem “Invalid fields”;
    5. Campos não preenchidos: retornar um erro com a mensagem “Missing fields”.
```
Exemplo de JSON para criação de carro:
``` json
{
    "year": 2018,
    "licensePlate": "PDV-0625",
    "model": "Audi",
    "color": "White"
}
```


- [x] **7 - Documentar Testes, Build e Deploy**

O README.md do projeto deve ser claro e mostrar tudo que precisa ser feito para executar o build do projeto,
deploy, testes, etc.


- [ ] **8 - Documentar Justificativa da solução**

O README.md do projeto deverá ter uma seção SOLUÇÃO com as justificativas e defesa técnica da solução que
está sendo entregue.

- [ ] **9 - Criar rota para upload da fotografia do usuário e do carro**

- [ ] **10 - Documentação da API com Swagger**

- [ ] **11 - Integrar com SonarQube**
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

- [x] **12 - Definir telas do frontend**

- [x] **13 - Definir estórias do frontend** 

- [x] **14 - Criar projeto Angular + Angular Material**

- [x] **15 - Implementar Tela para demonstrar endpoints sem autenticação**
```
Requisitos:
    Deve ser possível criar, listar, atualizar e deletar usuários
```
- [x] **16 - Implementar login, area logada e logout**

- [x] **17 - Implementar tela de usuário logado**
```
Requisitos:
    Deve ser possível criar, listar, atualizar e deletar carros
```

- [ ] **18 - Fazer deploy**