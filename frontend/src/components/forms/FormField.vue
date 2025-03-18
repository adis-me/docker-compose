<script setup lang="ts">
const props = defineProps<{
  label?: string,
  labelFor?: string,
  help?: string
}>()
</script>

<template>
  <div>
    <label v-if="props.label" :for="props.labelFor" class="block text-sm font-medium text-gray-700">
      {{ label }}
    </label>
    <div class="mt-1">
      <slot />
    </div>
    <div v-if="props.help" class="text-xs text-gray-500 dark:text-slate-400 mt-1">
      {{ help }}
    </div>
  </div>
</template>
