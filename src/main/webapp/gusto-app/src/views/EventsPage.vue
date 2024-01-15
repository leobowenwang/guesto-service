<template>
  <v-container>
    <v-data-table
        :items="events"
        :headers="headers"
        :items-per-page="itemsPerPage"
        :server-items-length="totalEvents"
        item-key="eventName"
        :loading="loading"
        @update:page="onPageChange"
        >
    </v-data-table>
  </v-container>
</template>
<script>
import authHeader from '../auth/auth-header';
const BASE_URL='http://localhost:8080/event';

export default {
  data() {
    return {
      events: [],
      headers: [
        { title: 'Event Name', value: 'eventName' },
        { title: 'Event Time', value: 'eventTime' },
        { title: 'Location', value: 'location' },
      ],
      itemsPerPage: 5, // Anzahl der Elemente pro Seite
      totalEvents: 0,
      loading: false,
    }
  },
  name: 'EventsPage',
  components: {
  },
  methods: {
    fetchData() {
      this.loading = true;
      this.$axios.get(BASE_URL, {
        params: {
          _page: this.$route.query.page || 1,
          _limit: this.itemsPerPage,
        },
        headers: authHeader()
      }).then(response => {
            this.events = response.data;
            this.totalEvents = Number(response.headers['x-total-count']);
            this.events.forEach(o => o.eventTime = this.formatDate(o.eventTime));
            this.loading = false;
          })
          .catch(error => {
            console.error('Error fetching data:', error);
            this.loading = false;
          });
    },
    formatDate(dateString) {
      if (!dateString) {
        return '';
      }
      const options = { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' };
      let formattedDate = new Date(dateString).toLocaleDateString('de-DE', options);
      formattedDate = formattedDate.replace(/[,]/g, ' ');
      return formattedDate;
    },
    onPageChange(page) {
      // Hier kann die aktuelle Seite in der URL aktualisiert werden
      this.$router.push({ query: { page } });
      this.fetchData();
    },
  },
  watch: {
    '$route.query.page'() {
      // Aktualisiere die Daten, wenn die Seite in der URL geändert wird
      this.fetchData();
    },
  },
  created() {
    this.fetchData();
  },
}
</script>
