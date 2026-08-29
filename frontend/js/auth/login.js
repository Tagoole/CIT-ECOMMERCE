(function () {
  const form = document.getElementById("loginForm");
  const identifier = document.getElementById("identifier");
  const password = document.getElementById("password");
  const identifierError = document.getElementById("identifierError");
  const passwordError = document.getElementById("passwordError");

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

  form.addEventListener("submit", function (event) {
    event.preventDefault();

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

    if (!valid) {
      return;
    }

    const identifierLower = idValue.toLowerCase();
    const user = getUsers().find(
      (existing) =>
        (existing.email || "").toLowerCase() === identifierLower ||
        (existing.username || "").toLowerCase() === identifierLower
    );

    if (!user || user.password !== password.value) {
      setError(password, passwordError, "Incorrect email/username or password.");
      return;
    }

    localStorage.setItem("cit_current_user", JSON.stringify(user));
    window.location.href = "login.html";
  });
})();