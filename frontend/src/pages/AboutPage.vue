<script>
import {defineComponent} from 'vue';
import MenuBar from '@/components/MenuBar.vue';

export default defineComponent({
  components: {MenuBar},
});
</script>
<template>
  <div class="isolate bg-white">
    <div class="px-6 pt-6 lg:px-8">
      <MenuBar/>
    </div>
    <main>
      <div class="relative px-6 lg:px-8">
        <div class="mx-auto max-w-3xl pt-20 pb-32 sm:pt-48 sm:pb-40">
          <div>
            <div class="hidden sm:mb-8 sm:flex sm:justify-center">
              <div
                  class="relative overflow-hidden rounded-full py-1.5 px-4 text-sm leading-6 ring-1 ring-gray-900/10 hover:ring-gray-900/20">
                <span class="text-gray-600">Voorbeeld over ons pagina
                  <a href="#" class="font-semibold text-indigo-600">
                    <span class="absolute inset-0" aria-hidden="true"/>Lees meer <span aria-hidden="true">&rarr;</span>
                  </a>
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>
