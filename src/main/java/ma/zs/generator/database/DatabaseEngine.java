/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.database;

import ma.zs.generator.request.RequestJavatEngine;
import ma.zs.generator.reflexivity.ReflexivityGetterAndSetter;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.zs.generator.reflexivity.ReflexivityUtil;
import ma.zs.generator.util.StringFormatterUtil;

/**
 *
 * @author YOUNES
 */
public class DatabaseEngine {

    private static final List<String> types = new ArrayList<>();

    static {
        types.add("String");
        types.add("int");
        types.add("Integer");
        types.add("float");
        types.add("Float");
        types.add("double");
        types.add("Double");
        types.add("long");
        types.add("Long");
        types.add("boolean");
        types.add("Boolean");
        types.add("Date");
        types.add("BigDecimal");
    }

    public static void generateDB(String dbName) throws SQLException {
        try {

            generateTables("bean");
        } catch (Exception ex) {
            Logger.getLogger(RequestJavatEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String constructDaoCreateTableRequette(Class myClass) throws Exception {
        String tableName = StringFormatterUtil.lowerCaseTheFirstLetter(myClass);
        String requette = "CREATE TABLE IF NOT EXISTS  " + tableName.toUpperCase()  + "(\n";
        List<Method> mothods = ReflexivityGetterAndSetter.getGetterList(myClass);
        List<String> params = ReflexivityGetterAndSetter.getParameterName(mothods);
        List<String> typeParams = ReflexivityGetterAndSetter.getTypeParams(mothods);
        String cleEtranger = "";
        String sequence ="CREATE SEQUENCE PRD_"+tableName.toUpperCase()+"_SEQ INCREMENT BY 1 START WITH 1 NOCYCLE NOCACHE NOORDER;";
        for (int i = 0; i < typeParams.size(); i++) {
            String myType = typeParams.get(i);
            String param = params.get(i);
            if (ReflexivityUtil.isGenericType(mothods.get(i))) {
                requette += "" + param.toUpperCase()  + " " + myType.toUpperCase()  + " NOT NULL,\n";
            } else {
                Class referencedBean=mothods.get(i).getReturnType();
                cleEtranger += " ALTER TABLE " + tableName.toUpperCase() + " ADD CONSTRAINT "
                        + tableName.toUpperCase() + "_" + param.toUpperCase() + i + "_FK"
                        + " FOREIGN KEY (" + param.toUpperCase()  + ") REFERENCES " +referencedBean.getSimpleName().toUpperCase() 
                        + "_ID (" + ReflexivityGetterAndSetter.getIdName(referencedBean).toUpperCase()  + ");\n";
            }
        }
        requette += "PRIMARY KEY (" + ReflexivityGetterAndSetter.getIdName(myClass).toUpperCase()  + ")) ENGINE=MyISAM DEFAULT CHARSET=latin1;\n";
        return requette + cleEtranger + sequence;
    }

    public static String generateTablesCode() throws Exception {
        List<Class> myClasses = DataBaseUtil.getPojos();
        String resultat = "";
        for (int i = 0; i < myClasses.size(); i++) {
            Class myClass = myClasses.get(i);
            resultat += constructDaoCreateTableRequette(myClass) + "\n\n";
        }
        return resultat;
    }

    public static Map<Class, String> generateTables() throws Exception {
        List<Class> myClasses = DataBaseUtil.getPojos();
        Map<Class, String> resultats = new HashMap<>();
        for (int i = 0; i < myClasses.size(); i++) {
            Class myClass = myClasses.get(i);
            resultats.put(myClass, constructDaoCreateTableRequette(myClass));
        }
        return resultats;
    }

    public static void generateTables(String beanPath) throws Exception {
        List<Class> myClasses = DataBaseUtil.getPojos();
        for (int i = 0; i < myClasses.size(); i++) {
            Class myClass = myClasses.get(i);
            System.out.println(constructDaoCreateTableRequette(myClass));
          //  ConnectDB.exec(constructDaoCreateTableRequette(myClass));
        }

    }

    public static List<String> getTypes() {
        return types;
    }

}
