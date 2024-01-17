import { createRouter, createWebHashHistory } from 'vue-router'
import RegistrationForm from '../views/RegistrationForm.vue'
import LoginForm from '../views/LoginForm.vue'
import EventPage from '../views/EventPage'
import UserPage from "@/views/UserPage";

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/register', component: RegistrationForm },
  { path: '/login', component: LoginForm },
  { path: '/events', component: EventPage },
  { path: '/users', component: UserPage },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
