/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author YOUNES
 */
public class DataBaseMapping {

    private static final Map<String, String> mapping = new HashMap<>();

    static {
        mappBeanId();
    }

    public static boolean isId(String beanName, String id) {
        return mapping.get(beanName).equalsIgnoreCase(id);
    }

    public static boolean isId(Class myClass, String id) {
        return mapping.get(myClass.getName()).equalsIgnoreCase(id);
    }

    public static String findMappedId(String beanName) {
        return mapping.get(beanName);
    }

    public static String findMappedId(Class myClass) {
        return mapping.get(myClass.getCanonicalName());
    }

    public static String findMappedIdGetter(String beanName) {
        return "get" + mapping.get(beanName).toLowerCase();
    }

    private static void mappBeanId() {
        try {
            List<String> myClasses = DataBaseUtil.findPojoName();
            for (String myClasse : myClasses) {
                if (!mapping.containsKey(myClasse)) {
                    mapping.put(myClasse, "id");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DataBaseMapping.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
