<template>
  <div class="container">
      <header>
        <div class="header-logo">
          <img class="logo-icon" src="../assets/logo.png" alt="">
          <div class="logo-name">LINKUP</div>
        </div>
      </header>
      <div class="container-video">
        <video autoplay muted loop class="background-video">
          <source :src="poster" type="video/mp4" />
        </video>
        <div class="logo">
          <LogoComponent />
        </div>
        <footer>
        <div class="footer-c">
          <img class="c-icon" src="../assets/copyrayt.png" alt="">
          <div class="c-name">2024 «LinkUp»</div>
        </div>
        <div class="footer-about">
          <a href="" class="about-name">О НАС</a>
        </div>
        <div class="footer-support">
          <a href="" class="support-name">ПОДДЕРЖКА</a>
        </div>
      </footer>
      </div>
      <div class="container-content">
          <SignUpComponent/>
      </div>
    </div>
</template>

<script setup>
import LogoComponent from "@/components/icons/LogoComponent2.vue";
import poster from "@/assets/poster.mp4";
import SignUpComponent from "@/components/SignUpComponent.vue";
import LoaderSquareComponent from "@/components/icons/LoaderSquareComponent.vue";
import {onMounted, ref} from "vue";

const loading = ref(true)

onMounted(() => {
  setTimeout(() => {
    loading.value = false
  }, 2000)
})

</script>

<style scoped>
header {
  position: absolute;
}
footer {
  display: flex;
  margin-top: 130px;
  margin-left: 35px;
}
.footer-support {
  margin-left: 25%;
}
.support-name {
  text-decoration: none;
  color: white;
  font-size: 16px;
  font-weight: 700;
  margin-top: 5px;
}
.footer-about {
  margin-left: 25%;
}
.about-name {
  text-decoration: none;
  color: white;
  font-size: 16px;
  font-weight: 700;
  margin-top: 5px;
}
.footer-c {
  justify-content: center;
  align-items: center;
  display: flex;
  margin-left: 25px;
}
.c-icon {
  margin-right: 5px;
  width: 15px;

}
.c-name {
  color: white;
  font-size: 16px;
  font-weight: 700;
}
.header-logo {
  position: absolute;
  margin-top: 30px;
  justify-content: center;
  align-items: center;
  width: 300px;
  display: flex;
  margin-right: 100px;
  margin-left: -40px;
}
.logo-icon {
  width: 40px;
  height: 40px;
  margin-right: 5px;
}
.logo-name {
  color: white;
  font-size: 30px;
  font-weight: 700;
  text-decoration: none;
}

.container {
  //background-color: black;
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
  display: flex;
}
.container-video {
  width: 60%;
  height: 100vh;
}
.background-video {
  position: absolute;
  top: 50%;
  left: 50%;
  min-width: 100%;
  min-height: 100%;
  width: auto;
  height: auto;
  transform: translate(-50%, -50%);
  z-index: -1;
  object-fit: cover; /* Заполняет весь контейнер, сохраняя соотношение сторон */
}

.container-content {
  position: relative; /* Создает новый контекст наложения */
  z-index: 1; /* Устанавливает выше фонового видео */
  background-color: white;
  width: 40%;
  height: 100vh;
  border-top-left-radius: 40px;
  box-shadow: -4px -4px 10px rgba(0, 0, 0, 0.2);
}
.logo {
  padding-top: 20vh;
}
@media (max-width: 1024px) {
  .container-video {
    width: 0;
  }
  .container-content {
    border-top-left-radius: 0;
    width: 100%;
  }
}

</style>