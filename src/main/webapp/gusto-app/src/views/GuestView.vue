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
                <td>{{ item.createdTimeFormatted }}</td>
                <td>
                  <v-icon
                      size="small"
                      class="me-2"
                      @click="scanGuest(item)"
                      color="rgb(200, 35, 51)"
                  >
                    mdi-qrcode-scan
                  </v-icon>
                  <v-icon
                      size="small"
                      class="me-2"
                      @click="editGuest(item)"
                      color="#2196F3"
                      v-if="addedByMe(item) || isAdmin"
                  >
                    mdi-pencil
                  </v-icon>
                  <v-icon
                      size="small"
                      @click="deleteGuest(item)"
                      color="rgb(200, 35, 51)"
                      v-if="addedByMe(item)"
                  >
                    mdi-delete
                  </v-icon>
                </td>
              </tr>
            </template>
          </v-data-table>
          <v-btn class="text-none mb-4 right-btn" color="#2196F3" @click="addGuest()" v-if="editAllowed">Gast hinzufügen</v-btn>
          <v-dialog v-model="guestDialogVisible" max-width="500">
            <v-card>
              <v-card-title>{{ guestData.id ? 'Gast bearbeiten' : 'Gast hinzufügen' }}</v-card-title>
              <v-card-text>
                <v-form @submit.prevent="saveGuest" ref="guestForm">
                  <v-text-field type="text" id="firstName" v-model="guestData.firstName" :rules="[v => !!v || 'Bitte Vorname eingeben']" required label="Vorname"></v-text-field>
                  <v-text-field type="text" id="lastName" v-model="guestData.lastName" :rules="[v => !!v || 'Bitte Nachname eingeben']" required label="Nachname"></v-text-field>
                  <v-text-field type="number" id="additionalGuests" v-model="guestData.additionalGuests" :rules="[v => v === 0 || (!!v && v > 0) || 'Bitte eine gültige Anzahl an Begleitpersonen eingeben']" required label="Anzahl Begleitung"></v-text-field>
                  <v-text-field type="text" id="comment" v-model="guestData.comment" :rules="[v => !!v || 'Bitte Kommentar eingeben']" required label="Kommentar"></v-text-field>
                  <v-text-field type="number" id="customPrice" v-model="guestData.customPrice" :rules="[v => v === 0 || (!!v && v > 0) || 'Bitte einen gültigen Preis eingeben']" required label="Benutzerdefinierter Preis"></v-text-field>
                  <div v-if="guestData.id">
                    <v-text-field
                        type="text"
                        id="qrcode"
                        v-model="guestData.qrcode"
                        label="QR-Code"
                        readonly
                        hide-details
                    ></v-text-field>
                    <vue-qrcode
                        :value="guestData.qrcode"
                        level="H"
                        :size="150"
                        style="margin-top: 10px;"
                    ></vue-qrcode>
                  </div>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn class="text-none mb-4" color="#757575" @click="closeDialog()">Abbrechen</v-btn>
                <v-btn type="submit" class="text-none mb-4" color="#2196F3" @click="saveGuest()" v-if="saveGuestAllowed">Speichern</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-container>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>
import VueQrcode from '@chenfengyuan/vue-qrcode';
import authHeader from '../auth/auth-header';
import store from "@/auth/store";
const BASE_URL= process.env.NODE_ENV === 'production' ? 'https://guesto.azurewebsites.net/event' : 'http://localhost:8080/event';
const USER_URL= process.env.NODE_ENV === 'production' ? 'https://guesto.azurewebsites.net/user/list' : 'http://localhost:8080/user/list';

export default {
  props: {
    eventId: {
      type: Number,
      required: true
    },
    editAllowed: {
      type : Boolean,
      required: true
    },
    isAdmin: {
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
        { title: 'Erstellungsdatum', key: 'createdTimeFormatted' },
        { title: 'Aktionen', key: 'actions' },
      ],
      itemsPerPage: 5,
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
        customPrice: 0,
        qrcode: "google.at"
      },
      users: [],
      myId: null,
      myRole: null,
      saveGuestAllowed: false
    }
  },
  components: {
    VueQrcode,
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
        this.guests.forEach( o => {
          o.addedByDisplayText = this.users.find( k => k.id === o.addedBy).username;
          o.createdTimeFormatted = o.createdTime ? this.formatDate(o.createdTime) : '-';
        });
        this.totalGuests = Number(response.headers['x-total-count']);
        this.guests.actions = '';
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
    scanGuest() {
      this.$router.push("/scan");
    },
    async deleteGuest(item) {
      const userConfirmed = window.confirm("Sind Sie sicher, dass Sie diesen Gast löschen möchten?");

      if (!userConfirmed) {
        return;
      }
      this.resetAlert();
      try {
        let response = await this.$axios.delete(BASE_URL + '/' + this.eventId + '/guest/' + item.id, {
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
    addGuest() {
      this.openDialog();
    },
    editGuest(item) {
      this.saveGuestAllowed = item.addedBy === this.myId;
      this.guestDialogVisible = true;
      this.guestData = {...this.guests.find(o => o.id === item.id)};
      this.guestData.qrcode = '/event/' + this.eventId + '/check-in/' + this.guestData.id;
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
      this.resetAlert();
      const isFormValid = await this.$refs.guestForm.validate();
      if (isFormValid.valid) {
        try {
          let response;
          if (this.guestData.id) {
            response = await this.$axios.put(BASE_URL + '/' + this.eventId + '/guest/' + this.guestData.id, this.guestData, {
              params: {},
              headers: authHeader()
            });
          } else {
            // Hier wird der QR-Code-Link mit dem Check-in-Endpunkt generiert
            this.guestData.qrcode = BASE_URL + '/' + this.eventId + '/check-in/' + this.guestData.id;

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
      this.resetAlert();
      try {
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
    },
    resetAlert() {
      this.success = false;
      this.failed = false;
      this.deleteSuccess = false;
      this.deleteFailed = false;
    },
    addedByMe(item) {
      return item.addedBy === this.myId;
    }
  },
  watch: {
    '$route.query.page'() {
      this.fetchData();
    },
  },
  created() {
    this.myId = store.state.auth.id;
    this.myRole = store.state.auth.role;
    console.log(this.isAdmin);
    this.fetchUsers();
    this.fetchData();
  },
}
</script>
