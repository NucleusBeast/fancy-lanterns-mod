@echo off
rem Full pipeline:
rem   1. datagen for every Minecraft version
rem   2. build every version
rem   3. collect the jars in build\libs\<minecraft version>\fancy_lanterns-<mod version>.jar
rem Steps 1 and 2 are separate Gradle runs, so the jars contain the new generated resources.

cd /d "%~dp0"

call "%~dp0gradlew.bat" runDataAll || exit /b 1
call "%~dp0gradlew.bat" buildAndCollectAll || exit /b 1

echo.
echo Done. Jars are in build\libs\
