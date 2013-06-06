#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
@echo off
@setlocal enabledelayedexpansion


for /f %%i in ("%CD%${symbol_escape}%0") do (
    set SCRIPT_PATH=%%~dpi
)

for /f %%i in ("%SCRIPT_PATH%${symbol_escape}..") do (
    set PROJECT_PATH=%%~fi
)

set BEES_APP_ID=%1
set MAIN_CLASS=${package}.${className}

if [%BEES_APP_ID%] == [] (
    echo "Use : deploybees <APP_ID>"
    goto end
)

set ARTIFACT_ID=${artifactId}


for /f %%i in ('unzip -Z -1 %PROJECT_PATH%${symbol_escape}target${symbol_escape}%ARTIFACT_ID%-cloudbees.zip ^| grep jar') do (
    if [!BEES_CLASSPATH!] == [] (
        set BEES_CLASSPATH=%%i
    ) else (
        set BEES_CLASSPATH=!BEES_CLASSPATH!:%%i
    )
)

bees app:deploy -a %BEES_APP_ID% -t java -R java_version=1.7 -R class=%MAIN_CLASS% -R classpath=%BEES_CLASSPATH% %PROJECT_PATH%${symbol_escape}target${symbol_escape}%ARTIFACT_ID%-cloudbees.zip

:end
@endlocal