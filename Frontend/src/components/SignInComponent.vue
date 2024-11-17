<template>
  <div v-if="sentRequest">
    <LoaderSquareComponent/>
  </div>
  <div v-else class="container-signin">
    <div class="header">Вход</div>
    <div class="error-container" :class="{'mobile': isMobile}">
    <transition-group name="error-message" tag="div">
      <div
          class="error-message"
          v-for="error in errors"
          :key="error.id"
      >
        <div class="error-message-text">{{ error.message }}</div>
      </div>
    </transition-group>
  </div>
    <div class="content">
      <div class="input-container">
        <label :class="{ float: username }" for="username">Имя пользователя</label>
        <input id="username" type="text" v-model="username" class="content-input">
      </div>
      <div class="input-container">
        <label :class="{ float: password || passwordVisible }" for="password">Пароль</label>
        <input :type="passwordVisible ? 'text' : 'password'" id="password" v-model="password" class="content-input">
        <button type="button" class="toggle-password" @click="togglePasswordVisibility">
          <img v-if="!passwordVisible" class="eye" src="../assets/eye.png" alt="">
          <img v-else class="eye" src="../assets/eye-close.png" alt="">
        </button>
      </div>
      <div class="forget">
        <a href="" class="forget-text">Забыли пароль?</a>
      </div>
      <div class="button-wrapper">
        <video autoplay muted loop class="background-video-btn">
          <source :src="poster" type="video/mp4" />
        </video>
        <button @click="signin" class="content-button">Войти</button>
      </div>

      <div class="register">
        <div class="register-first">Впервые с нами?</div>
        <router-link to="/signup" class="register-link">Присоединяйся!</router-link>
      </div>

      <div class="or">
        <div class="line"></div>
        <div class="or-text">Или</div>
        <div class="line"></div>
      </div>

      <div class="other">
        <button class="other-item">
            <img class="item-icon" src="../assets/google.png" alt="">
            <div class="item-name">Google</div>
        </button>
        <button class="other-item">
          <img class="item-icon" src="../assets/vk.png" alt="">
          <div class="item-name">ВКонтакте</div>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref,computed } from "vue";
import {Api} from "@/api/Api"

import poster from "@/assets/poster.mp4";
import { useRouter } from 'vue-router';
import LoaderSquareComponent from "@/components/icons/loaders/LoaderSquareComponent.vue";

const sentRequest = ref(false)
const api = new Api()

const router = useRouter()

const signin = async () => {
  sentRequest.value = true
  const body = {
    username: username.value,
    password: password.value
  }
  const response = await api.post("/auth/signin", null, body);
  if (!response.ok) {
    const errorJson = await response.json()
    addError(errorJson.errorMessage)
    sentRequest.value = false
  } else {
    setTimeout(() => {
      router.push('/profile')
      sentRequest.value = false}, 600);
  }
}

const username = ref("");
const password = ref("");
const passwordVisible = ref(false);

const togglePasswordVisibility = () => {
  passwordVisible.value = !passwordVisible.value;
};
const errors = ref([]);

const isMobile = computed(() => {
  return window.innerWidth <= 768;
});

function addError(message) {
  const error = { id: Date.now(), message };
  errors.value.push(error);
  setTimeout(() => {
    removeError(error.id);
  }, 4000);
}

function removeError(id) {
  errors.value = errors.value.filter(error => error.id !== id);
}
</script>

<style scoped>
.forget {
  width: 100%;
  text-align: right;
  margin: 15px auto;
}
.forget-text {
  color: var(--link-color-1);
  font-size: 14px;
  text-decoration: none;
}
.other {
  margin-top: 20px;
  display: flex;
  width: 100%;
}
.other-item {
  padding: 5px  0;
  display: flex;
  color: var(--text-color-1);
  background-color: white;
  border: 1px solid var(--border-input);
  width: 48%;
  margin-right: 5%;
  justify-content: center;
  align-items: center;
  border-radius: 10px;

}
.other-item:last-child {
  margin-right: 0;
}
.item-icon {
  width: 30px;
  height: 30px;
  margin-right: 7px;
}

.item-name {
  color: var(--text-color-1);
  font-size: 16px;
}
.or {
  margin-top: 20px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.or-text {
  font-size: 14px;
  margin: 0 10px;
  color: var(--text-color-1);
}
.line {
  border-bottom: 1px solid var(--border-input);
  width: 100%;
}

.container-signin {
  margin: 0 auto;
  width: 65%;
}

.header {
  color: var(--text-color-1);
  margin-top: 160px;
  font-weight: 600;
  font-size: 35px;
  /* margin-left: 10%; */
  text-align: center;
  margin-bottom: 50px;
}

.content {
  width: 100%;
  height: 600px;
  text-align: center;
}

.input-container {
  position: relative;
  width: 100%;
  margin: 40px auto 0 auto;
  text-align: left;
  border-bottom: 1px solid var(--border-input);
}

.content-input {
  width: 80%;
  height: 40px;
  padding: 0 10px;
  font-size: 16px;
  background-color: var(--color-input);
  border: none;
  outline: none;
}

label {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 16px;
  color: var(--text-color-1);
  transition: 0.3s ease;
  pointer-events: none;
}

label.float {
  top: 0;
  font-size: 12px;
  color: var(--text-color-2);
}


.toggle-password {
  position: absolute;
  top: 8px;
  right: 10px;
  background-color: transparent;
  border: none;
  cursor: pointer;
}

.eye {
   width: 20px;
}

.button-wrapper {
  position: relative;
  width: 100%;
  height: 40px;
  margin: 0 auto;
}

.background-video-btn {
  width: 100%;
  height: 100%;
  border-radius: 10px;
  object-fit: cover;
}

.content-button {
  border-radius: 10px;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: transparent;
  color: white;
  font-weight: bold;
  z-index: 1;
  border: none;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}

.content-button:hover {
  background-color: rgba(0, 0, 0, 0.1); 
}

.register {
  padding-top: 20px;
  display: flex;
  width: 60%;
  margin: 0 auto;
}
.register-first {
  color: var(--text-color-1);
  font-size: 14px;
  margin-right:  5px;
}
.register-link {
  color: var(--link-color-1);
  text-decoration: none;
  font-size: 14px;
}

.other-item:hover{
  background-color: whitesmoke;
}

.error-container {
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 1000;
}

.error-container.mobile {
  left: 50%;
  top: 10px;
  transform: translateX(-50%);
  right: auto;
}

.error-message {
  background-color: rgba(255, 79, 79, 0.9);
  color: white;
  border-radius: 10px;
  margin-bottom: 10px;
  width: 250px;
  display: flex;
  justify-content: center;
  align-content: center;
}
.error-message-text {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 90%;
  padding: 20px 0;
}
</style>