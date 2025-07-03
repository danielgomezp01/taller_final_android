# taller_final_android
Jetpack Compose CRUD App
MoviesApp: Aplicación CRUD de películas con Jetpack Compose

Este proyecto es una aplicación Android que permite gestionar una lista de películas, integrando operaciones CRUD (Crear, Leer, Actualizar, Eliminar) utilizando las siguientes tecnologías:

    Jetpack Compose: Para la construcción de la interfaz de usuario.

    Room: Como base de datos local para persistencia de datos.

    Retrofit + Moshi: Para el consumo de una API REST externa (The Movie Database - TMDb).

    ViewModel con StateFlow: Para la gestión del estado y la lógica de presentación.

    Navigation Compose: Para la navegación entre las diferentes pantallas de la aplicación.

    Hilt: Para la inyección de dependencias (opcional, pero recomendado para proyectos escalables).

Objetivo General

El objetivo de esta aplicación es demostrar la integración de diversas bibliotecas y componentes de Android Jetpack para construir una aplicación funcional y bien estructurada, siguiendo los principios de arquitectura limpia y buenas prácticas de desarrollo.
Funcionalidades

    Lista de Películas (Home Screen):

        Muestra una lista de películas obtenidas tanto de la base de datos local (Room) como de una API externa (TMDb).

        Sincronización inicial: Al iniciar la aplicación, se intentan cargar películas populares de la API y se guardan en la base de datos local.

        Permite navegar a la pantalla de detalles de una película existente.

Requerimientos Técnicos

    UI: Uso exclusivo de Jetpack Compose.

    Navegación: Implementación de Navigation Compose entre al menos 3 pantallas (Lista, Detalle/Edición).

    Modelo: Entidades modeladas correctamente para Room y DTOs para la API.

    Lógica: Uso de ViewModel con StateFlow para la gestión de datos y lógica de negocio.

    Base de datos: Persistencia local con Room (CRUD completo: Insertar, Leer, Actualizar, Eliminar).

    API: Consumo de API REST externa (TMDb) utilizando Retrofit y Moshi para la consulta de películas populares.

    Sincronización: Implementación de una sincronización básica entre Room y la API (las películas de la API se guardan localmente).

Librerías Utilizadas

    Jetpack Compose:

        androidx.compose.ui:ui

        androidx.compose.ui:ui-tooling-preview

        androidx.compose.material3:material3

        androidx.navigation:navigation-compose

        androidx.lifecycle:lifecycle-runtime-ktx

        androidx.activity:activity-compose

        androidx.lifecycle:lifecycle-viewmodel-compose

        androidx.hilt:hilt-navigation-compose

    Room (Base de Datos Local):

        androidx.room:room-runtime

        androidx.room:room-ktx (para coroutines)

        androidx.room:room-compiler (kapt)
