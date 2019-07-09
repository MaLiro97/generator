/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.vo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.zs.generator.business.pojo.Pojo;
import ma.zs.generator.business.pojo.PojoItem;
import ma.zs.generator.business.pojo.PojoUtilEnginImpl;
import ma.zs.generator.business.projet.ProjetBusinessConfig;
import ma.zs.generator.util.FileUtil;
import ma.zs.generator.util.StringFormatterUtil;
import org.springframework.stereotype.Component;

/**
 *
 * @author abdou
 */
@Component
public class VoEngineImpl implements VoEngine {

    @Override
    public String generateAttributeSimple(Pojo pojo) {
        List<Field> fields = pojo.getFieldsSimple();
        String resultat = "";

        for (Field field : fields) {
            String type = field.getType().getSimpleName();
            if (type.equals("String") || type.equals("Boolean") || type.equals("boolean") || field.getName().equals("id")) {
                resultat += "private String " + field.getName() + " ;\n";
            } else {
                resultat += "private String " + field.getName() + ";\n";
                resultat += "private String " + field.getName() + "Min ;\n";
                resultat += "private String " + field.getName() + "Max ;\n";

            }
        }
        return resultat;
    }

    @Override
    public String generateGetterAndSetterSimple(Pojo pojo) {
        List<Field> fields = pojo.getFieldsSimple();
        String getterSetter = "";
        for (Field field : fields) {
            String type = field.getType().getSimpleName();
            if (type.equals("String") || type.equals("Boolean") || type.equals("boolean") || field.getName().equals("id")) {
                getterSetter += generateGetter("String ", field.getName());
                getterSetter += generateSetter("String ", field.getName());
            } else {
                getterSetter += generateGetter("String ", field.getName());
                getterSetter += generateSetter("String ", field.getName());
                getterSetter += generateGetter("String ", field.getName() + "Min");
                getterSetter += generateSetter("String ", field.getName() + "Min");
                getterSetter += generateGetter("String ", field.getName() + "Max");
                getterSetter += generateSetter("String ", field.getName() + "Max");
            }
        }
        return getterSetter;
    }

    @Override
    public String generateAttributeGeneric(Pojo pojo) {
        List<Field> fields = pojo.getFieldsGeneric();
        String resultat = "";
        for (Field field : fields) {
            String type = field.getType().getSimpleName();
            resultat += "private " + type + VoConfig.getVoPrefix() + " " + field.getName() + VoConfig.getVoPrefix() + " ;\n";
        }
        return resultat;
    }

    @Override
    public String generateGetterAndSetterGeneric(Pojo pojo) {
        List<Field> fields = pojo.getFieldsGeneric();
        String getterSetter = "";
        for (Field field : fields) {
            String type = field.getType().getSimpleName();
            getterSetter += generateGetter(field.getType().getSimpleName() + VoConfig.getVoPrefix() + " ", field.getName() + VoConfig.getVoPrefix() + " ");
            getterSetter += generateSetter(field.getType().getSimpleName() + VoConfig.getVoPrefix() + " ", field.getName() + VoConfig.getVoPrefix() + " ");
        }
        return getterSetter;
    }

    @Override
    public String generateAttributeList(Pojo pojo) {
        List<PojoItem> pojoItem = pojo.getFieldsList();
        String resultat = "";
        for (PojoItem pojoI : pojoItem) {
            resultat += "private List<" + pojoI.getPojoItemType() + "" + VoConfig.getVoPrefix() + ">" + StringFormatterUtil.lowerCaseTheFirstLetter(pojoI.getPojoItemType()) + "" + VoConfig.getVoPrefixList() + ";\n";
        }
        return resultat;
    }

    @Override
    public String generateGetterAndSetterList(Pojo pojo) {
        List<PojoItem> pojoItem = pojo.getFieldsList();
        String getterSetter = "";
        for (PojoItem pojoI : pojoItem) {
            getterSetter += generateGetterList(pojoI.getPojoItemType());
            getterSetter += generateSetterList(pojoI.getPojoItemType());
        }
        return getterSetter;
    }

