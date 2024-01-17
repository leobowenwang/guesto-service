# Guesto-service App Deployment

## Deployment-Schritte:

1. **GitHub Actions einrichten:**
   - GitHub Actions wurde für das Repository aktiviert.
   - Der Workflow ist so konfiguriert, dass bei jedem Push in den `main`-Branch automatisch bereitgestellt wird.

2. **GitHub Token einrichten:**
   - Das Token `secrets.TOKEN` wird für den Zugriff auf GitHub Pages verwendet.

3. **GitHub Pages-Bereitstellung:**
   - Der Workflow baut die Vue.js-App und veröffentlicht sie auf GitHub Pages.
   - Der Source Code enthält einen Workflow-Abschnitt, der die Node.js-Umgebung einrichtet, Abhängigkeiten installiert und die Vue.js-App baut.
  
4. **GitHub Pages-Zugriff:**
   - Mit dem Link [https://leobowenwang.github.io/guesto-service/](https://leobowenwang.github.io/guesto-service/) kann auf die App zugegriffen werden.

## Lokales Setup:

1. **Lokales Einrichten der Vue.js-App:**
   - Navigieren zum Verzeichnis `./src/main/webapp/gusto-app`.
   - Führe die folgenden Befehle aus:
     ```bash
     npm install
     npm run serve
     ```

## Konfiguration der Micronaut-Anwendung:

1. **Datenbankkonfiguration:**
   - Die Micronaut-Anwendung ist so konfiguriert, dass sie auf eine PostgreSQL-Datenbank zugreift.
   - Die Verbindungsparameter (URL, Benutzername, Passwort) befinden sich in der Datei `application.yml` unter `datasources`.
   - PostgreSQL-Datenbank mittels Intellij
     
   -  ![image](https://github.com/leobowenwang/guesto-service/assets/82936664/128ff4b4-21c5-430b-ba31-3653942fbde1)
  

  
2. **Lokales Starten der Micronaut-Anwendung:**
   - Stellen Sie sicher, dass die PostgreSQL-Datenbank erreichbar ist.
   - Starten Sie die Micronaut-Anwendung lokal, z.B. über die Befehlszeile oder Ihre bevorzugte IDE.


     - **Micronaut-Anwendung im Intellij starten:**
       - Starten Sie Ihre Micronaut-Anwendung über das Terminal in IntelliJ mit dem Befehl:
         ```bash
         ./gradlew run
         ```
       - Alternativ können Sie die Datei `Application.java` mit der rechten Maustaste anklicken und "Run" auswählen.
       - ![image](https://github.com/leobowenwang/guesto-service/assets/82936664/35232d92-94f3-41b7-bde6-177e099b1187)







