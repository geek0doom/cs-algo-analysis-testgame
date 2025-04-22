#!/bin/bash

# Set directories and package
SRC="src"
OUT="out"
PACKAGE="tictactoe"
MAIN_CLASS="${PACKAGE}.Main"

# Ensure output directory exists
mkdir -p "$OUT"

echo "Compiling Java sources from $SRC/$PACKAGE..."
javac -d "$OUT" "$SRC/$PACKAGE/"*.java

if [ $? -ne 0 ]; then
    echo "❌ Compilation failed!"
    exit 1
fi

echo
echo "# Compilation complete"
echo
echo "## Arguments"
echo "-h or -help     : displays help, args, etc."
echo "-s              : runs simulations (minimax, random, greedy)"
echo
echo "## Syntax"
echo "java -cp $OUT $MAIN_CLASS -s <Algo1> <Algo2> <RunCount>"
echo
echo "## Example"
echo "java -cp $OUT $MAIN_CLASS -s minimax random 100"
echo "java -cp $OUT $MAIN_CLASS simulate   ; interactive run"

# Prompt user to run simulations
read -p "Run default simulations now? (500*3 runs for minimax (min,greed,rand,alphabeta,mcts)? [y/n]" answer
answer=${answer,,}  # lowercase

if [[ "$answer" == "y" ]]; then
    echo "Running simulations..."
    java -cp "$OUT" "$MAIN_CLASS" -s minimax random 500
    java -cp "$OUT" "$MAIN_CLASS" -s minimax greedy 500
    java -cp "$OUT" "$MAIN_CLASS" -s minimax minimax 500
	java -cp "$OUT" "$MAIN_CLASS" -s minimax alphabeta 500
	java -cp "$OUT" "$MAIN_CLASS" -s minimax mcts 500
else
    echo "Skipped simulation runs."
fi
