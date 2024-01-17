//todo felder required setzen - validierung
//todo Gast hinzufügen button nur wenn das Event:
//1) von mir ist weil ich Admin bin (check)
//2)mir zugewiesen weil ich was anderes bin
<template>
  <v-alert v-if="success && showAlert" type="success">Speichern erfolgreich!</v-alert>
  <v-alert v-if="failed && showAlert" type="error">Speichern fehlgeschlagen!</v-alert>
  <v-alert v-if="deleteSuccess && showAlert" type="success">Löschen erfolgreich!</v-alert>
  <v-alert v-if="deleteFailed && showAlert" type="error">Löschen fehlgeschlagen!</v-alert>

  <v-expansion-panels style="margin-bottom: 20px">
    <v-expansion-panel>
      <v-expansion-panel-title>
        <v-row>
          <v-col cols="4" class="d-flex justify-start">
            Gäste
          </v-col>
        </v-row>
      </v-expansion-panel-title>
      <v-expansion-panel-text>
        <v-container>
          <v-data-table
              :items="guests"
              :headers="headers"
              :items-per-page="itemsPerPage"
              :server-items-length="totalGuests"
              item-key="firstName"
              :loading="loading"
              @update:page="onPageChange"
          >
            <template v-slot:item="{ item }">
              <tr>
                <td>{{ item.firstName + ' ' + item.lastName }}</td>
                <td>{{ item.additionalGuests }}</td>
                <td>
                  <v-icon
                      size="small"
                      class="me-2"
                      @click="checkInGuest(item)"
                      v-if="item.remainingCheckIns > 0"
                      color="green"
                  >
                    mdi-checkbox-marked
                  </v-icon>
                  <v-icon
                      size="small"
                      class="me-2"
                      @click="checkOutGuest(item)"
                      v-if="item.remainingCheckIns == 0"
                      color="rgb(200, 35, 51)"
                  >
                    mdi-arrow-right-box
                  </v-icon>
                  {{ item.remainingCheckIns }}
                </td>
                <td>{{ getCheckedInDisplayText(item.checkedIn) }}</td>
                <td>{{ geCustomPriceDisplayText(item.customPrice)}}</td>
                <td>{{ item.comment }}</td>
                <td>{{ item.addedByDisplayText }}</td>
                <td>
                  <v-icon
                      size="small"
                      class="me-2"
                      @click="editGuest(item)"
                      color="#2196F3"
                  >
                    mdi-pencil
                  </v-icon>
                  <v-icon
                      size="small"
                      @click="deleteGuest(item)"
                      color="rgb(200, 35, 51)"
                  >
                    mdi-delete
                  </v-icon>
                </td>
              </tr>
            </template>
          </v-data-table>
          <v-btn class="text-none mb-4 right-btn" color="#2196F3" @click="addGuest()">Gast hinzufügen</v-btn>
          <v-dialog v-model="guestDialogVisible" max-width="500">
            <v-card>
              <v-card-title>{{ guestData.id ? 'Gast bearbeiten': 'Gast hinzufügen'}}</v-card-title>
              <v-card-text>
                <v-form @submit.prevent>
                  <v-text-field type="text" id="firstName" v-model="guestData.firstName" required label="Vorname"></v-text-field>
                  <v-text-field type="text" id="lastName" v-model="guestData.lastName" required label="Nachname"></v-text-field>
                  <v-text-field type="number" id="additionalGuests" v-model="guestData.additionalGuests" required label="Anzahl Begleitung"></v-text-field>
                  <v-text-field type="text" id="comment" v-model="guestData.comment" required label="Kommentar"></v-text-field>
                  <v-text-field type="number" id="customPrice" v-model="guestData.customPrice" required label="Benutzerdefinierter Preis"></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn class="text-none mb-4" color="#757575" @click="closeDialog()">Abbrechen</v-btn>
                <v-btn type="submit" class="text-none mb-4" color="#2196F3" @click="saveGuest()">Speichern</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-container>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
<script>
import authHeader from '../auth/auth-header';
const BASE_URL='http://localhost:8080/event';
const USER_URL='http://localhost:8080/user/list';

