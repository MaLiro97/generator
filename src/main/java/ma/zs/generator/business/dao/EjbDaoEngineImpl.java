/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.dao;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.zs.generator.business.pojo.Pojo;
import ma.zs.generator.business.pojo.PojoUtilEngin;
import ma.zs.generator.business.projet.ProjetBusinessConfig;
import ma.zs.generator.business.service.impl.ServiceImplConfig;
import ma.zs.generator.util.FileUtil;
import ma.zs.generator.util.ListUtil;
import ma.zs.generator.util.ProjetUtil;
import ma.zs.generator.util.StringFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mac
 */
@Service
public class EjbDaoEngineImpl implements DaoEngine {

    @Autowired
    private DaoEngineConfig daoEngineConfig;
    @Autowired
    private PojoUtilEngin pojoUtil;

    @Override
    public String generateFile(Pojo pojo) {

        String resultat = generateImport(pojo);
        resultat += generateClassHeader(pojo);
        resultat += generateAttributes();
        resultat += generateSave(pojo);
        resultat += generateFindAll(pojo);
        resultat += generateFindById(pojo);
        resultat += generateFindByReference(pojo);
        resultat += generateRemove(pojo);
        resultat += generateRemoveById(pojo);
        resultat += generateRemoveByReference(pojo);
        resultat += generateEdit(pojo);
//        resultat += generateClone(pojo);
        resultat += generateFindByCriteria(pojo);
        resultat += generateMethodConstructQuery(pojo);
        resultat += "}\n";

        return (resultat);
    }

    @Override
    public String generateImport(Pojo pojo) {

        String resultat = "package " + ProjetBusinessConfig.getProjectPackageBase() + "." + daoEngineConfig.getPackageName() + ";\n"
                + "import javax.ejb.Stateless;\n"
                + "import javax.persistence.EntityManager;\n"
                + "import javax.persistence.PersistenceContext;\n";
        for (Field field : pojo.getFieldsGeneric()) {
            String param = field.getType().getSimpleName();
            resultat += "import " + ProjetUtil.getPackageBean() + "." + StringFormatterUtil.upperCaseTheFirstLetter(param) + ";\n";

        }

        resultat += "import " + ProjetUtil.getPackageBean() + "." + (pojo.getPojoName()) + ";\n";
        
        return resultat;
    }

