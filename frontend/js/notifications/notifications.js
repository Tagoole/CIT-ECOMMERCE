// --------------------------------------------------------------------------
// NOTIFICATIONS CONTROLLER
// Owns page state ({ notifications, messages, editingNotificationId, active
// tab }) and the data flow. It coordinates the view (notifications-render.js,
// DOM only) and the data (notifications-api.js, network only). It never
// touches the DOM or fetch directly itself.
//
// Startup flow:
//   initNotificationsPage()
//     1. cacheElements()        collect every DOM node the page needs
//     2. createRenderer()       build the UI layer, pass save handlers
//     3. initEvents()           bind tabs + toolbar + list/table actions
//     4. loadNotifications()    fetch app notifications, then render table
//     5. loadMessages()         fetch in-app messages, then render list
//
// User action flow:
//   tab click       -> selectTab (show/hide the right panel)
//   notifications   -> handleNotificationSave / handleNotificationDelete
//   messages        -> handleMessageSave / handleMessageDelete
//   All paths:      -> notificationsApi / messagesApi -> renderer
// --------------------------------------------------------------------------
import { notificationsApi, messagesApi } from './notifications-api.js';
import { createRenderer } from './notifications-render.js';

let notifications = [];
let messages = [];
let editingNotificationId = null;
let elements;
let renderer;

function cacheElements() {
  return {
    // Tabs
    tabMessagesBtn: document.getElementById('tab-messages-btn'),
    tabNotificationsBtn: document.getElementById('tab-notifications-btn'),
    panelMessages: document.getElementById('panel-messages'),
    panelNotifications: document.getElementById('panel-notifications'),
    // Toolbar
    newMessageBtn: document.getElementById('new-message-btn'),
    addNotificationBtn: document.getElementById('add-notification-btn'),
    // Lists / tables
    messagesList: document.getElementById('messages-list'),
    notificationsTableBody: document.getElementById('notifications-table-body'),
    // Notification modal
    notificationModal: document.getElementById('notification-modal'),
    notificationModalTitle: document.getElementById('notification-modal-title'),
    notificationForm: document.getElementById('notification-form'),
    notificationFormFields: document.getElementById('notification-form-fields'),
    notificationCancelBtn: document.getElementById('notification-cancel-btn'),
    // Message modal
    messageModal: document.getElementById('message-modal'),
    messageModalTitle: document.getElementById('message-modal-title'),
    messageForm: document.getElementById('message-form'),
    messageFormFields: document.getElementById('message-form-fields'),
    messageCancelBtn: document.getElementById('message-cancel-btn'),
    // Status
    statusBar: document.getElementById('notifications-status'),
    errorBar: document.getElementById('notifications-error'),
  };
}

// --------------------------------------------------------------------------
// Tabs
// --------------------------------------------------------------------------
function selectTab(tab) {
  const messagesActive = tab === 'messages';

  elements.tabMessagesBtn.classList.toggle('active', messagesActive);
  elements.tabNotificationsBtn.classList.toggle('active', !messagesActive);
  elements.panelMessages.hidden = !messagesActive;
  elements.panelNotifications.hidden = messagesActive;
}

// --------------------------------------------------------------------------
// Data loading
// --------------------------------------------------------------------------
async function loadNotifications() {
  try {
    notifications = await notificationsApi.getAll();
    renderer.renderNotifications(notifications);
  } catch (err) {
    renderer.showError(`Failed to load notifications: ${err.message}`);
  }
}

async function loadMessages() {
  try {
    messages = await messagesApi.getAll();
    renderer.renderMessages(messages);
  } catch (err) {
    renderer.showError(`Failed to load messages: ${err.message}`);
  }
}

// --------------------------------------------------------------------------
// Mutations
// --------------------------------------------------------------------------
async function handleNotificationSave(data) {
  try {
    if (editingNotificationId) {
      await notificationsApi.update(editingNotificationId, data);
      renderer.showStatus('Notification updated.');
    } else {
      await notificationsApi.create(data);
      renderer.showStatus('Notification created.');
    }
    closeNotificationModal();
    await loadNotifications();
  } catch (err) {
    renderer.showError(`Save failed: ${err.message}`);
  }
}

async function handleNotificationDelete(id) {
  const confirmed = confirm('Delete this notification? This cannot be undone.');
  if (!confirmed) return;

  try {
    await notificationsApi.remove(id);
    renderer.showStatus('Notification deleted.');
    await loadNotifications();
  } catch (err) {
    renderer.showError(`Delete failed: ${err.message}`);
  }
}

async function handleMessageSave(data) {
  try {
    await messagesApi.create(data);
    renderer.showStatus('Message sent.');
    renderer.closeMessageModal();
    await loadMessages();
  } catch (err) {
    renderer.showError(`Send failed: ${err.message}`);
  }
}

async function handleMessageDelete(id) {
  const confirmed = confirm('Delete this message? This cannot be undone.');
  if (!confirmed) return;

  try {
    await messagesApi.remove(id);
    renderer.showStatus('Message deleted.');
    await loadMessages();
  } catch (err) {
    renderer.showError(`Delete failed: ${err.message}`);
  }
}

// --------------------------------------------------------------------------
// Modal control
// --------------------------------------------------------------------------
function openNotificationModal(notification = null) {
  editingNotificationId = notification ? notification.id : null;
  renderer.openNotificationModal(notification ?? null);
}

function closeNotificationModal() {
  renderer.closeNotificationModal();
  editingNotificationId = null;
}

// --------------------------------------------------------------------------
// Event wiring
// --------------------------------------------------------------------------
function onNotificationsTableClick(event) {
  const btn = event.target.closest('button[data-action]');
  if (!btn) return;

  const row = btn.closest('tr');
  const id = Number(row.dataset.id);
  const notification = notifications.find((n) => n.id === id);

  if (btn.dataset.action === 'edit') openNotificationModal(notification);
  if (btn.dataset.action === 'delete') handleNotificationDelete(id);
}

function onMessagesListClick(event) {
  const btn = event.target.closest('button[data-action]');
  if (!btn) return;

  const item = btn.closest('li');
  const id = Number(item.dataset.id);

  if (btn.dataset.action === 'delete') handleMessageDelete(id);
}

function initEvents(elements) {
  elements.tabMessagesBtn.addEventListener('click', () => selectTab('messages'));
  elements.tabNotificationsBtn.addEventListener('click', () => selectTab('notifications'));

  elements.newMessageBtn.addEventListener('click', () => renderer.openMessageModal());
  elements.messageCancelBtn.addEventListener('click', () => renderer.closeMessageModal());

  elements.addNotificationBtn.addEventListener('click', () => openNotificationModal(null));
  elements.notificationCancelBtn.addEventListener('click', closeNotificationModal);

  elements.notificationsTableBody.addEventListener('click', onNotificationsTableClick);
  elements.messagesList.addEventListener('click', onMessagesListClick);
}

// --------------------------------------------------------------------------
// Entry point
// --------------------------------------------------------------------------
export function initNotificationsPage() {
  elements = cacheElements();
  renderer = createRenderer(elements, {
    onNotificationSave: handleNotificationSave,
    onMessageSave: handleMessageSave,
  });
  renderer.init();
  initEvents(elements);
  selectTab('messages');
  loadNotifications();
  loadMessages();
}

document.addEventListener('DOMContentLoaded', initNotificationsPage);