<template>
  <v-alert v-if="saveSuccess && showAlert" type="success">Speichern erfolgreich!</v-alert>
  <v-alert v-if="saveFailed && showAlert" type="error">Speichern fehlgeschlagen!</v-alert>
  <v-container v-if="!selectedEvent">
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
    <v-btn class="text-none mb-4 create-btn" color="#48EDDD" @click="createEvent()">Erstellen</v-btn>
  </v-container>
  <v-container v-if="!!selectedEvent">
    <v-form @submit.prevent>
      <v-text-field type="text" id="eventName" v-model="formData.eventName" required label="Event Name"></v-text-field>
      <v-text-field type="datetime-local" id="eventTime" v-model="formData.eventTime" required label="Event Zeit"></v-text-field>
      <v-text-field type="number" id="maxGuestList" v-model="formData.maxGuestList" required label="Max Anzahl Gäste"></v-text-field>
      <v-text-field type="number" id="price" v-model="formData.price" required label="Preis"></v-text-field>
      <v-text-field type="text" id="location" v-model="formData.location" required label="Location"></v-text-field>
      <div>
        <v-btn class="text-none mb-4 save-btn" color="#48EDDD" @click="submitForm()">Speichern</v-btn>
        <v-btn class="text-none mb-4 cancel-btn" color="#48EDDD" @click="cancelForm()">Abbrechen</v-btn>
      </div>
    </v-form>
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
      selectedEvent: false,
      formData: {
        eventName: '',
        eventTime: new Date,
        maxGuestList: 0,
        price: 0,
        location: ''
      },
      saveSuccess: false,
      saveFailed: false,
      showAlert: false
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
      this.$router.push({ query: { page } });
      this.fetchData();
    },
    createEvent() {
      console.log("WUHU");
      this.selectedEvent = true;
    },
    cancelForm() {
      this.selectedEvent = false;
      this.formData = {
        eventName: '',
        eventTime: new Date,
        maxGuestList: 0,
        price: 0,
        location: ''
      }
    },
    async submitForm() {
      this.saveSuccess = false;
      this.saveFailed = false;
      try {
        const response = await this.$axios.post(BASE_URL, this.formData, {
          params: {},
          headers: authHeader()
        });
        if (response) {
          this.saveSuccess = true;
          this.showAlert = true;
          setTimeout(() => {
            this.showAlert = false;
          },2000);
          this.cancelForm();
        }
      } catch (error) {
        this.saveFailed = true;
        this.showAlert = true;
        setTimeout(() => {
          this.showAlert = false;
        },2000);
      }
    }
  },
  watch: {
    '$route.query.page'() {
      this.fetchData();
    },
  },
  created() {
    this.fetchData();
  },
}
</script>
