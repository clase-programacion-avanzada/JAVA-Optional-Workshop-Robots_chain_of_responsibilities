package com.javeriana.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(1)
public class RobotTest {

    @Nested
    @Order(1)
    class VerifyClassDefinition {
        @Test
        @Order(0)
        public void testRobotHasConstructorWithCodeAndMaxWeight() {

            try {
                Constructor<Robot> constructor = Robot.class.getDeclaredConstructor(String.class, double.class);
                assertNotNull(constructor, "Constructor with String and double parameters does not exist in Robot class.");
            } catch (NoSuchMethodException e) {
                fail("Constructor with String and double parameters does not exist in Robot class.");
            }

        }

        @Test
        @Order(1)
        public void testRobotHasGettersAndSetters() {
            try {
                Method getCodeMethod = Robot.class.getMethod("getCode");
                Method setCodeMethod = Robot.class.getMethod("setCode", String.class);
                Method getMaxWeightMethod = Robot.class.getMethod("getMaxWeight");
                Method setMaxWeightMethod = Robot.class.getMethod("setMaxWeight", double.class);

                assertAll(
                    () -> assertNotNull(getCodeMethod, "Getter for 'code' does not exist in Robot class."),
                    () -> assertNotNull(setCodeMethod, "Setter for 'code' does not exist in Robot class."),
                    () -> assertNotNull(getMaxWeightMethod, "Getter for 'maxWeight' does not exist in Robot class."),
                    () -> assertNotNull(setMaxWeightMethod, "Setter for 'maxWeight' does not exist in Robot class.")
                );
            } catch (NoSuchMethodException e) {
                fail("A getter or setter method does not exist in Robot class.");
            }
        }

        @Test
        @Order(2)
        public void testRobotHasMethodGetComponentsWeight() {
            try {
                Method getComponentsWeightMethod = Robot.class.getMethod("getComponentsWeight");
                assertNotNull(getComponentsWeightMethod, "Method getComponentsWeight does not exist in Robot class.");
            } catch (NoSuchMethodException e) {
                fail("Method getComponentsWeight does not exist in Robot class.");
            }
        }

        @Test
        @Order(3)
        public void testRobotHasMethodAddComponent() {
            try {
                Method addComponentMethod = Robot.class.getMethod("addComponent", int.class, String.class, double.class);
                assertNotNull(addComponentMethod, "Method addComponent does not exist in Robot class.");
            } catch (NoSuchMethodException e) {
                fail("Method addComponent does not exist in Robot class.");
            }
        }

        @Test
        @Order(4)
        public void testRobotHasMethodGetComponentsNames() {
            try {
                Method getComponentsNamesMethod = Robot.class.getMethod("getComponentsNames");
                assertNotNull(getComponentsNamesMethod, "Method getComponentsNames does not exist in Robot class.");
            } catch (NoSuchMethodException e) {
                fail("Method getComponentsNames does not exist in Robot class.");
            }
        }

        @Test
        @Order(5)
        public void testRobotHasAttributes() {
            try {
                Field codeField = Robot.class.getDeclaredField("code");
                Field maxWeightField = Robot.class.getDeclaredField("maxWeight");
                Field componentsField = Robot.class.getDeclaredField("components");

                assertAll(
                    () -> assertNotNull(codeField, "Attribute 'code' does not exist in Robot class."),
                    () -> assertNotNull(maxWeightField, "Attribute 'maxWeight' does not exist in Robot class."),
                    () -> assertNotNull(componentsField, "Attribute 'components' does not exist in Robot class.")
                );
            } catch (NoSuchFieldException e) {
                fail("An attribute does not exist in Robot class.");
            }
        }
    }

    @Nested
    @Order(2)
    class VerifyClassBehavior {
        Robot robot;
        @BeforeEach
        public void setUp() {
            Robot robotInstance = new Robot("R2D2", 100);
            robot = Mockito.spy(robotInstance);
        }

        @AfterEach
        public void tearDown() {
            robot = null;
        }

        @Test
        @Order(0)
        public void testRobotIsAddingComponent() {
            boolean result = robot.addComponent(1, "Component1", 10);
            assertTrue(result,
                "Robot is not adding a component when it not exceeds maximum weight.");
        }

        @Test
        @Order(1)
        public void testRobotIsNotAddingComponentWhenItExceedsMaximumWeight() {
            boolean result = robot.addComponent(1, "Component1", 101);
            assertFalse(result,
                "Robot is adding a component when it exceeds maximum weight.");
        }

        @Test
        @Order(2)
        public void testRobotIsGettingComponentsWeight() {
            robot.addComponent(1, "Component1", 10);
            robot.addComponent(2, "Component2", 20);
            robot.addComponent(3, "Component3", 30);
            int totalWeight = robot.getComponentsWeight();

            assertEquals(60, totalWeight,
                "Robot is not getting the correct total weight of its components.");

        }

        @Test
        @Order(3)
        public void testRobotIsGettinggetComponentsNames() {
            robot.addComponent(1, "Component1", 10);
            robot.addComponent(2, "Component2", 20);
            robot.addComponent(3, "Component3", 30);

            List<String> componentsNames = robot.getComponentsNames();
            List<String> expectedComponentsNames = List.of("Component1", "Component2", "Component3");

            assertAll(
                () -> assertEquals(3, componentsNames.size(),
                    "Robot is not getting the correct number of components names."),
                () -> assertEquals(expectedComponentsNames, componentsNames,
                    "Robot is not getting the correct components names.")
            );
        }
    }




}
