@echo off

cd commons-version
call mvn clean install clean

cd ..\commons-spring
call mvn clean install clean

cd ..\commons-spring-boot-deps
call mvn clean install clean

cd ..\commons-spring-boot
call mvn clean install clean

cd ..\commons-spring-boot-web
call mvn clean install clean

cd ..\commons-spring-boot-autoconfigure
call mvn clean install clean

rem 类库
cd ..\commons-util
call mvn clean install clean

cd ..\commons-pinyin
call mvn clean install clean

cd ..\commons-transformer
call mvn clean install clean

cd ..\commons-mybatis-plus-starter
call mvn clean install clean

cd ..\commons-webmvc-starter
call mvn clean install clean

cd ..\commons-sms-verifycode-starter
call mvn clean install clean

cd ..\commons-security-starter
call mvn clean install clean

cd ..

@pause