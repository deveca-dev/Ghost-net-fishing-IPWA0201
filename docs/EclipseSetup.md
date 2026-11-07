# Eclipse Setup
![Eclipse Logo](./images/eclipseLogo.png)
> Quelle und Lizenz: Eclipse Foundation - http://www.eclipse.org/artwork/, Gemeinfrei, https://commons.wikimedia.org/w/index.php?curid=73373554

# Eclipse herunterladen und installieren
1. Seite [https://www.eclipse.org/downloads/](https://www.eclipse.org/downloads/) besuchen
2. Eclipse IDE herunterladen und installieren
3. Tomcat Server herunterladen und in Eclipse importieren [https://tomcat.apache.org/download-11.cgi](https://tomcat.apache.org/download-11.cgi)
> Der Tomcat Server kann auch direkt in Eclipse installiert und heruntergeladen werden.

4. Nachdem Eclipse installiert ist, kann es geöffnet werden.
![Eclipse Startscreen](./images/image.png)
> Quelle: Eigene Darstellung, Bildschirmfoto von Eclipse IDE für Java EE Developers.

Prüfe unter Preferences → Java → Installed JREs, ob das JDK 25 eingerichtet und für das Projekt ausgewählt ist

5. Projekt importieren:
![Projekt importieren](./images/image%20copy.png)
> Quelle: Eigene Darstellung, Bildschirmfoto von Eclipse IDE für Java EE Developers.

6. Projekt GitHub Url eingeben, konfigurieren und importieren:
![Projekt GitHub Url eingeben](./images/image%20copy%202.png)
> Quelle: Eigene Darstellung, Bildschirmfoto von Eclipse IDE für Java EE Developers.

6. Projekt öffnen

7. Server konfigurieren und hinzufügen. Detailierte Anleitung in [Server konfigurieren und hinzufügen](./Tomcat%20Config.md)
Richte ggf. unter Preferences → Server → Runtime Environments Apache Tomcat 11 ein. Wähle dafür den Ordner deiner Tomcat-Installation aus.
Öffne die Ansicht Servers und füge einen neuen Tomcat-Server hinzu. Ordne dem Server anschließend das Projekt zu.

8. Prüfen ob Docker bereits läuft:
```bash
user@hostname IPWA0201_Ghost_Net % docker ps
CONTAINER ID   IMAGE         COMMAND                  CREATED      STATUS                    PORTS                      NAMES
a57c1fdef874   postgres:18   "docker-entrypoint.s…"   6 days ago   Up 10 seconds (healthy)   127.0.0.1:5432->5432/tcp   ghostnet-db
```

9. Projekt im Tomcat Server starten.
Damit die Anwendung auf die Datenbank zugreifen kann, müssen die folgenden Umgebungsvariablen in der Startkonfiguration von Tomcat gesetzt werden. Die Werte für Benutzer und Passwort müssen zu deiner .env-Datei passen:
```env
GHOSTNET_DB_URL=jdbc:postgresql://localhost:5432/ghostnet
GHOSTNET_DB_USER=ghostnet
GHOSTNET_DB_PASSWORD=ghostnet
```
Die Variablen können in Eclipse über Run → Run Configurations → Apache Tomcat → Environment eingetragen werden.

Starte zuerst die Datenbank und führe beide Migrationen aus, wie oben beschrieben. Danach kannst du den Tomcat-Server in der Ansicht Servers starten.

![Projekt im Tomcat Server starten](./images/image%20copy%204.png)
> Quelle: Eigene Darstellung, Bildschirmfoto von Eclipse IDE für Java EE Developers.

10. Projekt in Browser öffnen. Normalerweise öffnet sich der Browser automatisch. Wenn nicht, öffne den Browser und gebe die URL http://localhost:12074/Fallstudie-IPWA02/ oder http://localhost:12074/NAME_PROJEKT/ ein. Das kommt ganz auf deine Konfiguration an.
> Die Adresse der Anwendung hängt vom eingestellten Kontextpfad ab. Du kannst sie in Eclipse über Run As → Run on Server öffnen oder den Kontextpfad in der Serverkonfiguration nachsehen.
