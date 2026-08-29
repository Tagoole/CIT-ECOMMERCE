// --------------------------------------------------------------------------
// Payment API - one function per payment provider endpoint
// --------------------------------------------------------------------------
import { api } from '../utils/api.js';
import { ENDPOINTS } from '../config.js';

const base = ENDPOINTS.payments;

export const paymentApi = {
  // PayPal
  getAllPaypal: () => api.get(`${base}/paypal`),
  processPaypal: (data) => api.post(`${base}/paypal/process`, data),

  // MTN
  getAllMtn: () => api.get(`${base}/mtn`),
  processMtn: (data) => api.post(`${base}/mtn/process`, data),

  // Credit Card
  getAllCreditCard: () => api.get(`${base}/credit-card`),
  processCreditCard: (data) => api.post(`${base}/credit-card/process`, data),

  // Airtel
  getAllAirtel: () => api.get(`${base}/airtel`),
  processAirtel: (data) => api.post(`${base}/airtel/process`, data),
};
