-- Datos de prueba para probar los endpoints en Postman
-- Ejecutar en la base reciclaje_db, después de arrancar la aplicación una vez (para que existan las tablas)

INSERT INTO materiales (nombre, descripcion) VALUES
    ('PLASTICO', 'Botellas y envases plásticos'),
    ('CARTON', 'Cajas y cartón limpio'),
    ('VIDRIO', 'Botellas y frascos de vidrio');

INSERT INTO puntos_reciclaje (nombre, direccion, latitud, longitud, ubicacion_aproximada, activo) VALUES
    ('Punto Verde Centro', 'Carrera 23 # 30-15', 5.0703, -75.5138, 'Barrio Centro, Manizales', true);

-- El punto solo acepta plástico y cartón (RN-01)
INSERT INTO puntos_materiales (punto_id, material_id) VALUES (1, 1), (1, 2);

INSERT INTO campanas (nombre, descripcion, fecha_inicio, fecha_fin) VALUES
    ('Recicla en octubre', 'Campaña de recolección de plástico y cartón', '2026-10-01', '2026-11-30');

-- La campaña solo incluye plástico y cartón (RN-04): el vidrio se rechaza
INSERT INTO campanas_materiales (campana_id, material_id) VALUES (1, 1), (1, 2);
