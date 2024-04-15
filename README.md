# Pet Adoption System

## Design Arquitetural usado pelos microservices

### Arquitetura Hexagonal (Ports and Adapters)

A **Arquitetura Hexagonal** é um modelo de design de aplicativos de software que promove a isolamento da lógica de domínio, localizada na parte interna (núcleo de negócios), de fatores externos. A comunicação com a lógica de domínio ocorre por meio de portas e adaptadores, garantindo uma separação clara entre as partes internas e externas da aplicação.

#### Componentes da Arquitetura Hexagonal:

- **domain**: Lógica central da aplicação.
  - **models**: Aqui estão localizadas as entidades e objetos de valor que representam conceitos do domínio.
  - **ports**: Contém as interfaces (ports) que definem os contratos entre o domínio e os adaptadores.
  - **services**: Contém a lógica de aplicação e os serviços do domínio.

- **adapters**: Contém os adaptadores que conectam o domínio com o mundo exterior.
  - **inbound**: Adaptadores de entrada (driven) responsáveis por receber dados externos e encaminhá-los para o domínio.
  - **outbound**: Adaptadores de saída (driving) responsáveis por enviar dados processados pelo domínio para sistemas
    externos.
  - **mappers**: Contém os mapeamentos entre os objetos do domínio e os objetos dos adaptadores.

- **config**: Contém arquivos de configuração da aplicação.

- **utils**: Contém utilitários e funções auxiliares que podem ser compartilhados em toda a aplicação.

![hexagonal_architecture](https://github.com/murilohenzo/mono-to-micro/assets/28688721/467e9210-2584-4204-96e0-f4d8a36e9e78)

### Inversão de Dependência entre Domain Ports e Outbound Adapters

Na camada de adapters (outbound), aplicamos o princípio da **Inversão de Dependência** entre as ports definidas no
domínio e as implementações correspondentes nos adaptadores. Essa prática é essencial para garantir a flexibilidade e a
modularidade do sistema.

A Inversão de Dependência ocorre da seguinte forma:

1. **Definição de Ports no Domínio**:

- No domínio, estabelecemos interfaces (ports) que descrevem as operações ou serviços necessários para interagir com
  sistemas externos, como persistência de dados em bancos de dados, envio de notificações por e-mail, integração com
  APIs externas, entre outros. Essas interfaces representam os contratos que os adaptadores de saída devem seguir, mas
  não especificam detalhes de implementação.

2. **Implementação nos Adaptadores de Saída**:

- Na camada de adaptadores de saída, criamos implementações concretas para as interfaces definidas no domínio. Essas
  implementações são responsáveis por realizar as operações específicas necessárias para interagir com os sistemas
  externos, como realizar consultas e atualizações em um banco de dados, enviar e-mails, ou fazer chamadas para APIs
  externas.

3. **Dependência Invertida**:

- A Inversão de Dependência ocorre quando o domínio depende das interfaces (ports) definidas nele mesmo, em vez de
  depender diretamente das implementações concretas nos adaptadores de saída. Isso significa que o domínio não precisa
  conhecer os detalhes específicos de implementação dos adaptadores de saída, promovendo assim a separação de
  preocupações e reduzindo o acoplamento entre as diferentes partes do sistema.

Essa prática permite que diferentes implementações de adaptadores de saída possam ser facilmente substituídas, sem a
necessidade de alterar o código no domínio. Além disso, facilita a realização de testes e promove uma maior
flexibilidade e adaptabilidade do sistema às mudanças nos requisitos ou nas tecnologias utilizadas.
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
