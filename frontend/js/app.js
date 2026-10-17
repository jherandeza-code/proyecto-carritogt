// Direccion de la API (Spring Boot)
const API = 'http://localhost:8080/api';

// Codigo del visitante: reemplaza al cod_usr que el PHP guardaba en la sesion
function codUsuario() {
    let codigo = localStorage.getItem('codUsuario');
    if (!codigo) {
        codigo = crypto.randomUUID();
        localStorage.setItem('codUsuario', codigo);
    }
    return codigo;
}

// Hace la peticion HTTP y convierte los errores en un mensaje legible (RF-08)
async function pedir(url, opciones) {
    const respuesta = await fetch(url, opciones);
    if (!respuesta.ok) {
        let mensaje = 'Error ' + respuesta.status;
        try {
            const error = await respuesta.json();
            if (error.message) mensaje = error.message;
        } catch (e) { }
        throw new Error(mensaje);
    }
    return respuesta.status === 204 ? null : respuesta.json();
}

// Muestra un aviso en la pagina en lugar de bloquearla
function mostrarMensaje(texto, esError) {
    const caja = document.getElementById('mensaje');
    caja.textContent = texto;
    caja.className = esError ? 'mensaje mensaje-error' : 'mensaje mensaje-ok';
}
