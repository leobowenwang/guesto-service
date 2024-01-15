import { createRouter, createWebHashHistory } from 'vue-router'
import RegistrationForm from '../views/RegistrationForm.vue'
import LoginForm from '../views/LoginForm.vue'
import EventsPage from '../views/EventsPage'
import UserPage from "@/views/UserPage";

const routes = [
  { path: '/register', component: RegistrationForm },
  { path: '/login', component: LoginForm },
  { path: '/events', component: EventsPage },
  { path: '/users', component: UserPage },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
