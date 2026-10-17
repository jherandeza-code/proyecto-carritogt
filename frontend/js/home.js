const lista = document.getElementById('lista-productos');

// RF-01 y RF-02: pide los productos a la API y los dibuja
async function cargarProductos() {
    try {
        const productos = await pedir(`${API}/productos`);
        lista.innerHTML = '';
        productos.forEach(producto => {
            const item = document.createElement('div');
            item.className = 'producto-item';
            item.innerHTML = `
                <div class="producto-imagen"><img src="${producto.imagen}" alt="${producto.nombre}"></div>
                <div class="producto-pie-mosaico">
                    <div class="producto-titulo">${producto.nombre}</div>
                    <div class="producto-precio">${producto.precio.toFixed(2)}</div>
                    <div class="producto-acciones"><button class="ripple">Agregar</button></div>
                </div>`;
            item.querySelector('button').addEventListener('click', () => agregar(producto.idProducto));
            lista.appendChild(item);
        });
    } catch (error) {
        mostrarMensaje('No se pudieron cargar los productos: ' + error.message, true);
    }
}

// RF-03: agrega un producto al carrito con POST
async function agregar(idProducto) {
    try {
        await pedir(`${API}/carrito?usuario=${codUsuario()}&idProducto=${idProducto}`, { method: 'POST' });
        mostrarMensaje('Producto agregado al carrito', false);
    } catch (error) {
        mostrarMensaje('No se pudo agregar: ' + error.message, true);
    }
}

cargarProductos();
