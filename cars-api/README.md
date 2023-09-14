# Cars API

API RESTful para Sistema de Usuários de Carros

## Build

### Requisitos

- Java 17
- Maven 3.9.4

### Executar Testes

``` shellscript
mvn test
```

### Build

Para executar os testes e gerar versão do jar: 

``` shellscript
mvn package
```

O arquivo cars-api.jar será gerado na pasta ```target```

### Execução

Para subir o APP e disponibilizar a API:
``` shellscript
java -jar cars-api.jar
```