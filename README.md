# EmpowermentLabs-test
Repositorio creado para compartir el desarrollo de la prueba solicitada por EmpowermentLabs.

La arquitectura aplicada en la prueba está basada en la sugerida por la documentación de Android Developers donde se comenta y explica que es una buena practica separar las capas de UI y la de datos. Se puede encontrar 2 vistas sencillas que como el test lo pedía nos permiten ver una lista de recetas y el detalle de alguna seleccionada, las cuales sólo se encargan de atender y notificar las acciones del usuario para que de esa forma el viewModel pueda desde el repository acceder al data source correspondiente y entregar el resultado de la solicitud.

Se ha utilizado el patrón MVVM para poder abstraer la cada de UI de la capa de datos y así, de esta forma, no sea la UI quien deba de realizar las operaciones de consultas a la base de datos o al servidor (según sea el caso), cada vez que el usaurio haga una interacción. 

- Dentro del desarrollo se utilizó databinding, pero la actualización de la vista es realizada con ayuda del componente desde el fragment y no desde el XML (por gusto personal). 
- Para la persistencia de datos se utiliza room.
- Para la inyección de dependencias se utilizó Hilt.
- Para el consumo de de la API se utilizó retrofit.
