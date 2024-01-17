<template>
  <v-alert v-if="registrationSuccess" type="success">Registrierung erfolgreich!</v-alert>
  <v-alert v-if="registrationFailed" type="error">Registrierung fehlgeschlagen!</v-alert>
  <div>
    <h2>Registrierung</h2>
    <v-form @submit.prevent>
      <v-text-field type="text" id="username" v-model="formData.username" required label="Benutzername"></v-text-field>
      <v-text-field type="password" id="password" v-model="formData.password" required label="Passwort"></v-text-field>
      <v-select
          v-model="formData.role"
          label="Rolle"
          :items="roleOptions"
          item-title="label"
          item-value="value"
      ></v-select>
      <v-btn class="text-none mb-4" color="#48EDDD" size="x-large" block @click="submitForm()">Registrieren</v-btn>
    </v-form>
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
      },
      roleOptions: [],
      registrationSuccess: false,
      registrationFailed: false
    };
  },
  methods: {
    async submitForm() {
      this.registrationSuccess = false;
      this.registrationFailed = false;
      try {
        const response = await this.$axios.post(BASE_URL, this.formData);
        if (response) {
          this.registrationSuccess = true;
        }
      } catch (error) {
        this.registrationFailed = true;
      }
    }
  },
  created() {
    this.roleOptions = [
      { value: 'ADMIN', label: 'Admin' },
      { value: 'STAFF', label: 'Mitarbeiter' },
      { value: 'CONTROLLER', label: 'Kontrolleur' }
    ];
  },
  name: 'RegistrationForm'
}
</script>
