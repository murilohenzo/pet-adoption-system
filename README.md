# algafood-api

## Resumo
### Hexagonal Architecture
A arquitetura hexagonal é um modelo de design de aplicativos de software em torno da lógica de domínio para isolá-la de fatores externos. A lógica de domínio é especificada em um núcleo de negócios, que chamaremos de parte interna, sendo o restante partes externas. O acesso à lógica de domínio a partir do exterior está disponível através de portas e adaptadores

![Screenshot_1](https://user-images.githubusercontent.com/28688721/215502510-acab5f7d-e34c-44ed-85c9-c8f57f819812.png)

Através da camada de aplicação, o usuário ou qualquer outro programa interage com a aplicação. Essa área deve conter coisas como interfaces de usuário, controladores RESTful e bibliotecas de serialização JSON. Ele inclui qualquer coisa que exponha a entrada em nosso aplicativo e orquestre a execução da lógica de domínio.

Na camada de domínio, mantemos o código que toca e implementa a lógica de negócios. Este é o núcleo da nossa aplicação. Além disso, essa camada deve ser isolada da parte do aplicativo e da parte de infraestrutura. Além disso, ele também deve conter interfaces que definem a API para se comunicar com partes externas, como o banco de dados, com o qual o domínio interage.

Por fim, a camada de infraestrutura é a parte que contém tudo o que o aplicativo precisa para funcionar, como configuração de banco de dados ou configuração do Spring. Além disso, ele também implementa interfaces dependentes de infraestrutura da camada de domínio.

### Contract First
Em uma abordagem Contract-First, você primeiro define o contrato e, em seguida, implementa o serviço.
Quando começamos com o estabelecimento de um contrato, definimos um swagger e, em seguida, compartilhamos com nosso consumidor. Tudo isso pode acontecer antes mesmo de implementarmos o serviço e disponibilizá-lo.

O contrato informa ao consumidor qual deve ser a comunicação de solicitação e resposta. Uma vez que o contrato esteja em vigor, o provedor de serviços pode trabalhar na prestação de um serviço que adere ao contrato. O consumidor de serviço pode trabalhar no desenvolvimento de um aplicativo para consumi-lo.

Para a gerar o contract first, vamos utilizar o openapi code generator para gerar nossa camada de application com delegate pattern.

    <plugin>
        <groupId>org.openapitools</groupId>
        <artifactId>openapi-generator-maven-plugin</artifactId>
        <version>5.1.0</version>
        <executions>
            <execution>
                <goals>
                    <goal>generate</goal>
                </goals>
                <configuration>
                    <inputSpec>
                        ${project.basedir}/src/main/resources/swagger/openapi.yaml
                    </inputSpec>
                    <generatorName>spring</generatorName>
                    <apiPackage>${swagger.basePackage}</apiPackage>
                    <modelPackage>${swagger.modelPackage}</modelPackage>
                    <supportingFilesToGenerate>
                        ApiUtil.java
                    </supportingFilesToGenerate>
                    <configOptions>
                        <delegatePattern>true</delegatePattern>
                    </configOptions>
                </configuration>
            </execution>
        </executions>
    </plugin>
