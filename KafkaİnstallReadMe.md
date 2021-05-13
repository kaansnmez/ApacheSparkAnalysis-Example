Apache Kafka Installition
I'll explain how to install Kafka on Ubuntu system.

Ubuntu System Upgrade
```Linux
sudo apt-get update
```
Install Java Environment
```Linux
sudo apt-get default-jre
```
Install Zookeeper
```Linux
sudo apt-get install zookeeper
```
"2181" Zookeeper port Check (Result:Listen) 
```Linux
netstat -ant |grep :2181
```
Status Check Zookeeper
```Linux
sudo systemctl zookeeper
```
İnside opt folder,Create kafka folder in opt folder
```Linux
cd /opt/
mkdir kafka
cd kafka
```
Go to Apache kafka download site, Select version and Copy scala 2.12 tgz file link 
```Linux
wget <kafka tgz file download link>
```
File Extraction
```Linux
sudo tar -xvf <fileName> 
```
Linux Profile file edit
```Linux
sudo nano /etc/profile
```
And add profile file
```Linux
export KAFKA_HOME="/opt/kafka/kafka_2.11-2.2.2"
export PATH ="$PATH:${KAFKA_HOME}/bin"
```
Open baschrc write this alias
```Linux
sudo nano ~/.bashrc
    alias sudo='sudo env PATH="$PATH"'
```
And System reboot
```Linux
sudo reboot
```
Check Kafka Home and Path, Result not Null
```Linux
echo $KAFKA_HOME
echo $PATH
```
Start Kafka
```Linux
sudo kafka-server-start.sh /etc/kafka.properties
```
Kafka properties Path
```Linux
sudo ln -s $KAFKA_HOME/config/server.properties /etc/kafka.properties
```
Start kafka as a service
```Linux
nohup kafka-server-start.sh /etc/kafka.properties &
```
Check nohup.out file has kafka started
```Linux
tail -f nohup.out
```
Check kafka service PS code
```Linux
ps -ef |grep kafka
```
Firewall turn on Enable
```Linux
sudo ufw enable
```
Add Kafka,Zookeeper port
```Linux
sudo ufw allow 2181
sudo ufw allow 9092
```
Check Port List
```Linux
sudo ufw status
```
Take Kafka proses code, and kill service
```Linux
ps -ef | kafka
kill -9 <proses code>
```
Go to kafka config file
```Linux
cd /opt/kafka/kafka_2.11-2.2.2/config
```
Edit Server.properties
```Linux
nano server.properties
```
And if have "#" in the top line, delete this character. Find this code and edit.
```Linux
  socket server settings> listeners = 0.0.0.0:9092
  advertised listeners= <Your Server İp>:9092
```
And Again start Kafka server.


