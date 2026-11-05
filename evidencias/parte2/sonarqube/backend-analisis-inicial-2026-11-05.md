# SonarQube: Ejercicio5MyAPIGestor, análisis inicial

- **Fecha:** 2026-11-05 16:24 UTC (Jenkins `MicroservicioBack` build #1)
- **Fuente:** API de SonarQube (`/api/measures/component` y `/api/issues/search`), proyecto `Ejercicio5MyAPIGestor`
- **Estado:** análisis previo a las pruebas nuevas. Las pruebas existen en local, pero todavía no están en GitHub.

## Medidas (Overall Code)

| Métrica | Valor |
|---|---|
| Quality Gate | OK (Passed) |
| Cobertura total | 5.1 % |
| Cobertura de líneas | 5.9 % |
| Cobertura de ramas | 0.0 % |
| Líneas de código (ncloc) | 791 |
| Duplicación | 0.0 % |
| Bugs | 15 (Reliability C) |
| Vulnerabilidades | 18 (Security D) |
| Code smells | 5 (Maintainability A) |
| Security hotspots | 0 |

## Incidencias abiertas: 38

| Regla | Tipo | Severidad | Cantidad | Qué señala |
|---|---|---|---|---|
| `java:S4684` | Vulnerabilidad | CRITICAL | 18 | Los endpoints reciben la **entidad JPA** directamente (`@RequestBody Usuario`, etc.). Hay que usar un DTO para evitar *mass assignment*. |
| `java:S2159` | Bug | MAJOR | 15 | `if(!x.equals(null))`: comparar contra `null` con `equals` siempre da `false`. Hay que usar `x != null`. Es el mismo código muerto que deja 15 ramas sin cubrir en JaCoCo. |
| `java:S6813` | Code smell | MAJOR | 5 | Inyección por campo (`@Autowired` en atributo). Se recomienda la inyección por constructor. |

### Ubicación (archivo:línea)

- **S4684:**
  - AsignacionesController: 38, 51, 63
  - ComentariosController: 26, 44, 56, 68
  - ProyectosController: 49, 62, 74
  - TareasController: 26, 44, 56, 68
  - UsuarioController: 25, 56, 67, 78
- **S2159:**
  - AsignacionesController: 41, 54, 66
  - ComentariosController: 47, 59, 71
  - ProyectosController: 52, 65, 77
  - TareasController: 47, 59, 71
  - UsuarioController: 58, 69, 80
- **S6813:** Asignaciones:22, Comentarios:21, Proyectos:21, Tareas:22, Usuario:21 (los 5 `Controller`)
