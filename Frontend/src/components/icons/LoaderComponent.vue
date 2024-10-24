<script setup>

</script>

<template>
  <div class="box loader-15 loader-15"></div>
</template>

<style scoped>
.loader-15 {
  background: currentcolor;
  position: relative;
  animation: loader-15 1s ease-in-out infinite;
  animation-delay: 0.4s;
  width: 15px;
  height: 25px;
}

.loader-15::before,
.loader-15::after {
  content: '';
  position: absolute;
  width: inherit;
  height: inherit;
  background: inherit;
  animation: inherit;
}

.loader-15::before {
  right: 30px;
  animation-delay: 0.2s;
}

.loader-15::after {
  left: 30px;
  animation-delay: 0.6s;
}

@keyframes loader-15 {
  0%,
  100% {
    box-shadow: 0 0 0 currentcolor, 0 0 0 currentcolor;
  }
  50% {
    box-shadow: 0 -0.25em 0 currentcolor, 0 0.25em 0 currentcolor;
  }
}
</style>