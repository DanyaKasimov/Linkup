<template>
  <div class="spinner"></div>
</template>

<script setup>
</script>

<style scoped>
body {
  display: flex;
  height: 100px;
  width: 100px;
  justify-content: center;
  align-items: center;
}

.spinner {
  margin-top: 40vh;
  margin-left: 47%;
  position: absolute;
  color: rgb(153, 0, 255);
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100px;
}

.spinner:after {
  animation: changeContent .7s linear infinite;
  display: block;
  content: "⠋";
  font-size: 80px;
}

@keyframes changeContent {
  10% { content: "⠙"; }
  20% { content: "⠹"; }
  30% { content: "⠸"; }
  40% { content: "⠼"; }
  50% { content: "⠴"; }
  60% { content: "⠦"; }
  70% { content: "⠧"; }
  80% { content: "⠇"; }
  90% { content: "⠏"; }
}
</style>