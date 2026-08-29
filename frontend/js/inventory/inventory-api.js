// ---------------------------------------------------------------------------
// Inventory API — one function per inventory endpoint
// ---------------------------------------------------------------------------
import { api } from '../utils/api.js';
import { ENDPOINTS } from '../config.js';

export const inventoryApi = {
  getAll: () => api.get(ENDPOINTS.inventory),
  getOne: (id) => api.get(`${ENDPOINTS.inventory}/${id}`),
  create: (data) => api.post(ENDPOINTS.inventory, data),
  update: (id, data) => api.put(`${ENDPOINTS.inventory}/${id}`, data),
  adjustStock: (id, amount) => api.patch(`${ENDPOINTS.inventory}/${id}/stock`, { amount }),
  remove: (id) => api.delete(`${ENDPOINTS.inventory}/${id}`),
  checkAvailability: (id) => api.get(`${ENDPOINTS.inventory}/${id}/availability`),
  getLowStock: (threshold) => api.get(`${ENDPOINTS.inventory}/low-stock?threshold=${threshold}`),
};