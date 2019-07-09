/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.bean;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import ma.zs.generator.business.pojo.Pojo;
import ma.zs.generator.business.pojo.PojoItem;
import ma.zs.generator.business.pojo.PojoUtilEnginImpl;
import ma.zs.generator.business.projet.ProjetBusinessConfig;
import ma.zs.generator.util.FileUtil;
import ma.zs.generator.util.StringFormatterUtil;
import org.springframework.stereotype.Service;

/**
 *
 * @author ismail
 */
@Service
public class BeanEngineImpl implements BeanEngine {

    @Override
    public void generateBeans() throws IOException {
        generateBeanDir();
        generateBeanFile();
    }

    @Override
    public void generateBeanDir() throws IOException {
        FileUtil.createDirectory(getPath());
    }

    @Override
    public String getPath() {
        return ProjetBusinessConfig.getProjectPathWithPackagePath() + BeanEngineConfig.getPackagePath();
    }

    @Override
    public void generateBeanFile() throws IOException {
        List<Pojo> pojos = new PojoUtilEnginImpl().getPojos();
        for (Pojo pojo : pojos) {
            System.out.println(">>>>>>>>>>>>>>><<<<<<<<<<" + pojo.getPojoName());
            FileUtil.generateFile(pojo.getClazz(), new BeanEngineImpl().generate(pojo), getPath(), ".java");
        }
    }

    @Override
    public String generate(Pojo myPojo) {
        String res = generateFields(myPojo);
        res += "\n"
                + generateConstructor(myPojo)
                + generateGetterSimple(myPojo)
                + generateSetterSimple(myPojo)
                + generateGetterGeneric(myPojo)
                + generateSetterGeneric(myPojo)
                + generateGetterList(myPojo)
                + generateSetterList(myPojo)
                + hashCodeAndEquals(myPojo)
                + "\n";
        return generateClass(myPojo, res);
    }

    @Override
    public String generateClass(Pojo myPojo, String content) {
        return generateImports(myPojo)
                + BeanEngineConfig.getAnnotationClass() + "\n"
                + "public class " + myPojo.getPojoName() + " implements Serializable{\n\n"
                + content
                + "\n\n}";
    }

    @Override
    public String generateFields(Pojo myPojo) {
        String result = "";
        result += generateSimpleFields(myPojo);
        result += generateGenericFields(myPojo);
        result += generateListFields(myPojo);
        return result;
    }

    @Override
    public String generateSimpleFields(Pojo myPojo) {
        String result = "";
        List<Field> fields = myPojo.getFieldsSimple();
        for (Field field : fields) {
            String type = wrapType(field.getType().getSimpleName());
            if (field.getName().equals(myPojo.getIdName())) {
                result += BeanEngineConfig.getAutoIncrement() + "\n";
                result += BeanEngineConfig.getAnnotationId() + "\n";
                result += "private " + type + " " + field.getName() + " ;\n";
            } else if (field.getType().getSimpleName().equals("Date")) {
                result += BeanEngineConfig.getAnnotationDate() + "\n";
                result += "private " + field.getType().getSimpleName() + " " + field.getName() + " ;\n";
            } else {
                result += "private " + type + " " + field.getName() + " ;\n";

            }
        }
        return result;
    }

    @Override
    public String generateGenericFields(Pojo myPojo) {
        String result = "";
        List<Field> fields = myPojo.getFieldsGeneric();
        for (Field field : fields) {
            result += BeanEngineConfig.getAnnotationMappingObject() + "\n";
            result += "private " + field.getType().getSimpleName() + " " + field.getName() + " ;\n";
        }
        return result;
    }

    @Override
    public String generateListFields(Pojo myPojo) {
        String result = "";
        List<PojoItem> fields = myPojo.getFieldsList();
        for (PojoItem field : fields) {
            if (field.isPrimitif()) {
                result += "private " + "List<" + field.getPojoItemType() + "> " + field.getField().getName() + " ;\n";
            } else {
                String mappingBy = StringFormatterUtil.lowerCaseTheFirstLetter(myPojo.getPojoName());
                result += BeanEngineConfig.getAnnotationMappingListOpen() + mappingBy + BeanEngineConfig.getAnnotationMappingListClose() + "\n";
                result += "private " + "List<" + field.getPojoItemType() + "> " + field.getField().getName() + " ;\n";

            }
        }
        return result;
    }

    @Override
    public String generateGetterSimple(Pojo myPojos) {
        List<Field> fields = myPojos.getFieldsSimple();
        String res = "";
        for (Field field : fields) {
            String type = wrapType(field.getType().getSimpleName());
            String fieldName = field.getName();
            String fieldNameUpper = StringFormatterUtil.upperCaseTheFirstLetter(field.getName());
            res += "public " + type + " get" + fieldNameUpper + "() {\n return " + fieldName + " ;\n}\n";
        }
        return res;

    }

