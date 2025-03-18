<script setup lang="ts">
const emits = defineEmits(['update:modelValue']);
const props = defineProps<{
  label?: string,
  labelFor: string,
  help?: string,
  modelValue: string,
  required: boolean,
  elements: any,
}>()
</script>

<template>
  <div>
    <label v-if="props.label" :for="props.labelFor" class="block text-sm font-medium text-gray-700 mb-3">{{ label }}</label>
    <div class="relative">
      <select
        :value="modelValue"
        :name="props.labelFor" :autocomplete="props.labelFor"
        class="w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
        :required="props.required"
        @input="emits('update:modelValue', $event.target.value)"
      >
        <option>Selecteer een land</option>
        <option v-for="element in props.elements" :key="element.id" :value="element.id">{{ element.name }}</option>
      </select>
    </div>
    <div v-if="props.help" class="text-xs text-gray-500 dark:text-slate-400 mt-1">
      {{ help }}
    </div>
  </div>
</template>
