@echo off
setlocal

:: Set directories and package
set SRC=src
set OUT=out
set PACKAGE=tictactoe
set MAIN_CLASS=%PACKAGE%.Main

:: Ensure output directory exists
if not exist %OUT% (
    mkdir %OUT%
)

echo Compiling Java sources from %SRC%\%PACKAGE%...
javac -d %OUT% %SRC%\%PACKAGE%\*.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    exit /b 1
)

echo.
echo ✅ Compilation complete.
echo 💡 To run the program interactively:
echo java -cp %OUT% %MAIN_CLASS%
echo.
echo 💡 To run a simulation (e.g., minimax vs random, 100 games):
echo - Algorithims: Minimax, Greedy, and Random
echo java -cp %OUT% %MAIN_CLASS% -s minimax random 100
echo  - The -h or -help displays help and commands with args required
echo java -cp %OUT% %MAIN_CLASS% -h 
echo.

endlocal
