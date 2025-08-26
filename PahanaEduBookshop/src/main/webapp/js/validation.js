// webapp/js/validation.js
function validateLogin() {
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    
    if (username.trim() === '') {
        alert('Please enter username');
        return false;
    }
    
    if (password.trim() === '') {
        alert('Please enter password');
        return false;
    }
    
    if (password.length < 6) {
        alert('Password must be at least 6 characters');
        return false;
    }
    
    return true;
}

function validateCustomerForm() {
    var name = document.getElementById('name').value;
    var telephone = document.getElementById('telephone').value;
    var email = document.getElementById('email').value;
    
    if (name.trim() === '') {
        alert('Please enter customer name');
        return false;
    }
    
    // Validate phone number (Sri Lankan format)
    var phoneRegex = /^0[0-9]{9}$/;
    if (!phoneRegex.test(telephone)) {
        alert('Please enter a valid phone number (10 digits starting with 0)');
        return false;
    }
    
    // Validate email
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email && !emailRegex.test(email)) {
        alert('Please enter a valid email address');
        return false;
    }
    
    return true;
}

function validateItemForm() {
    var itemName = document.getElementById('itemName').value;
    var price = document.getElementById('price').value;
    var quantity = document.getElementById('quantity').value;
    
    if (itemName.trim() === '') {
        alert('Please enter item name');
        return false;
    }
    
    if (price <= 0) {
        alert('Price must be greater than 0');
        return false;
    }
    
    if (quantity < 0) {
        alert('Quantity cannot be negative');
        return false;
    }
    
    return true;
}

function confirmDelete(message) {
    return confirm(message || 'Are you sure you want to delete this record?');
}

// Auto-calculate bill total
function calculateTotal() {
    var items = document.querySelectorAll('.bill-item');
    var total = 0;
    
    items.forEach(function(item) {
        var quantity = parseFloat(item.querySelector('.quantity').value) || 0;
        var price = parseFloat(item.querySelector('.price').value) || 0;
        var itemTotal = quantity * price;
        item.querySelector('.item-total').value = itemTotal.toFixed(2);
        total += itemTotal;
    });
    
    var discount = parseFloat(document.getElementById('discount').value) || 0;
    var netTotal = total - discount;
    
    document.getElementById('totalAmount').value = total.toFixed(2);
    document.getElementById('netAmount').value = netTotal.toFixed(2);
}

// Add dynamic form fields for bill items
function addBillItem() {
    var container = document.getElementById('billItemsContainer');
    var itemCount = container.children.length + 1;
    
    var newItem = document.createElement('div');
    newItem.className = 'bill-item';
    newItem.innerHTML = `
        <div class="form-row">
            <div class="form-group col-md-4">
                <label>Item ${itemCount}</label>
                <select name="itemId${itemCount}" class="form-control item-select" onchange="updatePrice(this)">
                    <option value="">Select Item</option>
                    <!-- Options will be populated from database -->
                </select>
            </div>
            <div class="form-group col-md-2">
                <label>Quantity</label>
                <input type="number" name="quantity${itemCount}" class="form-control quantity" 
                       onchange="calculateTotal()" min="1">
            </div>
            <div class="form-group col-md-2">
                <label>Price</label>
                <input type="number" name="price${itemCount}" class="form-control price" 
                       readonly>
            </div>
            <div class="form-group col-md-2">
                <label>Total</label>
                <input type="number" name="itemTotal${itemCount}" class="form-control item-total" 
                       readonly>
            </div>
            <div class="form-group col-md-2">
                <label>&nbsp;</label>
                <button type="button" class="btn btn-danger" onclick="removeBillItem(this)">Remove</button>
            </div>
        </div>
    `;
    
    container.appendChild(newItem);
}

function removeBillItem(button) {
    button.closest('.bill-item').remove();
    calculateTotal();
}

function updatePrice(selectElement) {
    // This would typically fetch price from server based on selected item
    // For demo purposes, using static values
    var priceInput = selectElement.closest('.bill-item').querySelector('.price');
    var selectedOption = selectElement.options[selectElement.selectedIndex];
    
    if (selectedOption.value) {
        // In real implementation, this would be fetched from server
        priceInput.value = selectedOption.getAttribute('data-price') || 0;
        calculateTotal();
    }
}