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
echo # Compilation complete.
echo.
echo ##Arguments
echo -h or -help - displays help, args, etc.
echo -s - runs simulations eg. (minimax, random, greedy)
echo simulate for interactive run
echo ##Syntax: 
echo java -cp %OUT% %MAIN_CLASS% -s (Algo1) (Algo2) (RunCount)
echo ##Example:
echo java -cp %OUT% %MAIN_CLASS% -s minimax random 100  
echo java -cp %OUT% %MAIN_CLASS% simulate  ;Interactive run
echo.

:: Prompt user
choice /M "Run default simulations now? (500*3 runs for minimax (min,greed,rand,alphabeta,mcts)?"

if errorlevel 2 goto skip
if errorlevel 1 (
    echo Running simulations...
    java -cp %OUT% %MAIN_CLASS% -s minimax random 500 
    java -cp %OUT% %MAIN_CLASS% -s minimax greedy 500
    java -cp %OUT% %MAIN_CLASS% -s minimax minimax 500
	java -cp %OUT% %MAIN_CLASS% -s minimax alphabeta 500
	java -cp %OUT% %MAIN_CLASS% -s minimax mcts 500
)

:skip
echo Done.
endlocal
