# ArtificialT1 — V1 (versão original)

**[Read this in English / Leia em inglês](README.md)**

Um **Algoritmo Genético** em Java (módulo Maven `AlgoGenetico`) para uma
variação do **problema dos casais estáveis**: dois grupos de mesmo tamanho (A
e B), cada membro com uma lista de preferências ordenada sobre o outro grupo,
e o algoritmo busca o pareamento que minimiza a "insatisfação" geral (aptidão
`0` é a solução ideal). Usa seleção por torneio, crossover OX1, mutação por
troca e elitismo, com uma interface Swing para configurar e executar.

Desenvolvido como "Trabalho 1" da disciplina de **Inteligência Artificial**
na faculdade.

> **Esta é a versão original arquivada**, exatamente como foi entregue. A
> única mudança sobre ela é este README. A versão corrigida está na branch
> [`master`](../../tree/master).

## Executando

Requer Java e Maven.

```bash
cd AlgoGenetico
mvn compile
mvn exec:java -Dexec.mainClass="myPkg.AlgoGenetico.src.Main"
```

## Problemas conhecidos desta versão

Foram encontrados depois e estão corrigidos na `master`:

1. `Configurations.NAME_FILE` aponta para um caminho na máquina do autor, e
   o arquivo de dados não está no repositório, então o programa falha ao
   iniciar em qualquer outra máquina.
2. O arquivo de preferências é relido do disco a cada cálculo de aptidão.
3. A mutação pode escolher o índice `0`, corrompendo o indivíduo preservado
   pelo elitismo.
4. A impressão da solução final tem um erro de limite e imprime uma linha a
   mais.
5. `NUM_MUTACAO` é `SIZE / 2`, que vale `0` para entradas muito pequenas e
   faz `Random.nextInt(0)` lançar exceção.
