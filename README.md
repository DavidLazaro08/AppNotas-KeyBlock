# 📝 NOTASAPP - PROYECTO DE PROGRAMACIÓN

Este proyecto es una base para nuestra aplicación de notas. Ya incluye la estructura de carpetas y las clases Java mínimas para que podamos empezar a trabajar directamente desde Eclipse (o desde el repositorio en GitHub cuando lo tengamos).

---

## 📂 ESTRUCTURA PRINCIPAL

- `src/app`: contiene el `Main.java` para iniciar la aplicación.
- `src/modelo`: clases principales como `Usuario`, `Nota`, `Hashtag`, etc.
- `src/bbdd`: gestión de la base de datos (`GestorBBDD.java`).
- `src/vista`: interfaz gráfica (`Login`, `Principal`, etc.).
- `src/controlador`: clases donde se programará lo que ocurre cuando se pulsa un botón, se guarda una nota, etc.

---

## 🔄 FUNCIONAMIENTO INICIAL

- Al ejecutar `Main.java`, se abre una ventana de prueba simple.
- Cada parte del proyecto está preparada para que podamos trabajar de forma organizada y en paralelo.

---

## ✍️ REPARTO Y PRÓXIMOS PASOS

- Podemos dividirnos por áreas: interfaz, lógica, datos, etc.
- Lo siguiente sería:
  - Diseñar la base de datos (con tablas y relaciones).
  - Hacer funcional la interfaz de login.
  - Programar acciones como crear nota, editar, filtrar, etc.
  - Ir conectando cada parte poco a poco.

---

## 🚀 VAMOS

Gestion notas.

Sería importante que por cada usuario hubiese un documento .json con los nombres de las notas que hay, para que de esa manera fuese mas sencillo según se elija, crear las lineas de comando para ejecutar los select o los update.

### Patrones de búsqueda

````
Pattern pattern = Pattern.compile("### (.*)");

```
````

qué hace el código anterior? pattern registra una expresión regular, quen en el caso de ("### (.*)") apunta a todos los elementos que aparezcan después de tres almohadillas y un espacio. De esta manera podemos aplicar estilos diferentes a los elementos que haya en escritos en el txt.

Por otro lado tenemos Matcher

````
Pattern pattern = Pattern.compile("### (.*)");
Matcher matcher = pattern.matcher(text);

matcher.start(1); // para "###"
matcher.start(2); // para el texto después
```
````

Usamos `start(1)` y `end(1)` para aplicar estilo **solo** al texto tras `###`.


| Grupo | Qué captura         | Ejemplo              |
| ----- | -------------------- | -------------------- |
| `0`   | Coincidencia total   | `### Título grande` |
| `1`   | Lo que hay tras`###` | `Título grande`     |

G


| Formato en texto      | Estilo visual        | Regex (Java)        | Grupo útil |
| --------------------- | -------------------- | ------------------- | ----------- |
| `### Encabezado`      | Título (como H3)    | `### (.*)`          | `1`         |
| `**negrita**`         | Negrita              | `\\*\\*(.*?)\\*\\*` | `1`         |
| `*cursiva*`           | Cursiva              | `\\*(.*?)\\*`       | `1`         |
| `~~tachado~~`         | Tachado              | `~~(.*?)~~`         | `1`         |
| ``código``           | Texto estilo código | ``([^`]+)``         | `1`         |
| `- elemento de lista` | Viñeta simple       | `(?m)^- (.*)`       | `1`         |
| `> cita`              | Cita estilo bloque   | `(?m)^> (.*)`       | `1`         |
