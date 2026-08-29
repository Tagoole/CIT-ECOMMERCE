async function apiRequest(path, options = {}) {
  const headers = { "Content-Type": "application/json" };
  const token = localStorage.getItem("cit_token");
  if (token) headers.Authorization = `Bearer ${token}`;
  if (options.headers) Object.assign(headers, options.headers);

  const response = await fetch(path, { ...options, headers });

  const text = await response.text();
  let data = null;
  try {
    data = text ? JSON.parse(text) : null;
  } catch (_) {
    data = null;
  }

  if (!response.ok) {
    const message = (data && (data.message || data.error)) || "Something went wrong. Please try again.";
    throw new Error(message);
  }

  return data;
}