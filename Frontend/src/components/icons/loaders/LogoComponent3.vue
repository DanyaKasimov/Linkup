<template>
  <div class="container-logo">
    <svg :width="svgWidth" height="400" class="drawing">
      <defs>
        <linearGradient id="animated-gradient-2x" x1="0%" y1="0%" x2="100%" y2="0%" gradientUnits="objectBoundingBox">
          <stop offset="0%" stop-color="white" />
          <stop offset="100%" stop-color="ghostwhite" />
          <!-- Анимация вращения градиента -->
          <animateTransform
              attributeName="gradientTransform"
              type="rotate"
              from="0 0.5 0.5"
              to="360 0.5 0.5"
              dur="10s"
              repeatCount="indefinite"
          />
          <!-- Дополнительная анимация масштабирования градиента -->
          <animateTransform
              attributeName="gradientTransform"
              type="scale"
              values="1 1; 1.2 1.2; 1 1"
              keyTimes="0; 0.5; 1"
              dur="10s"
              repeatCount="indefinite"
              additive="sum"
          />
        </linearGradient>
      </defs>
      <text
          v-for="(letter, index) in letters"
          :key="index"
          class="letter"
          :x="xPositions[index]"
          :y="yPositions[index]"
          :style="{
          '--animation-delay': `${index * animationDuration}s`,
          '--end-offset': `${endOffsets[index]}`,
          'fill': 'url(#animated-gradient-2x)',
          'stroke': 'url(#animated-gradient-2x)',
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

const xPositions = [0, 180, 270, 495, 0, 240];
const yPositions = [300, 300, 300, 300, 670, 670];

// Увеличенные конечные значения stroke-dashoffset для масштаба 3x
const endOffsets = [1050, 1200, 840, 690, 840, 1200];

// Рассчитываем ширину SVG на основе позиций букв
const svgWidth = computed(() => Math.max(...xPositions) + 300);


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
  position: relative;
  z-index: 2;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 600px; /* Соответственно увеличенная высота */
}

.drawing {
  font-size: 300px; /* Увеличен размер шрифта */
  fill: url(#animated-gradient-2x); /* Применяем градиент к заполнению */
  stroke: url(#animated-gradient-2x); /* Применяем градиент к обводке */
  stroke-width: 6; /* Увеличена толщина линии */
  height: 700px;
}

.letter {
  fill-opacity: 0; /* Начинаем с прозрачного заполнения */
  stroke-dasharray: 1500; /* Увеличено для соответствия */
  stroke-dashoffset: 1500; /* Увеличено для соответствия */
  animation: drawPartial 0.5s forwards;
  animation-delay: var(--animation-delay);
}

@keyframes drawPartial {
  to {
    stroke-dashoffset: var(--end-offset);
  }
}

/* Анимация для полного дорисовывания букв и заполнения градиентом */
.letter.complete {
  animation: drawComplete 3s forwards, fillIn 1s forwards;
  animation-delay: 0s, 1s;
}

@keyframes drawComplete {
  from {
    stroke-dashoffset: var(--end-offset);
  }
  to {
    stroke-dashoffset: 0;
  }
}

@keyframes fillIn {
  to {
    //fill-opacity: 1; /* Плавно заполняем буквы градиентом */
  }
}
</style>