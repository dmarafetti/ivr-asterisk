apt-get install openssh-server build-essential git openssl libxml2-dev libncurses5-dev uuid-dev sqlite3 libsqlite3-dev pkg-config libjansson-dev
cd /usr/src/
git clone https://github.com/dmarafetti/ivr-asterisk.git
cd ivr-asterisk
dpkg -i ./asterisk-13_VerbioApp/verbio-clients_9.02_amd64.deb
cd asterisk-13_VerbioApp
./comfigure
make && make install
mkdir -pa /var/lib/asterisk/verbio/text
mkdir -pa /var/lib/asterisk/verbio/gram
mkdir -pa /var/lib/asterisk/verbio/audio