    //Globale 
    @Override
    public String generateFields(Pojo pojo) {
        String result = "";
        try {
            result = generateImport();
        } catch (Exception ex) {
            Logger.getLogger(VoEngineImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        result += generateClassHeader(pojo);
        result += generateAttributeSimple(pojo);
        result += generateAttributeGeneric(pojo);
        result += generateAttributeList(pojo) + "\n\n\n";
        result += generateGetterAndSetterSimple(pojo);
        result += generateGetterAndSetterGeneric(pojo);
        result += generateGetterAndSetterList(pojo);
        return result + "\n\n\n}";
    }

    @Override
    public String generateImport() {
        return "package " + ProjetBusinessConfig.getProjectPackageBase() + "." + VoConfig.getPackageName() + ";\n" + "\n"
                + "import java.util.ArrayList;\n"
                + "import java.util.List;\n\n\n";

    }

    @Override
    public String generateClassHeader(Pojo pojo) {
        String className = pojo.getPojoName();
        return "public class " + className + "" + VoConfig.getVoPrefix() + "{\n" + "\n";
    }

    @Override
    public String generateGetter(String type, String fieldName) {
        return "public " + type
                + " get" + StringFormatterUtil.upperCaseTheFirstLetter(fieldName)
                + "()"
                + "{\n"
                + "    return " + fieldName + ";\n}\n\n";
    }

    @Override
    public String generateSetter(String type, String fieldName) {
        return "public void"
                + " set" + StringFormatterUtil.upperCaseTheFirstLetter(fieldName)
                + "(" + type + " " + fieldName + ")"
                + "{\n"
                + "     this." + fieldName + " = " + fieldName + ";\n}\n\n";
    }

    @Override
    public String generateGetterList(String fieldName) {
        return "public List<" + StringFormatterUtil.upperCaseTheFirstLetter(fieldName) + "" + VoConfig.getVoPrefix() + ">"
                + " get" + StringFormatterUtil.upperCaseTheFirstLetter(fieldName)
                + "" + VoConfig.getVoPrefixList() + "()"
                + "{\n"
                + "    return " + StringFormatterUtil.lowerCaseTheFirstLetter(fieldName) + "" + VoConfig.getVoPrefixList() + ";\n}\n\n";
    }

    @Override
    public String generateSetterList(String fieldName) {
        return "public void"
                + " set" + StringFormatterUtil.upperCaseTheFirstLetter(fieldName)
                + "" + VoConfig.getVoPrefix() + "(List<" + StringFormatterUtil.upperCaseTheFirstLetter(fieldName) + "" + VoConfig.getVoPrefix() + "> " + fieldName + "" + VoConfig.getVoPrefixList() + ")"
                + "{\n"
                + "     this." + StringFormatterUtil.lowerCaseTheFirstLetter(fieldName) + "sVo = " + StringFormatterUtil.lowerCaseTheFirstLetter(fieldName) + "" + VoConfig.getVoPrefixList() + ";\n}\n\n";
    }

    @Override
    public void generateVo() {
        try {
            generateDir();
        } catch (IOException ex) {
            Logger.getLogger(VoEngineImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Pojo> pojos = new PojoUtilEnginImpl().getPojos();
        for (Pojo pojo : pojos) {
            try {
                FileUtil.generateFile(constructFileName(pojo), generateFields(pojo), getDir());
            } catch (IOException ex) {
                Logger.getLogger(VoEngineImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static String getDir() throws IOException {
        return (ProjetBusinessConfig.getProjectPath() + ProjetBusinessConfig.getProjectPackageBasePath() + VoConfig.getPackagePath());
    }

    public static void generateDir() throws IOException {
        FileUtil.createDirectory(getDir());
    }

    @Override
    public String constructFileName(Pojo pojo) {
        return pojo.getPojoName() + "Vo.java";
    }
}
