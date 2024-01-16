<template>
  <nav>
    <router-link to="/login">Login</router-link> |
    <!--<router-link to="/register">Register</router-link> |-->
    <router-link to="/events">Events</router-link> |
    <router-link to="/users">Benutzer</router-link>
  </nav>
  <router-view/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #48EDDD;
}
</style>
