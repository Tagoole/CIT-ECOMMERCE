// API endpoint - replace once the auth backend is ready
const API_URL = "https://backend-api-production-b1c1.up.railway.app/auth/register";

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

const EMAIL_RE = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const PHONE_RE = /^\+?\d[\d\s-]{8,14}$/;
const USERNAME_RE = /^[a-zA-Z0-9_]{3,20}$/;

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

  if (!EMAIL_RE.test(email.value.trim())) {
    setError(email, emailError, "Enter a valid email address.");
    valid = false;
  } else {
    setError(email, emailError);
  }

  if (!PHONE_RE.test(phone.value.trim())) {
    setError(phone, phoneError, "Enter a valid phone number.");
    valid = false;
  } else {
    setError(phone, phoneError);
  }

  if (!USERNAME_RE.test(username.value.trim())) {
    setError(username, usernameError, "Username must be 3-20 letters, numbers or underscores.");
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

function getUsers() {
  return JSON.parse(localStorage.getItem("cit_users") || "[]");
}

function saveUsers(users) {
  localStorage.setItem("cit_users", JSON.stringify(users));
}

// REGISTER
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

  // Show loading state
  statusEl.textContent = "Creating your account...";

  try {
    // --- API CALL (placeholder: swap in the real register endpoint/response) ---
    const response = await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newUser),
    });

    if (!response.ok) {
      throw new Error("Failed to register");
    }
    // ---------------------------------------------------------------------------

  } catch (error) {
    // Demo fallback - keep the flow working until the backend is wired up
    const users = getUsers();
    const exists = users.some(
      (existing) =>
        existing.email === newUser.email || existing.username === newUser.username
    );

    if (exists) {
      statusEl.textContent = "Email or username already registered.";
      return;
    }

    users.push(newUser);
    saveUsers(users);
  }

  statusEl.textContent = "Account created! Redirecting...";
  form.reset();
  window.location.href = "login.html";
});