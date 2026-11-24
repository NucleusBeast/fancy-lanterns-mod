@echo off
rem Publishes every Minecraft version to Modrinth.
rem   1. datagen for every Minecraft version
rem   2. build and upload every version
rem Test first with: publish_all.bat -PdryRun
rem The Modrinth token is read from modrinthToken in %USERPROFILE%\.gradle\gradle.properties.

cd /d "%~dp0"

call gradlew.bat runDataAll || exit /b 1
call gradlew.bat publishAll %* || exit /b 1

echo.
echo Done. Check https://modrinth.com/mod/fancy-lanterns/versions
