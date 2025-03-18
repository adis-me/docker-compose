<script setup lang="ts">
import Authenticated from '@/layouts/AuthenticatedLayout.vue';
import { getOfferStats } from '@/api/dashboard-api';
import { getAllOffers } from '@/api/offers-api';
import { ChevronRightIcon, DocumentIcon, UserIcon, TagIcon } from '@heroicons/vue/24/outline';

const numberOfCustomers = ref(0);
const customers = ref([]);

const getTotalAmountIncludingVat = function (offer) {
  if (!(offer.lines) || offer.lines.length === 0) return 0;

  const amount = offer.lines
    .map(line => (line.amountExcludingVat | 0) + (line.vatAmount | 0))
    .reduce((accumulator, currentValue) => accumulator + currentValue, 0);

  return parseFloat(amount).toFixed(2)
}

onMounted(() => {
  getOfferStats()
    .then(response => {
      numberOfCustomers.value = response.numberOfCustomers;
      customers.value = response.customers;
    })
})
</script>
<template>
  <Authenticated>

    <div class="mx-auto max-w-screen-2xl">
      <div class="py-3 grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-3">
        <!-- Card -->
        <div class="rounded-sm border border-stroke bg-white shadow-xl shadow-gray-200">
          <div class="p-4">
            <div class="flex items-center">
              <div class="flex-shrink-0">
                <DocumentIcon class="h-6 w-6 text-gray-400" aria-hidden="true" />
              </div>
              <div class="ml-5 w-0 flex-1">
                <dl>
                  <dt class="truncate text-sm font-medium text-gray-500">Something</dt>
                  <dd>
                    <div class="text-lg font-medium text-gray-900">1234</div>
                  </dd>
                </dl>
              </div>
            </div>
          </div>
          <div class="bg-gray-50 px-5 py-3">
            <div class="text-sm">
              <router-link to="offers" class="font-medium text-blue-700 hover:text-blue-900">Bekijk alles</router-link>
            </div>
          </div>
        </div>

        <div class="rounded-sm border border-stroke bg-white shadow-xl shadow-gray-200">
          <div class="p-4">
            <div class="flex items-center">
              <div class="flex-shrink-0">
                <UserIcon class="h-6 w-6 text-gray-400" aria-hidden="true" />
              </div>
              <div class="ml-5 w-0 flex-1">
                <dl>
                  <dt class="truncate text-sm font-medium text-gray-500">Klanten</dt>
                  <dd>
                    <div class="text-lg font-medium text-gray-900">{{ numberOfCustomers }}</div>
                  </dd>
                </dl>
              </div>
            </div>
          </div>
          <div class="bg-gray-50 px-5 py-3">
            <div class="text-sm">
              <router-link to="customers" class="font-medium text-blue-700 hover:text-blue-900">Bekijk alle klanten</router-link>
            </div>
          </div>
        </div>

        <div class="rounded-sm border border-stroke bg-white shadow-xl shadow-gray-200">
          <div class="p-4">
            <div class="flex items-center">
              <div class="flex-shrink-0">
                <TagIcon class="h-6 w-6 text-gray-400" aria-hidden="true" />
              </div>
              <div class="ml-5 w-0 flex-1">
                <dl>
                  <dt class="truncate text-sm font-medium text-gray-500">Producten</dt>
                  <dd>
                    <div class="text-lg font-medium text-gray-900">{{ 100 }}</div>
                  </dd>
                </dl>
              </div>
            </div>
          </div>
          <div class="bg-gray-50 px-5 py-3">
            <div class="text-sm">
              <p class="font-medium text-blue-700 hover:text-blue-900">Bekijk alle producten</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Authenticated>
</template>
