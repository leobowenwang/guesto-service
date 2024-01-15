<template>
  <v-container>
    <v-data-table :items="events" :headers="headers">
      <template v-slot:items="props">
        <td>{{ props.item.eventName }}</td>
        <td>{{ formatDate(props.item.eventTime) }}</td>
        <td>{{ props.item.location }}</td>
      </template>
    </v-data-table>
  </v-container>
</template>
<script>
import authHeader from '../auth/auth-header';
const BASE_URL='http://localhost:8080/user/event';

export default {
  data() {
    return {
      events: [],
      headers: [
        { text: 'Event Name', value: 'eventName' },
        { text: 'Event Time', value: 'eventTime' },
        { text: 'Location', value: 'location' },
      ],
    }
  },
  name: 'EventsPage',
  components: {
  },
  methods: {
    fetchData() {
      console.log(authHeader());
      this.$axios.get(BASE_URL, {
        params: {},
        headers: authHeader()
      }).then(response => {
            this.events = response.data;
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },
  },
  created() {
    this.fetchData();
  },
}
</script>
