package com.javeriana.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(1)
public class ComponentTest {

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String WEIGHT_FIELD = "weight";

    @Nested
    @Order(1)
    class VerifyClassDefinition {

        @Test
        @Order(1)
        void testComponentHasIdNameAndWeightAttributes() {
            try {
                Field[] fields = Component.class.getDeclaredFields();
                Map<String, Integer> fieldModifiersByName = Arrays.stream(fields)
                    .collect(
                        Collectors.toMap(
                            field -> field.getName(),
                            field -> field.getModifiers()
                        )
                    );

                Map<String, String> fieldTypesByName = Arrays.stream(fields)
                    .collect(
                        Collectors.toMap(
                            field -> field.getName(),
                            field -> field.getType().getSimpleName()
                        )
                    );

                assertAll(
                    () -> assertTrue(fieldModifiersByName.containsKey(ID_FIELD), "int attribute 'id' does not exist in Component class."),
                    () -> assertTrue(fieldModifiersByName.containsKey(NAME_FIELD), "String attribute 'name' does not exist in Component class."),
                    () -> assertTrue(fieldModifiersByName.containsKey(WEIGHT_FIELD), "double attribute 'weight' does not exist in Component class."),
                    () -> assertEquals(Modifier.toString(Modifier.PRIVATE), Modifier.toString(fieldModifiersByName.getOrDefault(ID_FIELD,0)), "int attribute 'id' is not private in Component class."),
                    () -> assertEquals(Modifier.toString(Modifier.PRIVATE), Modifier.toString(fieldModifiersByName.getOrDefault(NAME_FIELD, 0)), "String attribute 'name' is not private in Component class."),
                    () -> assertEquals(Modifier.toString(Modifier.PRIVATE), Modifier.toString(fieldModifiersByName.getOrDefault(WEIGHT_FIELD, 0)), "double attribute 'weight' is not private in Component class."),
                    () -> assertEquals("int", fieldTypesByName.getOrDefault(ID_FIELD, ""), "Attribute 'id' in Component class is not of type int."),
                    () -> assertEquals("String",fieldTypesByName.getOrDefault(NAME_FIELD, ""), "Attribute 'name' in Component class is not of type String."),
                    () -> assertEquals("double",fieldTypesByName.getOrDefault(WEIGHT_FIELD, ""), "Attribute 'weight' in Component class is not of type double.")

                );

            } catch (Exception e) {
                fail("An error occurred while checking the attributes of the Component class.");
            }
        }
        @Test
        @Order(2)
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
        @Order(3)
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
