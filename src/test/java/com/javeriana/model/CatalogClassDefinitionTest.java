package com.javeriana.model;

import helpers.AttributeData;
import helpers.ClassDefinitionHelper;
import helpers.MethodData;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test Catalog class definition")
public class CatalogClassDefinitionTest {
    private static final List<AttributeData> EXPECTED_ATTRIBUTES = Arrays.asList(
        new AttributeData("robotsCatalog", "List", ClassDefinitionHelper.PRIVATE_MODIFIER)
    );
    private final ClassDefinitionHelper definitionHelper = new ClassDefinitionHelper(Catalog.class);
    //Verify that Catalog has a list of robots
    @Test
    @DisplayName("Catalog class is defined with correct attributes")
    @Order(0)
    public void testCatalogHasRobotsCatalogList() {
        definitionHelper.testAttributes(EXPECTED_ATTRIBUTES);
    }

    @Test
    @DisplayName("Catalog class has getters and setters")
    @Order(1)
    public void testCatalogHasGettersAndSetters() {
        definitionHelper.testGetters(EXPECTED_ATTRIBUTES);
        //List of robotsCatalog should not have a setter
        definitionHelper.testSetters(List.of());
    }

    @Test
    @DisplayName("Catalog class has addRobot method")
    @Order(2)
    public void testCatalogHasAddRobotMethod() {
        var parameterTypes = new Class[] {String.class, double.class};
        MethodData methodData =
            new MethodData("addRobot", "boolean", ClassDefinitionHelper.PUBLIC_MODIFIER, parameterTypes);
        definitionHelper.testMethodDefinition(methodData);
    }

    @Test
    @DisplayName("Catalog class has searchRobotByCode method")
    @Order(3)
    public void testCatalogHasSearchRobotByCodeMethod() {
        var parameterTypes = new Class[] {String.class};
        MethodData methodData =
            new MethodData("searchRobotByCode", "Robot", ClassDefinitionHelper.PUBLIC_MODIFIER, parameterTypes);
        definitionHelper.testMethodDefinition(methodData);
    }

    @Test
    @DisplayName("Catalog class has addComponentToRobot method")
    @Order(4)
    public void testCatalogHasAddComponentToRobotMethod() {
        var parameterTypes = new Class[] {String.class, int.class, String.class, double.class};
        MethodData methodData =
            new MethodData("addComponentToRobot", "boolean", ClassDefinitionHelper.PUBLIC_MODIFIER, parameterTypes);
        definitionHelper.testMethodDefinition(methodData);
    }

    @Test
    @DisplayName("Catalog class has removeRobot method")
    @Order(5)
    public void testCatalogHasRemoveRobotMethod() {
        var parameterTypes = new Class[] {String.class};
        MethodData methodData =
            new MethodData("removeRobot", "boolean", ClassDefinitionHelper.PUBLIC_MODIFIER, parameterTypes);
        definitionHelper.testMethodDefinition(methodData);
    }

    @Test
    @DisplayName("Catalog class has getComponentsNamesUsedInAllRobots method")
    @Order(6)
    public void testCatalogHasGetComponentsNamesUsedInAllRobotsMethod() {
        MethodData methodData =
            new MethodData("getComponentsNamesUsedInAllRobots", "List", ClassDefinitionHelper.PUBLIC_MODIFIER);
        definitionHelper.testMethodDefinition(methodData);
    }

}
