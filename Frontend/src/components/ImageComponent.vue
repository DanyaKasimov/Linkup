<template>
  <div class="image-container">
    <img
        :alt="altText"
        :src="imageUrl"
        :style="imageStyles"
        @error="handleError"
    />
  </div>
</template>

<script setup>
import {computed, defineProps, ref} from 'vue';

const props = defineProps({
  imageUrl: {
    type: String,
    required: true,
    default: '',
  },
  altText: {
    type: String,
    default: 'Изображение',
  },
  width: {
    type: String,
    default: '50px',
  },
  height: {
    type: String,
    default: '50px',
  },
  borderRadius: {
    type: String,
    default: '0',
  },
  fallbackImage: {
    type: String,
    default: '/uploads/avatars/photo.jpg', //default image
  },
});

const currentImageUrl = ref(props.imageUrl);

const imageStyles = computed(() => ({
  width: props.width,
  height: props.height,
  borderRadius: props.borderRadius,
  objectFit: 'cover',
}));

const handleError = () => {
  if (currentImageUrl.value !== props.fallbackImage) {
    currentImageUrl.value = props.fallbackImage;
  }
};
</script>
