<template>
  <v-alert v-if="loginSuccess" type="success">Login erfolgreich!</v-alert>
  <v-alert v-if="loginFailed" type="error">Login fehlgeschlagen!</v-alert>
  <div>
    <h2>Login</h2>
    <v-form @submit.prevent>
      <v-text-field type="text" id="username" v-model="formData.username" required label="Benutzername"></v-text-field>
      <v-text-field type="password" id="password" v-model="formData.password" required label="Passwort"></v-text-field>
      <v-btn class="text-none mb-4" color="#48EDDD" size="x-large" block @click="submitForm()">Anmelden</v-btn>
    </v-form>
  </div>
</template>

<script>
import store from '../auth/store';
const BASE_URL='http://localhost:8080/user/login';

export default {
  data() {
    return {
      formData: {
        username: '',
        password: ''
      },
      loginSuccess: false,
      loginFailed: false
    };
  },
  methods: {
    async submitForm() {
      this.loginSuccess = false;
      this.loginFailed = false;
      try {
        const response = await this.$axios.post(BASE_URL, this.formData);
        this.loginSuccess = true;
        if (response.status === 200) {
          store.state.auth.loggedIn = true
          store.commit('auth/setJWT', response.data.token)
          setTimeout(() => {
            this.$router.push('/events');
          }, 1000);
          console.log('Anmeldung erfolgreich:', response.data);
        }
      } catch (error) {
        this.loginFailed = true;
        console.error('Fehler bei der Anmeldung:', error);
      }
    }
  },
  created() {
  },
  name: 'LoginForm'
}
</script>
