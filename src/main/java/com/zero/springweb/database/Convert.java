package com.zero.springweb.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Convert {
    public void fillObjectFromResultSet(Object object, ResultSet resultSet) throws SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {

            String fieldName = field.getName();

            Object value = resultSet.getObject(fieldName);

            setFieldValue(object, field, value);

        }
    }

    private static void setFieldValue(Object object, Field field, Object value) throws SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class<?> fieldType = field.getType();
        String methodName = "set" + field.getName();
        Method setter = object.getClass().getDeclaredMethod(capitalizeThirdLetter(methodName), fieldType);

        setter.invoke(object, value);
    }

    private static String capitalizeThirdLetter(String str) {
        if (str.length() < 3) {
            return str;
        } else {
            char[] chars = str.toCharArray();
            chars[3] = Character.toUpperCase(chars[3]);
            return new String(chars);
        }
    }
}
