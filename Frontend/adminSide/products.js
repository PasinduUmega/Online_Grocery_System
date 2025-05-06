import {
    getProducts,
    addProduct,
    updateProduct,
    deleteProduct
  } from './productService.js';
  
  const productForm = document.getElementById("product-form");
  const productTable = document.getElementById("product-table").querySelector("tbody");
  const addForm = document.getElementById("add-form");
  let editingId = null;
  
  function renderProductRow(product) {
    const row = document.createElement("tr");
    row.setAttribute("data-id", product.id);
    row.innerHTML = `
      <td>${product.id}</td>
      <td>${product.name}</td>
      <td>${product.category}</td>
      <td>${product.price}</td>
      <td>${product.quantity}</td>
      <td><img src="${product.imageUrl}" alt="Image"/></td>
      <td>
        <button class="btn small edit">Edit</button>
        <button class="btn small delete">Delete</button>
      </td>
    `;
    productTable.appendChild(row);
  }
  
  async function loadProducts() {
    const products = await getProducts();
    productTable.innerHTML = "";
    products.forEach(renderProductRow);
  }
  
  productForm.addEventListener("submit", async function (e) {
    e.preventDefault();
    const product = {
      name: document.getElementById('product-name').value,
      category: document.getElementById('category').value,
      price: parseFloat(document.getElementById('price').value),
      quantity: parseInt(document.getElementById('quantity').value),
      imageUrl: document.getElementById('image').value
    };
  
    if (editingId !== null) {
      await updateProduct(editingId, product);
      editingId = null;
    } else {
      await addProduct(product);
    }
  
    productForm.reset();
    addForm.style.display = "none";
    loadProducts();
  });
  
  productTable.addEventListener("click", async function (e) {
    const target = e.target;
    const row = target.closest("tr");
    const id = row.getAttribute("data-id");
  
    if (target.classList.contains("edit")) {
      editingId = id;
      document.getElementById("product-name").value = row.cells[1].textContent;
      document.getElementById("category").value = row.cells[2].textContent;
      document.getElementById("price").value = row.cells[3].textContent;
      document.getElementById("quantity").value = row.cells[4].textContent;
      document.getElementById("image").value = row.cells[5].querySelector("img").src;
      addForm.style.display = "block";
    }
  
    if (target.classList.contains("delete")) {
      await deleteProduct(id);
      loadProducts();
    }
  });
  
  document.getElementById("show-form-btn").addEventListener("click", () => {
    addForm.style.display = addForm.style.display === "block" ? "none" : "block";
    productForm.reset();
    editingId = null;
  });
  
  loadProducts();
  