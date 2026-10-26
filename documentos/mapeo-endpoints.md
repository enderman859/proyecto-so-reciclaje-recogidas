# Mapeo de endpoints y DTOs

Cada endpoint se mapea a un DTO de request (lo que envía el cliente) y a un DTO de response (lo que responde el servidor). Los DTO se orientan al caso de uso de cada endpoint y no a cada entidad: las entidades de apoyo (`materiales`, `puntos_materiales`, `campanas_materiales` y `seguimientos_solicitud`) no se exponen, por eso no tienen DTO propio.

Todos los DTO son `record` de Java y están en el paquete `dto`.

## Resumen

| # | Verbo | Ruta | Request | Response | Éxito | Errores |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | GET | `/api/puntos` | - | `List<PuntoResponse>` | 200 | - |
| 2 | GET | `/api/puntos/{id}` | - | `PuntoResponse` | 200 | 404 |
| 3 | GET | `/api/campanas` | - | `List<CampanaResponse>` | 200 | - |
| 4 | GET | `/api/campanas/{id}` | - | `CampanaResponse` | 200 | 404 |
| 5 | GET | `/api/solicitudes-recogida?estado=` | - | `List<SolicitudRecogidaResponse>` | 200 | - |
| 6 | GET | `/api/solicitudes-recogida/{id}` | - | `SolicitudRecogidaResponse` | 200 | 404 |
| 7 | POST | `/api/campanas` | `CampanaRequest` | `CampanaResponse` | 201 | 400 |
| 8 | POST | `/api/solicitudes-recogida` | `SolicitudRecogidaRequest` | `SolicitudRecogidaResponse` | 201 | 400, 404 |
| 9 | PUT | `/api/campanas/{id}` | `CampanaRequest` | `CampanaResponse` | 200 | 400, 404 |
| 10 | PATCH | `/api/solicitudes/{id}/estado` | `CambioEstadoRequest` | `SolicitudRecogidaResponse` | 200 | 400, 404 |
| 11 | DELETE | `/api/solicitudes/{id}` | - | - | 204 | 400, 404 |

Convenciones: rutas en plural, filtros como parámetros en la URL, 200 para consultas y actualizaciones, 201 al crear, 204 al eliminar y 404 cuando el recurso no existe.

## DTO de response

### PuntoResponse

La ubicación se muestra aproximada (RN-06): no se exponen la dirección exacta, la latitud ni la longitud.

```json
{
  "id": 1,
  "nombre": "Punto Verde Centro",
  "ubicacionAproximada": "Barrio Centro, Manizales",
  "activo": true,
  "materiales": ["PLASTICO", "CARTON"]
}
```

### CampanaResponse

```json
{
  "id": 3,
  "nombre": "Recicla en octubre",
  "descripcion": "Campaña de recolección de plástico y cartón",
  "fechaInicio": "2026-10-01",
  "fechaFin": "2026-10-31",
  "materiales": ["PLASTICO", "CARTON"]
}
```

### SolicitudRecogidaResponse

```json
{
  "id": 10,
  "ciudadanoNombre": "Ana Gómez",
  "punto": "Punto Verde Centro",
  "campana": "Recicla en octubre",
  "material": "PLASTICO",
  "cantidadKg": 12.5,
  "fechaRecogida": "2026-10-25",
  "estado": "SOLICITADA",
  "fechaCreacion": "2026-10-20T19:30:00"
}
```

El contacto del ciudadano no se devuelve en la respuesta.

## DTO de request

### CampanaRequest (POST y PUT `/api/campanas`)

```json
{
  "nombre": "Recicla en octubre",
  "descripcion": "Campaña de recolección de plástico y cartón",
  "fechaInicio": "2026-10-01",
  "fechaFin": "2026-10-31",
  "materialIds": [1, 2]
}
```

Reglas: `fechaFin` no puede ser anterior a `fechaInicio`. El PUT solo se permite antes de que la campaña inicie.

### SolicitudRecogidaRequest (POST `/api/solicitudes-recogida`)

```json
{
  "ciudadanoNombre": "Ana Gómez",
  "ciudadanoContacto": "3001234567",
  "puntoId": 1,
  "campanaId": 3,
  "materialId": 1,
  "cantidadKg": 12.5,
  "fechaRecogida": "2026-10-25"
}
```

Reglas de negocio: RN-02 la cantidad debe ser positiva, RN-03 la fecha debe estar dentro de la campaña y RN-04 el material debe ser compatible con el punto y con la campaña. El estado inicial de la solicitud es `SOLICITADA`.

### CambioEstadoRequest (PATCH `/api/solicitudes/{id}/estado`)

```json
{
  "estado": "PROGRAMADA",
  "observacion": "Recogida asignada para la mañana"
}
```

Estados posibles (RN-05): `SOLICITADA`, `PROGRAMADA`, `RECOLECTADA` y `CANCELADA`. Cada cambio queda registrado en `seguimientos_solicitud`.

## Detalle por endpoint

1. **GET `/api/puntos`**: lista los puntos con sus materiales aceptados (RN-01).
2. **GET `/api/puntos/{id}`**: un punto por ID. 404 si no existe.
3. **GET `/api/campanas`**: lista las campañas vigentes.
4. **GET `/api/campanas/{id}`**: una campaña por ID. 404 si no existe.
5. **GET `/api/solicitudes-recogida`**: lista las solicitudes; el parámetro `estado` es opcional.
6. **GET `/api/solicitudes-recogida/{id}`**: una solicitud por ID. 404 si no existe.
7. **POST `/api/campanas`**: crea una campaña (coordinador). 400 si las fechas no son válidas.
8. **POST `/api/solicitudes-recogida`**: crea una solicitud compatible (ciudadano). 404 si el punto, la campaña o el material no existen; 400 si incumple una regla de negocio.
9. **PUT `/api/campanas/{id}`**: reemplaza la campaña antes de que inicie. 404 si no existe; 400 si ya inició.
10. **PATCH `/api/solicitudes/{id}/estado`**: programa, recolecta o cancela una solicitud (gestor). 404 si no existe; 400 si el cambio de estado no es válido.
11. **DELETE `/api/solicitudes/{id}`**: retira una solicitud aún no programada. 404 si no existe; 400 si ya fue programada.
