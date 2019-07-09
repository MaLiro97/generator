/*
 * and open the template in the editor.
 */
package ma.zs.generator.business.rest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import ma.zs.generator.business.bean.BeanEngineConfig;
import ma.zs.generator.business.converter.ConverterConfig;
import ma.zs.generator.business.pojo.Pojo;
import ma.zs.generator.business.pojo.PojoUtilEnginImpl;
import ma.zs.generator.business.projet.ProjetBusinessConfig;
import ma.zs.generator.business.service.ServiceConfig;
import ma.zs.generator.business.utilEngine.UtilEngineConfig;
import ma.zs.generator.util.FileUtil;
import ma.zs.generator.util.GetterAndSetterUtil;
import ma.zs.generator.util.StringFormatterUtil;
import ma.zs.generator.business.vo.VoConfig;
import ma.zs.generator.util.ListUtil;
import org.springframework.stereotype.Component;

/**
 *
 * @author Anas
 */
@Component
public class RestEngineImpl implements RestEngine {

    @Override
    public String getPath() {
        return ProjetBusinessConfig.getProjectPathWithPackagePath() + RestConfig.getPackagePath();
    }

    @Override
    public void generateRest() throws IOException {
        generateRestDir();
        generateRestContent();
    }

    @Override
    public void generateRestDir() throws IOException {
        FileUtil.createDirectory(getPath());
    }

    @Override
    public void generateRestContent() throws IOException {

        List<Pojo> myList = new PojoUtilEnginImpl().getPojos();
        for (Pojo p : myList) {
            String rest = constructRestHeader(p);
            rest += constructRestBody(p);
            rest += constructRestFooter(p);
            FileUtil.generateFile(p.getClazz(), rest, getPath(), "Rest.java");
        }
    }

    @Override
    public String constructRestHeader(Pojo p) {
        return generateImport(p)
                + "@RestController\n"
                + "@RequestMapping(\"/"
                + RestConfig.getProjectName() + "/"
                + p.getPojoName() + "\")\n"
                + RestConfig.getCrossOrigin() + "\n"
                + "public class " + p.getPojoName() + "Rest {\n";
    }

    @Override
    public String constructRestBody(Pojo p) {
        String restBody = "";
        String service = p.getPojoName() + RestConfig.getServiceSuffix();
        String converter = p.getPojoName() + RestConfig.getConverterSuffix();

        restBody += "\n @Autowired \n private " + service
                + " " + StringFormatterUtil.lowerCaseTheFirstLetter(service)
                + ";\n"
                + "\n @Autowired \n"
                + "private " + converter + " " + StringFormatterUtil.lowerCaseTheFirstLetter(converter)
                + " ;\n"
                + "\n";

        restBody += generateCreateMethode(p);
        restBody += generateDeleteByIdMethode(p);
        restBody += generateRemoveByReference(p);
       // restBody += generateFindByCriteriaMethode(p);
        restBody += generateFindAllMethode(p);

        return restBody;
    }

    @Override
    public String generateCreateMethode(Pojo p) {
        String service = StringFormatterUtil.lowerCaseTheFirstLetter(p.getPojoName() + RestConfig.getServiceSuffix());
        String converter = StringFormatterUtil.lowerCaseTheFirstLetter(p.getPojoName() + RestConfig.getConverterSuffix());
        String voName = p.getPojoName() + RestConfig.getVoSuffix();

        return "@PostMapping(\"/\")\n"
                + "public " + p.getPojoName() + RestConfig.getVoSuffix() + " "
                + RestConfig.getSaveMethodeName()
                + "(@RequestBody " + voName + " "
                + StringFormatterUtil.lowerCaseTheFirstLetter(voName) + ")"
                + "{\n"
                + p.getPojoName() + " " + StringFormatterUtil.lowerCaseTheFirstLetter(p.getPojoName())
                + "= " + converter + ".to" + RestConfig.getItemName() + "("
                + StringFormatterUtil.lowerCaseTheFirstLetter(voName) + ");\n"
                + "return "
                + converter + ".to" + RestConfig.getVoSuffix() + "("
                + service + "."
                + RestConfig.getSaveMethodeName()
                + "(" + StringFormatterUtil.lowerCaseTheFirstLetter(p.getPojoName()) + "));"
                + "\n}\n";
    }

