<template>
  <div class="menu-container">
    <div class="menu-logo">
      <img alt="" class="menu-item-img" src="../assets/logo-black.png">
    </div>
    <div class="menu-items">
      <div class="menu-item">
        <HomeIcon size="30px"/>
      </div>
      <div class="menu-item">
        <MessageIcon size="30px"/>
      </div>
      <div class="menu-item">
        <HomeIcon size="30px"/>
      </div>
      <div class="menu-item">
        <HomeIcon size="30px"/>
      </div>
    </div>
  </div>
</template>


<script setup>

import HomeIcon from "@/components/icons/svg/HomeIcon.vue";
import MessageIcon from "@/components/icons/svg/MessageIcon.vue";
</script>

<style scoped>
.menu-container {
  width: 60px;
  height: 96vh;
  border-radius: 10px;
  background-color: var(--bg-color-1);
  box-shadow: 2px 4px 5px rgba(0, 0, 0, 0.2);
}

.menu-item {
  margin: 25%;
}

.menu-item-img {
  width: 40px;
}

.menu-items {
  margin-top: 120px;
}
</style>