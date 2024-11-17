<template>
  <div>
    <div class="loader-p"></div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.loader-p {
  width: 15px;
  height: 15px;
  border-radius: 50%;
  display: block;
  position: relative;
  left: -300px; /* Увеличено для большего пространства */
  box-sizing: border-box;
  animation: shadowRolling 2s linear infinite;
}

@keyframes shadowRolling {
  0% {
    box-shadow: 0px 0 rgba(255, 255, 255, 0),
    0px 0 rgba(255, 255, 255, 0),
    0px 0 rgba(255, 255, 255, 0),
    0px 0 rgba(255, 255, 255, 0);
  }
  12% {
    box-shadow: 150px 0 #0065ff,
    0px 0 rgba(255, 255, 255, 0),
    0px 0 rgba(255, 255, 255, 0),
    0px 0 rgba(255, 255, 255, 0);
  }
  25% {
    box-shadow: 165px 0 rgb(85, 0, 255),
    150px 0 #0065ff,
    0px 0 rgba(255, 255, 255, 0),
    0px 0 rgba(255, 255, 255, 0);
  }
  36% {
    box-shadow: 180px 0 #761bff,
    165px 0 rgb(85, 0, 255),
    150px 0 #0065ff,
    0px 0 rgba(255, 255, 255, 0);
  }
  50% {
    box-shadow: 195px 0 #d35fff,
    180px 0 #761bff,
    165px 0 #5500ff,
    150px 0 #0065ff;
  }
  62% {
    box-shadow: 300px 0 rgba(255, 255, 255, 0),
    195px 0 #d35fff,
    180px 0 #761bff,
    165px 0 #5500ff;
  }
  75% {
    box-shadow: 300px 0 rgba(255, 255, 255, 0),
    300px 0 rgba(255, 255, 255, 0),
    195px 0 #d35fff,
    180px 0 #761bff;
  }
  87% {
    box-shadow: 300px 0 rgba(255, 255, 255, 0),
    300px 0 rgba(255, 255, 255, 0),
    300px 0 rgba(255, 255, 255, 0),
    195px 0 #d35fff;
  }
  100% {
    box-shadow: 300px 0 rgba(255, 255, 255, 0),
    300px 0 rgba(255, 255, 255, 0),
    300px 0 rgba(255, 255, 255, 0),
    300px 0 rgba(255, 255, 255, 0);
  }
}
</style>