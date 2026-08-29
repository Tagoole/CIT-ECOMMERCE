// API endpoint - replace once the auth backend is ready
const API_URL = "https://backend-api-production-b1c1.up.railway.app/auth/register";

// Get the form and status element from the page
const form = document.querySelector("#registerForm");
const statusEl = document.querySelector("#registerStatus");

// Get every input field
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

// Shows or clears an error message under a field
function setError(input, errorEl, message) {
  input.classList.remove("auth-form__input--invalid");
  errorEl.textContent = "";
  if (message) {
    input.classList.add("auth-form__input--invalid");
    errorEl.textContent = message;
  }
}

// Check all fields and return true if everything is valid
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

// Read the stored users from the browser
function getUsers() {
  return JSON.parse(localStorage.getItem("cit_users") || "[]");
}

// Save the list of users to the browser
function saveUsers(users) {
  localStorage.setItem("cit_users", JSON.stringify(users));
}

// ADD USER
form.addEventListener("submit", async (event) => {
  event.preventDefault(); // stop the page from reloading

  // Stop here if any field is invalid
  if (!validate()) {
    return;
  }

  // Collect the form values into one object
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

  // Tell the user it worked and go to the login page
  statusEl.textContent = "Account created! Redirecting...";
  form.reset();
  window.location.href = "login.html";
});