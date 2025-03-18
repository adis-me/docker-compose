<script setup lang="ts">
import BaseLayout from '@/layouts/BaseLayout.vue';
import FormField from '@/components/forms/FormField.vue';
import { ref } from 'vue';
import { authenticate } from '@/api/auth-api';

const router = useRouter();

let username = ref('adis@live.nl');
let password = ref('secret');
let error = ref('');

function signIn() {
  authenticate(username.value, password.value)
    .then(() => {
      router.push({name: 'dashboard'})
      console.info('Ready to forward to dashboard')
    })
    .catch(reason => {
      password.value = '';
      error.value = "An error occurred. Please try again."
      console.log('failed to sign in: %s', reason)
    })
}

</script>
<template>
  <BaseLayout>
    <div class="flex min-h-full flex-col justify-center py-12 sm:px-6 lg:px-8">
      <div class="sm:mx-auto sm:w-full sm:max-w-md">
        <router-link :to="{ name: 'home'}" title="Welkom bij DemoApp.nl">
          <img class="mx-auto h-12 w-auto drop-shadow-xl " src="/logo.svg" alt="DemoApp" />
        </router-link>
        <h2 class="mt-6 text-center text-3xl font-bold tracking-tight text-gray-900">
          Welkom bij DemoApp.nl
        </h2>
        <p class="mt-2 text-center text-sm text-gray-600">
          Login of
          <router-link
            :to="{ name: 'sign-up'}"
            title="Welkom bij DemoApp.nl"
            class="font-medium text-blue-600 hover:text-blue-500">
            registreer
          </router-link>
          voor jouw gratis proefperiode
        </p>
      </div>

      <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
          <form class="space-y-6" @submit.prevent="signIn">
            <FormField label="Email" label-for="email">
              <input id="email" name="email" type="email" autocomplete="email" required=""
                     class="block w-full appearance-none rounded-md border border-gray-300
                     px-3 py-2 placeholder-gray-400 shadow-sm focus:border-blue-500
                     focus:outline-none focus:ring-blue-500 sm:text-sm" v-model="username"
              />
            </FormField>
            <FormField label="Password" label-for="password">
              <input id="password" name="password" type="password" autocomplete="current-password" required=""
                     class="block w-full appearance-none rounded-md border border-gray-300 px-3 py-2
                     placeholder-gray-400 shadow-sm focus:border-blue-500 focus:outline-none
                     focus:ring-blue-500 sm:text-sm" v-model="password"
              />
            </FormField>

            <div class="flex">
              <div class="text-sm">
                <a href="/forgot-password" class="font-medium text-blue-600 hover:text-blue-500">Wachtwoord vergeten?</a>
              </div>
            </div>

            <div v-if="error" class="flex bg-red-100 p-1.5 rounded">
              <div class="text-sm text-red-500">
                <p><strong>Whoops:</strong> {{ error }}</p>
              </div>
            </div>

            <div class="flex justify-end">
              <button type="submit" class="inline-flex rounded-md border border-transparent
                bg-blue-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-blue-700
                focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2">
                Login
              </button>
              <router-link :to="{ name: 'home'}" class="inline-flex ml-3 rounded-md border border-transparent
                bg-blue-200 py-2 px-4 text-sm font-medium text-gray-800 shadow-sm hover:bg-blue-200
                focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2">
                Annuleren
              </router-link>
            </div>
          </form>
        </div>
      </div>
    </div>
  </BaseLayout>
</template>
