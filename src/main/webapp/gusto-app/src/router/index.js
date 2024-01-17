import { createRouter, createWebHashHistory } from 'vue-router'
import LoginForm from '../views/LoginForm.vue'
import EventPage from '../views/EventPage'
import UserPage from "@/views/UserPage";
import store from '../auth/store';

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: LoginForm },
  {
    path: '/events',
    component: EventPage,
    meta: { requiresAuth: true }
  },
  {
    path: '/users',
    component: UserPage,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})


router.beforeEach((to, from, next) => {
  const requiresAuth = to.meta.requiresAuth;
  const requiresAdmin = to.meta.requiresAdmin;

  if (requiresAuth && !store.state.auth.loggedIn) {
    next('/login');
  } else if (requiresAdmin && store.state.auth.role !== 'ADMIN') {
    next('/login');
  } else {
    next();
  }
});
export default router
