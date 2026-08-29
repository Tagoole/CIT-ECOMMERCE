
// --------------------------------------------------------------------------
// INVENTORY RENDERER - pure UI layer
// Handles every DOM change for the inventory page: dynamic form fields, the
// add/edit modal, the product table, and status/error bars.
//
// It never calls the network or localStorage. The controller passes in the
// DOM elements it needs and the callback to trigger when the form is saved
// (onSave). Everything it returns is used by the controller after an action.
// --------------------------------------------------------------------------

// Schema - one source of truth for the product form. Add/remove a field here
// and both the modal form and the table stay in sync.
export const productFields = [
  { name: 'name', label: 'Product Name', type: 'text', required: true },
  { name: 'description', label: 'Description', type: 'textarea', required: false },
  { name: 'price', label: 'Price', type: 'number', required: true, step: '0.01', min: '0' },
  { name: 'quantityInStock', label: 'Quantity In Stock', type: 'number', required: true, min: '0' },
  { name: 'sku', label: 'SKU', type: 'text', required: false },
];

function escapeHtml(value) {
  return String(value)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

export function createRenderer(elements, { onSave } = {}) {
  let statusTimeout;

  // --------------------------
  // Modal form
  // --------------------------
  function renderFormFields(product = {}) {
    elements.formFields.innerHTML = productFields.map((field) => {
      const value = product[field.name] ?? '';
      const requiredAttr = field.required ? 'required' : '';

      if (field.type === 'textarea') {
        return `
          <label class="form-field">
            <span>${field.label}</span>
            <textarea name="${field.name}" ${requiredAttr}>${escapeHtml(value)}</textarea>
          </label>`;
      }

      const extra = [
        field.step ? `step="${field.step}"` : '',
        field.min !== undefined ? `min="${field.min}"` : '',
      ].join(' ');

      return `
        <label class="form-field">
          <span>${field.label}</span>
          <input type="${field.type}" name="${field.name}" value="${escapeHtml(value)}" ${requiredAttr} ${extra} />
        </label>`;
    }).join('');
  }

  function readFormData() {
    const formData = new FormData(elements.form);
    const data = {};

    productFields.forEach((field) => {
      const raw = formData.get(field.name);
      data[field.name] = field.type === 'number' ? Number(raw) : raw;
    });

    return data;
  }

  function openModal(isEditing, product = {}) {
    elements.modalTitle.textContent = isEditing ? 'Edit Product' : 'Add Product';
    renderFormFields(product);
    elements.modal.showModal();
  }

  function closeModal() {
    elements.modal.close();
    elements.form.reset();
  }

  // --------------------------
  // Table
  // --------------------------
  function renderTable(list) {
    if (!list.length) {
      elements.tableBody.innerHTML = `<tr><td colspan="6" class="empty-row">No products found.</td></tr>`;
      return;
    }

    elements.tableBody.innerHTML = list.map((p) => `
      <tr data-id="${p.id}" class="${p.quantityInStock <= 0 ? 'row-out-of-stock' : ''}">
        <td>${escapeHtml(p.name)}</td>
        <td>${escapeHtml(p.description ?? '')}</td>
        <td>${Number(p.price).toFixed(2)}</td>
        <td class="qty-cell">${p.quantityInStock}</td>
        <td>${escapeHtml(p.sku ?? '')}</td>
        <td class="actions-cell">
          <button class="btn-icon" data-action="dec" title="Reduce stock by 1">−</button>
          <button class="btn-icon" data-action="inc" title="Add 1 to stock">+</button>
          <button class="btn-link" data-action="availability">Check</button>
          <button class="btn-link" data-action="edit">Edit</button>
          <button class="btn-link btn-danger" data-action="delete">Delete</button>
        </td>
      </tr>
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
    elements.form.addEventListener('submit', (event) => {
      event.preventDefault();
      onSave?.(readFormData());
    });
  }

  return { init, openModal, closeModal, renderTable, showStatus, showError };
}