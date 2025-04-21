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
echo *Compilation complete!
echo
echo **Arguments
echo -h or -help - displays a help args, etc
echo -s - runs simulations eg. (minimax, random, greedy)
echo **Syntax. 
echo java -cp %OUT% %MAIN_CLASS% -s <Algo1> <algo2> <runcount>
echo **Example.
echo java -cp %OUT% %MAIN_CLASS% -s minimax random 100
echo
