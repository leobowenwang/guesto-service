<template>
  <div class="home">
    <HelloWorld msg="Welcome to LoginForm"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'LoginForm',
  components: {
    HelloWorld
  },
  methods: {
    loginUser() {
      console.log("login!");
    }
  }
}
</script>
