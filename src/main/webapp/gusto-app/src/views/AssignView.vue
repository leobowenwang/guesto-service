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
              item-title="displayText"
              item-value="id"
          ></v-select>
          <div>
            <v-btn class="text-none mb-4 right-btn" color="#2196F3" @click="saveAssignedUsers()">Speichern</v-btn>
          </div>
        </v-container>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
<script>
import authHeader from '../auth/auth-header';
const BASE_URL= process.env.NODE_ENV === 'production' ? 'https://guesto.azurewebsites.net' : 'http://localhost:8080';

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
    assignedUserIds: {
      type : Array,
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
            this.allUsers.forEach(o => o.displayText = o.username + " (" + o.role + ")");
            this.assignedUsers = this.allUsers.filter(user => this.assignedUserIds.includes(user.id));
            this.loading = false;
          })
          .catch(error => {
            console.error('Error fetching data:', error);
            this.loading = false;
          });
    },
    async saveAssignedUsers() {
      for (let i = 0; i < this.assignedUserIds.length; i++) {
        this.data = { 'userId' : this.assignedUserIds[i]}
        try {
          await this.$axios.post(BASE_URL + '/event/' + this.eventId + '/unassign', this.data, {
            params: {},
            headers: authHeader()
          });
        } catch (error) {
          console.log(error);
          errorCatchedForId = this.assignedUsers[i];
        }
      }
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
    this.fetchData();
  },
}
</script>
