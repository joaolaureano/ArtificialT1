# ArtificialT1 — V1 (original version)

**[Leia em português / Read this in Portuguese](README.pt-BR.md)**

A **Genetic Algorithm** in Java (Maven module `AlgoGenetico`) for a variant
of the **stable matching problem**: two equally-sized groups (A and B), each
member with a ranked preference list over the other group, and the algorithm
searches for the pairing that minimizes overall "unhappiness" (fitness `0`
is the ideal solution). It uses tournament selection, OX1 crossover, swap
mutation and elitism, with a Swing GUI to configure and run it.

Built as "Trabalho 1" for a college **Artificial Intelligence** course.

> **This is the archived original version**, exactly as it was submitted.
> The only change on top of it is this README. The fixed version lives on the
> [`master`](../../tree/master) branch.

## Running

Requires Java and Maven.

```bash
cd AlgoGenetico
mvn compile
mvn exec:java -Dexec.mainClass="myPkg.AlgoGenetico.src.Main"
```

## Known issues in this version

These were found later and are fixed on `master`:

1. `Configurations.NAME_FILE` points to a path on the author's machine, and
   the data file isn't in the repository, so the program fails on startup
   anywhere else.
2. The preferences file is re-read from disk for every single fitness
   calculation.
3. Mutation can pick index `0`, corrupting the individual kept by elitism.
4. Printing the final solution has an off-by-one and prints an extra line.
5. `NUM_MUTACAO` is `SIZE / 2`, which is `0` for tiny inputs and makes
   `Random.nextInt(0)` throw.
