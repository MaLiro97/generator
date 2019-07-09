/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.reflexivity;

import ma.zs.generator.database.DatabaseEngine;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author YOUNES
 */
public class ReflexivityUtil {

    public static boolean isNumberType(String type) {
        return isGenericType(type) && !type.equals("String");
    }

    public static boolean isString(String type) {
        return type.equals("String");
    }

    public static boolean isDate(String type) {
        return type.equals("Date");
    }

    public static boolean isGenericType(Method method) {
        return isGenericType(method.getReturnType().getSimpleName());
    }

    public static boolean isGenericType(String type) {
        for (int i = 0; i < DatabaseEngine.getTypes().size(); i++) {
            String myType = DatabaseEngine.getTypes().get(i);
            if (type.equals(myType)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isRequestMinMax(String type){
        if (type.equals("String") || type.equals("Boolean") || type.equals("boolean")) {
            return false;
        }else{
            return true;
        }
    }

    public static String constructGetterName(String type) {
        String retourn = "";
        if (type.equals("String")) {
            retourn = "String";
        } else if (type.equals("Integer") || type.equals("int")) {
            retourn = "Int";
        } else if (type.equals("Long") || type.equals("long")) {
            retourn = "Long";
        } else if (type.equals("Double") || type.equals("double")) {
            retourn = "Double";
        } else if (type.equals("Folat") || type.equals("float")) {
            retourn = "Folat";
        } else if (type.equals("Boolean") || type.equals("boolean")) {
            retourn = "Boolean";
        } else if (type.equals("Date")) {
            retourn = "Date";
        } else if (type.equals("BigDecimal")) {
            retourn = "BigDecimal";
        } else {
            return type;
        }
        return "get" + retourn;
    }

    public static String typeJavaToSql(String typeRetour) throws Exception {
        if (typeRetour.equals("String")) {
            return ("VARCHAR(255)");
        } else if (typeRetour.equals("int") || typeRetour.equals("Integer")) {
            return ("INT(5)");
        } else if (typeRetour.equals("long") || typeRetour.equals("Long")) {
            return ("INT(10)");
        } else if (typeRetour.equals("boolean") || typeRetour.equals("Boolean")) {
            return ("INT(1)");
        } else if (typeRetour.equals("Double") || typeRetour.equals("double") || typeRetour.equals("Float") || typeRetour.equals("float") || typeRetour.equals("BigDecimal")) {
            return ("Decimal(38.2)");
        } else if (typeRetour.equals("Date")) {
            return ("Date");
        }
        return ("Decimal(38.2)");
    }

    public static Object makeInstance(Class myClass, List<Method> setters, List<Object> values) {
        Object myObject = null;
        try {
            myObject = myClass.newInstance();
            for (int i = 0; i < values.size(); i++) {
                Object value = values.get(i);
                ReflexivityLuncher.lunchSetter(myClass, setters.get(i), value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myObject;
    }

}
