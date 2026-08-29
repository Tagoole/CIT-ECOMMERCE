(function () {
  const form = document.getElementById("registerForm");

  const email = document.getElementById("email");
  const phone = document.getElementById("phone");
  const username = document.getElementById("username");
  const password = document.getElementById("password");
  const confirmPassword = document.getElementById("confirmPassword");

  const emailError = document.getElementById("emailError");
  const phoneError = document.getElementById("phoneError");
  const usernameError = document.getElementById("usernameError");
  const passwordError = document.getElementById("passwordError");
  const confirmPasswordError = document.getElementById("confirmPasswordError");

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

  form.addEventListener("submit", function (event) {
    event.preventDefault();

    if (!validate()) {
      return;
    }

    const users = getUsers();
    const user = {
      email: email.value.trim().toLowerCase(),
      phone: phone.value.trim(),
      username: username.value.trim(),
      password: password.value,
      createdAt: new Date().toISOString()
    };

    const exists = users.some(
      (existing) =>
        existing.email === user.email || existing.username === user.username
    );

    if (exists) {
      setError(email, emailError, "Email or username already registered.");
      return;
    }

    users.push(user);
    saveUsers(users);
    window.location.href = "login.html";
  });
})();