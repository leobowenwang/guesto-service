<template>
  <div class="events-page">
    <h1>Welcome to Events</h1>
    <div v-if="events.length === 0">
      No events available.
    </div>
    <div v-else>
      <ul>
        <div v-for="(event, index) in events" :key="index">{{ event }}</div>
      </ul>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      events: []
    }
  },
  name: 'EventsPage',
  components: {
  },
  methods: {
    getEvents() {
      console.log("events!");
      this.events = ["Event1", "Event2", "Event3"];
    }
  },
  mounted() {
    this.getEvents();
  }
}
</script>
