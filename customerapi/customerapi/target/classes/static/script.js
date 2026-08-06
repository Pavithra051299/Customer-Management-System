const API_URL = "http://localhost:8080/customers";

// Load customers when page loads
window.onload = function () {
    loadCustomers();
};

// ===========================
// GET ALL CUSTOMERS
// ===========================
function loadCustomers() {

    fetch(API_URL)
        .then(response => response.json())
        .then(customers => {

            const table = document.getElementById("customerTable");
            table.innerHTML = "";

            customers.forEach(customer => {

                const row = `
                    <tr>
                        <td>${customer.id}</td>
                        <td>${customer.firstName}</td>
                        <td>${customer.lastName}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phone}</td>
                        <td>${customer.city}</td>
                        <td>

                            <button class="edit-btn"
                                onclick="editCustomer(
                                    ${customer.id},
                                    '${customer.firstName}',
                                    '${customer.lastName}',
                                    '${customer.email}',
                                    '${customer.phone}',
                                    '${customer.city}'
                                )">

                                Edit

                            </button>

                            <button class="delete-btn"
                                onclick="deleteCustomer(${customer.id})">

                                Delete

                            </button>

                        </td>
                    </tr>
                `;

                table.innerHTML += row;

            });

        });

}

// ===========================
// SAVE / UPDATE
// ===========================
function saveCustomer() {

    const id = document.getElementById("customerId").value;

    const customer = {

        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        phone: document.getElementById("phone").value,
        city: document.getElementById("city").value

    };

    if (id === "") {

        // POST

        fetch(API_URL, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(customer)

        })
            .then(() => {

                clearForm();
                loadCustomers();

            });

    } else {

        // PUT

        fetch(API_URL + "/" + id, {

            method: "PUT",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(customer)

        })
            .then(() => {

                clearForm();
                loadCustomers();

            });

    }

}

// ===========================
// EDIT
// ===========================
function editCustomer(id, firstName, lastName, email, phone, city) {

    document.getElementById("customerId").value = id;

    document.getElementById("firstName").value = firstName;
    document.getElementById("lastName").value = lastName;
    document.getElementById("email").value = email;
    document.getElementById("phone").value = phone;
    document.getElementById("city").value = city;

}

// ===========================
// DELETE
// ===========================
function deleteCustomer(id) {

    if (!confirm("Delete this customer?")) {
        return;
    }

    fetch(API_URL + "/" + id, {

        method: "DELETE"

    })
        .then(() => {

            loadCustomers();

        });

}

// ===========================
// CLEAR FORM
// ===========================
function clearForm() {

    document.getElementById("customerId").value = "";

    document.getElementById("firstName").value = "";
    document.getElementById("lastName").value = "";
    document.getElementById("email").value = "";
    document.getElementById("phone").value = "";
    document.getElementById("city").value = "";

}