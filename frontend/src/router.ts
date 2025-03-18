import {createRouter, createWebHistory} from 'vue-router'

import WelcomePage from '@/pages/WelcomePage.vue';
import SignInPage from '@/pages/SignInPage.vue';
import SignUpPage from '@/pages/SignUpPage.vue';
import AboutPage from '@/pages/AboutPage.vue';
import DashboardPage from '@/pages/DashboardPage.vue';
import CustomersPage from '@/pages/CustomersPage.vue';
import CustomerFormPage from '@/pages/CustomerFormPage.vue';
import SettingsPage from '@/pages/SettingsPage.vue';

const routes = [
  {
    path: '/',
    component: WelcomePage,
    name: 'home',
    meta: {
      title: 'Welcome to DemoApp',
    },
  },
  {
    path: '/about',
    component: AboutPage,
    name: 'about',
  },
  {
    path: '/sign-in',
    name: 'sign-in',
    component: SignInPage,
    meta: {
      title: 'Welcome to DemoApp',
    },
  },
  {
    path: '/sign-up',
    name: 'sign-up',
    component: SignUpPage,
    meta: {
      title: 'Welcome to DemoApp',
    },
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: DashboardPage,
    meta: {
      title: 'Dashboard',
    },
  },
  {
    path: '/customers',
    name: 'customers',
    component: CustomersPage,
    meta: {
      title: 'Klanten',
    },
  },
  {
    path: '/customers/:id',
    name: 'customer-update',
    component: CustomerFormPage,
    meta: {
      title: 'Bewerk klant',
    },
    props: true,
  },
  {
    path: '/customers/create',
    name: 'customer-create',
    component: CustomerFormPage,
    meta: {
      title: 'Nieuwe klant toevoegen',
    },
  },
  {
    path: '/settings',
    name: 'settings',
    component: SettingsPage,
    meta: {
      title: 'Instellingen',
    },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
