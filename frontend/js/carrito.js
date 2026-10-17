const cuerpo = document.getElementById('cuerpo-carrito');
const tabla = document.getElementById('tabla-carrito');
const vacio = document.getElementById('carrito-vacio');

// RF-04: pide a la API solo los items de este visitante
async function cargarCarrito() {
    try {
        const items = await pedir(`${API}/carrito?usuario=${codUsuario()}`);
        cuerpo.innerHTML = '';
        tabla.hidden = items.length === 0;
        vacio.hidden = items.length !== 0;
        items.forEach(item => {
            const fila = document.createElement('tr');
            fila.innerHTML = `
                <td data-label="Producto"><img src="${item.producto.imagen}" alt="${item.producto.nombre}"></td>
                <td data-label="Nombre">${item.producto.nombre}</td>
                <td data-label="Código">${item.producto.codigo}</td>
                <td data-label="Cantidad">${item.cantidad}</td>
                <td data-label="Precio">${item.producto.precio.toFixed(2)}</td>
                <td><button class="ripple">Eliminar</button></td>`;
            fila.querySelector('button').addEventListener('click', () => eliminar(item.idShoppingCart));
            cuerpo.appendChild(fila);
        });
    } catch (error) {
        mostrarMensaje('No se pudo cargar el carrito: ' + error.message, true);
    }
}

// RF-05: elimina un item con DELETE y vuelve a dibujar el carrito
async function eliminar(idShoppingCart) {
    try {
        await pedir(`${API}/carrito/${idShoppingCart}`, { method: 'DELETE' });
        mostrarMensaje('Producto eliminado del carrito', false);
        cargarCarrito();
    } catch (error) {
        mostrarMensaje('No se pudo eliminar: ' + error.message, true);
    }
}

cargarCarrito();
