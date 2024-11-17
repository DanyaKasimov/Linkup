<template>
    <div class="loading-container">
      <div class="loader"></div>
    </div>
  </template>
  
  <script setup>
  </script>
  
  <style>
  .loading-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .loader {
    border: 8px solid #5500ff;
    border-top: 8px solid rgb(255, 0, 255);
    border-radius: 50%;
    width: 20px;
    height: 20px;
    animation: spin 1s linear infinite;
  }
  
  @keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
  
  </style>