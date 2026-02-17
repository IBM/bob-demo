# Architecture Test Documentation

## Overview

This document describes the architectural constraints enforced by the `ArchitectureTest` class using the [Taikai](https://github.com/enofex/taikai) framework. The test ensures that the Spring PetClinic application maintains consistent architecture and follows best practices.

## What is Taikai?

Taikai is an automated architecture testing tool for Java projects built on top of ArchUnit. It provides predefined rules for common architectural patterns, especially for Spring Framework applications.

## Enforced Architectural Rules

### Java General Rules

1. **No Deprecated APIs**: Ensures no usage of APIs annotated with `@Deprecated`
2. **HashCode and Equals**: Classes implementing `hashCode()` must also implement `equals()` and vice versa
3. **No Generic Exceptions**: Methods should not declare generic exceptions like `Exception` or `RuntimeException`
4. **Utility Classes**: Utility classes (only static methods) must be `final` with private constructors
5. **Field Visibility**: Fields should not be `public` (except constants)
6. **Final Classes**: Classes declared as `final` should not have `protected` members
7. **No System.out/err**: Disallows usage of `System.out` or `System.err` for logging
8. **No Cyclic Dependencies**: Ensures no cyclic dependencies in imports

### Naming Conventions

1. **Package Naming**: Packages must follow standard naming conventions (`^[a-z_]+(\.[a-z_][a-z0-9_]*)*$`)
2. **No Impl Suffix**: Classes should not end with `Impl`
3. **Constants**: Constants must follow naming conventions (UPPER_SNAKE_CASE)
4. **No I Prefix**: Interfaces should not have the prefix `I`

### Spring Framework Rules

#### General Spring Rules
- **No @Autowired Fields**: Fields should not be annotated with `@Autowired` - constructor injection is preferred

#### Spring Boot
- **Application Class Location**: `@SpringBootApplication` class must reside in the base package (`org.springframework.samples.petclinic`)

#### Configuration Classes
- **Naming**: Configuration classes must end with `Configuration`
- **Pattern**: Must match the pattern `.*Configuration`

#### Controller Classes
- **Naming**: Controllers must end with `Controller`
- **Pattern**: Must match the pattern `.*Controller`
- **Isolation**: Controllers should not depend on other controllers
- **Visibility**: Controllers should be package-private (following PetClinic design)

#### Repository Classes
- **Naming**: Repositories must end with `Repository`
- **Pattern**: Must match the pattern `.*Repository`

### Test Rules

1. **No @Disabled**: Test classes and methods should not be annotated with `@Disabled`

### Custom PetClinic-Specific Rules

1. **Owner Package Isolation**: Classes in the `owner` package should only be accessed by:
   - Other classes in the `owner` package
   - Classes in the `system` package
   - Classes in the base package

2. **Vet Package Isolation**: Classes in the `vet` package should only be accessed by:
   - Other classes in the `vet` package
   - Classes in the `system` package
   - Classes in the base package

3. **Model Package Access**: Classes in the `model` package should only be accessed by:
   - Domain packages (`owner`, `vet`)
   - The `model` package itself
   - The `system` package
   - The base package

## Running the Test

### Run only the architecture test:
```bash
./mvnw test -Dtest=ArchitectureTest
```

### Run all tests including architecture test:
```bash
./mvnw test
```

## Benefits

1. **Automated Enforcement**: Architecture rules are automatically checked on every test run
2. **Documentation**: The test serves as living documentation of architectural decisions
3. **Early Detection**: Violations are caught early in the development process
4. **Consistency**: Ensures all developers follow the same architectural patterns
5. **Refactoring Safety**: Provides confidence when refactoring that architecture is maintained

## Customization

To add new rules or modify existing ones, edit the `ArchitectureTest.java` file. Refer to the [Taikai documentation](https://github.com/enofex/taikai/blob/main/docs/documentation.md) for available rules and configuration options.

## Dependencies

The test requires the following dependency (already added to `pom.xml`):

```xml
<dependency>
    <groupId>com.enofex</groupId>
    <artifactId>taikai</artifactId>
    <version>1.50.0</version>
    <scope>test</scope>
</dependency>