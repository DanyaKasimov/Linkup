<template>
  <div class="container-logo">
    <svg :width="svgWidth" height="200" class="drawing">
      <text
          v-for="(letter, index) in letters"
          :key="index"
          class="letter"
          :x="xPositions[index]"
          y="100"
          :style="{
          '--animation-delay': `${index * animationDuration}s`,
          '--end-offset': `${endOffsets[index]}`,
        }"
          :class="{ 'complete': completeAnimation }"
      >
        {{ letter }}
      </text>
    </svg>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';

const letters = 'LINKUP'.split('');
const animationDuration = 0.5; // Длительность анимации каждой буквы

// Индивидуальные позиции по оси X для каждой буквы
const xPositions = [30, 90, 120, 195, 270, 345];

// Индивидуальные конечные значения stroke-dashoffset для каждой буквы
const endOffsets = [350, 400, 280, 230, 280, 400];

// Рассчитываем ширину SVG на основе позиций букв
const svgWidth = computed(() => Math.max(...xPositions) + 100);

// Переменная для управления запуском полной анимации
const completeAnimation = ref(false);

// Общая длительность начальных анимаций
const totalDuration = ((letters.length - 1) * animationDuration + 0.5) * 1000; // в миллисекундах

onMounted(() => {
  setTimeout(() => {
    completeAnimation.value = true;
  }, totalDuration);
});
</script>



<style scoped>
.container-logo {
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 100px;
  height: 200px;
}

.drawing {
  font-size: 100px;
  fill: none;
  stroke: mediumpurple;
  stroke-width: 2;
}

.letter {
  stroke-dasharray: 500;
  stroke-dashoffset: 500;
  animation: drawPartial 0.5s forwards;
  animation-delay: var(--animation-delay);
}

@keyframes drawPartial {
  to {
    stroke-dashoffset: var(--end-offset); /* Используем индивидуальное значение для каждой буквы */
  }
}

/* Анимация для полного дорисовывания букв */
.letter.complete {
  animation: drawComplete 3s forwards;
}

@keyframes drawComplete {
  from {
    stroke-dashoffset: var(--end-offset);
  }
  to {
    stroke-dashoffset: 0;
  }
}
</style>

