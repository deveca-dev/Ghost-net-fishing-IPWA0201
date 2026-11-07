# IPWA0201_Ghost_Net
Prototypische Webanwendung zur Erfassung und koordantion der Bergung von Geisternetzen im Modul IPWA02-01.
Bei Fragen kannst du dich ein Issue eröffnen.

## Verwendete Technologien
- Jakarta Faces (JSF)
- CDI mit Weld
- JPa mit Hibernate
- PostgreSQL 18
- Apache Tomcat
- Docker Composer für die Datenbank

## Voraussetzungen für den Betrieb der Anwendung
- Java 25
- Maven
- Apache Tomcat 11
- Docker und Docker Compose

# Info
Die Anwendung wurde erstellt auf folgendem System:
- MacOS GoldenGate
- Java Version 8 Update 511 (Build 1.8.0_511-b33)

---

# Setup und Einrichtung
Hier erkläre ich dir kurz wie du die Anwendung bauen und starten kannst.

## Einrichten der Datenbank mit Docker
1. Kopiere als erstes die .env.example Datei und benenne sie in .env um.
```bash
cp .env.example .env
```
2. Starte danach die Datenbank mit Docker Compose
```bash
docker compose up -d
```
3. Wenn du den Status des Dockers prüfen möchtest, kannst du folgendes machen:
```
docker compose ps
```
Die hier erwartete ausgabe wäre: `healthy`
4. (optional) Stoppe die Datenbank mit Docker wenn du den Dienst nichtmehr benötigst. Aber ohne diesen Dienst funktioniert die Datenbank nicht.
```bash
docker compose down
```
## Danach die Datenbankmigrationen anwenden!
Wird das nicht durchgeführt, dann hat die Datenbank nicht das erforderliche Schema.
Datenbank-Migrationen ausführen:

1. Zuerst die initiale Datenbankstruktur anlegen:
```
docker compose exec -T postgres sh -c \
  'psql -X -U "$POSTGRES_USER" -d "$POSTGRES_DB" \
  -v ON_ERROR_STOP=1 --single-transaction -f -' \
  < db/migration/V001_initial_schema.sql
```
2. Danach die Kontaktdaten-Migration ausführen:
```
docker compose exec -T postgres sh -c \
  'psql -X -U "$POSTGRES_USER" -d "$POSTGRES_DB" \
  -v ON_ERROR_STOP=1 --single-transaction -f -' \
  < db/migration/V002_person_kontaktdaten.sql
```

Anwendung bauen
---
## Anwendung bauen

Da die Maven-Datei im Repository POM.XML heißt, muss sie explizit angegeben werden:
```bash
mvn -f POM.XML clean package
```
Die erzeugte WAR-Datei liegt anschließend im Verzeichnis target.

## Anwendung mit Tomcat starten

Vor dem Start von Tomcat müssen die Datenbankvariablen gesetzt werden:
```env
GHOSTNET_DB_URL="jdbc:postgresql://localhost:5432/ghostnet"
GHOSTNET_DB_USER="ghostnet"
GHOSTNET_DB_PASSWORD="ghostnet"
```
Danach die WAR-Datei in das webapps-Verzeichnis von Tomcat kopieren:
```bash
cp target/Fallstudie-IPWA02-0.0.1-SNAPSHOT.war "$CATALINA_HOME/webapps/ghostnet.war"
```
## Tomcat starten:
```bash
"$CATALINA_HOME/bin/catalina.sh" run
```

Die Anwendung ist anschließend unter folgender Adresse erreichbar:

# Datenbank-Migrationen
Um die Migration um laufenden Docker anzuwenden folgende Befehle ausführen:
1. Die erste Migration anwenden mit:
```bash
docker compose exec -T postgres sh -c \
  'psql -X -U "$POSTGRES_USER" -d "$POSTGRES_DB" \
  -v ON_ERROR_STOP=1 --single-transaction -f -' \
  < db/migration/V001_initial_schema.sql
```
2. Die zweite Migration anwenden mit:
```bash
docker compose exec -T postgres sh -c \
  'psql -X -U "$POSTGRES_USER" -d "$POSTGRES_DB" \
  -v ON_ERROR_STOP=1 --single-transaction -f -' \
  < db/migration/V001_initial_schema.sql
```

# Anwendung und Datenbank beenden

Tomcat im laufenden Terminal mit `Ctrl + C` beenden.

Die Datenbank stoppen:
```bash
docker compose down
```
Die Daten bleiben im Docker-Volume ghostnet-data erhalten.

