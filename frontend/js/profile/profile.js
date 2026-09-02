const form = document.querySelector("#profileForm");
const statusEl = document.querySelector("#profileDone");

const email = document.querySelector("#email");
const phone = document.querySelector("#phone");
const username = document.querySelector("#username");
const emailError = document.querySelector("#emailError");
const phoneError = document.querySelector("#phoneError");
const usernameError = document.querySelector("#usernameError");
const greetingEl = document.querySelector("#profileGreeting");
const logoutBtn = document.querySelector("#logoutButton");

function setError(input, errorEl, message) {
  input.classList.remove("profile-form__input--invalid");
  errorEl.textContent = "";
  if (message) {
    input.classList.add("profile-form__input--invalid");
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

  return valid;
}

async function loadProfile() {
  statusEl.textContent = "Loading profile...";
  try {
    const data = await apiRequest(API.profile);
    username.value = data.username || "";
    phone.value = data.phone || "";
    email.value = data.email || "";
    greetingEl.textContent = data.username || "friend";
    statusEl.textContent = "";
  } catch (error) {
    statusEl.textContent = error.message || "Failed to load profile.";
  }
}

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  if (!validate()) {
    return;
  }

  const updatedProfile = {
    username: username.value.trim(),
    phone: phone.value.trim(),
    email: email.value.trim(),
  };

  statusEl.textContent = "Saving changes...";

  try {
    await apiRequest(API.profile, {
      method: "PUT",
      body: JSON.stringify(updatedProfile),
    });
  } catch (error) {
    statusEl.textContent = error.message || "Failed to update profile.";
    return;
  }

  statusEl.textContent = "Profile updated!";
  greetingEl.textContent = updatedProfile.username;
});

logoutBtn.addEventListener("click", () => {
  localStorage.removeItem("cit_token");
  window.location.href = "login.html";
});

loadProfile();
