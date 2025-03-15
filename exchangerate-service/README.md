
************************* MAVEN (сборщик) *************************
mvn --version
mvn -v
mvn clean
mvn package

*********************** BREW (менеджер пакетов) ***********************
brew --version (менеджер пакетов)
Вот список основных команд при работе с Homebrew:
$ brew install имя_пакета (установка пакета);
$ brew uninstall имя_пакета (удаление пакета);
$ brew search имя_пакета (поиск пакета в репозитории Homebrew);
$ brew list (список установленных пакетов);
$ brew upgrade (обновить установленные пакеты).

*********************** ZSH (командный терминал) ***********************
echo $SHELL (проверить какая стоит командная оболочка /bin/zsh)
zsh --version

*********************** VIM (редактор) ***********************
Для сохранения и выхода в vi нужно нажать клавишу Esc, ввести сочетание :wq и нажать Enter. Для выхода без сохранения 
нужно также нажать Esc, ввести сочетание :q! и нажать Enter.
Чтобы начать ввод, нужно перейти в режим редактирования с помощью клавиши I. Чтобы вернуться в режим просмотра, нажмите Esc.
Перемещение по тексту выполняется с клавиатуры:
перемещение в начало текста: Esc + дважды G
перемещение в конец текста: Shift + G
перемещение по строке горизонтально: стрелки ← →
перемещение по строкам вертикально: стрелки ↑ ↓
отображение номеров строк: Esc, введите :set number, нажмите Enter
перемещение по номерам строк: Esc, введите : номер строки, нажмите Enter
Для выхода с сохранением файла нажмите Esc и введите :wq!

*********************** KILL (убить процесс) ***********************
kill -9 PID (-9 - послать сигнал KILL процессу с PID)

*********************** DOCKER (контейнер) ***********************
https://www.docker.com/ (скачать docker)
- нужно запустить docker desktop 
docker --version  (версия docker)
  (1) запуск первого контейнера
docker run --name my_first_container busybox:latest (создание и запуск контейнера busybox) или 
docker container run --name my_first_container busybox:latest
- образ busybox локально не удалось найти и он был загружен с Docker Hub (реестр, который Docker подключается автоматически)
- image (образы состоят из слоев файлов и каталогов, которые предоставляют файловую систему для создания контейнера)
  (2) запуск второго контейнера
docker run -dit --name alpine alpine:latest
- ключ -d (запуск контейнера в фоновом режиме)
- ключ -i (запуск в интерактивном режиме)
- ключ -t (для выходных данных будет использован терминал, когда контейнер работает в интерактивном режиме)

docker ps --all || docker container ls -a (проверить статусы контейнеров)
docker ps (активные контейнеры)
docker run -dit --name my_container busybox:latest (запуск контейнера в фоновом режиме)
docker ps (проверить активные контейнеры)

Exited (состояние контейнера "завершил работу")

------------------- состояния контейнера -------------------
docker container ls -a (посмотреть все контейнеры)
docker container pause my_container (поставить контейнер на паузу)
docker container unpause my_container (возобновление работы контейнера my_container)
docker container stop my_container (остановка контейнера)
docker container start my_container (запуск контейнера)

------------------- создание контейнера -------------------
docker container create --name my_container2 alpine:latest

------------------- выполнение команд в контейнере -------------------
docker images (просмотр всех образов)

mihail@MacBook ~ % docker container ls -a
CONTAINER ID   IMAGE            COMMAND     CREATED          STATUS                     PORTS     NAMES
f06d62a04454   alpine:latest    "/bin/sh"   14 seconds ago   Up 14 seconds                        nervous_snyder
88466a8bf9df   alpine:latest    "/bin/sh"   13 minutes ago   Exited (0) 7 minutes ago             my_container2
105cf791f0af   alpine:latest    "/bin/sh"   6 hours ago      Created                              my_container_create
6959f7ae65fe   busybox:latest   "sh"        6 hours ago      Up 6 hours                           my_container
7a4f85ed9681   alpine:latest    "/bin/sh"   7 hours ago      Up 7 hours                           alpine
188fec2cd82d   busybox:latest   "sh"        8 hours ago      Exited (0) 8 hours ago               my_first_container

mihail@MacBook ~ % docker container exec -i nervous_snyder
"docker container exec" requires at least 2 arguments.
See 'docker container exec --help'.

