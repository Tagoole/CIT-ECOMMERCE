// API endpoint - replace once the auth backend is ready
const API_URL = "https://backend-api-production-b1c1.up.railway.app/auth/login";

const form = document.querySelector("#loginForm");
const statusEl = document.querySelector("#loginStatus");

const identifier = document.querySelector("#identifier");
const password = document.querySelector("#password");
const identifierError = document.querySelector("#identifierError");
const passwordError = document.querySelector("#passwordError");

const EMAIL_RE = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

function setError(input, errorEl, message) {
  input.classList.remove("auth-form__input--invalid");
  errorEl.textContent = "";
  if (message) {
    input.classList.add("auth-form__input--invalid");
    errorEl.textContent = message;
  }
}

function getUsers() {
  return JSON.parse(localStorage.getItem("cit_users") || "[]");
}

function validate() {
  const idValue = identifier.value.trim();
  let valid = true;

  if (!idValue || (!EMAIL_RE.test(idValue) && idValue.length < 3)) {
    setError(identifier, identifierError, "Enter your email or username.");
    valid = false;
  } else {
    setError(identifier, identifierError);
  }

  if (!password.value) {
    setError(password, passwordError, "Enter your password.");
    valid = false;
  } else {
    setError(password, passwordError);
  }

  return valid;
}

// LOGIN
form.addEventListener("submit", async (event) => {
  event.preventDefault();

  if (!validate()) {
    return;
  }

  const idValue = identifier.value.trim().toLowerCase();

  // Show loading state
  statusEl.textContent = "Logging in...";

  try {
    // --- API CALL (placeholder: swap in the real login endpoint/response) ---
    const response = await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ identifier: idValue, password: password.value }),
    });

    if (!response.ok) {
      throw new Error("Failed to log in");
    }

    const user = await response.json();
    localStorage.setItem("cit_current_user", JSON.stringify(user));
    // -------------------------------------------------------------------------

  } catch (error) {
    // Demo fallback - keep the flow working until the backend is wired up
    const user = getUsers().find(
      (existing) =>
        (existing.email || "").toLowerCase() === idValue ||
        (existing.username || "").toLowerCase() === idValue
    );

    if (!user || user.password !== password.value) {
      setError(password, passwordError, "Incorrect email/username or password.");
      statusEl.textContent = "";
      return;
    }

    localStorage.setItem("cit_current_user", JSON.stringify(user));
  }

  statusEl.textContent = "Login successful! Redirecting...";
  form.reset();
  window.location.href = "profile.html";
});