package com.javeriana.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(3)
public class CatalogBehaviorTest {


        private static final String CODE = "Robot1";
        private static final double MAX_WEIGHT = 100;

        private static final int COMPONENT_ID = 1;
        private static final String COMPONENT_NAME = "Component1";
        private static final double COMPONENT_WEIGHT = 10;
        @Spy
        Catalog catalog;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @AfterEach
        void tearDown() {
            catalog = null;
        }

        @Test
        @Order(0)
        void testCatalogIsCreatingAnEmptyCatalog() {
            try {
                Method getRobotsCatalogMethod = Catalog.class.getMethod("getRobotsCatalog");
                List<Robot> robotsCatalog = (List<Robot>) getRobotsCatalogMethod.invoke(catalog);

                assertAll(
                    () -> assertNotNull(robotsCatalog, "Robots catalog should not be null when creating a new Catalog."),
                    () -> assertEquals(0, robotsCatalog.size(), "Robots catalog should be empty when creating a new Catalog.")
                );

            } catch (NoSuchMethodException e) {
                fail("Method getRobotsCatalog does not exist in Catalog class.");
            } catch (InvocationTargetException e) {
               fail("An error occurred while invoking getRobotsCatalog method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method getRobotsCatalog is not accessible. Check its access modifier.");
            }

        }
        @Test
        @Order(1)
        void testCatalogIsAddingARobot() {

            try {
                Method addRobotMethod = Catalog.class.getMethod("addRobot", String.class, double.class);
                boolean result = (boolean) addRobotMethod.invoke(catalog, CODE, MAX_WEIGHT);

                Method getRobotsCatalogMethod = Catalog.class.getMethod("getRobotsCatalog");
                List<Robot> robotsCatalog = (List<Robot>) getRobotsCatalogMethod.invoke(catalog);

                assertAll(
                    () -> assertTrue(result, "Catalog should add a robot when it does not exist."),
                    () -> assertEquals(1, robotsCatalog.size(), "Robots catalog should have one robot after adding it.")
                );
            } catch (NoSuchMethodException e) {
                fail("Method addRobot does not exist in Catalog class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking addRobot method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method addRobot is not accessible. Check its access modifier.");
            }

        }


        @Test
        @Order(2)
        void testCatalogIsNotAddingARobotWhenItAlreadyExists() {

            try {
                Method addRobotMethod = Catalog.class.getMethod("addRobot", String.class, double.class);
                boolean result = (boolean) addRobotMethod.invoke(catalog, CODE, MAX_WEIGHT);
                boolean resultWhenRobotAlreadyExists = (boolean) addRobotMethod.invoke(catalog, CODE, MAX_WEIGHT);

                assertAll(
                    () -> assertTrue(result, "Catalog should not add a robot when it exists in list."),
                    () -> assertFalse(resultWhenRobotAlreadyExists, "Catalog should not add a robot when it already exists in list.")
                );
            } catch (NoSuchMethodException e) {
                fail("Method addRobot does not exist in Catalog class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking addRobot method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method addRobot is not accessible. Check its access modifier.");
            }

        }

        @Test
        @Order(3)
        void testCatalogIsReturningARobotWhenSearchingRobotByCodeThatExistsInCatalog() {

            try {
                Method addRobotMethod = Catalog.class.getMethod("addRobot", String.class, double.class);
                addRobotMethod.invoke(catalog, CODE, MAX_WEIGHT);

                Method searchRobotByCodeMethod = Catalog.class.getMethod("searchRobotByCode", String.class);
                Robot robot = (Robot) searchRobotByCodeMethod.invoke(catalog, CODE);

                Method getCodeMethod = Robot.class.getMethod("getCode");
                String code = (String) getCodeMethod.invoke(robot);

                assertEquals(CODE, code, "Catalog should return a robot when it exists in list.");
            } catch (NoSuchMethodException e) {
                fail("Method does not exist in Catalog class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method is not accessible. Check its access modifier.");
            }
        }

        @Test
        @Order(4)
        void testCatalogIsReturningANullWhenSearchingRobotByCodeThatNotExistsInCatalog() {

            try {

                Method searchRobotByCodeMethod = Catalog.class.getMethod("searchRobotByCode", String.class);

                Robot robot = (Robot) searchRobotByCodeMethod.invoke(catalog, CODE);

                assertNull(robot, "Catalog should return null when searching a robot that does not exist in list.");
            } catch (NoSuchMethodException e) {
                fail("Method searchRobotByCode does not exist in Catalog class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking searchRobotByCode method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method searchRobotByCode is not accessible. Check its access modifier.");
            }
        }

