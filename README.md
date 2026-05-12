# PruebaTecBazar - Bazar API (Spring Boot)

API REST para gestionar **Productos**, **Clientes** y **Ventas** en un bazar, con manejo automático de stock, validaciones y DTOs para requests/responses.

## 🚀 Tecnologías

- Java
- Spring Boot (Web, Data JPA, Validation)
- Hibernate / JPA
- **H2 Database (en memoria)**
- Maven

## BONUS IMPLEMENTADO:
-Gestión automática de stock de productos

Funcionalidad: Al crear una venta se descuenta automáticamente el stock, al eliminarla se restaura, y al actualizarla se ajusta según los cambios.
Validación: Antes de crear/actualizar una venta se verifica que haya stock suficiente de cada producto.
Excepción: InsufficientStockException cuando no hay stock disponible.

-DTOs diferenciados para request y response en ventas

VentaRequestDTO: El usuario solo envía IDs de productos (List<Long>) y el ID del cliente.
VentaResponseDTO: La API devuelve objetos completos con toda la información de productos y cliente.
Beneficio: Evita inconsistencias y mejora la experiencia del usuario de la API.

-Validaciones automáticas con Jakarta Validation

Uso de anotaciones @NotNull, @NotBlank, @Positive, etc. en todos los DTOs.
Interceptación automática de errores de validación antes de llegar al service.

-Excepciones personalizadas

NotFoundException: recursos no encontrados (404)
InsufficientStockException: stock insuficiente (409)