<template>
 <div class="spin" v-if="sentRequest">
    <LoaderSquareComponent/>
  </div>
  <div v-else class="container-signin">
    <div class="header">Регистрация</div>
    <div class="line"></div>
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
    <transition name="fade-2">
        <div v-if="!emailAccepted" class="email-header">
          <div class="e-header-one">Пройдите верификацию</div>
          <div class="e-header-two">
            <p>На указанный электронный ящик будет отправлен код подтверждения.</p>
          </div>
          <div class="register-2">
            <div class="register-first">Есть аккаунт?</div>
            <router-link to="/signin" class="register-link">Войти.</router-link>
          </div>
        </div>
    </transition>
    <div :class="!emailAccepted ? 'email-container' : 'email-container-top'">
      <div class="input-container" :class="{disabled : emailPosted || emailWaiting}">
        <label :class="{ float: email }" for="email">Почта</label>
        <input id="email" type="text" :class="{disabled : emailWaiting || emailPosted}" :disabled="emailWaiting || emailPosted" v-model="email" class="content-input">
      </div>
      <div class="container-code" >
        <input type="text" class="code-input" v-model="code" v-if="emailPosted && !emailAccepted" :class="{disabled : emailAccepted}" :disabled="emailAccepted" placeholder="  Введите код">
        <button :class="{'code-btn-all' : (emailAccepted), 'code-btn-all-na' : !emailAccepted }" v-if="!emailWaiting" @click="handleBtn" class="code-btn">
          <video autoplay loop muted playsinline class="video-background">
            <source src="../assets/poster.mp4" type="video/mp4">
          </video>
          <span class="button-text" v-if="!emailPosted && !emailAccepted">Получить код</span>
          <span class="button-text" v-else-if="emailPosted && !emailAccepted" >Отправить код</span>
          <span class="button-text" v-else>Изменить адрес почты</span>
        </button>
        <LoaderPointsComponent v-else class="loader"/>
      </div>
    </div>
    <div class="email-again">
      <div class="again-timer" v-if="emailPosted && !emailAccepted && !showResendButton">
        <p>Вы сможете изменить адрес электронной почты или отправить код повторно через: {{ timer }} секунд(-ы)</p>
      </div>
      <div class="email-btns" v-if="showResendButton && !emailAccepted && emailPosted">
        <button class="again-btn" @click="sendEmailAgain">Отправить код повторно</button>
        <button class="again-btn" @click="sendAgain">Изменить адрес почты</button>
      </div>
    </div>
    <transition name="fade">
      <div v-if="showContent">
        <div class="content">
          <div class="input-container" :class="{disabled : !emailAccepted}">
            <label :class="{ float: username }" for="username">Имя пользователя</label>
            <input id="username" type="text" v-model="username" class="content-input" :class="{disabled : !emailAccepted}" :disabled="!emailAccepted">
          </div>
          <div class="input-container" :class="{disabled : !emailAccepted}">
            <label :class="{ float: name }" for="name">Имя</label>
            <input id="name" type="text" v-model="name" class="content-input" :class="{disabled : !emailAccepted}" :disabled="!emailAccepted">
          </div>
          <div class="input-container" :class="{disabled : !emailAccepted}">
            <label :class="{ float: surname }" for="surname">Фамилия</label>
            <input id="surname" type="text" v-model="surname" :class="{disabled : !emailAccepted}" class="content-input" :disabled="!emailAccepted">
          </div>
          <div class="input-container" :class="{disabled : !emailAccepted}">
            <label :class="{ float: password || passwordVisible }" for="password">Пароль</label>
            <input :type="passwordVisible ? 'text' : 'password'" id="password" v-model="password" class="content-input" :class="{disabled : !emailAccepted}" :disabled="!emailAccepted">
            <button type="button" class="toggle-password" @click="togglePasswordVisibility" :disabled="!emailAccepted">
              <img v-if="!passwordVisible" class="eye" src="../assets/eye.png" alt="">
              <img v-else class="eye" src="../assets/eye-close.png" alt="">
            </button>
          </div>
          <div class="input-container" :class="{disabled : !emailAccepted}">
            <label :class="{ float: passwordRepeat || passwordVisibleRepeat }" for="passwordRepeat">Повторите пароль</label>
            <input :type="passwordVisible ? 'text' : 'password'" :class="{disabled : !emailAccepted}" id="passwordRepeat" v-model="passwordRepeat" class="content-input" :disabled="!emailAccepted">
            <button type="button" class="toggle-password" @click="togglePasswordVisibility" :disabled="!emailAccepted">
              <img v-if="!passwordVisibleRepeat" class="eye" src="../assets/eye.png" alt="">
              <img v-else class="eye" src="../assets/eye-close.png" alt="">
            </button>
          </div>
          <div class="button-wrapper">
            <video autoplay muted loop playsinline class="background-video-btn">
              <source :src="poster" type="video/mp4" />
            </video>
            <button @click="registration" class="content-button" :class="{'disabled-btn': !emailAccepted}" :disabled="!emailAccepted">Зарегистрироваться</button>
          </div>
          <div class="register">
            <div class="register-first">Есть аккаунт?</div>
            <router-link to="/signin" class="register-link">Войти.</router-link>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import {computed, ref, watch} from "vue";
