import { defineStore } from 'pinia'

const versionString =
  import.meta.env.MODE === 'development'
    ? import.meta.env.VITE_APP_VERSION + '-dev'
    : import.meta.env.VITE_APP_VERSION

export const useStore = defineStore('main', {
  state: () => ({
    debug: import.meta.env.MODE === 'development',
    version: versionString,
    //
    email: null,
    tenantId: null,
    name: null,
    roles: [],
    client: {}
  }),

  actions: {
    initializeUser(account) {
      this.email = account.email;
      this.tenantId = account.client.tenant.id;
      this.name = account.name;
      this.roles = account.roles;
      this.client = account.client;
    },
  },

  getters: {
    user: (state) => {
      return {
        name: state.name,
        email: state.email
      }
    },
    getClient: (state) => {
      return state.client;
    }
  },
})