export default {
  props: {
    eventId: {
      type: Number,
      required: true
    },
    editAllowed: {
      type : Boolean,
      required: true
    }
  },
  data() {
    return {
      guests: [],
      headers: [
        { title: 'Name', value: 'firstName' + 'lastName' },
        { title: 'Anzahl Begleitung', value: 'additionalGuests' },
        { title: 'Übrige Check-ins', key: 'remainingCheckIns' },
        { title: 'Eingecheckt', key: 'checkedIn' },
        { title: 'B.def. Preis', key: 'customPrice' },
        { title: 'Kommentar', key: 'comment' },
        { title: 'Hinzugefügt von', key: 'addedByDisplayText' },
        { title: 'Aktionen', key: 'actions' },
      ],
      itemsPerPage: 5, // Anzahl der Elemente pro Seite
      totalGuests: 0,
      loading: false,
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
      users: []
    }
  },
  name: 'GuestView',
  components: {
  },
  methods: {
    fetchData() {
      this.loading = true;
      this.$axios.get(BASE_URL + '/' + this.eventId + '/guest', {
        params: {
          _page: this.$route.query.page || 1,
          _limit: this.itemsPerPage,
        },
        headers: authHeader()
      }).then(response => {
            this.guests = response.data;
            console.log(this.guests);
            this.guests.forEach( o => o.addedByDisplayText = this.users.find( k => k.id === o.addedBy).username);
            this.totalGuests = Number(response.headers['x-total-count']);
            this.guests.actions = '';
            this.loading = false;
          })
          .catch(error => {
            console.error('Error fetching data:', error);
            this.loading = false;
          });
    },
    fetchUsers() {
      this.loading = true;
      this.$axios.get(USER_URL, {
        params: {},
        headers: authHeader()
      }).then(response => {
        this.users = response.data;
        this.loading = false;
      }).catch(error => {
        console.error('Error fetching data:', error);
        this.loading = false;
      });
    },
    onPageChange(page) {
      this.$router.push({ query: { page } });
      this.fetchData();
    },
    async deleteGuest(item) {
      const userConfirmed = window.confirm("Sind Sie sicher, dass Sie diesen Gast löschen möchten?");

      if (!userConfirmed) {
        return;
      }
      this.deleteSuccess = false;
      this.deleteFailed = false;
      try {
        console.log("Löschen " + item.id);
        let response = await this.$axios.delete(BASE_URL + '/' + this.eventId + '/guest/' + item.id, {
          params: {},
          headers: authHeader()
        });
        if (response) {
          console.log("DELETED");
          console.log(response);
          this.deleteSuccess = true;
          this.showAlert = true;
          setTimeout(() => {
            this.showAlert = false;
          },2000);
          this.fetchData();
        }
      } catch (error) {
        console.log("ERROR");
        console.log(error);
        this.deleteFailed = true;
        this.showAlert = true;
        setTimeout(() => {
          this.showAlert = false;
        },2000);
      }
    },
    addGuest() {
      this.openDialog();
    },
    editGuest(item) {
      this.guestDialogVisible = true;
      this.guestData = {...this.guests.find(o => o.id === item.id)};
    },
    getCheckedInDisplayText(text) {
      return text === false ? 'Nein' : 'Ja';
    },
    geCustomPriceDisplayText(text) {
      return text + "€";
    },
    openDialog() {
      this.guestDialogVisible = true;
    },
    closeDialog() {
      this.guestDialogVisible = false;
      this.guestData = {
        firstName: '',
        lastName: '',
        additionalGuests: 0,
        comment: '',
        customPrice: 0
      };
    },
    async saveGuest() {
      console.log(this.guestData);
      this.success = false;
      this.failed = false;
      try {
        let response;
        if (this.guestData.id) {
          console.log("UMÄNDERN");
          response = await this.$axios.put(BASE_URL + '/' + this.eventId + '/guest/' + this.guestData.id, this.guestData, {
            params: {},
            headers: authHeader()
          });
        } else {
          console.log("NEEEU");
          response = await this.$axios.post(BASE_URL + '/' + this.eventId + '/guest', this.guestData, {
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
          this.closeDialog();
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
    async checkOutGuest(item) {
      const userConfirmed = window.confirm("Sind Sie sicher, dass Sie diesen Gast auschecken möchten?");

      if (!userConfirmed) {
        return;
      }
      await this.checkInGuest(item);
    },
    async checkInGuest(item) {
      console.log(item);
      try {
        console.log("UMÄNDERN");
        let response = await this.$axios.put(BASE_URL + '/' + this.eventId + '/check-in/' + item.id, {},{
            params: {},
            headers: authHeader()
          });
        if (response) {
          this.success = true;
          this.showAlert = true;
          setTimeout(() => {
            this.showAlert = false;
          },2000);
          this.fetchData();
        }
      } catch (error) {
        this.failed = true;
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
    console.log(this.eventId);
    this.fetchUsers();
    this.fetchData();
  },
}
</script>
