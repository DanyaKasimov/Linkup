<template>
  <div class="profile-container">
    <div class="margin-one">
      <Menu class="profile-menu"/>
    </div>
    <div class="margin-one content-container">
      <Avatar borderRadius="10px" class="profile-avatar" height="150px"
              image-url="/uploads/avatars/photo.jpg"
              width="150px"/>
      <Background borderRadius="10px" class="profile-background" height="200px"
                  image-url="/uploads/backgrounds/background-default.png"
                  width="100%"/>
      <div class="profile-name">
        Касимов Даниил
      </div>
    </div>
  </div>
</template>

<script setup>
import Menu from "@/components/MenuComponent.vue";
import Avatar from "@/components/ImageComponent.vue";
import Background from "@/components/ImageComponent.vue";
</script>

<style scoped>
.profile-container {
  display: flex;
  width: 100%;
  height: 100vh;
  background-color: var(--bg-color-2);
}

.profile-menu {
  margin-left: 19%;
}

.profile-avatar {
  margin-left: 2%;
  margin-top: 120px;
  position: absolute;
  border: 5px solid var(--bg-color-3);
  border-radius: 10px;
}

.margin-one {
  margin-top: 1%;
}

.content-container {
  margin-left: 1.5%;
  background-color: var(--bg-color-3);
  width: var(--block-size-1);
  height: 300px;
  border-radius: 10px;
  box-shadow: 2px 4px 5px rgba(0, 0, 0, 0.2);
}

.profile-name {
  width: 600px;
  margin-top: 10px;
  font-size: 24px;
  margin-left: 27%;
  font-weight: 800;
}
</style>