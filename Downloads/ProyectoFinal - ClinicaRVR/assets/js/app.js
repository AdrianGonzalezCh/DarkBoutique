
const app = {
  init(){
    this.bindMenu();
    this.bindFilters();
    this.bindForms();
    this.fillYear();
  },
  bindMenu(){
    const btn = document.querySelector('.menu-toggle');
    if(btn){
      btn.addEventListener('click', () => document.body.classList.toggle('nav-open'));
    }
  },
  bindFilters(){
    const chips = document.querySelectorAll('[data-filter]');
    if(!chips.length) return;
    chips.forEach(chip => {
      chip.addEventListener('click', () => {
        chips.forEach(c => c.classList.remove('active'));
        chip.classList.add('active');
        const value = chip.dataset.filter;
        document.querySelectorAll('.treatment-item').forEach(item => {
          item.classList.toggle('hidden', !(value === 'all' || item.dataset.category === value));
        });
      });
    });
  },
  bindForms(){
    const contacto = document.getElementById('contactoForm');
    if(contacto){
      contacto.addEventListener('submit', e => {
        e.preventDefault();
        const formData = new FormData(contacto);
        const payload = Object.fromEntries(formData.entries());
        payload.fecha = new Date().toISOString();
        localStorage.setItem('contacto_rvr', JSON.stringify(payload));
        this.showNotice(contacto, 'Mensaje registrado correctamente en modo demostración. También puedes conectarlo al backend PHP incluido.', 'success');
        contacto.reset();
      });
    }

    const agenda = document.getElementById('agendaForm');
    if(agenda){
      agenda.addEventListener('submit', e => {
        e.preventDefault();
        const formData = new FormData(agenda);
        const payload = Object.fromEntries(formData.entries());
        payload.fecha = new Date().toISOString();
        localStorage.setItem('agenda_rvr', JSON.stringify(payload));
        this.showNotice(agenda, 'La cita fue simulada correctamente. En servidor local, el formulario puede enviarse al backend y a MySQL.', 'success');
        agenda.reset();
      });
    }

    const login = document.getElementById('loginForm');
    if(login){
      login.addEventListener('submit', e => {
        e.preventDefault();
        localStorage.setItem('usuario_demo_rvr', JSON.stringify({
          nombre: 'Ana Vargas',
          correo: document.getElementById('loginEmail').value || 'ana@gmail.com'
        }));
        window.location.href = 'perfil.html';
      });
    }

    const registro = document.getElementById('registroForm');
    if(registro){
      registro.addEventListener('submit', e => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(registro).entries());
        localStorage.setItem('registro_demo_rvr', JSON.stringify(data));
        window.location.href = 'registro-detalle.html';
      });
    }

    const detalle = document.getElementById('detalleRegistroForm');
    if(detalle){
      detalle.addEventListener('submit', e => {
        e.preventDefault();
        const prev = JSON.parse(localStorage.getItem('registro_demo_rvr') || '{}');
        const now = Object.fromEntries(new FormData(detalle).entries());
        const user = {...prev, ...now};
        localStorage.setItem('usuario_demo_rvr', JSON.stringify({
          nombre: user.nombre || 'Ana Vargas',
          correo: user.email || 'ana@gmail.com',
          sexo: user.sexo || 'Mujer',
          edad: user.fecha_nacimiento || '1999-01-01',
          estatura: user.estatura || '152',
          peso: user.peso || '53'
        }));
        window.location.href = 'perfil.html';
      });
    }

    if(document.body.dataset.page === 'perfil'){
      const user = JSON.parse(localStorage.getItem('usuario_demo_rvr') || '{}');
      if(user.nombre){
        const target = document.getElementById('perfilNombre');
        if(target) target.textContent = user.nombre;
        const email = document.getElementById('perfilEmail');
        if(email) email.textContent = user.correo || '';
      }
    }
  },
  showNotice(form, message, type='success'){
    let notice = form.querySelector('.notice');
    if(!notice){
      notice = document.createElement('div');
      notice.className = 'notice';
      form.appendChild(notice);
    }
    notice.className = `notice ${type}`;
    notice.textContent = message;
  },
  fillYear(){
    document.querySelectorAll('.current-year').forEach(el => el.textContent = new Date().getFullYear());
  }
};
document.addEventListener('DOMContentLoaded', () => app.init());
