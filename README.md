# 🗂️ KEYBLOCK - APP DE NOTAS MULTIUSUARIO

**Proyecto final del módulo de Programación (DAM) desarrollado en Java.** KeyBlock es una aplicación de escritorio para la gestión de notas personales, con interfaz gráfica estilo IDE, base de datos MySQL y funcionalidades como etiquetas con hashtags, sección de contraseñas y roles de acceso.

---

## 🧠 OBJETIVO DEL PROYECTO

Crear una aplicación funcional, visualmente cuidada y técnicamente estructurada, poniendo en práctica los conceptos vistos en clase: POO, Swing, JDBC, modularidad y diseño de interfaces. El proyecto se desarrolla como una experiencia completa de trabajo en equipo.

---

## 📁 ESTRUCTURA DEL CÓDIGO

Organizado por paquetes siguiendo el patrón MVC:

- `app`: punto de entrada (`Main.java`)
- `modelo`: datos (`Usuario`, `Nota`, `Hashtag`, `ContraseñaGuardada`, etc.)
- `vista`: interfaz gráfica (`PrincipalVista`, `LoginVista`, `PanelContenido`, etc.)
- `controlador`: lógica de interacción (`LoginControlador`, `ActualizarNota`, etc.)
- `bbdd`: conexión con la base de datos y queries (`GestorBBDD`, `NotaDAO`)

---

## 💻 FUNCIONALIDADES

### 👤 Gestión de usuarios

- Inicio de sesión con verificación
- Roles diferenciados: usuario normal y administrador
- Carga personalizada de notas según el usuario logueado

### 📝 Gestión de notas

- Crear, editar y eliminar notas
- Guardado únicamente al pulsar “Guardar”
- Hashtags detectados automáticamente y coloreados
- Filtro de notas por hashtags
- Estilos visuales mediante markdown simplificado:
  - `### título` → encabezado
  - `**negrita**`, `*cursiva*`, `~~tachado~~`, ``código``

### 🔐 Contraseñas protegidas

- Zona exclusiva accesible desde el panel de navegación
- Datos cifrados (base64)
- Visualización y gestión solo por parte del usuario logueado

### ⚙️ Panel de administración (rol admin)

- Vista diferenciada con posibles gestiones extra (estructura preparada)
- Acceso limitado según rol

---

## 🎨 DISEÑO Y EXPERIENCIA

- Modo oscuro con estética de editor de código
- Botón flotante "+" para añadir notas
- Iconos y disposición adaptada a tareas de escritura
- Uso de `CardLayout` para navegación entre secciones
- Clase `EstiloVisual` centraliza colores y fuentes

---

## 🔧 TECNOLOGÍAS USADAS

- Java 17 + Swing
- MySQL + JDBC
- IntelliJ IDEA + XAMPP (entorno local)
- Git y GitHub (control de versiones)
- Regex para estilizado de texto y detección de etiquetas

---

## 🧪 MODO PRUEBA

Durante el desarrollo, se utilizó la clase `NotasTemporal` para mostrar ejemplos sin necesidad de base de datos. Ya no es necesaria salvo para pruebas aisladas.

---

## 👥 EQUIPO Y MÉTODO DE TRABAJO

El desarrollo de KeyBlock ha sido totalmente colaborativo. Aunque se asignaron algunas partes concretas según afinidad (interfaz, lógica, base de datos…), el trabajo se organizó con una filosofía de implicación conjunta: todos los miembros del equipo han revisado y probado las distintas secciones del proyecto.

Se dividieron responsabilidades de forma lógica:
- **Diseño de interfaz** y experiencia de usuario (vista)
- **Lógica y validaciones** entre botones y datos (controlador)
- **Estructura de datos** y conexión con la base de datos (modelo + bbdd)

El uso de GitHub, reuniones informales y pruebas continuas permitieron mantener la coherencia general del proyecto y facilitar el aprendizaje compartido.


---

## ✅ ESTADO ACTUAL

- ✔ Aplicación funcional completa
- ✔ Gestión por usuarios terminada
- ✔ Estilos de texto implementados
- ✔ Hashtags funcionales y visuales
- ✔ Zona de contraseñas operativa
- ✔ Base de datos estructurada y conectada
- ⏳ Panel admin preparado (funcionalidades en desarrollo)

---

## 🧩 EXPERIENCIA

El proyecto ha sido desarrollado en equipo, con división clara de tareas, seguimiento en GitHub, revisiones compartidas y un enfoque realista sobre el flujo de trabajo. Cada clase ha sido comentada de forma pedagógica, respetando lo visto en clase.

---

## 👥 AUTORES

- [Alberto Estepa Gómez](https://github.com/SantanaOlmo)  
- [Juan Jiménez Nieto](https://github.com/True-Felas)  
- [David Gutiérrez Ortiz](https://github.com/DavidLazaro08)  

---

## 📌 REPOSITORIO ORIGINAL

[https://github.com/SantanaOlmo/NoteLab](https://github.com/SantanaOlmo/NoteLab)

---

Gracias por revisar nuestro trabajo.  
