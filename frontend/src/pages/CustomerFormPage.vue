<script setup lang="ts">
import Authenticated from '@/layouts/AuthenticatedLayout.vue';
import { getById, save } from '@/api/customer-api';
import TextField from '@/components/forms/TextField.vue';
import SelectField from '@/components/forms/SelectField.vue';
import FormPanel from '@/components/forms/FormPanel.vue';
import FormPanelButtons from '@/components/forms/FormPanelButtons.vue';
import FormButton from '@/components/forms/FormButton.vue';
import PageHeader from '@/components/PageHeader.vue';

const router = useRouter();

let customer = ref({
  id: null,
  name: 'Flink Software',
  address: 'Oranjesingel 51',
  postalCode: '6511 NP',
  city: 'Nijmegen',
  country: {
    id: 'NL',
  },
  email: 'info@flinksoftware.com',
});

const props = defineProps<{
  id?: number
}>()

function submitForm() {
  save(customer.value).then(response => {
    if (response.id) {
      router.push({name: 'customers'});
    }
  }).catch(err => {
    console.error(err)
  });
}

onMounted(() => {
  console.log(props.id)
  if (props.id) {
    getById(props.id).then(response => {
      console.log(response)
      customer.value = response
    })
  }
})
</script>
<template>
  <Authenticated>
    <PageHeader
      :title="'Klant:' + customer.name"
    />

    <FormPanel>
      <div class="flex flex-col gap-5 p-6.5">

        <TextField
          v-model="customer.name"
          label="Naam"
          label-for="name"
          type="text"
        />

        <TextField
          v-model="customer.address"
          label="Adres"
          label-for="address"
          type="text"
        />

        <TextField
          v-model="customer.postalCode"
          label="Postcode"
          label-for="postalCode"
          type="text"
        />

        <TextField
          v-model="customer.city"
          label="Stad"
          label-for="city"
          type="text"
        />

        <TextField
          v-model="customer.email"
          label="Email"
          label-for="email"
          type="text"
        />

        <SelectField
          v-model="customer.country.id"
          label="Land"
          label-for="customer.country.id"
          required="required"
          :elements="[{id: 'NL', name: 'Nederland'}, {id: 'BE', name: 'België'}]"
        />

      </div>

      <FormPanelButtons>
        <router-link :to="{ name: 'customers'}">
          <p
            class="rounded-md border border-gray-300 bg-white py-2 px-4 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
            Annuleren
          </p>
        </router-link>
        <FormButton
          label="Opslaan"
          :click-handler="submitForm"
        />
      </FormPanelButtons>

    </FormPanel>

  </Authenticated>
</template>
