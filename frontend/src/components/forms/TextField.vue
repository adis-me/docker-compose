<script setup lang="ts">
const emits = defineEmits(['update:modelValue']);

const props = defineProps<{
  label?: string,
  labelFor: string,
  type: string,
  help?: string,
  modelValue: string,
}>()
</script>

<template>
  <div>
    <label v-if="props.label" :for="props.labelFor" class="block text-sm font-medium text-gray-700 mb-3">{{ label }}</label>
    <div class="relative">
      <input
        :type="props.type" :name="props.labelFor" :autocomplete="props.labelFor" :value="modelValue"
        class="w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
        @input="emits('update:modelValue', $event.target.value)"
      />
    </div>
    <div v-if="props.help" class="text-xs text-gray-500 dark:text-slate-400 mt-1">
      {{ help }}
    </div>
  </div>
</template>
