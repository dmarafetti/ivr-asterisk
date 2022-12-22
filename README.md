IVR - JAVA / ASTERISK Solution
==================================================
## System requirements

1. Java 1.8.0 or greater installed.
2. Maven 3.3.3 or greater.
3. Asterisk 11 or greater.
4. Verbio with valid licences.
5. An Unix environment


## Setup environment
We are assuming that the IVR will be installed within an [Ubuntu Server](http://releases.ubuntu.com/14.04.3/ubuntu-14.04.3-server-amd64.iso) 14.04.3 LTS.
There are some dependencies that must be installed before moving to the next step.
```sh
apt-get install openssh-server build-essential \
        git openssl libxml2-dev libncurses5-dev \
        uuid-dev sqlite3 libsqlite3-dev pkg-config \
        libjansson-dev
```

### Install Extra packages and Asterisk with Verbio App included
```sh
cd /usr/src
```

> Clone the IVR project
```sh
git clone https://github.com/dmarafetti/ivr-asterisk.git
cd ivr-asterisk/
```
```sh
dpkg -i ./Verbio/verbio-clients_9.02_amd64.deb
git clone -b 13 http://gerrit.asterisk.org/asterisk asterisk-13
cp Verbio/verbio-asterisk-4.2/app_verbio_speech.c asterisk-13/app/
vi asterisk-13/apps/Makefile 
```
> Before "all: _all" insert the next tree lines save and exit:
> MENUSELECT_DEPENDS_app_verbio_speech+=VOX
> VOX_LIB=-lvoxlib
> _ASTCFLAGS+=-DASTERISK_VERSION_NUM=130000
```sh
./configure
make    
make install 
make samples 
make config
```
> Create some folder: 
```sh
mkdir /var/lib/asterisk/verbio
mkdir /var/lib/asterisk/verbio/gram
mkdir /var/lib/asterisk/verbio/text
mkdir /var/lib/asterisk/verbio/audio
```
> Edit files.
```sh
vi /etc/asterisk/asterisk.conf
```
> Uncomment:
> ;defaultlanguage = en
> ;languageprefix = yes
> then,
> defaultlanguage = en
> languageprefix = yes
>
> Verbio config change with server parameters. To start copy verbio.conf file to /etc/asterisk
```sh
cp /usr/src/ivr-asterisk/Verbio/verbio-asterisk-4.2/verbio.conf /etc/asterisk
```

IVR
==================================================

This project requires Maven to be installed in your environment.

[Maven 3.3.3](http://maven.apache.org/download.cgi)

## Dependencies
This project is based on the free Java library for Asterisk PBX integration. Since the most updated version of this 
library doesn't exist on Maven's central repo we must installed locally first. By the time we wrote this file the `
2.0.0.CI-SNAPSHOT` was the last one.
```
git clone https://github.com/asterisk-java/asterisk-java.git
cd asterisk-java
mvn dependency:resolve
mvn clean install
```
----


## Java Project Setup
This is a Maven multi-module project. Hence, we have to install commons libraries before compiling the IVR solution.

```
cd ivr-asterisk/ivr-java-project
mvn dependency:resolve
mvn clean install
mvn eclipse:eclipse
```

### Eclipse IDE
We are guessing Eclipse is your environment by choice. However, any other ide is well accepted like IntelliJ for example.

1. Go to `package explorer`, right button and select `import `.
2. Select the *ivr-java-project* folder.
3. Three projects must appear: *ivr-commons*, *ivr-api* and *ivr-dialplan*. 

### Running
This project was built at the top of [Spring Boot](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-using-springbootapplication-annotation). 
Because of that, there are multiple ways of running the `ivr-dialplan` project.

###### Using Spring Boot
Within the dialplan project run the following command.
```
mvn clean compile spring-boot:run
```

###### Using Maven exec plugin
Within the dialplan project run the following command.
```
mvn clean compile exec:java
```
