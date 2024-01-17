<template>
  <v-container class="fill-height">
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card class="elevation-12 pa-8">
          <v-alert v-if="loginSuccess" type="success" icon="mdi-check-circle-outline" class="mb-4">Login erfolgreich!</v-alert>
          <v-alert v-if="loginFailed" type="error" icon="mdi-alert-circle-outline" class="mb-4">Login fehlgeschlagen!</v-alert>

          <h2 class="text-h2 mb-4">Login</h2>

          <v-form ref="form">
            <v-text-field type="text" id="username" v-model="formData.username" :rules="[rules.required]" label="Benutzername"></v-text-field>
            <v-text-field type="password" id="password" v-model="formData.password" :rules="[rules.required]" label="Passwort"></v-text-field>

            <v-btn color="#2196F3" dark block @click="validateAndSubmitForm">Anmelden</v-btn>

            <v-alert v-if="validationError" type="error" class="mt-4">{{ validationError }}</v-alert>
          </v-form>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import store from '../auth/store';

const BASE_URL = 'http://localhost:8080/user/login';

export default {
  data() {
    return {
      formData: {
        username: '',
        password: ''
      },
      loginSuccess: false,
      loginFailed: false,
      validationError: '',
      rules: {
        required: value => !!value || 'Dieses Feld ist erforderlich',
      },
    };
  },
  methods: {
    async validateAndSubmitForm() {
      this.loginSuccess = false;
      this.loginFailed = false;
      this.validationError = '';

      if (this.$refs.form.validate()) {
        this.submitForm();
      } else {
        this.validationError = 'Bitte füllen Sie alle erforderlichen Felder aus.';
      }
    },
    async submitForm() {
      this.loginSuccess = false;
      this.loginFailed = false;
      this.validationError = '';

      if (!this.formData.username || !this.formData.password) {
        this.validationError = 'Bitte füllen Sie alle erforderlichen Felder aus.';
        return;
      }

      try {
        const response = await this.$axios.post(BASE_URL, this.formData);
        this.loginSuccess = true;

        if (response.status === 200) {
          store.state.auth.loggedIn = true;
          store.state.auth.username = response.data.username;
          store.state.auth.role = response.data.role;
          store.commit('auth/setJWT', response.data.token);

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


