<template>
  <v-alert v-if="success && showAlert" type="success">Check-in erfolgreich!</v-alert>
  <v-alert v-if="failed && showAlert" type="error">Check-in fehlgeschlagen!</v-alert>
  <v-alert v-if="notValid && showAlert" type="error">Check-in ungültig!</v-alert>
  <v-container>
    <h1>QR Code Scan</h1>
    <div>
      <StreamBarcodeReader @decode="onDecode" @loaded="onLoaded" v-if="showScanner"></StreamBarcodeReader>
      <v-btn class="text-none mb-4 create-btn left-btn" color="#2196F3" @click="cancel()">Abbrechen</v-btn>
    </div>
  </v-container>
</template>
<script>
import { StreamBarcodeReader } from "vue-barcode-reader";
import authHeader from '../auth/auth-header';
const BASE_URL= process.env.NODE_ENV === 'production' ? 'https://guesto.azurewebsites.net' : 'http://localhost:8080';
export default {
  data() {
    return {
      link: null,
      success: false,
      failed: false,
      showAlert: false,
      notValid: false,
      showScanner: true
    }
  },
  name: 'QrCodeScanPage',
  components: {
    StreamBarcodeReader,
  },
  methods: {
    onDecode(text) {
      console.log(`Decode text from QR code is ${text}`)
      this.resetAlert();
      if (text.startsWith('/event')) {
        console.log("valid");
        this.checkInGuest(text);
        this.showScanner = false;
      } else {
        console.log("not valid");
        this.notValid = true;
        this.showAlert = true;
        this.showScanner = false;
        setTimeout(() => {
          this.showAlert = false;
        },1000);
      }
    },
    cancel() {
      this.$router.push({ path: '/events'});
    },
    async checkInGuest(qrCodeText) {
      this.resetAlert();
      try {
        let response = await this.$axios.put(BASE_URL + qrCodeText, {},{
          params: {},
          headers: authHeader()
        });
        if (response) {
          console.log("success");
          this.success = true;
          this.showAlert = true;
          setTimeout(() => {
            this.showAlert = false;
            this.cancel();
          },500);
        }
      } catch (error) {
        console.log("failed");
        this.failed = true;
        this.showAlert = true;
        setTimeout(() => {
          this.showAlert = false;
          this.cancel();
        },500);
      }
    },
    resetAlert() {
      this.success = false;
      this.failed = false;
    },
  },
  watch: {
  },
  created() {
    this.showScanner = true;
  },
}
</script>
