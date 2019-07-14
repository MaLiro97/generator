package ma.zs.generator.business.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import ma.zs.generator.business.pojo.Pojo;
import ma.zs.generator.business.pojo.PojoItem;
import ma.zs.generator.business.pojo.PojoUtilEngin;

import org.springframework.stereotype.Service;

import ma.zs.generator.business.projet.ProjetBusinessConfig;
import ma.zs.generator.util.FileUtil;
import ma.zs.generator.util.ListUtil;
import ma.zs.generator.util.StringFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EjbServiceEngineImpl implements ServiceEngine {

    @Autowired
    private PojoUtilEngin pojoUtil;

    @Override
    public void generate() throws IOException {

        generateDir();
        System.out.println("findPojos");
        List<Pojo> pojos = pojoUtil.getPojos();
        for (Pojo pojo : pojos) {
            FileUtil.generateFile(constructFileName(pojo), generateFile(pojo), getDir());
        }
    }

    public static void generateDir() throws IOException {
        FileUtil.createDirectory(ProjetBusinessConfig.getProjectPath() + ProjetBusinessConfig.getProjectPackageBasePath() + ServiceConfig.getPackagePath());
    }

    public static String getDir() throws IOException {
        return (ProjetBusinessConfig.getProjectPath() + ProjetBusinessConfig.getProjectPackageBasePath() + ServiceConfig.getPackagePath());
    }

    @Override
    public String generateFile(Pojo pojo) {
        String resultat = generateImport(pojo);
        resultat += generateClassHeader(pojo);
        resultat += generateSave(pojo);
        resultat += generateSaveWithItems(pojo);

        resultat += generateFindAll(pojo);
        resultat += generateFindById(pojo);
        resultat += generateFindByReference(pojo);

        resultat += generateRemove(pojo);
        resultat += generateRemoveById(pojo);
        resultat += generateRemoveByReference(pojo);

//        resultat += generateClone(pojo);
//        resultat += generateCloneList(pojo);

        resultat += generateFindByCriteria(pojo);
        resultat += "\n}\n";
        return (resultat);
    }

    @Override
    public String generateImport(Pojo pojo) {
        String className = pojo.getPojoName();
        String resultat = "package " + ProjetBusinessConfig.getProjectPackageBase() + "." + ServiceConfig.getPackageName() + ";\n" + "\n" + "import java.util.ArrayList;\n"
                + "import java.util.List;\n"
                + "import java.util.Date; \n"
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + ".bean." + className + ";\n";
        for (Field declaredField : pojo.getFieldsGeneric()) {
            resultat += "import " + ProjetBusinessConfig.getProjectPackageBase() + ".bean." + declaredField.getType().getSimpleName() + "; \n";
        }
        return resultat;
    }

    @Override
    public String generateClassHeader(Pojo pojo) {
        String className = pojo.getPojoName();
        return "public interface " + className + "Service {\n" + "\n";
    }

    @Override
    public String generateSave(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        return "public " + className + " " + ServiceConfig.getSaveMethodeName() + "(" + className + " "
                + classNameLowerCase + "); \n";

    }

    @Override
    public String generateSaveWithItems(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        String resultat = "";
        for (PojoItem pojoItem : pojo.getFieldsList()) {
            if (!pojoItem.isPrimitif()) {
                String typeName = pojoItem.getPojoItemType();
                String AttribuName = pojoItem.getField().getName();
                resultat += "public " + className + "  " + ServiceConfig.getSaveWithItemsMethodePrefix() + StringFormatterUtil.upperCaseTheFirstLetter(AttribuName) + " (" + className + " " + classNameLowerCase + ");";
            }
        }
        return resultat;
    }

    @Override
    public String generateRemove(Pojo pojo) {

        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();

        return "public int " + ServiceConfig.getDeleteMethodeName() + "(" + className + " " + classNameLowerCase
                + ");\n";

    }

    @Override
    public String generateRemoveById(Pojo pojo) {
        return "public void  " + ServiceConfig.getDeleteByPrefix() + "Id(" + pojo.getIdType() + " " + pojo.getIdName() + ");"
                + "\n";
    }

    @Override
    public String generateRemoveByReference(Pojo pojo) {
        if (pojo.getReferenceName() != null) {
            return "public void  " + ServiceConfig.getDeleteByPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(pojo.getReferenceName()) + "(" + pojo.getReferenceType() + "  " + pojo.getReferenceName() + ");\n";
        }
        return "";
    }

    @Override
    public String generateFindAll(Pojo pojo) {
        String className = pojo.getPojoName();
        return "public List<" + className + "> " + ServiceConfig.getFindAllMethodeName() + "(" + ");\n";
    }

    @Override
    public String generateFindById(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        String resultat = "public " + className + " findById(" + pojo.getIdType() + " " + pojo.getIdName() + ");\n";
        return resultat;
    }

    @Override
    public String generateFindByReference(Pojo pojo) {

        if (pojo.getReferenceName() != null) {
            return "public " + pojo.getPojoName() + "  " + "findBy" + StringFormatterUtil.upperCaseTheFirstLetter(pojo.getReferenceName()) + "(" + pojo.getReferenceType() + "  " + pojo.getReferenceName()
                    + ");\n";
        }
        return "";
    }

    @Override
    public String generateFindByCriteria(Pojo pojo) {

        String className = pojo.getPojoName();

        return " public List<" + className + ">  " + ServiceConfig.getFindByCriteriaMethodeName() + "(" + generateParams(pojo) + ");\n";

    }

    @Override
    public String generateCloneList(Pojo pojo) {
        String objectType = pojo.getPojoName();
        String objectList = pojo.getPojoName().toLowerCase() + "s";
        return "public List<" + objectType + "> clone(List<" + objectType + ">" + objectList + ");";
    }

    @Override
    public String constructClassName(Pojo pojo) {
        return StringFormatterUtil.upperCaseTheFirstLetter(pojo.getPojoName());
    }

    @Override
    public String constructFileName(Pojo pojo) {
        return pojo.getPojoName() + "Service.java";
    }

    @Override
    public String generateClone(Pojo pojo) {
        String objectType = pojo.getPojoName();
        String objectName = pojo.getPojoName().toLowerCase();
        String objectClone = objectName + "Clone";
        String resulta = "public void clone(" + objectType + " " + objectName + "," + objectType + " " + objectClone + ");\n";
        resulta += "public " + objectType + " clone(" + objectType + " " + objectName + ");\n";
        return resulta;
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
        }else{
              for (Field declaredField : fields) {
            values += declaredField.getName() + "Min,";
            values += declaredField.getName() + "Max,";
        }
        return values;
         }
       
    }

    private String generateValuesSimpleNumberOrDate(List<Field> fields) {
        String values = "";
        if (ListUtil.isNull(fields)) {
            return values;
        }else{
             for (Field declaredField : fields) {
            values += declaredField.getName() + "Min,";
            values += declaredField.getName() + "Max,";
        }
        return values;
        }
       
    }

    private String generateParams(Pojo pojo) {
        String params = "";
        if (ListUtil.isNull(pojo.getFieldsSimpleStringOrBoolean()) && ListUtil.isNull(pojo.getFieldsSimpleNumberOrDate())) {
           return params;
        }else{
         
            params += generateParamsSimple(pojo.getFieldsSimpleStringOrBoolean());

        
            params += generateParamsNumberOrDate(pojo.getFieldsSimpleNumberOrDate());

        if(!params.equals("")){
             params = params.substring(0, params.length() - 1);
        }
       
         return params;
        }
      
       
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
        if (ListUtil.isNull(fieldsNumberOrDate)) {
            return params;
        } else {
            for (Field declaredField : fieldsNumberOrDate) {
                String declaredFieldType = declaredField.getType().getSimpleName();
                params += declaredFieldType + " " + declaredField.getName() + "Min,";
                params += declaredFieldType + " " + declaredField.getName() + "Max,";
            }
             return params;
        }

       
    }

}
