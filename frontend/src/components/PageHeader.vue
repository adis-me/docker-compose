<script setup lang="ts">
const props = defineProps<{
  title: string,
}>()
</script>

<template>
  <div class="flex flex-auto justify-between">
    <h2 class="text-2xl font-bold">
      {{ props.title }}
    </h2>
    <slot />
  </div>
</template>
