// --------------------------------------------------------------------------
// PAYMENT CONTROLLER
// Owns page state and coordinates the view (payment-render.js) and data
// (payment-api.js). Never touches the DOM or fetch directly.
//
// Flow:
//   initPaymentPage()
//     1. cacheElements()
//     2. createRenderer()
//     3. initEvents()
//     4. loadPayments()  for each provider
//
// User action:
//   form submit -> handleProcess() -> paymentApi.process*() -> reload table
// --------------------------------------------------------------------------
import { paymentApi } from './payment-api.js';
import { createRenderer } from './payment-render.js';

let currentProvider = 'paypal';
let renderer;

function cacheElements() {
  return {
    form: document.getElementById('payment-form'),
    formFields: document.getElementById('payment-form-fields'),
    statusBar: document.getElementById('payment-status'),
    errorBar: document.getElementById('payment-error'),
    submitBtn: document.getElementById('payment-submit-btn'),
    tabBtns: Array.from(document.querySelectorAll('[data-provider]')),
    tabPanels: Array.from(document.querySelectorAll('[data-provider-panel]')),
    tableBodies: {
      paypal: document.getElementById('paypal-table-body'),
      mtn: document.getElementById('mtn-table-body'),
      creditCard: document.getElementById('credit-card-table-body'),
      airtel: document.getElementById('airtel-table-body'),
    },
  };
}

// --------------------------------------------------------------------------
// Data loading
// --------------------------------------------------------------------------
async function loadPayments(provider) {
  try {
    const loaders = {
      paypal: paymentApi.getAllPaypal,
      mtn: paymentApi.getAllMtn,
      creditCard: paymentApi.getAllCreditCard,
      airtel: paymentApi.getAllAirtel,
    };

    const list = await loaders[provider]();
    renderer.renderTable(provider, list);
  } catch (err) {
    renderer.showError(`Failed to load ${provider} payments: ${err.message}`);
  }
}

async function loadAll() {
  await Promise.all([
    loadPayments('paypal'),
    loadPayments('mtn'),
    loadPayments('creditCard'),
    loadPayments('airtel'),
  ]);
}

// --------------------------------------------------------------------------
// Process payment
// --------------------------------------------------------------------------
async function handleProcess(data) {
  try {
    const processors = {
      paypal: paymentApi.processPaypal,
      mtn: paymentApi.processMtn,
      creditCard: paymentApi.processCreditCard,
      airtel: paymentApi.processAirtel,
    };

    await processors[currentProvider](data);
    renderer.showStatus(`${currentProvider.toUpperCase()} payment processed successfully.`);
    elements.form.reset();
    renderer.renderForm(currentProvider);
    await loadPayments(currentProvider);
  } catch (err) {
    renderer.showError(`Payment failed: ${err.message}`);
  }
}

// --------------------------------------------------------------------------
// Event wiring
// --------------------------------------------------------------------------
let elements;

function initEvents() {
  elements.tabBtns.forEach((btn) => {
    btn.addEventListener('click', () => {
      currentProvider = btn.dataset.provider;
    });
  });
}

// --------------------------------------------------------------------------
// Entry point
// --------------------------------------------------------------------------
export function initPaymentPage() {
  elements = cacheElements();
  renderer = createRenderer(elements, { onProcess: handleProcess });
  renderer.init();
  renderer.renderForm(currentProvider);
  initEvents();
  loadAll();
}

document.addEventListener('DOMContentLoaded', initPaymentPage);
