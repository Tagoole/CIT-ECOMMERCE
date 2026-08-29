
// --------------------------------------------------------------------------
// NOTIFICATIONS RENDERER - pure UI layer
// Handles every DOM change for the notifications page: dynamic form fields,
// the notification + message modals, tab panels, message cards, the
// notifications table, and status/error bars.
//
// It never calls the network or localStorage. The controller passes in the
// DOM elements it needs and the callbacks to trigger on form submit
// (onNotificationSave, onMessageSave).
// --------------------------------------------------------------------------

// Schema - one source of truth per form. Add/remove a field here and both the
// modal forms and the lists stay in sync with the backend model.
export const notificationFields = [
  { name: 'title', label: 'Title', type: 'text', required: true },
  { name: 'body', label: 'Body', type: 'textarea', required: true },
];

export const messageFields = [
  { name: 'user', label: 'User', type: 'text', required: true },
  { name: 'productOwner', label: 'Product Owner', type: 'text', required: true },
  { name: 'text', label: 'Message', type: 'textarea', required: true },
];

function escapeHtml(value) {
  return String(value)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

function fieldHtml(field, value) {
  const valueEscaped = escapeHtml(value);
  const requiredAttr = field.required ? 'required' : '';

  if (field.type === 'textarea') {
    return `
      <label class="form-field">
        <span>${field.label}</span>
        <textarea name="${field.name}" ${requiredAttr}>${valueEscaped}</textarea>
      </label>`;
  }

  const extra = [
    field.step ? `step="${field.step}"` : '',
    field.min !== undefined ? `min="${field.min}"` : '',
  ].join(' ');

  return `
    <label class="form-field">
      <span>${field.label}</span>
      <input type="${field.type}" name="${field.name}" value="${valueEscaped}" ${requiredAttr} ${extra} />
    </label>`;
}

export function createRenderer(elements, { onNotificationSave, onMessageSave } = {}) {
  let statusTimeout;

  // --------------------------
  // Dynamic form fields (shared by both modals)
  // --------------------------
  function renderFormFields(container, fields, source = {}) {
    container.innerHTML = fields
      .map((field) => fieldHtml(field, source[field.name] ?? ''))
      .join('');
  }

  function readFormData(formEl, fields) {
    const formData = new FormData(formEl);
    const data = {};

    fields.forEach((field) => {
      const raw = formData.get(field.name);
      data[field.name] = field.type === 'number' ? Number(raw) : raw;
    });

    return data;
  }

  // --------------------------
  // Notification modal
  // --------------------------
  function openNotificationModal(notification = null) {
    elements.notificationModalTitle.textContent = notification ? 'Edit Notification' : 'New Notification';
    renderFormFields(elements.notificationFormFields, notificationFields, notification ?? {});
    elements.notificationModal.showModal();
  }

  function closeNotificationModal() {
    elements.notificationModal.close();
    elements.notificationForm.reset();
  }

  // --------------------------
  // Message modal
  // --------------------------
  function openMessageModal() {
    elements.messageModalTitle.textContent = 'New Message';
    renderFormFields(elements.messageFormFields, messageFields);
    elements.messageModal.showModal();
  }

  function closeMessageModal() {
    elements.messageModal.close();
    elements.messageForm.reset();
  }

  // --------------------------
  // Tables / lists
  // --------------------------
  function renderNotifications(list) {
    if (!list.length) {
      elements.notificationsTableBody.innerHTML =
        `<tr><td colspan="3" class="empty-row">No notifications yet.</td></tr>`;
      return;
    }

    elements.notificationsTableBody.innerHTML = list.map((n) => `
      <tr data-id="${n.id}">
        <td>${escapeHtml(n.title)}</td>
        <td>${escapeHtml(n.body ?? '')}</td>
        <td class="actions-cell">
          <button class="btn-link" data-action="edit">Edit</button>
          <button class="btn-link btn-danger" data-action="delete">Delete</button>
        </td>
      </tr>
    `).join('');
  }

  function renderMessages(list) {
    if (!list.length) {
      elements.messagesList.innerHTML = `<li class="empty-row">No messages yet.</li>`;
      return;
    }

    elements.messagesList.innerHTML = list.map((m) => `
      <li class="message-card" data-id="${m.id}">
        <div class="message-meta">
          <span class="message-author">${escapeHtml(m.user)}</span>
          <span class="message-arrow">→</span>
          <span class="message-owner">${escapeHtml(m.productOwner)}</span>
        </div>
        <p class="message-text">${escapeHtml(m.text)}</p>
        <div class="message-actions">
          <button class="btn-link btn-danger" data-action="delete">Delete</button>
        </div>
      </li>
    `).join('');
  }

  // --------------------------
  // Status / error
  // --------------------------
  function showStatus(message) {
    elements.statusBar.textContent = message;
    elements.statusBar.hidden = false;
    elements.errorBar.hidden = true;
    clearTimeout(statusTimeout);
    statusTimeout = setTimeout(() => { elements.statusBar.hidden = true; }, 3000);
  }

  function showError(message) {
    elements.errorBar.textContent = message;
    elements.errorBar.hidden = false;
    elements.statusBar.hidden = true;
  }

  // --------------------------
  // Wiring
  // --------------------------
  function init() {
    elements.notificationForm.addEventListener('submit', (event) => {
      event.preventDefault();
      onNotificationSave?.(readFormData(elements.notificationForm, notificationFields));
    });

    elements.messageForm.addEventListener('submit', (event) => {
      event.preventDefault();
      onMessageSave?.(readFormData(elements.messageForm, messageFields));
    });
  }

  return {
    init,
    openNotificationModal,
    closeNotificationModal,
    openMessageModal,
    closeMessageModal,
    renderNotifications,
    renderMessages,
    showStatus,
    showError,
  };
}