import {Api} from "@/api/Api"
import poster from "@/assets/poster.mp4";
import LoaderPointsComponent from "@/components/icons/LoaderPointsComponent.vue";
import { useRouter } from 'vue-router';

import LoaderSquareComponent from "@/components/icons/LoaderSquareComponent.vue";

const sentRequest = ref(false)

const username = ref("");
const name = ref("");
const surname = ref("");
const email = ref("");
const code = ref("");
const password = ref("");
const passwordRepeat = ref("");
const passwordVisible = ref(false);
const passwordVisibleRepeat = ref(false);

const emailAccepted = ref(false); 
const emailPosted = ref(false); 
const emailWaiting = ref(false);
const showContent = ref(false);
const flag = ref(false);

const showResendButton = ref(false);
const showCodeElements = ref(false);
const timer = ref(10);
const router = useRouter();


const togglePasswordVisibility = () => {
  passwordVisible.value = !passwordVisible.value;
  passwordVisibleRepeat.value = !passwordVisibleRepeat.value;
};

const sendAgain = () => {
  showResendButton.value = false;
  showCodeElements.value = true;
  emailPosted.value = false;
  emailAccepted.value = false;
};


const api = new Api()

const handleBtn = async () => {
  flag.value = true;
  if (!emailPosted.value && !emailAccepted.value) {
    await sendEmail()
    startTimer();
  }
  else if (emailPosted.value && !emailAccepted.value) {
    await sendVerify()
  }
  else if (emailAccepted.value) {
    code.value = ''
    emailAccepted.value = false
    emailPosted.value = false
    emailWaiting.value = false
  }
}

const startTimer = () => {
  timer.value = 10;
  const countdown = setInterval(() => {
    if (timer.value > 0) {
      timer.value--;
    } else {
      clearInterval(countdown);
      showResendButton.value = true;
    }
  }, 1000);
};

const sendEmailAgain = () => {
  emailWaiting.value = true
  showResendButton.value = false;
  showCodeElements.value = true;
  emailPosted.value = false
  timer.value = 10;
  handleBtn()
}

const sendEmail = async () => {
  emailWaiting.value = true
  const body = {
      email: email.value
  }
  const response = await api.post("/auth/request-verification", null, body);
  if (!response.ok) {
    const errorJson = await response.json()
    addError(errorJson.errorMessage)
  } else {
    emailPosted.value = true;
  }
  emailWaiting.value = false
}

const sendVerify = async () => {
  const body = {
    email: email.value,
    code: code.value
  }
  const response = await api.post("/auth/verify-code", null, body);
  if (!response.ok) {
    const errorJson = await response.json()
    addError(errorJson.errorMessage)
  } else {
    emailAccepted.value = true
  }
}