Usage:  docker container exec [OPTIONS] CONTAINER COMMAND [ARG...]

Execute a command in a running container
mihail@MacBook ~ % docker container exec -i nervous_snyder sh (выполнить действия в контейнере)

->> ps
PID   USER     TIME  COMMAND
1 root      0:00 /bin/sh
7 root      0:00 sh
13 root      0:00 ps


->> ls -a
.
..........
->> exit
mihail@MacBook ~ %

------------------- создание java контейнера -------------------
docker container run -dit --name javacontainer openjdk:23 

------------------- создание img (образа) на основе описанного Dockerfile -------------------
1. необходимо перейти в директорию в которой описан Dokerfile ~/IdeaProjects/bramkov...
2. выполнить команду: docker build -t your-image-name . (docker build -t microservice-ex-img .) 
3. просмотреть созданный образ docker images
4. запуск контейнера: docker container run -p 8081:8081 microservice-ex-img
5. http://localhost:8081/api/v1/exchange/rate

*********************** ВВОД КОМАНД ***********************
* PATH — это переменная среды, используемая операционной системой для определения местоположения исполняемых файлов,
  которые можно запускать из любой директории.
* Символ $ означает, что команда выполняется от имени обычного пользователя.
* Символ # означает, что команда выполняется от имени суперпользователя (root).

option+N (~ знак тильда в английской раскладке клавиатуры)
clear (очистить командную строку)
touch deploy (создать файл deploy)
chmod +x deploy (добавить к файлу deploy права на исполнение)
ls -al deploy (просмотр прав)
vim deploy (редактор)
i (запустить режим редактирование, после того как был запущен редактор)
ESC (выйти из режима редактирования)
:wq (записать и выйти)
./deploy (запуск файла deploy)
printenv (список всех переменных сред)
env (переменные)

*********************** НЮАНСЫ ***********************
- для выполнения сборки проекта необходимо выполнить команду mvn clean package (из директории проекта ~/IdeaProject/bramkov...)
- в результате выполнения команды в системе может быть установлена старая версия java, которая не актуальна для нового spring boot
- так как spring boot 3.3.5 поддерживает java от 17 версии. Чтобы проверить какая версия java установлена нужно выполнить 
- команду echo $JAVA_HOME в случае если ниже чем поддерживает spring boot, тогда установить новую версию https://www.oracle.com/cis/java/technologies/downloads/#jdk23-mac
- JDK Development Kit 23.0.1 downloads (macOS) ARM64 DMG Installer
- проверить командой какой установлен терминал echo $SHELL и файл ~/.zsh_profile добавить export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home
  export PATH=$PATH:$JAVA_HOME/bin также добавить в файлы ~/.zshrc и ~/.zshenv
- !необходимо перезагрузить терминал 
- таким образом новая версия java 23 была добавлена в глобальную область видимости 
- проверить можно командой java -version

проблема с тем, что версия JAVA (java -version) по умолчанию установлена глобальная ниже чем применена в проекте в проекте
версия 23 JAVA а если выполнять команду mvn package, то maven будет смотреть на глобальную версию JAVA, которая ниже чем в проекте

- нюанс в том, что есть глобальные настройки maven так и локальные у проекта

* запуск spring-boot проекта
1. (можно указать полный путь) /Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home/bin/java -jar /Users/mihail/IdeaProjects/bramkov-microservice-exchangerate/target/bramkov-microservice-exchangerate-0.0.1-SNAPSHOT.jar
2. (так как java глобальна уже) java -jar ~/IdeaProject/bramkov-microservice-exchangerate/target/bramkov-microservice-exchangerate-0.0.1-SNAPSHOT.jar

*********************** Postgres DataBase ***********************
sudo -u postgres /Library/PostgreSQL/17/bin/psql
Password: sde9REBKA435k

mihail@MacBook bin % sudo -u postgres /Library/PostgreSQL/17/bin/psql
Password for user postgres:
psql (17.0)
Type "help" for help.

postgres=# SELECT version();
version
------------------------------------------------------------------------------------------------------------------
PostgreSQL 17.0 on x86_64-apple-darwin23.6.0, compiled by Apple clang version 16.0.0 (clang-1600.0.26.3), 64-bit
(1 row)

postgres=# SHOW server_version;
server_version
----------------
17.0
(1 row)

postgres=# exit