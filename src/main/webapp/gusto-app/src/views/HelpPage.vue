<template>
  <v-container>
    <h1>Benutzerdokumentation</h1>
    <h2>Benutzer Seite:</h2>
    <v-list>
      <v-list-item v-for="(item, index) in items" :key="index">
        <div>
          <v-list-item-content class="help">
            <v-list-item-title>
              <v-icon>mdi-chevron-right</v-icon>
              <strong>
                {{ item }}
              </strong>
            </v-list-item-title>
            <span v-if="index === 0">
              <p> - Ein neuer Benutzer kann nur von einem Administrator erstellt werden. </p>
              <p> - Der Administrator navigiert zur Benutzerseite und klickt auf "Erstellen". </p>
              <p> - Geben Sie einen Benutzernamen, ein Passwort und eine Rolle für den neuen Benutzer ein. </p>
              <p> - Klicken Sie auf "Erstellen", um den Benutzer zu erstellen. </p>
              <img src="@/assets/1.png" alt="Hilfe Bild" />
            </span>
            <span v-if="index === 1">
              <p> - Auf der Benutzerseite hat der Administrator eine Übersicht aller Benutzer. </p>
              <p> - Hier kann er grundlegende Informationen zu jedem Benutzer sehen. </p>
              <img src="@/assets/2.png" alt="Hilfe Bild" />
            </span>
            <span v-if="index === 2">
              <p> - Der Administrator kann einen Benutzer bearbeiten, indem er auf die entsprechende Option auf der Benutzerseite klickt. </p>
              <p> - Es können Änderungen am Benutzernamen und der Rolle vorgenommen werden. </p>
              <img src="@/assets/3.png" alt="Hilfe Bild" />
            </span>
            <span v-if="index === 3">
              <p> - Der Administrator hat die Möglichkeit, einen Benutzer vollständig zu löschen. </p>
              <p> - Dies geschieht durch Auswahl der Löschoption auf der Benutzerseite. </p>
              <img src="@/assets/4.png" alt="Hilfe Bild" />
            </span>
            <span v-if="index === 4">
              <p> - Stellen Sie sicher, dass die vergebenen Benutzernamen eindeutig sind. </p>
              <p> - Wählen Sie sichere Passwörter, um die Konten vor unbefugtem Zugriff zu schützen. </p>
              <p> - Überprüfen Sie regelmäßig die Benutzerliste, um sicherzustellen, dass alle Informationen aktuell sind. </p>
            </span>
            <span v-if="index === 5">
              <p> - Die Rolle gibt an, welche Rechte der Benutzer hat. Achten Sie darauf, die Rollen entsprechend den Aufgaben zuzuweisen. </p>
              <p> - Eine genauere Beschreibung finden Sie in <a :href="pdfUrl" download="Benutzerdokumentation.pdf">Benutzerdokumentation.pdf</a></p>
            </span>
          </v-list-item-content>
        </div>
      </v-list-item>
    </v-list>
  </v-container>
</template>
<script>

export default {
  data() {
    return {
      pdfUrl: process.env.NODE_ENV === 'production' ? '/guesto-service/files/Benutzerdokumentation.pdf' : '/files/Benutzerdokumentation.pdf',
      items: ['Registrierung:', 'Benutzerübersicht:', 'Benutzer bearbeiten:', 'Benutzer löschen:', 'Hinweise für den Administrator:', 'Hinweis:']
    }
  },
  name: 'HelpPage',
  components: {
  },
  methods: {
  },
  watch: {
  },
  created() {
  },
}
</script>
