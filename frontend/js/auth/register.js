//const API = "api-url"; // Replace with your actual API URL
const form = document.querySelector("#registerForm");
const statusEl = document.querySelector("#registerStatus");

const email = document.querySelector("#email");
const phone = document.querySelector("#phone");
const username = document.querySelector("#username");
const password = document.querySelector("#password");
const confirmPassword = document.querySelector("#confirmPassword");
const emailError = document.querySelector("#emailError");
const phoneError = document.querySelector("#phoneError");
const usernameError = document.querySelector("#usernameError");
const passwordError = document.querySelector("#passwordError");
const confirmPasswordError = document.querySelector("#confirmPasswordError");

function setError(input, errorEl, message) {
  input.classList.remove("auth-form__input--invalid");
  errorEl.textContent = "";
  if (message) {
    input.classList.add("auth-form__input--invalid");
    errorEl.textContent = message;
  }
}

function validate() {
  let valid = true;

  if (!email.value.trim() || !email.value.includes("@")) {
    setError(email, emailError, "Enter a valid email address.");
    valid = false;
  } else {
    setError(email, emailError);
  }

  if (!phone.value.trim()) {
    setError(phone, phoneError, "Enter your phone number.");
    valid = false;
  } else {
    setError(phone, phoneError);
  }

  if (username.value.trim().length < 3) {
    setError(username, usernameError, "Username must be at least 3 characters.");
    valid = false;
  } else {
    setError(username, usernameError);
  }

  if (password.value.length < 8) {
    setError(password, passwordError, "Password must be at least 8 characters.");
    valid = false;
  } else {
    setError(password, passwordError);
  }

  if (confirmPassword.value !== password.value) {
    setError(confirmPassword, confirmPasswordError, "Passwords do not match.");
    valid = false;
  } else {
    setError(confirmPassword, confirmPasswordError);
  }

  return valid;
}

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  if (!validate()) {
    return;
  }


  const newUser = {
    email: email.value.trim().toLowerCase(),
    phone: phone.value.trim(),
    username: username.value.trim(),
    password: password.value,
  };

  statusEl.textContent = "Creating your account...";

  try {
    await apiRequest(API.register, {
      method: "POST",
      body: JSON.stringify(newUser),
    });
  } catch (error) {
    statusEl.textContent = error.message || "Failed to register. Please try again.";
    return;
  }


  statusEl.textContent = "Account created! Redirecting...";
  form.reset();
  window.location.href = "login.html";
});