
// --------------------------------------------------------------------------
// NOTIFICATIONS & MESSAGES API - one function per endpoint
//
// Backend model (per backend partner):
//   Notification: { id, title, body }
//   Message:      { id, user, productOwner, text }
// --------------------------------------------------------------------------
import { api } from '../utils/api.js';
import { ENDPOINTS } from '../config.js';

export const notificationsApi = {
  getAll: () => api.get(ENDPOINTS.notifications),
  getOne: (id) => api.get(`${ENDPOINTS.notifications}/${id}`),
  create: (data) => api.post(ENDPOINTS.notifications, data),
  update: (id, data) => api.put(`${ENDPOINTS.notifications}/${id}`, data),
  remove: (id) => api.delete(`${ENDPOINTS.notifications}/${id}`),
};

export const messagesApi = {
  getAll: () => api.get(ENDPOINTS.messages),
  getOne: (id) => api.get(`${ENDPOINTS.messages}/${id}`),
  create: (data) => api.post(ENDPOINTS.messages, data),
  remove: (id) => api.delete(`${ENDPOINTS.messages}/${id}`),
};