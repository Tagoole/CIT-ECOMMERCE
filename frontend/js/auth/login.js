const form = document.querySelector("#loginForm");
const identifier = document.querySelector("#identifier");
const password = document.querySelector("#password");
const identifierError = document.querySelector("#identifierError");
const passwordError = document.querySelector("#passwordError");
const statusEl = document.querySelector("#loginStatus");

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

  if (!identifier.value.trim()) {
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

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  if (!validate()) {
    return;
  }

  statusEl.textContent = "Logging in...";

  try {
    const data = await apiRequest(API.login, {
      method: "POST",
      body: JSON.stringify({
        identifier: identifier.value.trim(),
        password: password.value,
      }),
    });

    const user = (data && data.user) || data;
    if (data && data.token) {
      localStorage.setItem("cit_token", data.token);
    }
    localStorage.setItem("cit_current_user", JSON.stringify(user));

    statusEl.textContent = "Logged in! Redirecting...";
    window.location.href = "profile.html";
  } catch (error) {
    statusEl.textContent = error.message || "Login failed. Please try again.";
  }
});