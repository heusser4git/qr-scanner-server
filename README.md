#QR-Scanner REST Server

###Server herunterladen
https://github.com/heusser4git/qr-scanner-server/blob/e729d219c7aed1643adf9046daf0299ce7411cd6/target/qrscanner-1.0-SNAPSHOT-jar-with-dependencies.jar

##Server starten (InMemoryRepository):
java -jar qrscanner-1.0-SNAPSHOT-jar-with-dependencies.jar --test=true

##Server mit MySQL-Datenbank betrieben:
Installation MariaDB von https://mariadb.org/download/

####Datenbank erstellen mit:
CREATE DATABASE personal;
exit;

####Speichern der SQL-Queries in ein lokales File von:
https://github.com/heusser4git/qr-scanner-server/blob/e729d219c7aed1643adf9046daf0299ce7411cd6/src/main/resources/META-INF/create_mysql_database.sql

####Einrichten der Datenbankverbindung und Tabelle:
mysql -h localhost -uroot -p < <Pfad zum lokalen File mit den SQL-Queries>

##Server starten (SQLRepository):
java -jar qrscanner-1.0-SNAPSHOT-jar-with-dependencies.jar