const registration = async () => {
  sentRequest.value = true
  const body = {
    username: username.value,
    name: name.value,
    secondName: surname.value,
    password: password.value,
    email: email.value
  }
  const response = await api.post("/auth/signup", null, body);
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

watch(emailAccepted, (newValue) => {
  if (newValue) {
    setTimeout(() => {
      showContent.value = true;
    }, 800);
  } else {
    flag.value = true;
    showContent.value = false;
  }
});
function removeError(id) {
  errors.value = errors.value.filter(error => error.id !== id);
}

</script>

<style scoped>
.line {
  border-bottom: 1px solid var(--border-input);
}
.email-header {
  position: absolute;
  margin-top: 60px;
}
.e-header-one {
  font-size: 20px;
  font-weight: 700;
}
.e-header-two {
  font-size: 15px;
  font-weight: 100;
}
.fade-2-enter-active {
  transition: opacity 2s ease;
}

.fade-2-leave-active {
  transition: opacity 0.5s ease;
}
.none {
  opacity: 0;
}
.fade-2-enter-from,
.fade-2-leave-to {
  opacity: 0;
}


.fade-enter-active {
  transition: opacity 1s ease;
}
.fade-enter-from {
  opacity: 0;
}
.fade-enter-to {
  opacity: 1;
}

.fade-leave-active {
  transition: opacity 0.5s ease;
}
.fade-leave-from {
  opacity: 1;
}
.fade-leave-to {
  opacity: 0;
}


.edit {
  margin-top: 10px;
}
.loader {
  width: 70px;
  margin: 10px 300px 9px;
}
.container-code {
  margin-top: 10px;
  display: flex;
}

.code-input {
  border: 1px solid var(--border-input-2);
  border-radius: 10px;
  height: 30px;
  width: 40%;
  margin-right: 5%;
  color: var(--text-color-2);
}
.code-btn-edit {
  position: relative;
  overflow: hidden;
  width: 55%;
  height: 34px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  padding: 0;
  display: inline-block;
  color: white;
  background-color: rgb(125, 82, 255);
}
.code-btn-edit .video-background-2 {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  z-index: 0;
}

.code-btn-edit .button-text-2{
  position: relative;
  z-index: 1;
  color: white;
  font-weight: bold;
  font-size: 14px;
}


.code-btn {
  position: relative;
  overflow: hidden;
  width: 55%;
  height: 34px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  padding: 0;
  display: inline-block;
  color: white;
  background-color: rgb(125, 82, 255);
}

.code-btn-all-na {
  width: 100%;
}

.code-btn-all {
  animation-duration: 2s;
  animation-name: slidein;
  width: 100%;
}

@keyframes slidein {
  0% {
    opacity: 0;
  }
  20% {
    opacity: 0;
  }
  40% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
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
.email-container-top {
  transform: translate(0, -6%);
  transition: top 1s ease, transform 1s ease;
}
.email-container {
  transform: translate(0, 200%);
  transition: top 1s ease, transform 1s ease;
}
.code-btn:hover {
  background-color: rgb(110, 61, 255);
  /* Дополнительные стили при наведении */
  opacity: 0.9;
}
.code-btn .video-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  z-index: 0;
}

.code-btn .button-text {
  position: relative;
  z-index: 1;
  color: white;
  font-weight: bold;
  font-size: 14px;
}

.container-signin {
  margin: 0 auto;
  width: 65%;
}

.header {
  margin-bottom: 15px;
  color: var(--text-color-1);
  margin-top: 50px;
  font-weight: 800;
  font-size: 35px;
  /* margin-left: 10%; */
  text-align: left;
}

.content {
  width: 100%;
  height: 600px;
  text-align: center;
}

.input-container {
  position: relative;
  width: 100%;
  margin: 30px auto 0 auto;
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
.fade-3-enter-from {
  opacity: 0;
}
.fade-3-enter-active {
  transition: opacity 0.5s ease;
}

.fade-3-leave-active {
  transition: opacity 0s;
}
.fade-3-leave-to {
  opacity: 1;
}
label {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 16px;
  color: var(--text-color-1);
  transition: 0.3s ease;
  pointer-events: none; /* Чтобы label не мешал клику на input */
}

label.float {
  top: 0;
  font-size: 12px;
  color: var(--text-color-2); /* Изменение цвета для активной метки */
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
  margin: 40px auto 20px;
}

.background-video-btn {
  width: 100%;
  height: 100%;
  border-radius: 10px;
  object-fit: cover; /* Заполняет весь контейнер, сохраняя соотношение сторон */
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
  background-color: rgba(0, 0, 0, 0.1); /* Легкий эффект затемнения при наведении */
}

.register {
  display: flex;
  width: 37%;
  margin: 0 auto;
}
.register-2 {
  margin-top: 58vh;
  display: flex;
  width: 37%;
  margin-left: 25%;
}
.register-first {
  color: var(--text-color-1);
  font-size: 14px;
  margin-right: 5px;
}

.register-link {
  color: var(--link-color-1);
  text-decoration: none;
  font-size: 14px;
}

.other-item:hover {
  background-color: whitesmoke;
}

.disabled {
  background-color: whitesmoke;
  border-radius: 10px;
  border: none;
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
.disabled-btn {
  opacity: 0.5;
}
.disabled-btn:hover {
  background-color: transparent;
}

.email-again {
  position: absolute;
  margin-top: 165px;
}
.again-timer {
  font-size: 12px;
  margin-left: 2px;
  color: var(--text-color-2);
  width: 80%;
}
.again-btn {
  font-size: 12px;
  text-align: center;
  width: 185px;
  margin-right: 10px;
  margin-top: 13px;
  background: none;
  color: var(--text-color-2);
  border: 1px solid var(--border-input-2);
  border-radius: 10px;
  padding: 5px;
}
.again-btn:hover {
  background: #f6f6f6;
}
.again-btn:last-child{
  margin-right: 0;
}

.email-btns {
  display: flex;
}

</style>