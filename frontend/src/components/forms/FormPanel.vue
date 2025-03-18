<script setup lang="ts">
</script>

<template>
    <div class="rounded-sm border border-stroke bg-white shadow-xl shadow-gray-200 p-3 mt-3">
      <form class="flex flex-col p-4" @submit.prevent>
        <slot />
      </form>
    </div>
</template>