        @Test
        @Order(5)
        void testCatalogIsAddingAComponentToARobot() {

            try {


                Method addComponentToRobotMethod = Catalog.class.getMethod("addComponentToRobot", String.class, int.class, String.class, double.class);
                boolean result = (boolean) addComponentToRobotMethod.invoke(catalog, CODE, COMPONENT_ID, COMPONENT_NAME, COMPONENT_WEIGHT);

                assertTrue(result, "Catalog should add a component to a robot when it does exist.");
            } catch (NoSuchMethodException e) {
                fail("Method addComponentToRobot does not exist in Catalog class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking addComponentToRobot method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method addComponentToRobot is not accessible. Check its access modifier.");
            }

        }

        @Test
        @Order(6)
        void testCatalogIsNotAddingAComponentToARobotWhenItDoesNotExist() {

            try {
                Method addComponentToRobotMethod = Catalog.class.getMethod("addComponentToRobot", String.class, int.class, String.class, double.class);
                boolean result = (boolean) addComponentToRobotMethod.invoke(catalog, CODE, COMPONENT_ID, COMPONENT_NAME, COMPONENT_WEIGHT);

                assertFalse(result, "Catalog should not add a component to a robot if it does not exist.");
            } catch (NoSuchMethodException e) {
                fail("Method addComponentToRobot does not exist in Catalog class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking addComponentToRobot method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method addComponentToRobot is not accessible. Check its access modifier.");
            }

        }

        @Test
        @Order(7)
        void testCatalogIsRemovingARobot() {
            try {
                // Obtain and invoke addRobot method
                Method addRobotMethod = Catalog.class.getMethod("addRobot", String.class, double.class);
                addRobotMethod.invoke(catalog, CODE, MAX_WEIGHT);

                // Obtain and invoke removeRobot method
                Method removeRobotMethod = Catalog.class.getMethod("removeRobot", String.class);
                boolean result = (boolean) removeRobotMethod.invoke(catalog, CODE);

                assertTrue(result, "Catalog should remove a robot when it exists in list.");
            } catch (NoSuchMethodException e) {
                fail("Method does not exist in Catalog class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method is not accessible. Check its access modifier.");
            }
        }

        @Test
        @Order(8)
        void testCatalogIsNotRemovingARobotWhenItDoesNotExist() {
            try {
                // Obtain and invoke removeRobot method
                Method removeRobotMethod = Catalog.class.getMethod("removeRobot", String.class);
                boolean result = (boolean) removeRobotMethod.invoke(catalog, CODE);

                assertFalse(result, "Catalog should not remove a robot when it does not exist in list.");
            } catch (NoSuchMethodException e) {
                fail("Method removeRobot does not exist in Catalog class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking removeRobot method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method removeRobot is not accessible. Check its access modifier.");
            }
        }

        @Test
        @Order(9)
        void testCatalogIsGettingComponentsNamesUsedInAllRobots() {
            try {
                // Obtain and invoke addRobot method
                Method addRobotMethod = Catalog.class.getMethod("addRobot", String.class, double.class);
                addRobotMethod.invoke(catalog, CODE, MAX_WEIGHT);

                // Obtain and invoke addComponentToRobot method
                Method addComponentToRobotMethod = Catalog.class.getMethod("addComponentToRobot", String.class, int.class, String.class, double.class);
                addComponentToRobotMethod.invoke(catalog, CODE, COMPONENT_ID, COMPONENT_NAME, COMPONENT_WEIGHT);

                // Add another robot and component
                addRobotMethod.invoke(catalog, "Robot2", MAX_WEIGHT);
                String component2 = "Component2";
                addComponentToRobotMethod.invoke(catalog, "Robot2", 2, component2, 20);

                // Obtain and invoke getComponentsNamesUsedInAllRobots method
                Method getComponentsNamesUsedInAllRobotsMethod = Catalog.class.getMethod("getComponentsNamesUsedInAllRobots");
                List<String> componentsNames = (List<String>) getComponentsNamesUsedInAllRobotsMethod.invoke(catalog);

                List<String> expectedComponentsNames = List.of(COMPONENT_NAME, component2);
                assertAll(
                    () -> assertEquals(2, componentsNames.size(), "Catalog should return the correct number of components names."),
                    () -> assertEquals(expectedComponentsNames, componentsNames, "Catalog should return the correct components names.")
                );
            } catch (NoSuchMethodException e) {
                fail("Method does not exist in Catalog class.");
            } catch (InvocationTargetException e) {
                fail("An error occurred while invoking method. Check its implementation.");
            } catch (IllegalAccessException e) {
                fail("Method is not accessible. Check its access modifier.");
            }
        }
}
