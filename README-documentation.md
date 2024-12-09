This page contains the documentation about this project's source code, API, principles and architecture

# Tech stack

This project makes use of the following dependencies as its main tech stack:

- Spring Core, for IoC, Aspects and DI capabilities
- Spring Boot for easy auto-configuration
- Spring Data JPA/Hibernate as its ORM persistence framework
- Spring Boot Validation/Hibernate Validator as its standard validation framework
- JavaFx as its GUI framework
- FxWeaver to integrate between Spring and JavaFx
- Lombok to reduce boilerplate codes

# Architecture

This project's architecture closely resembles the Three Layers Architecture and the MVC/Front Controller Pattern

## High level architecture

On the highest level, this project follows the Three Layers Architecture, where the entire project is divided into 3 layers, each with its own separate responsibilities

![3 layers architecture](readme-resources/3-layers-architecture.png)

### Data Access Layer

The Data Access Layer