    @Override
    public String generateDeleteByIdMethode(Pojo p) {
        String service = StringFormatterUtil.lowerCaseTheFirstLetter(p.getPojoName() + RestConfig.getServiceSuffix());
        return "@DeleteMapping(\"/{" + p.getIdName() + "}\")\n"
                + "public void "
                + RestConfig.getDeleteByPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(p.getIdName())
                + "(@PathVariable " + p.getIdType() + " " + p.getIdName() + ")"
                + "{\n" + service + "."
                + RestConfig.getDeleteByPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(p.getIdName())
                + "(" + p.getIdName() + ");"
                + "\n}\n";
    }

    @Override
    public String generateRemoveByReference(Pojo p) {
        String service = StringFormatterUtil.lowerCaseTheFirstLetter(p.getPojoName() + RestConfig.getServiceSuffix());
        if (p.getReferenceName() != null) {
            return "@DeleteMapping(\"/{" + p.getReferenceName() + "}\")\n"
                    + "public void  "
                    + RestConfig.getDeleteByPrefix()
                    + StringFormatterUtil.upperCaseTheFirstLetter(p.getReferenceName())
                    + "(@PathVariable " + p.getReferenceType() + "  " + p.getReferenceName() + ")"
                    + "{\n" + service + "."
                    + RestConfig.getDeleteByPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(p.getReferenceName())
                    + "(" + p.getReferenceName() + ");"
                    + "\n}\n";
        }
        return "";
    }

    @Override
    public String generateFindByCriteriaMethode(Pojo p) {
        String service = StringFormatterUtil.lowerCaseTheFirstLetter(p.getPojoName() + RestConfig.getServiceSuffix());
        String converter = StringFormatterUtil.lowerCaseTheFirstLetter(p.getPojoName() + RestConfig.getConverterSuffix());
        String searchObjetName = StringFormatterUtil.lowerCaseTheFirstLetter(p.getPojoName()) + RestConfig.getSearchVoSuffix();

        return "@GetMapping(\"/criteria\")\n"
                + "public " + "List<" + p.getPojoName() + RestConfig.getVoSuffix() + "> "
                + RestConfig.getFindByCriteriaMethodeName()
                + "(@RequestBody " + p.getPojoName() + RestConfig.getVoSuffix() + " "
                + searchObjetName + ")"
                + "{\n"
                + "return " + converter
                + ".to" + RestConfig.getVoSuffix()
                + "(" + service + "."
                + RestConfig.getFindByCriteriaMethodeName()
                + "(" + generateCriteriaParams(p) + "));"
                + "\n}\n";
    }

    public String generateCriteriaParams(Pojo p) {
        String params = "";
        if (ListUtil.isNull(p.getFieldsSimpleStringOrBoolean()) && ListUtil.isNull(p.getFieldsSimpleNumberOrDate())) {
            return params;
        } else {

            params += generateParamsSimple(p);

            params += generateParamsNumberOrDate(p);

            if (!params.equals("")) {
                params = params.substring(0, params.length() - 1);
            }

            return params;
        }
    }

    private String generateParamsSimple(Pojo p) {
        String params = "";
        String searchObjetName = StringFormatterUtil.lowerCaseTheFirstLetter(p.getPojoName()) + RestConfig.getSearchVoSuffix();

        if (ListUtil.isNull(p.getFieldsSimpleStringOrBoolean())) {
            return params;
        } else {

            for (Field declaredField : p.getFieldsSimpleStringOrBoolean()) {

                String getter = searchObjetName + ".get"
                        + StringFormatterUtil.upperCaseTheFirstLetter(declaredField.getName()) + "()";
                params += getter + ",";

            }
            return params;
        }

    }

