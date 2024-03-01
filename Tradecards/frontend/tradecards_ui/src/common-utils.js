const convertBase64toImage = (base64String) => {
  if (base64String) {
    const byteCharacters = atob(base64String);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray,], { type: 'image/jpeg', });
    const imageUrl = URL.createObjectURL(blob);
    return imageUrl;
  }
};

const setStorage = (key, value) => {
  localStorage.setItem(key, value);
};

const getStorage = (key) => {
  return localStorage.getItem(key);
};

export {
  convertBase64toImage,
  setStorage,
  getStorage,
};
