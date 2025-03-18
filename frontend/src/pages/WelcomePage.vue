<script setup lang="ts">
import MenuBar from '@/components/MenuBar.vue';
</script>

<template>
  <div class="isolate bg-white">
    <div class="px-6 pt-6 lg:px-8">
      <MenuBar/>
    </div>
    <main>
      <div class="relative px-6 lg:px-8">
        <div class="mx-auto max-w-3xl pt-20 pb-32 sm:pt-48 sm:pb-40">
          <div>
            <div>
              <h1 class="text-4xl font-bold tracking-tight sm:text-center sm:text-6xl">Slimmer werken?</h1>
              <p class="mt-6 text-lg leading-8 text-gray-600 sm:text-center">Het allerbeste product ooit.</p>
              <div class="mt-8 flex gap-x-4 sm:justify-center">
                <a href="#"
                   class="inline-block rounded-lg bg-indigo-600 px-4 py-1.5 text-base font-semibold leading-7 text-white shadow-sm ring-1 ring-indigo-600 hover:bg-indigo-700 hover:ring-indigo-700">
                  Begin nu
                  <span class="text-indigo-200" aria-hidden="true">&rarr;</span>
                </a>
                <a href="#"
                   class="inline-block rounded-lg px-4 py-1.5 text-base font-semibold leading-7 text-gray-900 ring-1 ring-gray-900/10 hover:ring-gray-900/20">
                  Liever uitproberen
                  <span class="text-gray-400" aria-hidden="true">&rarr;</span>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>
