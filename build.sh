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
echo "✅ Compilation complete."
echo "💡 To run the program interactively:"
echo "java -cp $OUT $MAIN_CLASS"
echo
echo "💡 To run a simulation (e.g., minimax vs random, 100 games):"
echo "- Algorithms: Minimax, Greedy, and Random"
echo "java -cp $OUT $MAIN_CLASS simulate minimax random 100"
echo
echo "- The -h or -help displays help and commands with args required"
echo "java -cp $OUT $MAIN_CLASS -h"
echo
