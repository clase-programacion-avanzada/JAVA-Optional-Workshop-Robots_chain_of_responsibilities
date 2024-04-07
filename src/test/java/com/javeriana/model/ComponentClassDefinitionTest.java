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
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ComponentClassDefinitionTest {

    public static final String DOUBLE_TYPE = "double";
    public static final String GET_ID = "getId";
    public static final String GET_NAME = "getName";
    public static final String GET_WEIGHT = "getWeight";
    public static final String ID_FIELD = "id";
    public static final String INT_TYPE = "int";
    public static final String NAME_FIELD = "name";
    public static final String PRIVATE_MODIFIER = Modifier.toString(Modifier.PRIVATE);
    public static final String PUBLIC_MODIFIER = Modifier.toString(Modifier.PUBLIC);
    public static final String SET_ID = "setId";
    public static final String SET_NAME = "setName";
    public static final String SET_WEIGHT = "setWeight";
    public static final String STRING_TYPE = "String";
    public static final String VOID_TYPE = "void";
    public static final String WEIGHT_FIELD = "weight";
    public static final String GET = "get";
    public static final String SET = "set";

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
                    () -> assertEquals(PRIVATE_MODIFIER, Modifier.toString(fieldModifiersByName.getOrDefault(ID_FIELD,0)), "int attribute 'id' is not private in Component class."),
                    () -> assertEquals(
                        PRIVATE_MODIFIER, Modifier.toString(fieldModifiersByName.getOrDefault(NAME_FIELD, 0)), "String attribute 'name' is not private in Component class."),
                    () -> assertEquals(
                        PRIVATE_MODIFIER, Modifier.toString(fieldModifiersByName.getOrDefault(WEIGHT_FIELD, 0)), "double attribute 'weight' is not private in Component class."),
                    () -> assertEquals(INT_TYPE, fieldTypesByName.getOrDefault(ID_FIELD, ""), "Attribute 'id' in Component class is not of type int."),
                    () -> assertEquals(STRING_TYPE,fieldTypesByName.getOrDefault(NAME_FIELD, ""), "Attribute 'name' in Component class is not of type String."),
                    () -> assertEquals(DOUBLE_TYPE,fieldTypesByName.getOrDefault(WEIGHT_FIELD, ""), "Attribute 'weight' in Component class is not of type double.")

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

            Method[] declaredMethods = Component.class.getDeclaredMethods();

            Map<String, Map.Entry<Integer,String>> getters = Arrays.stream(declaredMethods)
                .filter(method -> method.getName().startsWith(GET))
                .collect(
                    Collectors.toMap(
                        method -> method.getName(),
                        method -> Map.entry(method.getModifiers(), method.getReturnType().getSimpleName())
                    )
                );

            Map <String, Map.Entry<Integer,String>> setters = Arrays.stream(declaredMethods)
                .filter(method -> method.getName().startsWith(SET))
                .collect(
                    Collectors.toMap(
                        method -> method.getName(),
                        method -> Map.entry(method.getModifiers(), method.getReturnType().getSimpleName())
                    )
                );
            final Map.Entry<Integer, String> defaultEntry = Map.entry(0, "");

            assertAll(
                () -> assertTrue(getters.containsKey(GET_ID), "getId method does not exist in Component class."),
                () -> assertTrue(getters.containsKey(GET_NAME), "getName method does not exist in Component class."),
                () -> assertTrue(getters.containsKey(GET_WEIGHT), "getWeight method does not exist in Component class."),
                () -> assertFalse(setters.containsKey(SET_ID), "setId method should not exist in Component class."),
                () -> assertTrue(setters.containsKey(SET_NAME), "setName method does not exist in Component class."),
                () -> assertTrue(setters.containsKey(SET_WEIGHT), "setWeight method does not exist in Component class."),
                () -> assertEquals(PUBLIC_MODIFIER, Modifier.toString(getters.getOrDefault(GET_ID, defaultEntry).getKey()), "getId method is not public in Component class."),
                () -> assertEquals(PUBLIC_MODIFIER, Modifier.toString(getters.getOrDefault(GET_NAME, defaultEntry).getKey()), "getName method is not public in Component class."),
                () -> assertEquals(PUBLIC_MODIFIER, Modifier.toString(getters.getOrDefault(GET_WEIGHT, defaultEntry).getKey()), "getWeight method is not public in Component class."),
                () -> assertEquals(PUBLIC_MODIFIER, Modifier.toString(setters.getOrDefault(SET_NAME, defaultEntry).getKey()), "setName method is not public in Component class."),
                () -> assertEquals(PUBLIC_MODIFIER, Modifier.toString(setters.getOrDefault(SET_WEIGHT, defaultEntry).getKey()), "setWeight method is not public in Component class."),
                () -> assertEquals(INT_TYPE, getters.getOrDefault(GET_ID, defaultEntry).getValue(), "getId method in Component class does not return an int."),
                () -> assertEquals(STRING_TYPE, getters.getOrDefault(GET_NAME, defaultEntry).getValue(), "getName method in Component class does not return a String."),
                () -> assertEquals(DOUBLE_TYPE, getters.getOrDefault(GET_WEIGHT, defaultEntry).getValue(), "getWeight method in Component class does not return a double."),
                () -> assertEquals(VOID_TYPE, setters.getOrDefault(SET_NAME, defaultEntry).getValue(), "setName method in Component class does not return void."),
                () -> assertEquals(VOID_TYPE, setters.getOrDefault(SET_WEIGHT, defaultEntry).getValue(), "setWeight method in Component class does not return void.")
            );
        }
    }
}
