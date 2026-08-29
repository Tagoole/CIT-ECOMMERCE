// --------------------------------------------------------------------------
// INVENTORY CONTROLLER
// Owns page state ({ products, editingId }) and the data flow. It coordinates
// the view (inventory-render.js, DOM only) and the data (inventory-api.js,
// network only). It never touches the DOM or fetch directly itself.
//
// Startup flow:
//   initInventoryPage()
//     1. cacheElements()   collect every DOM node the page needs
//     2. createRenderer()  build the UI layer, pass handleSave as hook
//     3. initEvents()      bind toolbar + table action listeners
//     4. loadProducts()    fetch inventory, then render the table
//
// User action flow:
//   toolbar/table click -> handler (handleSave / handleDelete /
//     handleStockAdjust / handleAvailabilityCheck)
//       -> inventoryApi (fetch) -> renderer.showStatus / renderTable
// --------------------------------------------------------------------------
import { inventoryApi } from './inventory-api.js';
import { createRenderer } from './inventory-render.js';

let products = [];
let editingId = null;
let renderer;

function cacheElements() {
  return {
    addBtn: document.getElementById('add-product-btn'),
    cancelBtn: document.getElementById('product-cancel-btn'),
    tableBody: document.getElementById('inventory-table-body'),
    lowStockInput: document.getElementById('low-stock-threshold'),
    lowStockBtn: document.getElementById('low-stock-btn'),
    showAllBtn: document.getElementById('show-all-btn'),
    modal: document.getElementById('product-modal'),
    modalTitle: document.getElementById('product-modal-title'),
    form: document.getElementById('product-form'),
    formFields: document.getElementById('product-form-fields'),
    statusBar: document.getElementById('inventory-status'),
    errorBar: document.getElementById('inventory-error'),
  };
}

// --------------------------------------------------------------------------
// Modal control
// --------------------------------------------------------------------------
function openModal(product = null) {
  editingId = product ? product.id : null;
  renderer.openModal(editingId !== null, product ?? {});
}

function closeModal() {
  renderer.closeModal();
  editingId = null;
}

// --------------------------------------------------------------------------
// Data loading
// --------------------------------------------------------------------------
async function loadProducts() {
  try {
    products = await inventoryApi.getAll();
    renderer.renderTable(products);
  } catch (err) {
    renderer.showError(`Failed to load inventory: ${err.message}`);
  }
}

async function loadLowStock(threshold) {
  try {
    const list = await inventoryApi.getLowStock(threshold);
    renderer.renderTable(list);
    renderer.showStatus(`Showing products at or below ${threshold} units.`);
  } catch (err) {
    renderer.showError(`Failed to load low-stock products: ${err.message}`);
  }
}

// --------------------------------------------------------------------------
// Mutations
// --------------------------------------------------------------------------
async function handleSave(data) {
  try {
    if (editingId) {
      await inventoryApi.update(editingId, data);
      renderer.showStatus('Product updated.');
    } else {
      await inventoryApi.create(data);
      renderer.showStatus('Product added.');
    }
    closeModal();
    await loadProducts();
  } catch (err) {
    renderer.showError(`Save failed: ${err.message}`);
  }
}

async function handleDelete(id) {
  const confirmed = confirm('Delete this product? This cannot be undone.');
  if (!confirmed) return;

  try {
    await inventoryApi.remove(id);
    renderer.showStatus('Product deleted.');
    await loadProducts();
  } catch (err) {
    renderer.showError(`Delete failed: ${err.message}`);
  }
}

async function handleStockAdjust(id, amount) {
  try {
    const updated = await inventoryApi.adjustStock(id, amount);
    renderer.showStatus(amount > 0 ? 'Stock increased.' : 'Stock decreased.');
    const idx = products.findIndex((p) => p.id === id);
    if (idx !== -1) products[idx] = { ...products[idx], ...updated };
    renderer.renderTable(products);
  } catch (err) {
    renderer.showError(`Stock update failed: ${err.message}`);
  }
}

async function handleAvailabilityCheck(id) {
  try {
    const result = await inventoryApi.checkAvailability(id);
    renderer.showStatus(result.available
      ? `In stock -- ${result.quantity} available.`
      : 'Out of stock.');
  } catch (err) {
    renderer.showError(`Availability check failed: ${err.message}`);
  }
}

// --------------------------------------------------------------------------
// Event wiring
// --------------------------------------------------------------------------
function onTableClick(event) {
  const btn = event.target.closest('button[data-action]');
  if (!btn) return;

  const row = btn.closest('tr');
  const id = Number(row.dataset.id);
  const product = products.find((p) => p.id === id);

  switch (btn.dataset.action) {
    case 'edit':
      openModal(product);
      break;
    case 'delete':
      handleDelete(id);
      break;
    case 'inc':
      handleStockAdjust(id, 1);
      break;
    case 'dec':
      handleStockAdjust(id, -1);
      break;
    case 'availability':
      handleAvailabilityCheck(id);
      break;
  }
}

function initEvents(elements) {
  elements.addBtn.addEventListener('click', () => openModal(null));
  elements.cancelBtn.addEventListener('click', closeModal);
  elements.tableBody.addEventListener('click', onTableClick);

  elements.lowStockBtn.addEventListener('click', () => {
    const threshold = Number(elements.lowStockInput.value) || 10;
    loadLowStock(threshold);
  });

  elements.showAllBtn.addEventListener('click', loadProducts);
}

// --------------------------------------------------------------------------
// Entry point
// --------------------------------------------------------------------------
export function initInventoryPage() {
  const elements = cacheElements();
  renderer = createRenderer(elements, { onSave: handleSave });
  renderer.init();
  initEvents(elements);
  loadProducts();
}

document.addEventListener('DOMContentLoaded', initInventoryPage);