    @Override
    public String generateNextIdMethode(Pojo pojo) {
        String res = "@Stateless\n"
                + " public class " + pojo.getPojoName() + daoEngineConfig.getSuffix() + "{\n";
        try {
            //generer les method pour le reference
            res += generateMethodsForReference(pojo);
        } catch (Exception ex) {
            Logger.getLogger(DaoEngineImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //generer des method poud des object
            res += generateMethodsForObjects(pojo);
        } catch (Exception ex) {
            Logger.getLogger(DaoEngineImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        res += "\n";
        return res;

    }

    public String generateEdit(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        String res = "public " + className + "  " + ServiceImplConfig.getEditMethodeName() + " (" + className + " " + classNameLowerCase + ")" + "{\n"
                + "\n" + "if(" + classNameLowerCase + "== null){ \n return null; \n}else {\n "
                + "em.merge(" + classNameLowerCase + "); \n "
                + "return " + classNameLowerCase + ";\n"
                + "}\n"
                + "}\n";

        return res;
    }

    public String generateClassHeader(Pojo pojo) {
        String res = "@Stateless\n"
                + " public class " + pojo.getPojoName() + daoEngineConfig.getSuffix() + "{\n";

        return res;
    }

    public String generateAttributes() {
        String res = daoEngineConfig.getPersistenceContext() + "\n"
                + "private EntityManager em ; \n";
        return res;
    }

    public String generateSave(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        String res = "public " + className + "  " + ServiceImplConfig.getSaveMethodeName() + " (" + className + " " + classNameLowerCase + ")" + "{\n"
                + "\n" + "if(" + classNameLowerCase + "== null){ \n return null; \n}else {\n "
                + "em.persist(" + classNameLowerCase + "); \n"
                + "return " + classNameLowerCase + ";\n"
                + "}\n"
                + "}\n";

        return res;
    }

    public String generateFindAll(Pojo pojo) {

        String className = constructClassName(pojo);
        String beanAbrev = className.substring(0, 1).toLowerCase();
        return "public List< " + className + ">  " + ServiceImplConfig.getFindByAllMethodePrefix() + "(" + ")" + "{\n" 
                
                + " return em.createQuery(\"SELECT "+beanAbrev+" FROM "+ className +" "+beanAbrev+"\").getResultList();" 
                + "}\n";

    }

    public String constructClassName(Pojo pojo) {
        return StringFormatterUtil.upperCaseTheFirstLetter(pojo.getPojoName());
    }

    public String generateFindById(Pojo pojo) {

        String className = pojo.getPojoName();

        String resultat = "public " + className + " findById(" + pojo.getIdType() + " " + pojo.getIdName() + "){\n"
                + " return " + "em.find(" + pojo.getIdName() + ");\n"
                + "}\n";
        return resultat;

    }

    public String generateRemove(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        String resultat = "";
        resultat += ServiceImplConfig.getOverride()
                + "public int " + ServiceImplConfig.getDeleteMethodeName() + "(" + className + " " + classNameLowerCase + ")" + "{\n"
                + "if(" + classNameLowerCase + "== null){ \n"
                + "  return -1; \n"
                + "}else {\n "
                + "em.remove("
                + classNameLowerCase + ");\n" + "return 1 ;\n" + "}\n" + "}\n";
        return resultat;
    }

    public String generateRemoveById(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        return ServiceImplConfig.getOverride()
                + "public void " + ServiceImplConfig.getDeleteByPrefix() + "Id(" + pojo.getIdType() + " " + pojo.getIdName() + "){\n"
                + className + " " + classNameLowerCase + "= findById( " + pojo.getIdName() + ");\n"
                + "em.remove(" + classNameLowerCase + ");\n"
                + "}\n";
    }

    public String generateFindByCriteria(Pojo pojo) {

        String className = pojo.getPojoName();

        return " @Override \n "
                + "public List< " + className + ">  " + ServiceImplConfig.getFindByCriteriaMethodeName() + "(" + generateParams(pojo) + ")"
                + "{\n"
                + " return em.createQuery(constructQuery(" + generateValues(pojo) + ")).getResultList(); \n "
                + "}\n";
    }

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

    public String generateFindByReference(Pojo pojo) {
        String className = pojo.getPojoName();
        String beanAbrev = className.substring(0, 1).toLowerCase();
        String constructQueryBody = "String query = \"SELECT " + beanAbrev + " FROM " + className + " " + beanAbrev + " where 1=1 \";\n";
        constructQueryBody += generateConstraintSimple(beanAbrev, pojo.getFieldsSimpleStringOrBoolean());
        if (pojo.getReferenceName() != null) {
            return "public " + className + "  " + ServiceImplConfig.getFindByPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(pojo.getReferenceName()) + "(" + pojo.getReferenceType() + "  " + pojo.getReferenceName() + "){\n"
                    + constructQueryBody + "\n"
                    + "return (" + className + ")em.createQuery(query).getSingleResult();\n"
                    + "}\n";
        }
        return "";
    }

    public String generateRemoveByReference(Pojo pojo) {
        String className = pojo.getPojoName();
        String classNameLowerCase = className.toLowerCase();
        if (pojo.getReferenceName() != null) {
            return ServiceImplConfig.getOverride()
                    + "public void  " + ServiceImplConfig.getDeleteByPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(pojo.getReferenceName()) + "(" + pojo.getReferenceType() + "  " + pojo.getReferenceName() + "){\n"
                    + className + " " + classNameLowerCase + ServiceImplConfig.getFindByPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(pojo.getReferenceName()) + "( " + pojo.getReferenceName() + ");\n"
                    + "em.remove(" + classNameLowerCase + ");\n"
                    + "}\n";
        }
        return "";
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

    @Override
    public void generete() {
        try {
            FileUtil.createDirectory(getPath());
        } catch (IOException ex) {
            Logger.getLogger(DaoEngineImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Pojo> pojos = pojoUtil.getPojos();
        for (Pojo pojo : pojos) {
            try {
                FileUtil.generateFile(constructFileName(pojo), generateFile(pojo), getDir());
            } catch (IOException ex) {
                Logger.getLogger(EjbDaoEngineImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String constructFileName(Pojo pojo) {
        return pojo.getPojoName() + daoEngineConfig.getSuffix() + ".java";

    }

    @Override
    public String generateMethodsForReference(Pojo pojo) {
        String res = "";
        if (pojo.getReferenceName() != null) {
            String ref = StringFormatterUtil.upperCaseTheFirstLetter(pojo.getReferenceName());
            res += "\t public " + pojo.getPojoName() + " findBy" + ref + "(" + pojo.getReferenceType() + " " + pojo.getReferenceName() + ");\n";
            res += "\t public int deleteBy" + ref + "(" + pojo.getReferenceType() + " " + pojo.getReferenceName() + ");\n";
        }

        res += "\n";
        return res;

    }

    @Override
    public String generateMethodsForObjects(Pojo pojo) {
        String upperclasse = StringFormatterUtil.upperCaseTheFirstLetter(pojo.getPojoName());
        String res = "";
        for (Field field : pojo.getFieldsGeneric()) {
            String paramName = field.getName();
            String paramType = field.getType().getSimpleName();
            res += "\t public " + upperclasse + " findBy" + StringFormatterUtil.upperCaseTheFirstLetter(paramName) + "(" + paramType + ' ' + (paramName) + ");\n";
            res += "\t public int deleteBy" + StringFormatterUtil.upperCaseTheFirstLetter(paramName) + "(" + paramType + ' ' + (paramName) + ");\n";
        }
        res += "\n";
        return res;

    }

    @Override
    public String getPath() {
        return ProjetBusinessConfig.getProjectPathWithPackagePath() + daoEngineConfig.getPackagePath();

    }

    public String getDir() {
        return (ProjetBusinessConfig.getProjectPath() + ProjetBusinessConfig.getProjectPackageBasePath() + daoEngineConfig.getPackagePath());
    }

}
