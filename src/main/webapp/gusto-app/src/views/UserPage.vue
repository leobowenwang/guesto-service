<template>
  <v-container>
    <v-data-table :items="events" :headers="headers">
      <template v-slot:items="props">
        <td>{{ props.item.username }}</td>
        <td>{{ props.item.role }}</td>
      </template>
    </v-data-table>
  </v-container>
</template>
<script>
import authHeader from '../auth/auth-header';
const BASE_URL='http://localhost:8080/user/list';

export default {
  data() {
    return {
      events: [],
      headers: [
        { text: 'Benutzername', value: '' },
        { text: 'Rolle', value: 'role' },
      ],
    }
  },
  name: 'UsersPage',
  components: {
  },
  methods: {
    fetchData() {
      this.$axios.get(BASE_URL, {
        params: {},
        headers: authHeader()
      }).then(response => {
        this.events = response.data;
      })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    }
  },
  created() {
    this.fetchData();
  },
}
</script>
