(function () {
  const form = document.getElementById("profileForm");
  const username = document.getElementById("username");
  const phone = document.getElementById("phone");
  const email = document.getElementById("email");
  const usernameError = document.getElementById("usernameError");
  const phoneError = document.getElementById("phoneError");
  const emailError = document.getElementById("emailError");
  const greeting = document.getElementById("profileGreeting");
  const logoutButton = document.getElementById("logoutButton");

  const EMAIL_RE = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const PHONE_RE = /^\+?\d[\d\s-]{8,14}$/;
  const USERNAME_RE = /^[a-zA-Z0-9_]{3,20}$/;

  function getCurrentUser() {
    return JSON.parse(localStorage.getItem("cit_current_user") || "null");
  }

  function setError(input, errorEl, message) {
    input.classList.remove("profile-form__input--invalid");
    errorEl.textContent = "";
    if (message) {
      input.classList.add("profile-form__input--invalid");
      errorEl.textContent = message;
    }
  }

  const currentUser = getCurrentUser();

  if (!currentUser) {
    window.location.href = "login.html";
    return;
  }

  username.value = currentUser.username || "";
  phone.value = currentUser.phone || "";
  email.value = currentUser.email || "";
  greeting.textContent = currentUser.username || "friend";

  function validate() {
    let valid = true;

    if (!USERNAME_RE.test(username.value.trim())) {
      setError(username, usernameError, "Username must be 3-20 letters, numbers or underscores.");
      valid = false;
    } else {
      setError(username, usernameError);
    }

    if (!PHONE_RE.test(phone.value.trim())) {
      setError(phone, phoneError, "Enter a valid phone number.");
      valid = false;
    } else {
      setError(phone, phoneError);
    }

    if (!EMAIL_RE.test(email.value.trim())) {
      setError(email, emailError, "Enter a valid email address.");
      valid = false;
    } else {
      setError(email, emailError);
    }

    return valid;
  }

  form.addEventListener("submit", async function (event) {
    event.preventDefault();

    if (!validate()) {
      return;
    }

    const updated = {
      email: email.value.trim().toLowerCase(),
      phone: phone.value.trim(),
      username: username.value.trim(),
    };

    try {
      const data = await apiRequest(API.profile, {
        method: "PUT",
        body: JSON.stringify(updated),
      });

      const savedUser = (data && data.user) || data || {};
      const sessionUser = Object.assign({}, currentUser, savedUser);

      localStorage.setItem("cit_current_user", JSON.stringify(sessionUser));
      greeting.textContent = sessionUser.username;
      alert("Profile updated.");
    } catch (error) {
      alert(error.message || "Failed to update profile. Please try again.");
    }
  });

  logoutButton.addEventListener("click", function () {
    localStorage.removeItem("cit_current_user");
    localStorage.removeItem("cit_token");
    window.location.href = "login.html";
  });
})();