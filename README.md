# QR-Scanner
---
## REST Server herunterladen
```bash
https://github.com/heusser4git/qr-scanner-server/tree/master/target/qrscanner-1.0-SNAPSHOT-jar-with-dependencies.jar
```
## REST Server starten (InMemoryRepository):
```bash
java -jar qrscanner-1.0-SNAPSHOT-jar-with-dependencies.jar --test=true
```
## REST Server mit MySQL-Datenbank betrieben:
```bash
Installation MariaDB von https://mariadb.org/download/
```
#### Datenbank erstellen mit:
```bash
CREATE DATABASE personal;
exit;
```
#### Speichern der SQL-Queries in ein lokales File von:
```bash
https://github.com/heusser4git/qr-scanner-server/blob/e729d219c7aed1643adf9046daf0299ce7411cd6/src/main/resources/META-INF/create_mysql_database.sql
```
#### Einrichten der Datenbankverbindung und Tabelle:
```bash
mysql -h localhost -uroot -p < <Pfad zum lokalen File mit den SQL-Queries>
```

## Server starten (SQLRepository):
```bash
java -jar qrscanner-1.0-SNAPSHOT-jar-with-dependencies.jar
```


# Client in Betrieb nehmen
siehe separate Instruktionen hier [https://github.com/heusser4git/qr-scanner-server/tree/master/client](https://github.com/heusser4git/qr-scanner-server/tree/master/client)