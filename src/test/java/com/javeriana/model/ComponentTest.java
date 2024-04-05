package com.javeriana.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(0)
public class ComponentTest {

    @Nested
    @Order(1)
    class VerifyClassDefinition {
        @Test
        @Order(0)
        void testComponentHasConstructorWithIdNameAndWeight() {
            try {
                Constructor<Component> constructor =
                    Component.class.getDeclaredConstructor(int.class, String.class, double.class);
                    assertNotNull(constructor, "Constructor with int, String and double parameters does not exist in Component class.");
            } catch (NoSuchMethodException e) {
                fail("Constructor with int, String and double parameters does not exist in Component class.");
            }
        }

        @Test
        @Order(1)
        void testComponentHasGettersAndSetters() {

            try {
                Method getIdMethod = Component.class.getMethod("getId");
                Method getNameMethod = Component.class.getMethod("getName");
                Method setNameMethod = Component.class.getMethod("setName", String.class);
                Method getWeightMethod = Component.class.getMethod("getWeight");
                Method setWeightMethod = Component.class.getMethod("setWeight", double.class);

                assertAll(
                    () -> assertNotNull(getIdMethod, "Getter for 'id' does not exist in Component class."),
                    () -> assertNotNull(getNameMethod, "Getter for 'name' does not exist in Component class."),
                    () -> assertNotNull(setNameMethod, "Setter for 'name' does not exist in Component class."),
                    () -> assertNotNull(getWeightMethod, "Getter for 'weight' does not exist in Component class."),
                    () -> assertNotNull(setWeightMethod, "Setter for 'weight' does not exist in Component class.")
                );
            } catch (NoSuchMethodException e) {
                fail("A getter or setter method does not exist in Component class.");
            }
        }
    }
}
