<script setup lang="ts">
const props = defineProps<{
  count: number,
  zeroText: string,
}>()
</script>
<template>
  <div class="rounded-sm border border-stroke bg-white p-3 mt-3 shadow-xl shadow-gray-200">
    <table v-if="props.count" class="w-full table-auto">
      <slot />
    </table>
    <div v-if="props.count <= 0" class="py-10">
      {{ zeroText }}
    </div>
    <slot v-if="count > 0" name="pagination" />
  </div>
</template>