    private String generateParamsNumberOrDate(Pojo p) {
        String searchObjetName = StringFormatterUtil.lowerCaseTheFirstLetter(p.getPojoName()) + RestConfig.getSearchVoSuffix();

        String params = "";
        if (ListUtil.isNull(p.getFieldsSimpleNumberOrDate())) {
            return params;
        } else {
            for (Field declaredField : p.getFieldsSimpleNumberOrDate()) {
                if (!declaredField.getName().equals(p.getIdName())) {

                    String getterMin = searchObjetName + ".get"
                            + StringFormatterUtil.upperCaseTheFirstLetter(declaredField.getName()) + "Min()";
                    String getterMax = searchObjetName + ".get"
                            + StringFormatterUtil.upperCaseTheFirstLetter(declaredField.getName()) + "Max()";
                    if (declaredField.getType().getSimpleName().equals("Date")) {
                        params += "DateUtil.to" + declaredField.getType().getSimpleName() + "(" + getterMin + "),";
                        params += "DateUtil.to" + declaredField.getType().getSimpleName() + "(" + getterMax + "),";
                    } else {
                        params += "NumberUtil.to" + declaredField.getType().getSimpleName() + "(" + getterMin + "),";
                        params += "NumberUtil.to" + declaredField.getType().getSimpleName() + "(" + getterMax + "),";
                    }

                }
            }
            return params;
        }

    }

    @Override
    public String generateFindAllMethode(Pojo p) {
        String service = p.getPojoName() + RestConfig.getServiceSuffix();
        String converter = p.getPojoName() + RestConfig.getConverterSuffix();
        return "@GetMapping(\"/\")\n"
                + "public List<" + p.getPojoName() + RestConfig.getVoSuffix() + "> "
                + RestConfig.getFindAllName() + "()" + "{\n"
                + "return "
                + StringFormatterUtil.lowerCaseTheFirstLetter(converter) + ".to" + RestConfig.getVoSuffix() + "("
                + StringFormatterUtil.lowerCaseTheFirstLetter(service) + "."
                + RestConfig.getFindAllName() + "());"
                + "\n}\n";
    }

    @Override
    public String generateImport(Pojo p) {
        return "package " + ProjetBusinessConfig.getProjectPackageBase() + "." + RestConfig.getPackageName() + " ;\n\n" + "\n" + "import java.util.ArrayList;\n"
                + "import org.springframework.beans.factory.annotation.Autowired;\n"
                + "import org.springframework.web.bind.annotation.CrossOrigin;\n"
                + "import org.springframework.web.bind.annotation.DeleteMapping;\n"
                + "import org.springframework.web.bind.annotation.GetMapping;\n"
                + "import org.springframework.web.bind.annotation.PathVariable;\n"
                + "import org.springframework.web.bind.annotation.PostMapping;\n"
                + "import org.springframework.web.bind.annotation.RequestBody;\n"
                + "import org.springframework.web.bind.annotation.RequestMapping;\n"
                + "import org.springframework.web.bind.annotation.RestController;\n"
                + "import java.util.List;\n"
                //service Import
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + "."
                + ServiceConfig.getPackageName() + "."
                + p.getPojoName() + RestConfig.getServiceSuffix() + ";\n"
                //Bean import
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + "." + BeanEngineConfig.getPackageName()
                + "." + p.getPojoName() + ";\n"
                // Vo import
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + "."
                + VoConfig.getPackageName() + "." + p.getPojoName()
                + RestConfig.getVoSuffix() + ";\n"
                //Converter Import
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + "."
                + ConverterConfig.getPackageName() + "." + p.getPojoName() + RestConfig.getConverterSuffix() + ";\n"
                //Util Ipml
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + "."
                + UtilEngineConfig.getPackageName() + ".* ;\n";

    }

    @Override
    public boolean isSupportMinMax(String typeName) {
        List<String> types = Arrays.asList("int", "Integer", "float", "Float", "double", "Double", "long", "Long", "Date");
        return types.contains(typeName);
    }

    @Override
    public String constructRestFooter(Pojo p) {
        String rest = "";
        rest += GetterAndSetterUtil.generateClassGetter(p.getPojoName() + RestConfig.getConverterSuffix());
        rest += GetterAndSetterUtil.generateClassSetter(p.getPojoName() + RestConfig.getConverterSuffix());
        rest += GetterAndSetterUtil.generateClassGetter(p.getPojoName() + RestConfig.getServiceSuffix());
        rest += GetterAndSetterUtil.generateClassSetter(p.getPojoName() + RestConfig.getServiceSuffix());
        rest += "\n}";
        return rest;
    }

}
