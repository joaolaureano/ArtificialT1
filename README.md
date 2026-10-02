# ArtificialT1 — Genetic Algorithm for the Stable Matching Problem

🇧🇷 [Versão em português aqui](README.pt-BR.md)

## About the project

This repository contains a **Genetic Algorithm** implemented in Java
(Maven module `AlgoGenetico`) that solves a variant of the **stable
matching problem**: given two equally-sized groups (A and B), where each
member has a ranked preference list over members of the other group, the
algorithm searches for a pairing (one partner per person) that minimizes
overall "unhappiness", ideally converging to a solution where everyone
gets their most preferred available match.

This project was built as an assignment for a **college Artificial
Intelligence course** ("Trabalho 1"), for educational purposes. It is not
an actively maintained software product, but an academic exercise on
genetic algorithms (tournament selection, OX1 crossover, swap mutation,
and elitism).

### How the algorithm works, in short

- Each individual in the population is a chromosome — a permutation of
  size `SIZE`, where position `i` says which member of group B person
  `A_i` is paired with.
- An individual's **fitness** is the sum, for every pair, of the current
  partner's rank in each side's preference list (lower is better; `0` is
  the ideal solution).
- Every round: fitness is computed for the whole population, the best
  individual is kept (elitism), tournament selection + OX1 crossover
  produce the next generation, and mutation (swapping two genes inside a
  chromosome) is occasionally applied.
- The process repeats until a fitness-`0` solution is found or the
  configured number of rounds is exhausted.

### Input data

The algorithm reads a text file (`Configurations.NAME_FILE`) with the
format:

```
<SIZE>
<label> <group A preferences, one line per person>
...
<label> <group B preferences, one line per person>
...
```

A sample file with a known ideal solution was added at
[`AlgoGenetico/data/duplos6ideal.txt`](AlgoGenetico/data/duplos6ideal.txt)
— **this data file did not exist in the original version** (see the bugs
section below).

### How to run it

```bash
cd AlgoGenetico
mvn compile
mvn exec:java -Dexec.mainClass="myPkg.AlgoGenetico.src.Main"
```

This opens the Swing GUI, where you can configure the number of
generations/rounds, pick an input file, and run the algorithm. By
default, without selecting anything, it already runs against the sample
file bundled in the repository.

## Versions in this repository

- **[`V1`](../../tree/V1)** — exact copy of
  the state the assignment was originally submitted/developed in at
  college, with no changes at all. Preserved as a tag for history and
  to allow comparison against the fixed version.
- **Current version (this branch)** — same logic and goal as the
  original assignment, with the bugs listed below fixed.

## Bugs found and fixed in this version

While reviewing the code of the original version (`v1.0-original`), the
following issues were found:

1. **Hardcoded, author-machine-specific file path**
   (`Configurations.NAME_FILE` pointed to
   `/home/loreano/Desktop/.../duplos10ideal.txt`, a path that doesn't
   exist on any other machine, and the data file itself was not even
   included in the repository). This made the program fail on startup
   for anyone other than the original author.
   **Fix:** the default path is now relative (`data/duplos6ideal.txt`)
   and a valid sample file was added to the repository, so the project
   can run out of the box.

2. **Preferences file re-read on every single fitness calculation**
   (`Aptidao.aptidao()` called `new Decoder().read_preferences(...)`
   every time it computed the fitness contribution of **a single
   person**, instead of once per run). This re-opens and re-parses the
   file from disk `rounds × population × size` times — a serious
   performance problem (and a potential source of inconsistency if the
   file changed mid-run).
   **Fix:** preferences are now loaded once and cached; a new
   `Aptidao.reload()` method is called only when the user picks a new
   file from the GUI.

3. **Elitism broken by mutation** (`Mutacao.mutacao()` picked any index
   from `0` to `geracoes - 1` to mutate, including index `0`, which is
   where the previous generation's best individual is preserved via
   elitism in `Populacao.melhorIndividuo()`/`App.algoritmo()`). This
   meant the best solution found so far could be randomly corrupted by
   mutation, breaking the elitism guarantee.
   **Fix:** mutation now never picks index `0`.

4. **Off-by-one error when printing the final solution**
   (`Solucao.verificarSolucao()` looped up to
   `Populacao.getPopulacoes()[geracao].length`, which is `SIZE + 1`
   because the last chromosome slot stores the fitness value, not a
   partner). This made the program print an extra, meaningless line
   like `Pessoa A<SIZE> se juntou com B<fitness value>`.
   **Fix:** the loop now only goes up to `Configurations.SIZE`.

5. **Division-by-zero / `NUM_MUTACAO` equal to zero**
   (`Configurations.NUM_MUTACAO` was `SIZE / 2`; for small inputs
   (`SIZE` equal to `0` or `1`) this evaluated to `0`, and
   `Mutacao.mutacao()` calls `rand.nextInt(Configurations.NUM_MUTACAO)`,
   which throws `IllegalArgumentException` when the argument is `0`.
   **Fix:** `NUM_MUTACAO` now has a minimum of `1`
   (`Math.max(1, SIZE / 2)`).

These changes preserve the original genetic algorithm's logic — the
fixed version was validated by running the algorithm end-to-end, and it
correctly finds the ideal solution (fitness `0`) for the bundled sample
dataset.

## Repository structure

```
AlgoGenetico/
├── data/                     # (new) sample data to run the project
├── pom.xml
└── src/
    ├── main/java/myPkg/AlgoGenetico/src/
    │   ├── Main.java          # Swing GUI
    │   ├── App.java           # Main genetic algorithm loop
    │   ├── Populacao.java     # Population representation and init
    │   ├── Crossover.java     # Tournament selection + OX1 crossover
    │   ├── Mutacao.java       # Swap mutation
    │   ├── Aptidao.java       # Fitness calculation
    │   ├── Solucao.java       # Optimal solution check
    │   ├── Decoder.java       # Input file reader
    │   ├── Configurations.java# Algorithm parameters
    │   └── Log.java           # Execution log shown in the GUI
    └── test/java/AlgoGenetico/AppTest.java
```
