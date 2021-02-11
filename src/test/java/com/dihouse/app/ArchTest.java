package com.dihouse.app;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.dihouse.app");

        noClasses()
            .that()
            .resideInAnyPackage("com.dihouse.app.service..")
            .or()
            .resideInAnyPackage("com.dihouse.app.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.dihouse.app.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
