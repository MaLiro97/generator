package ma.zs.generator.business.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import ma.zs.generator.business.pojo.Pojo;
import ma.zs.generator.business.pojo.PojoItem;
import ma.zs.generator.business.pojo.PojoUtilEngin;

import org.springframework.stereotype.Service;

import ma.zs.generator.business.projet.ProjetBusinessConfig;
import ma.zs.generator.business.utilEngine.UtilEngineConfig;
import ma.zs.generator.util.FileUtil;
import ma.zs.generator.util.ListUtil;
import ma.zs.generator.util.StringFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ServiceImplEngineImpl implements ServiceImplEngine {

    @Autowired
    private PojoUtilEngin pojoUtil;

    @Override
    public void generate() throws IOException {
        generateDir();
        List<Pojo> pojos = pojoUtil.getPojos();
        for (Pojo pojo : pojos) {
            FileUtil.generateFile(constructFileName(pojo), generateFile(pojo), getDir());
        }
    }

    public static void generateDir() throws IOException {
        FileUtil.createDirectory(ProjetBusinessConfig.getProjectPath() + ProjetBusinessConfig.getProjectPackageBasePath() + ServiceImplConfig.getPackagePath());
    }

    public static String getDir() {
        return (ProjetBusinessConfig.getProjectPath() + ProjetBusinessConfig.getProjectPackageBasePath() + ServiceImplConfig.getPackagePath());
    }

    @Override
    public String generateFile(Pojo pojo) {
        String resultat = generateImport(pojo);
        resultat += generateClassHeader(pojo);
        resultat += generateAttributes(pojo);
        resultat += generateSave(pojo);
        resultat += generateSaveWithItems(pojo);

        resultat += generateFindAll(pojo);
        resultat += generateFindById(pojo);
        resultat += generateFindByReference(pojo);

        resultat += generateRemove(pojo);
        resultat += generateRemoveById(pojo);
        resultat += generateRemoveByReference(pojo);

        resultat += generateClone(pojo);

        resultat += generateFindByCriteria(pojo);
        resultat += generateMethodConstructQuery(pojo);
        resultat += "}\n";
        return (resultat);
    }

    @Override
    public String generateSaveWithItems(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        String resultat = "";
        for (PojoItem pojoItem : pojo.getFieldsListGenric()) {

            String typeName = pojoItem.getPojoItemType();
            String AttribuName = pojoItem.getField().getName();
            resultat += ServiceImplConfig.getOverride() + "public " + className + "  " + ServiceImplConfig.getSaveWithItemsMethodePrefix() + StringFormatterUtil.upperCaseTheFirstLetter(AttribuName) + " (" + className + " " + classNameLowerCase + ")" + "{\n"
                    + "\n" + "if(" + classNameLowerCase + "== null){ \n"
                    + "        return null; "
                    + "\n}else {\n ";

            resultat += "  if(" + classNameLowerCase + ".get" + StringFormatterUtil.upperCaseTheFirstLetter(AttribuName) + "().isEmpty()){\n"
                    + "            return null;\n"
                    + "        }else{\n";

            resultat += "            for (" + typeName + " " + typeName.toLowerCase() + " : " + classNameLowerCase + ".get" + StringFormatterUtil.upperCaseTheFirstLetter(AttribuName) + "()) {\n";
            if (isPojoContainsAttribu(pojoItem.getPojo(), className)) {
                Field f = findFild(pojoItem.getPojo(), className);

                resultat += typeName.toLowerCase() + ".set" + StringFormatterUtil.upperCaseTheFirstLetter(f.getName()) + "(" + classNameLowerCase + ");\n "
                        + "";
            }
            resultat += typeName.toLowerCase() + ServiceImplConfig.getServiceSuffix() + "." + ServiceImplConfig.getSaveMethodeName() + "(  " + typeName.toLowerCase() + ");          \n";
            resultat += "            }\n";
            resultat += "            }\n";
            resultat += "" + "" + classNameLowerCase
                    + ServiceImplConfig.getDaoSuffix() + ".save(" + classNameLowerCase + ");\n" + "return " + classNameLowerCase + ";\n" + "}\n" + "}\n";

        }

        return resultat;
    }

    @Override
    public String generateSave(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();

        return ServiceImplConfig.getOverride()
                + "public " + className + "  " + ServiceImplConfig.getSaveMethodeName() + " (" + className + " " + classNameLowerCase + ")" + "{\n"
                + "\n" + "if(" + classNameLowerCase + "== null){ \n return null; \n}else {\n " + "" + classNameLowerCase
                + ServiceImplConfig.getDaoSuffix() + ".save(" + classNameLowerCase + ");\n" + "return " + classNameLowerCase + ";\n" + "}\n" + "}\n";

    }

    private boolean isPojoContainsAttribu(Pojo pojo, String attribu) {
        return findFild(pojo, attribu) != null;
    }

    private Field findFild(Pojo pojo, String myChaine) {
        for (Field declaredField : pojo.getFieldsGeneric()) {
            if (declaredField.getType().getSimpleName().equals(myChaine)) {
                return declaredField;
            }
        }
        return null;
    }

    @Override
    public String generateRemove(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        String resultat = "";
        resultat += ServiceImplConfig.getOverride()
                + "public int " + ServiceImplConfig.getDeleteMethodeName() + "(" + className + " " + classNameLowerCase + ")" + "{\n"
                + "if(" + classNameLowerCase + "== null){ \n"
                + "  return -1; \n"
                + "}else {\n "
                + "" + classNameLowerCase + ServiceImplConfig.getDaoSuffix() + ".delete("
                + classNameLowerCase + ");\n" + "return 1 ;\n" + "}\n" + "}\n";
        return resultat;
    }

    @Override
    public String generateRemoveById(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        return ServiceImplConfig.getOverride()
                + "public void " + ServiceImplConfig.getDeleteByPrefix() + "Id(" + pojo.getIdType() + " " + pojo.getIdName() + "){\n"
                + "       " + classNameLowerCase + ServiceImplConfig.getDaoSuffix() + ".deleteById(" + pojo.getIdName() + ");\n"
                + "}\n";
    }

    @Override
    public String generateRemoveByReference(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        if (pojo.getReferenceName() != null) {
            return ServiceImplConfig.getOverride()
                    + "public void  " + ServiceImplConfig.getDeleteByPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(pojo.getReferenceName()) + "(" + pojo.getReferenceType() + "  " + pojo.getReferenceName() + "){\n"
                    + "  " + classNameLowerCase
                    + ServiceImplConfig.getDaoSuffix() + "." + ServiceImplConfig.getDeleteMethodeName() + "By" + StringFormatterUtil.upperCaseTheFirstLetter(pojo.getReferenceName()) + "(" + pojo.getReferenceName() + ");\n" + "}\n";
        }
        return "";
    }

    @Override
    public String generateFindAll(Pojo pojo) {

        String className = constructClassName(pojo);
        String classNameLowerCase = className.toLowerCase();

        return ServiceImplConfig.getOverride()
                + "public List< " + className + ">  " + ServiceImplConfig.getFindByAllMethodePrefix() + "(" + ")" + "{\n" + " return "
                + classNameLowerCase + ServiceImplConfig.getDaoSuffix() + ".findAll();\n" + "}\n";

    }

    @Override
    public String generateFindById(Pojo pojo) {

        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        String resultat = ServiceImplConfig.getOverride()
                + "public " + className + " findById(" + pojo.getIdType() + " " + pojo.getIdName() + "){\n" + " return " + classNameLowerCase
                + ServiceImplConfig.getDaoSuffix() + ".getOne(" + pojo.getIdName() + ");\n"
                + "}\n";
        return resultat;

    }

    @Override
    public String generateFindByReference(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        String resultat = "";
        if (pojo.getReferenceName() != null) {
            return ServiceImplConfig.getOverride()
                    + "public " + className + "  " + ServiceImplConfig.getFindByPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(pojo.getReferenceName()) + "(" + pojo.getReferenceType() + "  " + pojo.getReferenceName() + "){\n"
                    + " return " + classNameLowerCase
                    + ServiceImplConfig.getDaoSuffix() + ".findBy" + StringFormatterUtil.upperCaseTheFirstLetter(pojo.getReferenceName()) + "(" + pojo.getReferenceName() + ");\n" + "}\n";
        }
        return "";
    }

    @Override
    public String generateAttributes(Pojo pojo) {
        String className = pojo.getPojoName();
        String resultat
                = ServiceImplConfig.getAutowired()
                + "\n private " + className + ServiceImplConfig.getDaoSuffix() + " " + className.toLowerCase() + ServiceImplConfig.getDaoSuffix() + ";\n"
                + ServiceImplConfig.getAutowired()
                + "\n private EntityManager entityManager; \n";
        for (PojoItem pojoItem : pojo.getFieldsListGenric()) {
            String typeName = pojoItem.getPojoItemType();
            resultat += ServiceImplConfig.getAutowired() + "\n"
                    + " private " + typeName + ServiceImplConfig.getServiceSuffix() + " " + typeName.toLowerCase() + "Service; \n";

        }
        for (Field field : pojo.getFieldsGeneric()) {
            String typeName = field.getType().getSimpleName();
            resultat += ServiceImplConfig.getAutowired() + "\n"
                    + " private " + typeName + ServiceImplConfig.getServiceSuffix() + " " + typeName.toLowerCase() + "Service; \n";

        }
        return resultat;
    }

    @Override
    public String generateClassHeader(Pojo pojo) {
        String className = pojo.getPojoName();
        return ServiceImplConfig.getServiceAnnotation()
                + "\n public class " + className + ServiceImplConfig.getServiceImplSuffix() + " implements " + className + ServiceImplConfig.getServiceSuffix() + " " + " {\n" + "\n";
    }

    @Override
    public String generateMethodConstructQuery(Pojo pojo) {
        String className = pojo.getPojoName();
        String beanAbrev = className.substring(0, 1).toLowerCase();
        String constructQueryBody = "String query = \"SELECT " + beanAbrev + " FROM " + className + " " + beanAbrev + " where 1=1 \";\n";
        constructQueryBody += generateConstraintSimple(beanAbrev, pojo.getFieldsSimpleStringOrBoolean());
        constructQueryBody += generateConstraintNumberOrDate(beanAbrev, pojo.getFieldsSimpleNumberOrDate());
        return "private String constructQuery(" + generateParams(pojo) + "){\n"
                + "" + constructQueryBody + "\n"
                + "  return query; "
                + "\n}"
                + "\n";

    }

    @Override
    public String generateFindByCriteria(Pojo pojo) {

        String className = pojo.getPojoName();

        return " @Override \n "
                + "public List< " + className + ">  " + ServiceImplConfig.getFindByCriteriaMethodeName() + "(" + generateParams(pojo) + ")"
                + "{\n"
                + " return entityManager.createQuery(constructQuery(" + generateValues(pojo) + ")).getResultList(); \n "
                + "}\n";
    }

    @Override
    public String generateImport(Pojo pojo) {
        String className = pojo.getPojoName();
        String resultat = "\n" + "package " + ProjetBusinessConfig.getProjectPackageBase() + "." + ServiceImplConfig.getPackageName() + " ;\n"
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + "." + ServiceImplConfig.getServicePackage() + "." + className + ServiceImplConfig.getServiceSuffix() + " ; \n"
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + "." + ServiceImplConfig.getDaoPackage() + "." + className + ServiceImplConfig.getDaoSuffix() + " ;\n"
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + "." + UtilEngineConfig.getPackageName() + ".SearchUtil" + ";\n"
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + ".bean." + className + ";\n"
                + "import org.springframework.beans.factory.annotation.Autowired; \n"
                + "import java.util.ArrayList; \n"
                + "import javax.persistence.EntityManager;\n"
                + "import javax.persistence.PersistenceContext;\n"
                + "import java.util.Date; \n"
                + "import org.springframework.stereotype.Service; \n"
                + "import java.util.List; \n";
        for (Field declaredField : pojo.getFieldsGeneric()) {
            resultat += "import " + ProjetBusinessConfig.getProjectPackageBase() + ".bean." + declaredField.getType().getSimpleName() + "; \n";
            resultat += "import " + ProjetBusinessConfig.getProjectPackageBase() + "." + ServiceImplConfig.getServicePackage() + "." + declaredField.getType().getSimpleName() + ServiceImplConfig.getServiceSuffix() + " " + "; \n";

        }
        for (PojoItem pojoItem : pojo.getFieldsListGenric()) {
            String typeName = pojoItem.getPojoItemType();
            resultat += "import " + ProjetBusinessConfig.getProjectPackageBase() + "." + ServiceImplConfig.getServicePackage() + "." + typeName + ServiceImplConfig.getServiceSuffix() + " " + "; \n";
            resultat += "import " + ProjetBusinessConfig.getProjectPackageBase() + ".bean." + typeName + " " + "; \n";
        }
        return resultat;
    }

    @Override
    public String constructClassName(Pojo pojo) {
        return StringFormatterUtil.upperCaseTheFirstLetter(pojo.getPojoName());
    }

    @Override
    public String constructFileName(Pojo pojo) {
        return pojo.getPojoName() + ServiceImplConfig.getServiceImplSuffix() + ".java";
    }

    private String listNameType(Field field) {
        String chaine = field.getGenericType().getTypeName();
        int lastUpperCase = 0;
        for (int i = 0; i < chaine.toCharArray().length; i++) {
            char c = chaine.toCharArray()[i];
            if (Character.isUpperCase(c)) {
                lastUpperCase = i;
            }
        }
        if (field.getType().getSimpleName().equals("List")) {
            return chaine.substring(lastUpperCase, chaine.toCharArray().length - 1);
        }
        return chaine.substring(lastUpperCase, chaine.toCharArray().length);
    }

    private String simpleName(String chaine) {
        int lastUpperCase = 0;
        for (int i = 0; i < chaine.toCharArray().length; i++) {
            char c = chaine.toCharArray()[i];
            if (Character.isUpperCase(c)) {
                lastUpperCase = i;
            }
        }
        return chaine.substring(lastUpperCase, chaine.toCharArray().length);
    }

    private String generateValues(Pojo pojo) {
        String values = "";
        values += generateValuesStringOrBoolean(pojo.getFieldsSimpleStringOrBoolean());
        values += generateValuesSimpleNumberOrDate(pojo.getFieldsSimpleNumberOrDate());
        values = values.substring(0, values.length() - 1);
        return values;
    }

    private String generateValuesStringOrBoolean(List<Field> fields) {
        String values = "";
         if (ListUtil.isNull(fields)) {
            return values;
        } else {
             for (Field declaredField : fields) {
            values += declaredField.getName() + ",";
        }
        return values; 
         }
       
    }

    private String generateValuesSimpleNumberOrDate(List<Field> fields) {
        String values = "";
         if (ListUtil.isNull(fields)) {
            return values;
        } else {
             for (Field declaredField : fields) {
            values += declaredField.getName() + "Min,";
            values += declaredField.getName() + "Max,";
        }
        return values;
         }
        
    }

    private String generateParams(Pojo pojo) {
        String params = generateParamsSimple(pojo.getFieldsSimpleStringOrBoolean());
        params += generateParamsNumberOrDate(pojo.getFieldsSimpleNumberOrDate());
        params = params.substring(0, params.length() - 1);
        return params;
    }

    private String generateParamsSimple(List<Field> fields) {
        String params = "";
        if (ListUtil.isNull(fields)) {
            return params;
        } else {
            for (Field declaredField : fields) {
            String declaredFieldType = declaredField.getType().getSimpleName();
            params += declaredFieldType + " " + declaredField.getName() + ",";
        }
        return params;
        }
        
    }

    private String generateParamsNumberOrDate(List<Field> fieldsNumberOrDate) {
        String params = "";
        for (Field declaredField : fieldsNumberOrDate) {
            String declaredFieldType = declaredField.getType().getSimpleName();
            params += declaredFieldType + " " + declaredField.getName() + "Min,";
            params += declaredFieldType + " " + declaredField.getName() + "Max,";
        }
        return params;
    }

    //    public String addConstraint
    private String generateConstraintSimple(String beanAbrev, List<Field> fields) {
        String Constraint = "";
        if (ListUtil.isNull(fields)) {
            return Constraint;
        } else {
            for (Field declaredField : fields) {
            Constraint += "query += SearchUtil.addConstraint( \"" + beanAbrev + "\", \""
                    + declaredField.getName() + "\"," + "\"=\"" + ","
                    + declaredField.getName() + "" + ");\n";
        }
        return Constraint;
        }
        
    }

    private String generateConstraintNumberOrDate(String beanAbrev, List<Field> fieldsNumberOrDate) {
        String Constraint = "";
        for (Field declaredField : fieldsNumberOrDate) {
            String declaredFieldType = declaredField.getType().getSimpleName();
            if (declaredFieldType.equals("Date")) {
                Constraint += "query += SearchUtil.addConstraintMinMaxDate(\"" + beanAbrev + "\",\" "
                        + declaredField.getName() + "\", "
                        + declaredField.getName() + "Min, "
                        + declaredField.getName() + "Max);\n";

            } else {
                Constraint += "query += SearchUtil.addConstraintMinMax(\"" + beanAbrev + "\", "
                        + "\"" + declaredField.getName() + "\"" + ", "
                        + declaredField.getName() + "Min, "
                        + declaredField.getName() + "Max);\n";
            }
        }
        return Constraint;
    }

    private String constructSimpleMethode(String className, String methodeName, String attributType, String attributName) {
        return ServiceImplConfig.getOverride()
                + "public " + className + " " + methodeName + "(" + attributType + "  " + attributName + "){\n"
                + "         return " + className.toLowerCase() + ServiceImplConfig.getDaoSuffix() + ServiceImplConfig.getDeleteMethodeName() + "By" + StringFormatterUtil.upperCaseTheFirstLetter(attributName) + "(" + attributName + ");\n"
                + "}\n\n";
    }

    private String generateGetter(Field field) {
        return "get" + StringFormatterUtil.upperCaseTheFirstLetter(field.getName()) + "()";
    }

    private String generateSetter(Field field, String value) {
        return "set" + StringFormatterUtil.upperCaseTheFirstLetter(field.getName()) + "(" + value + ")";
    }

    @Override
    public String generateClone(Pojo pojo) {
        String resulta = "";
        resulta += generateCloneWithTwoParms(pojo);
        resulta += generateCloneWithReturn(pojo);
        resulta += generateListClone(pojo);
        return resulta;
    }

    @Override
    public String generateListClone(Pojo pojo) {
        String objectType = pojo.getPojoName();
        String objectName = pojo.getPojoName().toLowerCase();
        String objectList = pojo.getPojoName().toLowerCase() + "s";
        String objectListClone = objectList + "Clone";
        String resulta = "public List<" + objectType + "> clone(List<" + objectType + ">" + objectList + "){\n";
        resulta += "if(" + objectList + "== null){\n";
        resulta += "       return null ;\n";
        resulta += "}else{"
                + "List<" + objectType + "> " + objectListClone + " = new ArrayList();\n";
        resulta += "\t \t \t " + objectList + ".forEach((" + objectName + ")->{"
                + objectListClone + ".add(clone(" + objectName + "));\n"
                + "});";
        resulta += "return " + objectListClone + ";\n";
        resulta += "}\n";
        resulta += "}\n";
        return resulta;
    }

    public String generateCloneWithTwoParms(Pojo pojo) {
        String objectType = pojo.getPojoName();
        String objectName = pojo.getPojoName().toLowerCase();
        String objectClone = objectName + "Clone";
        String resulta = "public void clone(" + objectType + " " + objectName + "," + objectType + " " + objectClone + "){\n";
        resulta += "if(" + objectName + "!= null && " + objectClone + " != null){\n";
        resulta += generateCloneSimple(objectName, objectClone, pojo.getFieldsSimple());
        resulta += generateCloneGeneric(objectName, objectClone, pojo.getFieldsGeneric());
        resulta += generateCloneListGenric(objectName, objectClone, pojo.getFieldsListGenric());
        resulta += "}\n";
        resulta += "}\n";
        return resulta;
    }

    public String generateCloneWithReturn(Pojo pojo) {
        String objectType = pojo.getPojoName();
        String objectName = pojo.getPojoName().toLowerCase();
        String objectClone = objectName + "Clone";
        String resulta = "public " + objectType + " clone(" + objectType + " " + objectName + "){\n";
        resulta += "if(" + objectName + "== null){";
        resulta += "       return null ;\n";
        resulta += "}else{";
        resulta += objectType + " " + objectClone + "= new " + objectType + "();\n";
        resulta += generateCloneSimple(objectName, objectClone, pojo.getFieldsSimple());
        resulta += generateCloneGeneric(objectName, objectClone, pojo.getFieldsGeneric());
        resulta += generateCloneListGenric(objectName, objectClone, pojo.getFieldsListGenric());

        resulta += "return " + objectClone + ";\n";
        resulta += "}\n";
        resulta += "}\n";
        return resulta;
    }

    public String generateCloneGeneric(String objectName, String objectClone, List<Field> fieldsGeneric) {
        String resulta = "";
        for (Field field : fieldsGeneric) {
            String value = field.getType().getSimpleName().toLowerCase() + "" + ServiceImplConfig.getServiceSuffix() + "." + "clone(" + objectName + "." + generateGetter(field) + ")";
            resulta += objectClone + "." + generateSetter(field, value) + ";\n";
        }
        return resulta;
    }

    public String generateCloneSimple(String objectName, String objectClone, List<Field> fieldsSimple) {
        String resulta = "";
        for (Field field : fieldsSimple) {
            resulta += objectClone + "." + generateSetter(field, objectName + "." + generateGetter(field)) + ";\n";
        }
        return resulta;
    }

    private String generateCloneListGenric(String objectName, String objectClone, List<PojoItem> fieldsListGenric) {
        String resulta = "";
        for (PojoItem pojoItem : fieldsListGenric) {
            String value = pojoItem.getPojoItemType().toLowerCase() + "" + ServiceImplConfig.getServiceSuffix() + "." + "clone(" + objectName + "." + generateGetter(pojoItem.getField()) + ")";

            resulta += objectClone + "." + generateSetter(pojoItem.getField(), value) + ";\n";
        }
        return resulta;
    }
}
