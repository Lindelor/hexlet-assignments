package exercise;

import java.lang.reflect.Field;
import java.text.Annotation;
import java.util.*;

// BEGIN
public class Validator {
    public static List<String> validate(Address address) {
        List<String> result = new ArrayList<>();
        for (Field field : address.getClass().getDeclaredFields()) {
            String check = notNullCheck(address, field);
            if (check != null)
                result.add(field.getName());
        }
        return result;
    }

    public static Map<String, List<String>> advancedValidate(Address address) {
        Map<String, List<String>> result = new HashMap<>();
        for (Field field : address.getClass().getDeclaredFields()) {
            String nullCheck = notNullCheck(address, field);
            String lengthCheck = minLengthCheck(address, field);
            List<String> errors = new ArrayList<>();

            if (nullCheck != null) {
                errors.add("cannot be null");
            }
            if (lengthCheck != null) {
                errors.add(lengthCheck);
            }

            if (!errors.isEmpty()) {
                result.put(field.getName(), errors);
            }

        }
        return result;
    }

    private static String notNullCheck(Address address, Field field) {
        try {
            field.setAccessible(true);
            if (field.getAnnotation(NotNull.class) != null && field.get(address) == null) {
                return field.getName();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String minLengthCheck(Address address, Field field) {
        try {
            field.setAccessible(true);
            if (field.get(address) == null) {
                return String.format("length less than %s", field.getAnnotation(MinLength.class).minLength());
            }
            if (field.getAnnotation(MinLength.class) != null && field.get(address).toString().length() <
                    field.getAnnotation(MinLength.class).minLength()) {
                return String.format("length less than %s", field.getAnnotation(MinLength.class).minLength());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
// END
