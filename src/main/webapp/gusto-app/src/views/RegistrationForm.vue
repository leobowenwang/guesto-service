<template>
  <div>
    <h2>Registrierung</h2>
    <form @submit.prevent="submitForm">
      <label for="username">Benutzername:</label>
      <input type="text" id="username" v-model="formData.username" required>

      <label for="password">Passwort:</label>
      <input type="password" id="password" v-model="formData.password" required>

      <label for="role">Rolle:</label>
      <select id="role" v-model="formData.role" required>
        <option value="admin">Admin</option>
        <option value="mitarbeiter">Mitarbeiter</option>
        <option value="kontrolleur">Kontrolleur</option>
      </select>

      <button type="submit">Registrieren</button>
    </form>
  </div>
</template>

<script>

const BASE_URL='http://localhost:8080/user/register';

export default {
  data() {
    return {
      formData: {
        username: '',
        password: '',
        role: 'ADMIN' // Default-Rolle
      }
    };
  },
  methods: {
    async submitForm() {
      try {
        // Hier fügst du die Logik für die API-Anfrage ein
        const response = await this.$axios.post(BASE_URL, this.formData);

        // Handle die API-Antwort nach Bedarf
        console.log('Registrierung erfolgreich:', response.data);
      } catch (error) {
        // Handle einen Fehler bei der API-Anfrage
        console.error('Fehler bei der Registrierung:', error);
      }
    }
  },
  name: 'RegistrationForm'
}
</script>
