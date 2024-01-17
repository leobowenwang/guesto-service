<template>
  <v-app>
    <v-app-bar app color="#2196F3" dark>
      <v-container>
        <v-row align="center" justify="space-between">
          <v-col>
            <router-link to="/login" class="nav-link">Login</router-link>
            <!--<router-link to="/register" class="nav-link">Register</router-link>-->
            <router-link to="/events" class="nav-link" v-if="loggedIn">Events</router-link>
            <router-link to="/users" class="nav-link" v-if="loggedIn">Benutzer</router-link>
            <button @click="logout" class="button button--primary button_navigation nav-link right-btn" v-if="loggedIn">Logout</button>
          </v-col>
        </v-row>
      </v-container>
    </v-app-bar>
    <v-main>
      <v-container>
        <router-view/>
      </v-container>
    </v-main>
  </v-app>
</template>
<script setup>
import { computed } from "vue"
import store from './auth/store';
import router from './router'
const loggedIn = computed(() => store.state.auth.loggedIn)
function logout() {
  store.commit('auth/logout');
  router.push('/login');
}
</script>

