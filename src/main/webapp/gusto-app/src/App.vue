<template>
  <v-app>
    <v-app-bar app color="#2196F3" dark v-if="loggedIn">
      <v-container>
        <v-row align="center" justify="space-between">
          <v-col>
            <router-link to="/events" class="nav-link">Events</router-link>
            <router-link to="/users" class="nav-link" v-if="isAdmin">Benutzer</router-link>
            <router-link to="/help" class="nav-link">Hilfe</router-link>
            <button @click="logout" class="button button--primary button_navigation nav-link right-btn">Logout</button>
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
import { computed } from "vue";
import store from './auth/store';
import router from './router';

let loggedIn = computed(() => store.state.auth.loggedIn);
let isAdmin = computed(() => store.state.auth.role === 'ADMIN');

function logout() {
  store.commit('auth/logout');
  router.push('/login');
}
</script>
