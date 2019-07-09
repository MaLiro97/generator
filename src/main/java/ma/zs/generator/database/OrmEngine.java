/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.database;

import ma.zs.generator.reflexivity.ReflexivityGetterAndSetter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ma.zs.generator.reflexivity.ReflexivityUtil;
import ma.zs.generator.util.StringFormatterUtil;

/**
 *
 * @author YOUNES
 */
public class OrmEngine {

  
    private static String constructOrm(Class myClass, String module, String moduleAbreviation) throws Exception {
        String idName = ReflexivityGetterAndSetter.getIdName(myClass);
        String tableName = myClass.getSimpleName().toUpperCase();
        String requette = "<entity class=\"samac2." + module + ".production.dal." + myClass.getSimpleName() + "BO\">\n"
                + " <table name=\"" + moduleAbreviation + "_PRD_" + myClass.getSimpleName().toUpperCase() + "\" />\n"
                + " <attributes>\n"
                + "   <id name=\"" + idName + "\">\n"
                + "    <column name=\"" + idName.toUpperCase() + "\"/>\n"
                + "		<generated-value generator=\"PRD_" + tableName + "_SEQ\" strategy=\"SEQUENCE\"/>\n"
                + "		<sequence-generator name=\"PRD_" + tableName + "_SEQ\" sequence-name=\"PRD_" + tableName + "_SEQ\" allocation-size=\"1\"/>\n"
                + "   </id>\n";
        List<Method> mothods = ReflexivityGetterAndSetter.getGetterListExceptId(myClass);
        List<String> params = ReflexivityGetterAndSetter.getParameterName(mothods);
        List<String> typeParams = ReflexivityGetterAndSetter.getTypeParams(mothods);
        String reference = "";
        String fin = " </attributes>\n"
                + " </table>\n"
                + "</entity>\n";
        for (int i = 0; i < typeParams.size(); i++) {
            String myType = typeParams.get(i);
            String param = params.get(i);
            if (ReflexivityUtil.isGenericType(mothods.get(i))) {
                String dateDetail = "";
                if (myType.equalsIgnoreCase("DATE")) {
                    dateDetail = "      <temporal>DATE</temporal>\n";
                }
                requette += "  <basic name=\"" + param + "\">\n"
                        + "	      <column name=\"" + param.toUpperCase() + "\" />\n"
                        + dateDetail
                        + "	</basic>\n";
            } else {
                Class referencedBean = mothods.get(i).getReturnType();
                String paramName = ReflexivityGetterAndSetter.getParameterName(mothods.get(i));
                reference += "  <many-to-one name=\"ref" + StringFormatterUtil.upperCaseTheFirstLetter(paramName) + "Ident\" target-entity=\"samac2." + module + ".referentiel.dal." + referencedBean.getSimpleName() + "BO\" fetch=\"LAZY\">\n"
                        + "		<join-column name=\"ID_" + paramName.toUpperCase() + "\"/>\n"
                        + "	 </many-to-one>\n";
            }
        }
        return requette + reference + fin;
    }

    public static String generateFileContent(String module, String moduleAbreviation) throws Exception {
        List<Class> myClasses = DataBaseUtil.getPojos();
        String resultats = "";
        for (int i = 0; i < myClasses.size(); i++) {
            Class myClass = myClasses.get(i);
            resultats += constructOrm(myClass, module, moduleAbreviation) + "\n\n";
        }
        return resultats;
    }

}
