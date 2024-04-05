package com.javeriana.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
@Order(2)
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
            try {
                // Obtain the constructor of Robot class
                Constructor<Robot> constructor = Robot.class.getDeclaredConstructor(String.class, double.class);

                // Create a new instance of Robot
                Robot robotInstance = constructor.newInstance("R2D2", 100.0);

                // Spy the created instance
                robot = Mockito.spy(robotInstance);
            } catch (NoSuchMethodException e) {
                fail("Constructor with String and double parameters does not exist in Robot class.");
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                fail("An error occurred while creating a new instance of Robot class.");
            }
        }

        @AfterEach
        public void tearDown() {
            robot = null;
        }

        @Test
        @Order(0)
        public void testRobotIsAddingComponent() {
            try {
                // Obtain and invoke addComponent method
                Method addComponentMethod =
                    Robot.class.getMethod("addComponent", int.class, String.class, double.class);
                boolean result = (boolean) addComponentMethod.invoke(robot, 1, "Component1", 10.0);

                assertTrue(result,
                    "Robot is not adding a component when it not exceeds maximum weight.");
            } catch (NoSuchMethodException e) {
                fail("Method addComponent does not exist in Robot class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking addComponent method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method addComponent is not accessible. Check its access modifier.");
            }
        }

        @Test
        @Order(1)
        public void testRobotIsNotAddingComponentWhenItExceedsMaximumWeight() {
            try {
                // Obtain and invoke addComponent method
                Method addComponentMethod =
                    Robot.class.getMethod("addComponent", int.class, String.class, double.class);
                boolean result = (boolean) addComponentMethod.invoke(robot, 1, "Component1", 101.0);

                assertFalse(result,
                    "Robot is adding a component when it exceeds maximum weight.");
            } catch (NoSuchMethodException e) {
                fail("Method addComponent does not exist in Robot class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking addComponent method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method addComponent is not accessible. Check its access modifier.");
            }
        }

        @Test
        @Order(2)
        public void testRobotIsGettingComponentsWeight() {
            try {
                // Obtain and invoke addComponent and getComponentsWeight methods
                Method addComponentMethod =
                    Robot.class.getMethod("addComponent", int.class, String.class, double.class);
                Method getComponentsWeightMethod = Robot.class.getMethod("getComponentsWeight");

                addComponentMethod.invoke(robot, 1, "Component1", 10.0);
                addComponentMethod.invoke(robot, 2, "Component2", 20.0);
                addComponentMethod.invoke(robot, 3, "Component3", 30.0);

                int totalWeight = (int) getComponentsWeightMethod.invoke(robot);

                assertEquals(60, totalWeight,
                    "Robot is not getting the correct total weight of its components.");
            } catch (NoSuchMethodException e) {
                fail("A method does not exist in Robot class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking a method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("A method is not accessible. Check its access modifier.");
            }
        }

        @Test
        @Order(3)
        public void testRobotIsGettingComponentsNames() {
            try {
                // Obtain and invoke addComponent and getComponentsNames methods
                Method addComponentMethod =
                    Robot.class.getMethod("addComponent", int.class, String.class, double.class);
                Method getComponentsNamesMethod = Robot.class.getMethod("getComponentsNames");

                addComponentMethod.invoke(robot, 1, "Component1", 10.0);
                addComponentMethod.invoke(robot, 2, "Component2", 20.0);
                addComponentMethod.invoke(robot, 3, "Component3", 30.0);

                List<String> componentsNames = (List<String>) getComponentsNamesMethod.invoke(robot);
                List<String> expectedComponentsNames = List.of("Component1", "Component2", "Component3");

                assertAll(
                    () -> assertEquals(3, componentsNames.size(),
                        "Robot is not getting the correct number of components names."),
                    () -> assertEquals(expectedComponentsNames, componentsNames,
                        "Robot is not getting the correct components names.")
                );
            } catch (NoSuchMethodException e) {
                fail("A method does not exist in Robot class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking a method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("A method is not accessible. Check its access modifier.");
            }
        }

    }
}
