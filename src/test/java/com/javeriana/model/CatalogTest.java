package com.javeriana.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(2)
public class CatalogTest {

    @Nested
    @Order(1)
    class VerifyClassDefinition {
        //Verify that Catalog has a list of robots
        @Test
        @Order(0)
        public void testCatalogHasRobotsCatalogList() {
            try {
                Field robotsCatalogField = Catalog.class.getDeclaredField("robotsCatalog");
                assertNotNull(robotsCatalogField, "List of robots does not exist in Catalog class.");
            } catch (NoSuchFieldException e) {
                fail("List of robots does not exist in Catalog class.");
            }
        }
    }

    @Nested
    @Order(1)
    class VerifyClassBehavior {
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

            List<Robot> robotsCatalog = catalog.getRobotsCatalog();

            assertEquals(0, robotsCatalog.size());

        }
        @Test
        @Order(1)
        void testCatalogIsAddingARobot() {

            boolean result = catalog.addRobot(CODE, MAX_WEIGHT);

            assertTrue(result);

        }


        @Test
        @Order(2)
        void testCatalogIsNotAddingARobotWhenItAlreadyExists() {

            boolean result = catalog.addRobot(CODE, MAX_WEIGHT);
            boolean resultWhenRobotAlreadyExists = catalog.addRobot(CODE, MAX_WEIGHT);

            assertAll(
                () -> assertTrue(result),
                () -> assertFalse(resultWhenRobotAlreadyExists)
            );

        }

        @Test
        @Order(3)
        void testCatalogIsReturningARobotWhenSearchingRobotByCodeThatExistsInCatalog() {

            catalog.addRobot(CODE, MAX_WEIGHT);

            Robot robot = catalog.searchRobotByCode(CODE);

            assertEquals(CODE, robot.getCode());

        }

        @Test
        @Order(4)
        void testCatalogIsReturningANullWhenSearchingRobotByCodeThatNotExistsInCatalog() {

            catalog.searchRobotByCode(CODE);

            Robot robot = catalog.searchRobotByCode(CODE);

            assertNull(robot);
        }

        @Test
        @Order(5)
        void testCatalogIsAddingAComponentToARobot() {

            when(catalog.searchRobotByCode(CODE)).thenReturn(new Robot(CODE, MAX_WEIGHT));

            boolean result = catalog.addComponentToRobot(CODE, COMPONENT_ID, COMPONENT_NAME, COMPONENT_WEIGHT);

            assertTrue(result);

        }

        @Test
        @Order(6)
        void testCatalogIsNotAddingAComponentToARobotWhenItDoesNotExist() {

            boolean result = catalog.addComponentToRobot(CODE, COMPONENT_ID, COMPONENT_NAME, COMPONENT_WEIGHT);

            assertFalse(result);

        }

        @Test
        @Order(7)
        void testCatalogIsRemovingARobot() {

            catalog.addRobot(CODE, MAX_WEIGHT);

            boolean result = catalog.removeRobot(CODE);

            assertTrue(result);

        }

        @Test
        @Order(8)
        void testCatalogIsNotRemovingARobotWhenItDoesNotExist() {

            boolean result = catalog.removeRobot(CODE);

            assertFalse(result);

        }

        @Test
        @Order(9)
        void testCatalogIsGettingComponentsNamesUsedInAllRobots() {


            catalog.addRobot(CODE, MAX_WEIGHT);
            catalog.addComponentToRobot(CODE, COMPONENT_ID, COMPONENT_NAME, COMPONENT_WEIGHT);

            catalog.addRobot("Robot2", MAX_WEIGHT);
            String component2 = "Component2";
            catalog.addComponentToRobot("Robot2", 2, component2, 20);

            List<String> expectedComponentsNames = List.of(COMPONENT_NAME, component2);
            List<String> componentsNames = catalog.getComponentsNamesUsedInAllRobots();
            assertAll(
                () -> assertEquals(2, componentsNames.size()),
                () -> assertEquals(expectedComponentsNames, componentsNames)
            );

        }
    }









}
