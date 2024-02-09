# PetStoreApi

## Resumo

### Arquitetura Hexagonal

A **Arquitetura Hexagonal** é um modelo de design de aplicativos de software que promove a isolamento da lógica de domínio, localizada na parte interna (núcleo de negócios), de fatores externos. A comunicação com a lógica de domínio ocorre por meio de portas e adaptadores, garantindo uma separação clara entre as partes internas e externas da aplicação.

#### Componentes da Arquitetura Hexagonal:

1. **Camada de Aplicação:**
   - Interface pela qual usuários e outros programas interagem com a aplicação.
   - Inclui elementos como interfaces de usuário, controladores RESTful e bibliotecas de serialização JSON.
   - Responsável por expor a entrada na aplicação e orquestrar a execução da lógica de domínio.

2. **Camada de Domínio:**
   - Contém o código que implementa a lógica de negócios, sendo o núcleo da aplicação.
   - Isolada das partes de aplicação e infraestrutura.
   - Inclui interfaces que definem a API para comunicação com partes externas, como um banco de dados.

3. **Camada de Infraestrutura:**
   - Contém todos os recursos necessários para o funcionamento da aplicação.
   - Inclui configuração de banco de dados, configuração do Spring e implementações de interfaces dependentes de infraestrutura da camada de domínio.

![hexagonal_architecture](https://github.com/murilohenzo/mono-to-micro/assets/28688721/467e9210-2584-4204-96e0-f4d8a36e9e78)

### Dependency Inversion na Camada de Repository

Na camada de infraestrutura, implementamos o princípio de **Dependency Inversion** na camada de Repository. Isso é alcançado por meio da criação de uma interface no domínio e uma implementação correspondente na camada de infraestrutura. Essa abordagem faz com que nosso serviço de domínio dependa de abstrações, não de implementações específicas, tornando o código mais flexível e adaptável a mudanças.

### Contrato First

A abordagem **Contract-First** envolve a definição do contrato antes da implementação do serviço. Um Swagger é criado e compartilhado com os consumidores, estabelecendo a comunicação esperada de solicitação e resposta. Isso permite que o provedor de serviços trabalhe na prestação de um serviço que adere ao contrato, enquanto o consumidor desenvolve um aplicativo para consumi-lo.

Para gerar o contrato primeiro, utilizamos o **OpenAPI Code Generator**, que cria a camada de aplicação com o padrão Delegate. Este padrão permite a criação de classes intermediárias (delegates) que encaminham chamadas de métodos para a implementação real da lógica. Essa abordagem promove uma estrutura modular e flexível baseada no contrato definido pelo OpenAPI.

### Padrão Delegate

O **Padrão Delegate** (Delegate Pattern) é empregado na geração da camada de aplicação. Nesse padrão, um objeto delegante é responsável por encaminhar chamadas de métodos para um objeto delegado, promovendo a separação de responsabilidades, reusabilidade, flexibilidade e facilitando a manutenção do código.

Principais vantagens do Padrão Delegate:

1. **Separação de Responsabilidades:** O delegante controla o fluxo geral, enquanto o delegado se concentra na execução específica da lógica.
  
2. **Reusabilidade:** A lógica encapsulada no delegado pode ser reutilizada em diferentes contextos, promovendo modularidade.
  
3. **Flexibilidade:** Permite alterar o comportamento do delegante sem modificar sua própria implementação, apenas alterando o objeto delegado.

4. **Manutenção mais Fácil:** Alterações na lógica específica podem ser realizadas no delegado sem afetar o delegante.
