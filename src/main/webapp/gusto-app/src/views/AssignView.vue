//todo felder required setzen - validierung
<template>
  <v-alert v-if="success && showAlert" type="success">Speichern erfolgreich!</v-alert>
  <v-alert v-if="failed && showAlert" type="error">Speichern fehlgeschlagen!</v-alert>

  <v-expansion-panels style="margin-bottom: 20px">
    <v-expansion-panel>
      <v-expansion-panel-title>
        <v-row>
          <v-col cols="4" class="d-flex justify-start">
            Zugeordnete Benutzer
          </v-col>
        </v-row>
      </v-expansion-panel-title>
      <v-expansion-panel-text>
        <v-container>
          <v-select
              v-model="assignedUsers"
              :items="allUsers"
              chips
              label="Benutzer"
              multiple
              item-title="username"
              item-value="id"
          ></v-select>
          <div>
            <v-btn class="text-none mb-4 right-btn" color="#28a745" @click="saveAssignedUsers()">Speichern</v-btn>
          </div>
        </v-container>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
<script>
import authHeader from '../auth/auth-header';
const BASE_URL='http://localhost:8080';

export default {
  props: {
    eventId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      assignedUsers: [],
      allUsers: [],
      loading: false,
      success: false,
      failed: false,
    }
  },
  name: 'AssignView',
  components: {
  },
  methods: {
    fetchData() {
      this.loading = true;
      this.$axios.get(BASE_URL + '/user/list', {
        params: {},
        headers: authHeader()
      }).then(response => {
            this.allUsers = response.data;
            this.loading = false;
          })
          .catch(error => {
            console.error('Error fetching data:', error);
            this.loading = false;
          });
    },
    async saveAssignedUsers() {
      //todo zuerst alle reassignen
      //dann nochmal für alle in der Liste assignen

      console.log(this.assignedUsers);
      this.success = false;
      this.failed = false;
      let errorCatchedForId = false;
      for (let i = 0; i < this.assignedUsers.length; i++) {
        if (this.assignedUsers[i]) {
          this.data = { 'userId' : this.assignedUsers[i]}
          try {
            await this.$axios.post(BASE_URL + '/event/' + this.eventId + '/assign', this.data, {
              params: {},
              headers: authHeader()
            });
          } catch (error) {
            console.log(error);
            errorCatchedForId = this.assignedUsers[i];
          }
        }
      }
      if (errorCatchedForId) {
        console.log("errorCatchedForId " + errorCatchedForId);
        this.failed = true;
        this.showAlert = true;
        setTimeout(() => {
          this.showAlert = false;
        },2000);
      } else {
        this.success = true;
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
    this.fetchData();
  },
}
</script>
