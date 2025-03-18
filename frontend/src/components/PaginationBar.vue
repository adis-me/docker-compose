<script setup lang="ts">
import { ChevronLeftIcon, ChevronRightIcon } from '@heroicons/vue/20/solid'

const props = defineProps<{
  totalElements: number,
  totalPages: number,
  pageSize: number,
  pageNumber: number,
  collection: string,
}>();

const hasPrevious = computed(() => (props.pageNumber > 0))

function previousPage() {
  return {
    pageSize: props.pageSize,
    pageNumber: props.pageNumber - 1,
  }
}

const hasNext = computed(() => props.pageNumber < props.totalPages - 1)

function nextPage() {
  return {
    pageSize: props.pageSize,
    pageNumber: props.pageNumber + 1,
  }
}

</script>

<template>
  <div class="flex items-center justify-between border-t border-gray-200 bg-white px-4 py-3 sm:px-6">
    <div class="flex flex-1 justify-between sm:hidden">
      <a href="#"
         class="relative inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50">Previous</a>
      <a href="#"
         class="relative ml-3 inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50">Next</a>
    </div>
    <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
      <div>
        <p class="text-sm text-gray-700">
          Totaal
          <span class="font-medium">{{ totalElements }}</span>
          resultaten
        </p>
      </div>
      <div>
        <nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
          <router-link :to="{name: collection, query: previousPage() }"
                       v-if="hasPrevious"
                       class="relative inline-flex items-center rounded-l-md border border-gray-300 bg-white px-2 py-2 text-sm font-medium text-gray-500 hover:bg-gray-50 focus:z-20">
            <span class="sr-only">Previous</span>
            <ChevronLeftIcon class="h-5 w-5" aria-hidden="true" />
            Vorige
          </router-link>

          <router-link :to="{name: collection, query: nextPage() }"
                       v-if="hasNext"
                       class="relative inline-flex items-center rounded-r-md border border-gray-300 bg-white px-2 py-2 text-sm font-medium text-gray-500 hover:bg-gray-50 focus:z-20">
            <span class="sr-only">Next</span>
            Volgende
            <ChevronRightIcon class="h-5 w-5" aria-hidden="true" />
          </router-link>
        </nav>
      </div>
    </div>
  </div>
</template>
