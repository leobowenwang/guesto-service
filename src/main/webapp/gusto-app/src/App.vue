<template>
  <v-app>
    <v-app-bar app color="#2196F3" dark>
      <v-container>
        <v-row align="center" justify="space-between">
          <v-col>
            <router-link to="/login" class="nav-link">Login</router-link>
            <!--<router-link to="/register" class="nav-link">Register</router-link>-->
            <router-link to="/events" class="nav-link">Events</router-link>
            <router-link to="/users" class="nav-link">Benutzer</router-link>
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


