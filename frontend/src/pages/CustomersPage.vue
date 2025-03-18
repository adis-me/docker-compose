<script setup lang="ts">
import Authenticated from '@/layouts/AuthenticatedLayout.vue';
import { getAllCustomers, archiveCustomer } from '@/api/customer-api';
import PopupModal from '@/components/PopupModal.vue';
import { MagnifyingGlassCircleIcon } from '@heroicons/vue/24/outline';
import PageHeader from '@/components/PageHeader.vue';
import PaginationBar from '@/components/PaginationBar.vue';
import DataTable from '@/components/DataTable.vue';

const router = useRouter();
const route = useRoute();

let pageable = ref({
  totalElements: route.query.totalElements || 0,
  totalPages: route.query.totalPages || 0,
  pageNumber: route.query.pageNumber || 0,
  pageSize: route.query.pageSize || 5,
});

let confirmModal = ref({
  identifier: 0,
  show: false,
  text: '',
});

let customers = ref([]);
let view = ((id: number) => {
  router.push({name: 'customer-update', params: {id: id}});
});
let edit = ((id: number) => {
  router.push({name: 'customer-update', params: {id: id}});
});
let confirm = ((id: number) => {
  confirmModal.value.identifier = id;
  confirmModal.value.show = true
  confirmModal.value.text = 'Put something here'
})
let archive = ((data: any) => {
  confirmModal.value.show = false
  confirmModal.value.text = 'Put something here'
  if ('proceed' === data.action) {
    archiveCustomer(data.identifier)
      .then(archived => {
        if (archived) {
          loadResources()
        } else {
          // show warning that deletion failed
        }
      })
  }
});

onMounted(() => {
  loadResources();
});
onUpdated(() => {
  pageable.value = {
    totalElements: route.query.totalElements || 0,
    totalPages: route.query.totalPage || 0,
    pageNumber: route.query.pageNumber || 0,
    pageSize: route.query.pageSize || 5,
  }
  loadResources();
});

function loadResources() {
  let text = null;
  let page = pageable.value.pageNumber;
  let size = pageable.value.pageSize;

  getAllCustomers(text, parseInt(page.toString()), parseInt(size.toString()))
    .then(response => {
      pageable.value = {
        totalElements: response.totalElements,
        totalPages: response.totalPages,
        pageSize: response.size,
        pageNumber: response.number,
      }
      // todo: set filter.value.text here
      customers.value = response.content
    });
};
</script>
<template>
  <Authenticated>
    <PageHeader
      title="Klanten"
    >
      <router-link
        class="inline-flex rounded-md border border-transparent justify-end
                bg-blue-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-blue-700
                focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 mb-1.5"
        :to="{ name: 'customer-create', params: { id: null }}">
        Toevoegen
      </router-link>
    </PageHeader>

    <DataTable count="customers.length" zero-text="Je hebt nog geen klanten geregistreerd, wil je misschien eerst een klant toevoegen?">
      <tr class="text-gray-500 text-sm text-left">
        <th>Naam</th>
        <th>Adres</th>
        <th>Contact</th>
        <th>Aangemaakt op</th>
        <th>Acties</th>
      </tr>
      <tr v-for="customer in customers" :key="customer.id" class="table-row border-t hover:bg-blue-50">
        <td>{{ customer.name }}</td>
        <td>
          {{ customer.address }} <br>
          {{ customer.postalCode }} {{ customer.city }}
        </td>
        <td>{{ customer.email }}</td>
        <td>
          <time :datetime="customer.createdAt" class="font-medium text-gray-900">{{ new Date(customer.createdAt).toLocaleDateString() }}</time>
        </td>
        <td>
          <a :href="'/customers/' + customer.id" class="block px-4 py-4">
            <MagnifyingGlassCircleIcon class="h-5 w-5 flex-shrink-0 text-gray-400" aria-hidden="true" />
          </a>
        </td>
      </tr>
      <template #pagination>
        <PaginationBar
          v-if="customers.length > 0"
          :page-number="parseInt(pageable.pageNumber)"
          :total-pages="parseInt(pageable.totalPages)"
          :page-size="parseInt(pageable.pageSize)"
          :total-elements="parseInt(pageable.totalElements)"
          collection="customers"
        />
      </template>
    </DataTable>

    <PopupModal
      :open="confirmModal.show"
      :modal-text="confirmModal.text"
      :identifier="confirmModal.identifier"
      stop-label="Annuleer"
      proceed-label="Bevestig"
      @postConfirm="archive"
    >
      <div>
        <div class="mt-3 text-center sm:mt-5">
          <h3 class="text-lg font-medium leading-6 text-gray-900">Bevestig actie</h3>
          <div class="mt-2">
            <p class="text-sm text-gray-500">Weet je zeker dat je door wilt gaat?</p>
          </div>
        </div>
      </div>
    </PopupModal>
  </Authenticated>
</template>
