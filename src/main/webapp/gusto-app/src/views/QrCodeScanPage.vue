<template>
  <v-container>
    <h1>QR Code Scan</h1>
    <div>
      <StreamBarcodeReader @decode="onDecode" @loaded="onLoaded"></StreamBarcodeReader>
    </div>
  </v-container>
</template>
<script>
import { StreamBarcodeReader } from "vue-barcode-reader";
const BASE_URL_PROD= 'https://guesto.azurewebsites.net/';
const BASE_URL_LOCAL='http://localhost:8080/';
export default {
  data() {
    return {
      link: null
    }
  },
  name: 'QrCodeScanPage',
  components: {
    StreamBarcodeReader,
  },
  methods: {
    onDecode(text) {
      console.log(`Decode text from QR code is ${text}`)
      if (text.startsWith(BASE_URL_PROD || BASE_URL_LOCAL)) {
        window.open(text, "_blank");
      } else {
        console.log("nope");
      }

    },
    onLoaded() {
      console.log(`Ready to start scanning barcodes`)
    },
  },
  watch: {
  },
  created() {
  },
}
</script>
