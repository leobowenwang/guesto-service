# Guesto-service App Deployment

## Deployment-Schritte:

### Frontend

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

### Backend

1. **Azure-Konto und Visual Studio Code:**

   - Sicherstellen, dass man ein Azure-Konto hat und Visual Studio Code installiert ist.
   - Die "Azure App Service"-Erweiterung von Microsoft in Visual Studio Code isnstallierenerden.
   - Der Backend-Code sollte in einem Projektordner in Visual Studio Code abgelegt werden.

2. **Azure einrichten:**

   - Das Azure-Abonnement kann über das Azure-Logo in der linken Seitenleiste von Visual Studio Code verknüpft werden.
   - Um eine Azure App Service-Instanz zu erstellen, sollte man das Azure-Logo verwenden und App-Namen, Ressourcengruppe und Laufzeit-Stack angeben
   - Die Deployment-Einstellungen können durch Auswahl des Ordners mit dem Backend-Code konfiguriert werden.

3. **Deployment durchführen:**
   - Das Deployment kann gestartet werden, indem man auf "Deploy" klickt. Der Backend-Code wird kompiliert und auf Azure hochgeladen.
   - Der Status des Deployments kann in Visual Studio Code überwacht werden, und man kann das bereitgestellte Backend testen, um sicherzustellen, dass es funktioniert.
  
4. **API Dokumentation - Swagger:**
   - https://guesto.azurewebsites.net/swagger-ui/index.html#/


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
   - ![image](https://github.com/leobowenwang/guesto-service/assets/82936664/128ff4b4-21c5-430b-ba31-3653942fbde1)

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
