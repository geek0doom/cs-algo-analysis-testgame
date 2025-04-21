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
echo -h or -help - displays a help args, etc
echo -s - runs simulations eg. (minimax, random, greedy)
echo ##Syntax. 
echo java -cp %OUT% %MAIN_CLASS% -s (Algo1) (algo2) (runcount)
echo ##Example.
echo java -cp %OUT% %MAIN_CLASS% -s minimax random 100
echo.


endlocal
