# Evidencias: microserviciobackend (Ejercicio5MyAPIGestor)

Práctica 3 · Desarrollo e Integración Continua. Los documentos técnicos completos están en `documentos/`.

## parte1/: Actualización a Java 21

| Archivo | Qué demuestra |
|---|---|
| `pom-ORIGINAL.xml` / `pom-ACTUALIZADO.xml` | POM antes (Spring Boot 2.7.17, Java 11) y después (3.5.16, Java 21, Surefire 3.5.3, JaCoCo 0.8.13, Sonar 5.5.0.6356) |
| `cambios-migracion-java21.diff` | Todos los cambios de la migración (POM, `javax` → `jakarta`, configuración) |
| `build-local-java21.log` | Compilación y pruebas locales con JDK 21 |
| `jenkins-java-version-21.txt` | Jenkins ejecutando con `openjdk 21.0.13` y Maven 3.9.0 |

## parte2/: Pruebas, seguridad y pipelines

| Carpeta | Contenido |
|---|---|
| `pruebas-y-cobertura/` | 41 pruebas (`surefire/`), `jacoco.xml`, reporte HTML de JaCoCo (`reporte-jacoco-html.zip`, abrir `index.html`) y resumen: **100 %** de líneas, ramas y métodos |
| `jenkins/` | Logs de todos los builds de `MicroservicioBack`, incluidos los fallidos; configuración del job, de JDK21, de Maven y del servidor de SonarQube (sin secretos) |
| `sonarqube/` | Análisis inicial (5.1 %, 38 incidencias) y análisis con las pruebas (95.2 %) |
| `semgrep/` | Reportes SAST excluyendo `target`: backend 0 hallazgos; base de datos 0 hallazgos |
| `incidencias/` | Clasificación y atención de cada incidencia, y el resultado después: **38 → 0** |
| `base-de-datos/` | Pipeline `MicroservicioBD` (usa `db/`): log, configuración, análisis del esquema, esquema exportado, SQLFluff y Semgrep |
| `infraestructura/` | `docker-compose.yml` y Dockerfile de Jenkins (JDK 21 y cliente de Docker) junto con SonarQube |
| `capturas/` | Jenkins (Tools, etapas, los tres pipelines), SonarQube antes y después, y los diffs en GitHub |

## Commits

| Commit | Cambio |
|---|---|
| `39c5fc4` | Estado original |
| `ece0481` | Migración a Java 21 y Spring Boot 3.5.16 |
| `9354efa` | Pruebas unitarias (cobertura 100 % de líneas) |
| `6ec50c0` | Atención de incidencias de SonarQube (S2159, S6813, S4684) |
