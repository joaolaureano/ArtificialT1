# ArtificialT1 — Algoritmo Genético para o Problema dos Casais Estáveis

🇺🇸 [English version here](README.en.md)

## Sobre o projeto

Este repositório contém um **Algoritmo Genético** implementado em Java
(módulo Maven `AlgoGenetico`) para resolver uma variação do **problema do
casamento estável / casais estáveis**: dado dois grupos de mesmo tamanho
(A e B), cada membro possui uma lista de preferências sobre os membros do
outro grupo, e o algoritmo busca encontrar um pareamento (um casal por
pessoa) que minimize o "descontentamento" geral, idealmente chegando a uma
solução onde cada pessoa fica com sua opção mais desejada disponível.

Este projeto foi desenvolvido como **trabalho de uma disciplina de
Inteligência Artificial da faculdade** (Trabalho 1), com fins educacionais.
Ele não é um produto de software mantido ativamente, mas um exercício
acadêmico sobre algoritmos genéticos (seleção por torneio, crossover OX1,
mutação por troca de genes e elitismo).

### Como o algoritmo funciona, resumidamente

- Cada indivíduo da população é um cromossomo — uma permutação de tamanho
  `SIZE`, em que a posição `i` indica com qual pessoa do grupo B a pessoa
  `A_i` está pareada.
- A **aptidão** (fitness) de um indivíduo é a soma, para cada par, da
  posição do parceiro atual na lista de preferências de cada um dos dois
  lados (quanto menor, melhor; `0` é a solução ideal).
- A cada rodada: calcula-se a aptidão de toda a população, guarda-se o
  melhor indivíduo (elitismo), aplica-se seleção por torneio + crossover
  OX1 para gerar a próxima geração, e ocasionalmente aplica-se mutação
  (troca de posições dentro de um cromossomo).
- O processo se repete até encontrar uma solução com aptidão `0` ou até
  esgotar o número de rodadas configurado.

### Entrada de dados

O algoritmo lê um arquivo texto (`Configurations.NAME_FILE`) no formato:

```
<TAMANHO>
<rótulo> <preferências do grupo A, uma por linha>
...
<rótulo> <preferências do grupo B, uma por linha>
...
```

Um arquivo de exemplo, com uma solução ideal conhecida, foi adicionado em
[`AlgoGenetico/data/duplos6ideal.txt`](AlgoGenetico/data/duplos6ideal.txt)
— **esse arquivo de dados não existia na versão original** (veja a seção
de bugs abaixo).

### Como executar

```bash
cd AlgoGenetico
mvn compile
mvn exec:java -Dexec.mainClass="myPkg.AlgoGenetico.src.Main"
```

Isso abre a interface gráfica (Swing) onde é possível configurar o número
de gerações/rodadas, selecionar um arquivo de entrada e rodar o algoritmo.
Por padrão, sem selecionar nada, ele já roda usando o arquivo de exemplo
incluído no repositório.

## Versões deste repositório

- **[`v1.0-original`](../../tree/v1.0-original)** — cópia exata do estado
  em que o trabalho foi entregue/desenvolvido originalmente na faculdade,
  sem nenhuma alteração. Preservada como tag para fins de histórico e
  para permitir comparação com a versão corrigida.
- **Versão atual (este branch)** — mesma lógica e mesmo objetivo do
  trabalho original, porém com os bugs listados abaixo corrigidos.

## Bugs identificados e corrigidos nesta versão

Ao revisar o código da versão original (`v1.0-original`), foram
encontrados os seguintes problemas:

1. **Caminho de arquivo absoluto e específico da máquina do autor**
   (`Configurations.NAME_FILE` apontava para
   `/home/loreano/Desktop/.../duplos10ideal.txt`, um caminho que não
   existe em nenhuma outra máquina, e o arquivo de dados nem estava
   incluído no repositório). Isso fazia o programa falhar ao ser
   inicializado por qualquer pessoa que não fosse o autor original.
   **Correção:** o caminho padrão agora é relativo
   (`data/duplos6ideal.txt`) e um arquivo de exemplo válido foi
   adicionado ao repositório, permitindo executar o projeto do zero.

2. **Leitura do arquivo de preferências repetida em todo cálculo de
   aptidão** (`Aptidao.aptidao()` chamava `new Decoder().read_preferences(...)`
   toda vez que calculava a aptidão de **uma única pessoa**, em vez de uma
   vez por execução). Isso reabre e reprocessa o arquivo em disco
   `rodadas × população × tamanho` vezes, um problema sério de desempenho
   (e potencial fonte de inconsistência caso o arquivo mudasse no meio da
   execução). **Correção:** as preferências agora são carregadas uma vez
   e mantidas em cache; um novo método `Aptidao.reload()` é chamado
   apenas quando o usuário seleciona um novo arquivo pela interface.

3. **Elitismo quebrado pela mutação** (`Mutacao.mutacao()` sorteava
   qualquer índice de `0` a `geracoes - 1` para mutar, incluindo o índice
   `0`, que é onde o melhor indivíduo da geração anterior é preservado
   por elitismo em `Populacao.melhorIndividuo()`/`App.algoritmo()`). Ou
   seja, a melhor solução encontrada até então podia ser corrompida por
   uma mutação aleatória, perdendo a garantia de elitismo.
   **Correção:** a mutação agora nunca sorteia o índice `0`.

4. **Erro de "off-by-one" ao imprimir a solução final**
   (`Solucao.verificarSolucao()` iterava até
   `Populacao.getPopulacoes()[geracao].length`, que é `SIZE + 1` porque a
   última posição do cromossomo guarda o valor de aptidão, não um
   parceiro). Isso fazia o programa imprimir uma linha extra e sem
   sentido do tipo `Pessoa A<SIZE> se juntou com B<valor da aptidão>`.
   **Correção:** o laço agora vai apenas até `Configurations.SIZE`.

5. **Divisão por zero / `NUM_MUTACAO` igual a zero** (`Configurations.NUM_MUTACAO`
   era `SIZE / 2`; para entradas pequenas (`SIZE` igual a `0` ou `1`)
   isso resultava em `0`, e `Mutacao.mutacao()` chama
   `rand.nextInt(Configurations.NUM_MUTACAO)`, que lança
   `IllegalArgumentException` quando o argumento é `0`.
   **Correção:** `NUM_MUTACAO` agora tem um mínimo de `1`
   (`Math.max(1, SIZE / 2)`).

Esses ajustes preservam a lógica original do algoritmo genético — a
versão corrigida foi validada rodando o algoritmo de ponta a ponta,
encontrando corretamente a solução ideal (aptidão `0`) para o conjunto de
dados de exemplo incluído.

## Estrutura do repositório

```
AlgoGenetico/
├── data/                     # (novo) dados de exemplo para rodar o projeto
├── pom.xml
└── src/
    ├── main/java/myPkg/AlgoGenetico/src/
    │   ├── Main.java          # Interface gráfica (Swing)
    │   ├── App.java           # Laço principal do algoritmo genético
    │   ├── Populacao.java     # Representação e inicialização da população
    │   ├── Crossover.java     # Seleção por torneio + crossover OX1
    │   ├── Mutacao.java       # Mutação por troca de genes
    │   ├── Aptidao.java       # Cálculo de aptidão (fitness)
    │   ├── Solucao.java       # Verificação de solução ótima
    │   ├── Decoder.java       # Leitura do arquivo de entrada
    │   ├── Configurations.java# Parâmetros do algoritmo
    │   └── Log.java           # Log de execução exibido na interface
    └── test/java/AlgoGenetico/AppTest.java
```
