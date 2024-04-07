package helpers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassDefinitionHelper {

    private static final String GET = "get";
    public static final String SET = "set";
    public static final String PUBLIC_MODIFIER = Modifier.toString(Modifier.PUBLIC);

    public static final String PRIVATE_MODIFIER = Modifier.toString(Modifier.PRIVATE);

    public static final Map.Entry<Integer, String> DEFAULT_ENTRY = Map.entry(0, "");
    Class<?> testClass;

    public ClassDefinitionHelper(Class<?> testClass) {
        this.testClass = testClass;
    }

    public Map<String, Map.Entry<Integer,String>> getters() {
        var declaredMethods = testClass.getDeclaredMethods();

        return Arrays.stream(declaredMethods)
            .filter(method -> method.getName().startsWith(GET))
            .collect(
                Collectors.toMap(
                    method -> method.getName(),
                    method -> Map.entry(method.getModifiers(), method.getReturnType().getSimpleName())
                )
            );
    }

    public Map<String, Map.Entry<Integer,String>> setters() {
        var declaredMethods = testClass.getDeclaredMethods();

        return Arrays.stream(declaredMethods)
            .filter(method -> method.getName().startsWith(SET))
            .collect(
                Collectors.toMap(
                    method -> method.getName(),
                    method -> Map.entry(method.getModifiers(), method.getReturnType().getSimpleName())
                )
            );
    }

    public Map<String, Map.Entry<Integer,String>> attributes() {
        var declaredFields = testClass.getDeclaredFields();

        return Arrays.stream(declaredFields)
            .collect(
                Collectors.toMap(
                    field -> field.getName(),
                    field -> Map.entry(field.getModifiers(), field.getType().getSimpleName())
                )
            );
    }

    public boolean hasConstructor(Class<?>... parameterTypes) {
        try {
            testClass.getDeclaredConstructor(parameterTypes);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private String getGetterName(String attributeName) {
        return GET + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
    }

    public void testGetters(List<AttributeData> expectedAttributes) {

        var getters = getters();

        assertAll(
            expectedAttributes.stream()
                .flatMap(attribute -> Stream.of(
                 () -> assertTrue(getters.containsKey(getGetterName(attribute.name())), getGetterName(attribute.name()) + " method does not exist in " + testClass.getSimpleName() + " class."),
                 () -> assertEquals(PUBLIC_MODIFIER, Modifier.toString(getters.getOrDefault(getGetterName(attribute.name()), DEFAULT_ENTRY).getKey()), getGetterName(attribute.name()) + " method is not public in " + testClass.getSimpleName() + " class."),
                 () -> assertEquals(attribute.type(), getters.getOrDefault(getGetterName(attribute.name()), DEFAULT_ENTRY).getValue(), getGetterName(attribute.name()) + " method in " + testClass.getSimpleName() + " class is not of type " + attribute.type() + ".")))

        );
    }

    private String getSetterName(String attributeName) {
        return SET + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
    }

    public void testSetters(List<AttributeData> expectedAttributes) {

        var setters = setters();

        assertAll(
            expectedAttributes.stream()
                .flatMap(attribute -> Stream.of(
                 () -> assertTrue(setters.containsKey(getSetterName(attribute.name())), getSetterName(attribute.name()) + " method does not exist in " + testClass.getSimpleName() + " class."),
                 () -> assertEquals(PUBLIC_MODIFIER, Modifier.toString(setters.getOrDefault(getSetterName(attribute.name()), DEFAULT_ENTRY).getKey()), getSetterName(attribute.name()) + " method is not public in " + testClass.getSimpleName() + " class."),
                 () -> assertEquals("void", setters.getOrDefault(getSetterName(attribute.name()), DEFAULT_ENTRY).getValue(), getSetterName(attribute.name()) + " method in " + testClass.getSimpleName() + " class is not void.")))

        );

        if (setters.size() > expectedAttributes.size()) {
            List<String> extraSetters = setters.keySet().stream()
                .filter(setter -> !expectedAttributes.stream()
                    .map(attribute -> getSetterName(attribute.name()))
                    .anyMatch(attribute -> attribute.equals(setter)))
                .toList();
            throw new AssertionError(
                "Extra setters found in " + testClass.getSimpleName() + " class: " + extraSetters);
        }
    }

    public void testAttributes(List<AttributeData> expectedAttributes) {
        var attributes = attributes();

        assertAll(
            expectedAttributes.stream()
                .flatMap(attribute -> Stream.of(
                    () -> assertTrue(attributes.containsKey(attribute.name()),
                        attribute.name() + " attribute does not exist in " + testClass.getSimpleName() + " class."),
                    () -> assertEquals(attribute.modifier(),
                        Modifier.toString(attributes.getOrDefault(attribute.name(), DEFAULT_ENTRY).getKey()),
                        attribute.name() + " attribute in " + testClass.getSimpleName() + " class is not " +
                            attribute.modifier() + "."),
                    () -> assertEquals(attribute.type(),
                        attributes.getOrDefault(attribute.name(), DEFAULT_ENTRY).getValue(),
                        attribute.name() + " attribute in " + testClass.getSimpleName() + " class is not of type " +
                            attribute.type() + ".")))

        );
    }

    public void testConstructor(Class<?>... parameterTypes) {

        assertTrue(hasConstructor(parameterTypes),
            "Constructor with " + Arrays.toString(parameterTypes) + " parameters does not exist in " +
                testClass.getSimpleName() + " class.");

    }

    public void testMethodDefinition(MethodData methodData) {
        try {
            Method method = testClass.getMethod(methodData.name(), methodData.parameterTypes());
            assertAll(
                () -> assertEquals(methodData.modifier(), Modifier.toString(method.getModifiers()),
                    methodData.name() + " method in " + testClass.getSimpleName() + " class is not " +
                        methodData.modifier() + "."),
                () -> assertEquals(methodData.returnType(), method.getReturnType().getSimpleName(),
                    methodData.name() + " method in " + testClass.getSimpleName() + " class does not return " +
                        methodData.returnType() + ".")
            );
        } catch (NoSuchMethodException e) {
            throw new AssertionError(
                methodData.name() + " method does not exist in " + testClass.getSimpleName() + " class.");
        }
    }
}
