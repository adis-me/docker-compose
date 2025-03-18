<script setup lang="ts">
import { getAllCustomers, getById } from '@/api/customer-api';
import { ref } from 'vue'
import { CheckIcon, ChevronUpDownIcon } from '@heroicons/vue/20/solid'
import {
  Combobox,
  ComboboxButton,
  ComboboxInput,
  ComboboxLabel,
  ComboboxOption,
  ComboboxOptions,
} from '@headlessui/vue'

let editable = ref(false);
let customer = ref({
  id: null,
  name: null,
  country: {
    id: null,
  },
});
const selectedCustomer = ref({});
let customers = ref([]);

const emits = defineEmits(['customerChanged']);
const props = defineProps<{
  customerId: number
}>()

function loadCustomer(customerId: any) {
  if (parseInt(customerId)) {
    getById(customerId)
      .then(response => {
        console.log('Fetched customerById: ', response)
        customer.value = response
      })
  }
}

function editCustomer() {
  console.log('Edit customer, data passed: ', selectedCustomer.value)
  emits('customerChanged', selectedCustomer.value)
  loadCustomer(selectedCustomer.value.id)
  editable.value = false
}

function queryCustomers(value) {
  if (value.length > 1) {
    getAllCustomers(value)
      .then(response => {
        if (response.content.length > 0) {
          customers.value = response.content
            .map(cus => {
              return {id: cus.id, name: cus.name}
            })
        } else {
          customers.value = [{
            id: 0, name: 'Geen resultaten...',
          }]
        }
      })
  }
}

onMounted(() => {
  console.info('CustomerComponent:onMounted', props.customerId)
  if (props.customerId) {
    loadCustomer(props.customerId)
  } else {
    editable.value = true
  }
})
</script>

<template>
  <div>
    <div v-if="!editable">
      <p v-if="Object.keys(customer).length === 0">Klant kan niet gevonden worden...</p>
      <h2 class="font-bold">{{ customer.name }}</h2>
      <p>{{ customer.address }}</p>
      <p>{{ customer.postalCode }} {{ customer.city }}</p>
      <p v-if="customer.country">{{ customer.country.name }}</p>
    </div>
    <div v-if="editable">
      <Combobox as="div" v-model="selectedCustomer">
        <ComboboxLabel class="block text-sm font-medium text-gray-700">Klant</ComboboxLabel>
        <div class="relative mt-1">
          <ComboboxInput
            placeholder="Voer een klantnaam in om te zoeken.."
            class="w-full rounded-md border border-gray-300 bg-white py-2 pl-3 pr-10 shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-1 focus:ring-indigo-500 sm:text-sm"
            :display-value="(customer) => customer?.name"
            @change="queryCustomers($event.target.value)" />
          <ComboboxButton class="absolute inset-y-0 right-0 flex items-center rounded-r-md px-2 focus:outline-none">
            <ChevronUpDownIcon class="h-5 w-5 text-gray-400" aria-hidden="true" />
          </ComboboxButton>

          <ComboboxOptions v-if="customers.length > 0"
                           class="absolute z-10 mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
            <ComboboxOption v-for="cust in customers" :key="cust.id" :value="cust" as="template" v-slot="{ active, selected }">
              <li :class="['relative cursor-default select-none py-2 pl-3 pr-9', active ? 'bg-indigo-600 text-white' : 'text-gray-900']">
            <span :class="['block truncate', selected && 'font-semibold']">
              {{ cust.name }}
            </span>

                <span v-if="selected" :class="['absolute inset-y-0 right-0 flex items-center pr-4', active ? 'text-white' : 'text-indigo-600']">
              <CheckIcon class="h-5 w-5" aria-hidden="true" />
            </span>
              </li>
            </ComboboxOption>
          </ComboboxOptions>
        </div>
      </Combobox>
    </div>
    <div class="mt-3">
      <button v-if="!editable" class="inline-flex rounded-md border border-transparent
                bg-blue-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-blue-700
                focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 mb-1.5" @click="editable = true">
        Wijzigen
      </button>
      <button v-if="editable" class="inline-flex rounded-md border border-transparent
                bg-blue-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-blue-700
                focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 mb-1.5 ml-1" @click="editable = false">
        Annuleren
      </button>
      <button v-if="editable && selectedCustomer" class="inline-flex rounded-md border border-transparent
                bg-blue-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-blue-700
                focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 mb-1.5 ml-1" @click="editCustomer">
        Opslaan
      </button>
    </div>
  </div>
</template>
