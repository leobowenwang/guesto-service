//todo felder required setzen - validierung

<template>
  <v-alert v-if="success && showAlert" type="success">Speichern erfolgreich!</v-alert>
  <v-alert v-if="failed && showAlert" type="error">Speichern fehlgeschlagen!</v-alert>
  <v-alert v-if="deleteSuccess && showAlert" type="success">Löschen erfolgreich!</v-alert>
  <v-alert v-if="deleteFailed && showAlert" type="error">Löschen fehlgeschlagen!</v-alert>
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
      <template v-slot:item="{ item }">
        <tr>
          <td>{{ item.eventName }}</td>
          <td>{{ item.eventTimeDisplay }}</td>
          <td>{{ item.location }}</td>
          <td>{{ item.checkedInGuestsCount }}</td>
          <td>{{ item.totalGuestCount }}</td>
          <td>
            <v-icon
                size="small"
                class="me-2"
                @click="editEvent(item)"
                color="rgb(72, 237, 221)"
            >
              mdi-pencil
            </v-icon>
            <v-icon
                size="small"
                @click="deleteItem(item)"
                color="rgb(200, 35, 51)"
            >
              mdi-delete
            </v-icon>
          </td>
        </tr>
      </template>
    </v-data-table>
    <v-btn class="text-none mb-4 create-btn" color="#48EDDD" @click="createEvent()">Erstellen</v-btn>
  </v-container>
  <v-container v-if="!!selectedEvent">
    <v-form @submit.prevent="submitForm">
      <v-text-field type="text" id="eventName" v-model="formData.eventName" required label="Event Name"></v-text-field>
      <v-text-field type="datetime-local" id="eventTime" v-model="formData.eventTime" required label="Event Zeit"></v-text-field>
      <v-text-field type="number" id="maxGuestList" v-model="formData.maxGuestList" required label="Max Anzahl Gäste"></v-text-field>
      <v-text-field type="number" id="price" v-model="formData.price" required label="Preis"></v-text-field>
      <v-text-field type="text" id="location" v-model="formData.location" required label="Location"></v-text-field>
      <div v-if="formData.id">
        <guest-view :eventId="formData.id"></guest-view>
        <assign-view :eventId="formData.id"></assign-view>
      </div>
      <div style="clear: both"></div>
      <div>
        <v-btn type="submit" class="text-none mb-4 right-btn" color="#28a745">Speichern</v-btn>
        <v-btn class="text-none mb-4 left-btn" color="#ffc107" @click="cancelForm()">Abbrechen</v-btn>
      </div>
    </v-form>
  </v-container>
</template>
<script>
import authHeader from '../auth/auth-header';
import GuestView from "@/views/GuestView";
import AssignView from "@/views/AssignView";
import store from '../auth/store';
const BASE_URL='http://localhost:8080/event';

export default {
  data() {
    return {
      events: [],
      headers: [
        { title: 'Event Name', value: 'eventName' },
        { title: 'Event Time', value: 'eventTime' },
        { title: 'Location', value: 'location' },
        { title: 'Anzahl eingecheckte Gäste', value: 'checkedInGuestsCount' },
        { title: 'Anzahl Gäste', value: 'totalGuestCount' },
        { title: 'Aktionen', key: 'actions' },
      ],
      itemsPerPage: 5, // Anzahl der Elemente pro Seite
      totalEvents: 0,
      loading: false,
      selectedEvent: false,
      formData: {
        eventName: '',
        eventTime: null,
        maxGuestList: 0,
        price: 0,
        location: ''
      },
      success: false,
      failed: false,
      deleteSuccess: false,
      deleteFailed: false,
      showAlert: false,
      guestDialogVisible: false,
      guestData: {
        firstName: '',
        lastName: '',
        additionalGuests: 0,
        comment: '',
        customPrice: 0
      },
    }
  },
  name: 'EventsPage',
  components: {
    AssignView,
    GuestView
  },
  methods: {
    fetchData() {
      this.loading = true;
      this.$axios.get(BASE_URL, {
        params: {
          _page: this.$route.query.page || 1,
          _limit: this.itemsPerPage,
          sortBy: 'eventName',
          order: 'asc'
        },
        headers: authHeader()
      }).then(response => {
            this.events = response.data;
            this.totalEvents = Number(response.headers['x-total-count']);
            this.events.forEach(o => {
              o.eventTimeDisplay = this.formatDate(o.eventTime);
              const dateTime = new Date(o.eventTime);
              const localTime = new Date(dateTime.getTime() - dateTime.getTimezoneOffset() * 60000);
              o.eventTime = localTime.toISOString().slice(0, 16);
            });
            this.events.actions = '';
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
      this.selectedEvent = true;
    },
    editEvent(item) {
      this.selectedEvent = true;
      this.formData = {...this.events.find(o => o.id === item.id)};
    },
    async deleteItem(item) {
      const userConfirmed = window.confirm("Sind Sie sicher, dass Sie dieses Event löschen möchten?");

      if (!userConfirmed) {
        return;
      }
      this.deleteSuccess = false;
      this.deleteFailed = false;
      try {
        console.log("Löschen " + item.id);
        let response = await this.$axios.delete(BASE_URL + '/' + item.id, {
          params: {},
          headers: authHeader()
        });
        if (response) {
          this.deleteSuccess = true;
          this.showAlert = true;
          setTimeout(() => {
            this.showAlert = false;
          },2000);
          this.fetchData();
        }
      } catch (error) {
        this.deleteFailed = true;
        this.showAlert = true;
        setTimeout(() => {
          this.showAlert = false;
        },2000);
      }
    },
    cancelForm() {
      this.selectedEvent = false;
      this.formData = {
        eventName: '',
        eventTime: new Date,
        maxGuestList: 0,
        price: 0,
        location: ''
      };
    },
    async submitForm() {
      this.success = false;
      this.failed = false;
      try {
        let response;
        if (this.formData.id) {
          console.log("UMÄNDERN");
          response = await this.$axios.put(BASE_URL + '/' + this.formData.id, this.formData, {
            params: {},
            headers: authHeader()
          });
        } else {
          console.log("NEEEU");
          response = await this.$axios.post(BASE_URL, this.formData, {
            params: {},
            headers: authHeader()
          });
        }
        if (response) {
          this.success = true;
          this.showAlert = true;
          setTimeout(() => {
            this.showAlert = false;
          },2000);
          this.cancelForm();
          this.fetchData();
        }
      } catch (error) {
        this.failed = true;
        this.showAlert = true;
        setTimeout(() => {
          this.showAlert = false;
        },2000);
      }
    },
  },
  watch: {
    '$route.query.page'() {
      this.fetchData();
    },
  },
  created() {
    console.log(store.state.auth);
    this.fetchData();
  },
}
</script>
