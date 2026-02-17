/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic;

import com.enofex.taikai.Taikai;
import com.enofex.taikai.TaikaiRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

/**
 * Architecture tests for Spring PetClinic application using Taikai. These tests enforce
 * architectural constraints based on the actual design of the application.
 *
 * @author Architecture Test
 */
class ArchitectureTest {

	private static final String BASE_PACKAGE = "org.springframework.samples.petclinic";

	@Test
	void shouldFulfillArchitecturalConstraints() {
		Taikai.builder()
			.namespace(BASE_PACKAGE)
			.failOnEmpty(false)
			// Java General Rules
			.java(java -> java
				// Ensure no usage of deprecated APIs
				.noUsageOfDeprecatedAPIs()
				// Classes implementing hashCode should also implement equals
				.classesShouldImplementHashCodeAndEquals()
				// Methods should not declare generic exceptions
				.methodsShouldNotDeclareGenericExceptions()
				// Utility classes should be final with private constructor
				.utilityClassesShouldBeFinalAndHavePrivateConstructor()
				// Fields should not be public (except constants)
				.fieldsShouldNotBePublic()
				// Final classes should not have protected members
				.finalClassesShouldNotHaveProtectedMembers()
				// No usage of System.out or System.err
				.noUsageOfSystemOutOrErr()
				// Import rules
				.imports(imports -> imports
					// No cyclic dependencies
					.shouldHaveNoCycles())
				// Naming conventions
				.naming(naming -> naming
					// Packages should follow standard naming
					.packagesShouldMatchDefault()
					// Classes should not end with Impl
					.classesShouldNotMatch(".*Impl")
					// Constants should follow conventions
					.constantsShouldFollowConventions()
					// Interfaces should not have prefix I
					.interfacesShouldNotHavePrefixI()))
			// Spring Framework Rules
			.spring(spring -> spring
				// No @Autowired fields - prefer constructor injection
				.noAutowiredFields()
				// Spring Boot Application
				.boot(boot -> boot
					// @SpringBootApplication should be in base package
					.applicationClassShouldResideInPackage(BASE_PACKAGE))
				// Configuration classes
				.configurations(configuration -> configuration
					// Configuration classes should end with Configuration
					.namesShouldEndWithConfiguration()
					// Configuration classes should match naming pattern
					.namesShouldMatch(".*Configuration"))
				// Controller classes
				.controllers(controllers -> controllers
					// Controllers should end with Controller
					.namesShouldEndWithController()
					// Controllers should match naming pattern
					.namesShouldMatch(".*Controller")
					// Controllers should not depend on other controllers
					.shouldNotDependOnOtherControllers()
					// Controllers should be package-private (following PetClinic design)
					.shouldBePackagePrivate())
				// Repository classes
				.repositories(repositories -> repositories
					// Repositories should end with Repository
					.namesShouldEndWithRepository()
					// Repositories should match naming pattern
					.namesShouldMatch(".*Repository")))
			// Test Rules
			.test(test -> test.junit(junit -> junit
				// Test classes should not be annotated with @Disabled
				.classesShouldNotBeAnnotatedWithDisabled()
				// Test methods should not be annotated with @Disabled
				.methodsShouldNotBeAnnotatedWithDisabled()))
			// Custom rules specific to PetClinic architecture
			.addRule(TaikaiRule.of(classes().that()
				.resideInAPackage("..owner..")
				.and(not(resideInAPackage("..owner..")))
				.should()
				.onlyBeAccessed()
				.byClassesThat()
				.resideInAnyPackage("..owner..", "..system..", BASE_PACKAGE)
				.as("Owner package classes should only be accessed by owner, system packages or base package")))
			.addRule(TaikaiRule.of(classes().that()
				.resideInAPackage("..vet..")
				.and(not(resideInAPackage("..vet..")))
				.should()
				.onlyBeAccessed()
				.byClassesThat()
				.resideInAnyPackage("..vet..", "..system..", BASE_PACKAGE)
				.as("Vet package classes should only be accessed by vet, system packages or base package")))
			.addRule(TaikaiRule.of(classes().that()
				.resideInAPackage("..model..")
				.should()
				.onlyBeAccessed()
				.byClassesThat()
				.resideInAnyPackage("..owner..", "..vet..", "..model..", "..system..", BASE_PACKAGE)
				.as("Model classes should only be accessed by domain packages")))
			.build()
			.check();
	}

}

// Made with Bob
