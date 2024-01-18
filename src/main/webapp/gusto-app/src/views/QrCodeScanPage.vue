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
      if (text.startsWith('/event')) {
        this.$router.push(text);
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
