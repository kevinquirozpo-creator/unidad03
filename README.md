# Cotizacion — Cotización (puntos 1–24)
Abrir la carpeta que contiene pom.xml en NetBeans (File > Open Project). Usar JDK 17 o superior. Clean and Build, después Run. Menú Prácticas > Cotización.
En MySQL Workbench ejecutar cotizacion.sql. La conexión usa localhost:3306/cotizaciones, usuario root y contraseña vacía por defecto; establecer DB_URL, DB_USER y DB_PASSWORD como variables de entorno antes de abrir NetBeans si son diferentes. No subir contraseñas a GitHub.
El formulario se construye por código Swing; no incluye archivo .form de NetBeans. El menú es independiente; si ya existe un proyecto con Terreno/Producto, copiar las cuatro clases de cotización y dependencias al proyecto existente y agregar la opción al menú sin reemplazarlo. Para un JDialog existente, usar new jdlCotizacion((java.awt.Frame)null,true).setVisible(true).
Los botones Buscar, Guardar, Habilitar y Deshabilitar de la interfaz están desactivados porque sus eventos no forman parte de los puntos 1–24; sus métodos JDBC sí están implementados.
Prueba: abrir Cotización (campos bloqueados); Nuevo (campos activos); introducir número C001, descripción Laptop, precio 12000, porcentaje 20, plazo 12 (resultados 2400, 9600, 800); Limpiar (vacío, sigue activo); Cancelar (vacío, bloqueado); Cerrar (confirmar No y luego Sí).
UML.puml: fuente PlantUML editable del diagrama.
GitHub: crear repositorio público vacío y ejecutar git init, git add ., git commit -m "Cotizacion puntos 1 al 24", git branch -M main, git remote add origin URL_DEL_REPOSITORIO, git push -u origin main.