    @Override
    public String generateSetterSimple(Pojo myPojos) {

        List<Field> fields = myPojos.getFieldsSimple();
        String res = "";
        for (Field field : fields) {
            String type = wrapType(field.getType().getSimpleName());
            String fieldName = field.getName();
            String fieldNameUpper = StringFormatterUtil.upperCaseTheFirstLetter(field.getName());
            res += "public void set" + fieldNameUpper + "(" + type + " " + fieldName + ") {\n this." + fieldName + " = " + fieldName + ";\n }\n";
        }
        return res;
    }

    @Override
    public String generateGetterGeneric(Pojo myPojos) {
        List<Field> fields = myPojos.getFieldsGeneric();
        String res = "";
        for (Field field : fields) {
            String type = field.getType().getSimpleName();
            String fieldName = field.getName();
            String fieldNameUpper = StringFormatterUtil.upperCaseTheFirstLetter(field.getName());
            res += "public " + type + " get" + fieldNameUpper + "() {\n return " + fieldName + " ;\n}\n";
        }
        return res;
    }

    @Override
    public String generateSetterGeneric(Pojo myPojos) {
        List<Field> fields = myPojos.getFieldsGeneric();
        String res = "";
        for (Field field : fields) {
            String type = field.getType().getSimpleName();
            String fieldName = field.getName();
            String fieldNameUpper = StringFormatterUtil.upperCaseTheFirstLetter(field.getName());
            res += "public void set" + fieldNameUpper + "(" + type + " " + fieldName + ") {\n this." + fieldName + " = " + fieldName + ";\n }\n";
        }
        return res;
    }

    @Override
    public String generateGetterList(Pojo myPojos) {
        List<PojoItem> fields = myPojos.getFieldsList();
        String res = "";
        for (PojoItem field : fields) {
            String type = "List<" + field.getPojoItemType() + ">";
            String fieldName = field.getField().getName();
            String fieldNameUpper = StringFormatterUtil.upperCaseTheFirstLetter(fieldName);
            res += "public " + type + " get" + fieldNameUpper + "() {\n return " + fieldName + " ;\n}\n";
        }
        return res;
    }

    @Override
    public String generateSetterList(Pojo myPojos) {

        List<PojoItem> fields = myPojos.getFieldsList();
        String res = "";
        for (PojoItem field : fields) {
            String type = "List<" + field.getPojoItemType() + ">";
            String fieldName = field.getField().getName();
            String fieldNameUpper = StringFormatterUtil.upperCaseTheFirstLetter(fieldName);
            res += "public void set" + fieldNameUpper + "(" + type + " " + fieldName + ") {\n this." + fieldName + " = " + fieldName + ";\n }\n";
        }
        return res;
    }

    @Override
    public String generateImports(Pojo myPojo) {
        String res = "package " + ProjetBusinessConfig.getProjectPackageBase() + "." + BeanEngineConfig.getPackageName() + ";\n"
                + "\n"
                + "import java.io.Serializable;\n"
                + "import javax.persistence.Entity;\n"
                + "import javax.persistence.GeneratedValue;\n"
                + "import javax.persistence.GenerationType;\n"
                + "import javax.persistence.Id;\n";
        if (existType("List", myPojo)) {
            res += "import java.util.List;\n"
                    + "import javax.persistence.OneToMany;\n";
        }
        if (existType("Date", myPojo)) {
            res += "import javax.persistence.Temporal;\n"
                    + "import java.util.Date;";
        }
        if (existGenericType(myPojo)) {
            res += "import javax.persistence.ManyToOne;\n\n";
        }
        return res;
    }

    private Boolean existType(String type, Pojo myPojo) {

        List<Field> fields = myPojo.getFields();
        List<Field> fieldsG = myPojo.getFieldsGeneric();
        for (Field field : fields) {
            if (field.getType().getSimpleName().equals(type)) {
                return true;
            }
        }
        return false;

    }

    private Boolean existGenericType(Pojo myPojo) {
        List<Field> fieldsG = myPojo.getFieldsGeneric();
        if (fieldsG.size() != 0) {
            return true;
        }
        return false;
    }

    private String wrapType(String type) {
        if (type.equals("int")) {
            return "Integer";
        } else {
            return StringFormatterUtil.upperCaseTheFirstLetter(type);
        }

    }

    @Override
    public String hashCodeAndEquals(Pojo myPojo) {
        return " @Override\n"
                + "    public int hashCode() {\n"
                + "        int hash = 0;\n"
                + "        hash += (id != null ? id.hashCode() : 0);\n"
                + "        return hash;\n"
                + "    }\n"
                + "\n"
                + "    @Override\n"
                + "    public boolean equals(Object object) {\n"
                + "        // TODO: Warning - this method won't work in the case the id fields are not set\n"
                + "        if (!(object instanceof " + myPojo.getPojoName() + ")) {\n"
                + "            return false;\n"
                + "        }\n"
                + "        " + myPojo.getPojoName() + " other = (" + myPojo.getPojoName() + ") object;\n"
                + "        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {\n"
                + "            return false;\n"
                + "        }\n"
                + "        return true;\n"
                + "    }";
    }

    @Override
    public String generateConstructor(Pojo myPojo) {
        return "public " + myPojo.getPojoName() + "() {\n"
                + "    }\n\n";
    }

}
