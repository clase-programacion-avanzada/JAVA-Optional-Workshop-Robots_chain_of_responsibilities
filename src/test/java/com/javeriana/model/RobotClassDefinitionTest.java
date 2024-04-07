package com.javeriana.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import helpers.AttributeData;
import helpers.ClassDefinitionHelper;
import helpers.MethodData;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test Robot class definition")
public class RobotClassDefinitionTest {

    public static final String CODE_FIELD = "code";
    public static final String MAX_WEIGHT_FIELD = "maxWeight";
    public static final String COMPONENTS_FIELD = "components";
    public static final String PRIVATE_MODIFIER = Modifier.toString(Modifier.PRIVATE);
    public static final String STRING_TYPE = "String";
    public static final String DOUBLE_TYPE = "double";
    public static final String LIST_TYPE = "List";


    public static final List<AttributeData> EXPECTED_ATTRIBUTES = Arrays.asList(
        new AttributeData(CODE_FIELD, PRIVATE_MODIFIER, STRING_TYPE),
        new AttributeData(MAX_WEIGHT_FIELD, PRIVATE_MODIFIER, DOUBLE_TYPE),
        new AttributeData(COMPONENTS_FIELD, PRIVATE_MODIFIER, LIST_TYPE)
    );
    public ClassDefinitionHelper definitionHelper = new ClassDefinitionHelper(Robot.class);
    @Test
    @DisplayName ("Robot class is defined with correct attributes")
    @Order(0)
    void testRobotHasCodeMaxWeightAndComponentsAttributes() {
        try {

            definitionHelper.testAttributes(EXPECTED_ATTRIBUTES);
        } catch (Exception e) {
            fail("An attribute does not exist in Robot class.");
        }
    }

    @Test
    @DisplayName ("Robot class has constructor with code and maxWeight parameters")
    @Order(1)
    public void testRobotHasConstructorWithCodeAndMaxWeight() {

        definitionHelper.testConstructor(int.class, String.class, double.class);

    }

    @Test
    @DisplayName ("Robot class has getters and setters for code and maxWeight attributes")
    @Order(2)
    public void testRobotHasGettersAndSetters() {

        definitionHelper.testGetters(EXPECTED_ATTRIBUTES);


    }

    @Test
    @DisplayName ("Robot class has method getComponentsWeight")
    @Order(3)
    public void testRobotHasMethodGetComponentsWeight() {
        var methodData = new MethodData("getComponentsWeight", "double", definitionHelper.PUBLIC_MODIFIER);
        definitionHelper.testMethodDefinition(methodData);
    }

    @Test
    @DisplayName ("Robot class has method addComponent")
    @Order(4)
    public void testRobotHasMethodAddComponent() {
        Class[] parameterTypes = {int.class, String.class, double.class};
        var methodData = new MethodData(
            "addComponent",
            "void",
            definitionHelper.PUBLIC_MODIFIER,
            parameterTypes);
        definitionHelper.testMethodDefinition(methodData);
    }

    @Test
    @DisplayName ("Robot class has method getComponentsNames")
    @Order(5)
    public void testRobotHasMethodGetComponentsNames() {
        var methodData = new MethodData("getComponentsNames", "List", definitionHelper.PUBLIC_MODIFIER);
        definitionHelper.testMethodDefinition(methodData);
    }

}
