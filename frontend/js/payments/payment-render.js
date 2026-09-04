// --------------------------------------------------------------------------
// PAYMENT RENDERER - pure UI layer
// Handles tab switching, payment forms, history tables, and status bars.
// Never calls the network directly.
// --------------------------------------------------------------------------

function escapeHtml(value) {
  return String(value)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

// --------------------------------------------------------------------------
// Field schemas for each provider
// --------------------------------------------------------------------------
export const paymentFields = {
  paypal: [
    { name: 'email', label: 'Email', type: 'email', required: true },
    { name: 'amount', label: 'Amount', type: 'number', required: true, step: '0.01', min: '0.01' },
    { name: 'currency', label: 'Currency', type: 'text', required: true, placeholder: 'USD' },
  ],
  mtn: [
    { name: 'phoneNumber', label: 'Phone Number', type: 'tel', required: true, placeholder: '+256700000000' },
    { name: 'amount', label: 'Amount', type: 'number', required: true, step: '0.01', min: '0.01' },
    { name: 'currency', label: 'Currency', type: 'text', required: true, placeholder: 'UGX' },
  ],
  creditCard: [
    { name: 'cardNumber', label: 'Card Number', type: 'text', required: true, placeholder: '4111111111111111' },
    { name: 'cardHolder', label: 'Card Holder', type: 'text', required: true, placeholder: 'John Doe' },
    { name: 'amount', label: 'Amount', type: 'number', required: true, step: '0.01', min: '0.01' },
    { name: 'currency', label: 'Currency', type: 'text', required: true, placeholder: 'USD' },
  ],
  airtel: [
    { name: 'phoneNumber', label: 'Phone Number', type: 'tel', required: true, placeholder: '+256700000000' },
    { name: 'amount', label: 'Amount', type: 'number', required: true, step: '0.01', min: '0.01' },
    { name: 'currency', label: 'Currency', type: 'text', required: true, placeholder: 'UGX' },
  ],
};

// --------------------------------------------------------------------------
// Table column schemas for each provider
// --------------------------------------------------------------------------
const tableColumns = {
  paypal: ['id', 'email', 'amount', 'currency', 'status', 'createdAt'],
  mtn: ['id', 'phoneNumber', 'amount', 'currency', 'status', 'createdAt'],
  creditCard: ['id', 'cardNumber', 'cardHolder', 'amount', 'currency', 'status', 'createdAt'],
  airtel: ['id', 'phoneNumber', 'amount', 'currency', 'status', 'createdAt'],
};

const columnLabels = {
  id: 'ID',
  email: 'Email',
  phoneNumber: 'Phone',
  cardNumber: 'Card Number',
  cardHolder: 'Card Holder',
  amount: 'Amount',
  currency: 'Currency',
  status: 'Status',
  createdAt: 'Date',
};

export function createRenderer(elements, { onProcess } = {}) {
  let statusTimeout;

  // --------------------------
  // Tabs
  // --------------------------
  function switchTab(provider) {
    elements.tabBtns.forEach((btn) => {
      btn.classList.toggle('active', btn.dataset.provider === provider);
    });
    elements.tabPanels.forEach((panel) => {
      panel.hidden = panel.dataset.provider !== provider;
    });
  }

  // --------------------------
  // Form
  // --------------------------
  function renderForm(provider) {
    const fields = paymentFields[provider];
    elements.formFields.innerHTML = fields.map((field) => {
      const requiredAttr = field.required ? 'required' : '';
      const placeholder = field.placeholder ? `placeholder="${field.placeholder}"` : '';
      const extra = [
        field.step ? `step="${field.step}"` : '',
        field.min !== undefined ? `min="${field.min}"` : '',
      ].join(' ');

      return `
        <label class="form-field">
          <span>${field.label}</span>
          <input type="${field.type}" name="${field.name}" ${requiredAttr} ${placeholder} ${extra} />
        </label>`;
    }).join('');
  }

  function readFormData() {
    const formData = new FormData(elements.form);
    const data = {};
    const activeProvider = elements.tabBtns.find((btn) => btn.classList.contains('active'))?.dataset.provider;
    const fields = paymentFields[activeProvider] || [];

    fields.forEach((field) => {
      const raw = formData.get(field.name);
      data[field.name] = field.type === 'number' ? Number(raw) : raw;
    });

    return data;
  }

  // --------------------------
  // Table
  // --------------------------
  function renderTable(provider, list) {
    const cols = tableColumns[provider];
    const tbody = elements.tableBodies[provider];

    if (!tbody) return;

    if (!list.length) {
      tbody.innerHTML = `<tr><td colspan="${cols.length}" class="empty-row">No payments found.</td></tr>`;
      return;
    }

    tbody.innerHTML = list.map((row) => `
      <tr data-id="${row.id}">
        ${cols.map((col) => {
          let value = row[col];
          if (col === 'amount') value = Number(value).toFixed(2);
          if (col === 'status') value = `<span class="status-badge status-${String(value).toLowerCase()}">${escapeHtml(value)}</span>`;
          else if (col === 'createdAt') value = value ? new Date(value).toLocaleString() : '-';
          else value = escapeHtml(String(value ?? '-'));
          return `<td>${value}</td>`;
        }).join('')}
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
      onProcess?.(readFormData());
    });

    elements.tabBtns.forEach((btn) => {
      btn.addEventListener('click', () => {
        switchTab(btn.dataset.provider);
        renderForm(btn.dataset.provider);
      });
    });
  }

  return {
    init,
    switchTab,
    renderForm,
    renderTable,
    showStatus,
    showError,
  };
}
