/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.management.Attribute;
import org.omg.Dynamic.Parameter;

/**
 *
 * @author Anas
 */
public class GetterAndSetterUtil {

    //hadi tan3tiha les attribus ou tatssawb lihum lgetter
    public static List<String> generateGettersFromFields(Field[] fields) {
        List<String> getters = new ArrayList<>();
        for (Field field : fields) {
            String getterMethode = "public " + field.getType().getSimpleName()
                    + " get" + StringFormatterUtil.upperCaseTheFirstLetter(field.getName())
                    + "()"
                    + "{\n"
                    + "return " + field.getName() + ";\n}\n";
            getters.add(getterMethode);
        }
        return getters;
    }

    //hadi tan3tiha hi class ou tatssawb lia lgetter dial objet dial had la class
    public static String generateClassGetter(String myClassName) {
        String getterMethode = "\n public " + myClassName
                + " get" + myClassName
                + "()"
                + "{\n"
                + "return " + StringFormatterUtil.lowerCaseTheFirstLetter(myClassName)
                + ";\n}\n";
        return getterMethode;
    }
        //hadi tan3tiha hi class ou tatssawb lia setter dial objet dial had la class
    public static String generateClassSetter(String myClassName) {
        String setterMethode = " \n public void"
                + " set" + myClassName
                + "("+myClassName+" "+StringFormatterUtil.lowerCaseTheFirstLetter(myClassName)+ ")"
                + "{\n"
                + "this." + StringFormatterUtil.lowerCaseTheFirstLetter(myClassName)+"="+StringFormatterUtil.lowerCaseTheFirstLetter(myClassName)
                + ";\n}\n";
        return setterMethode;
    }
}
