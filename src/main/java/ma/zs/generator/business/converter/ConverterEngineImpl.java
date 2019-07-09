/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.converter;

import java.lang.reflect.Field;
import java.util.List;
import ma.zs.generator.business.pojo.Pojo;
import ma.zs.generator.business.pojo.PojoItem;
import ma.zs.generator.business.pojo.PojoUtilEnginImpl;
import ma.zs.generator.business.projet.ProjetBusinessConfig;
import ma.zs.generator.business.utilEngine.UtilEngineConfig;
import ma.zs.generator.business.vo.VoConfig;
import ma.zs.generator.util.FileUtil;
import ma.zs.generator.util.StringFormatterUtil;
import org.springframework.stereotype.Component;

/**
 *
 * @author BOSS
 */
@Component
public class ConverterEngineImpl implements ConverterEngine {

//    @Autowired
//    private PojoUtilEngin pojoUtil;
    @Override
    public void generateConverter() throws Exception {
        generateConverterDir();
        List<Pojo> pojos = new PojoUtilEnginImpl().getPojos();
        for (Pojo pojo : pojos) {
            FileUtil.generateFile(constructFileName(pojo), generateconverterContent(pojo), getPath());

        }
    }

    @Override
    public void generateConverterDir() throws Exception {
        FileUtil.createDirectory(getPath());
    }

    @Override
    public String generateconverterContent(Pojo pojo) throws Exception {
        FileUtil.create("" + ProjetBusinessConfig.getProjectPath() + ProjetBusinessConfig.getProjectPackageBasePath() + ConverterConfig.getPackagePath() + "AbstractConverter.java", ConverterConfig.getConverterClassCode());
        String converter = generateImport(pojo);
        converter += constructConverterHeader(pojo);
        converter += constructConverterBody(pojo);
        converter += generateinitMethod(pojo);
        converter += "\n } \n";

        return converter;

    }

    @Override
    public String generateImport(Pojo pojo) throws Exception {
        return "package " + ProjetBusinessConfig.getProjectPackageBase() + "." + ConverterConfig.getPackageName() + ";\n \n"
                + "import org.springframework.beans.factory.annotation.Autowired;\n"
                + "import org.springframework.stereotype.Component;\n"
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + "." + UtilEngineConfig.getPackageName() + ".*;\n"
             
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + ".bean." + pojo.getPojoName() + "; \n"
                + "import " + ProjetBusinessConfig.getProjectPackageBase() + "." + VoConfig.getPackageName() + "." + pojo.getPojoName() + "Vo; \n"
                + "\n ";

    }

    @Override
    public String constructConverterHeader(Pojo pojo) throws Exception {
        return "@Component \n"
                + "public class " + pojo.getPojoName() + "Converter extends AbstractConverter<" + pojo.getPojoName() + "," + pojo.getPojoName() + "Vo>{ \n";
    }

    @Override
    public String constructConverterBody(Pojo pojo) throws Exception {
        String converterBody = "\n";
        List<Field> attgeneric = pojo.getFieldsGeneric();
        for (int i = 0; i < attgeneric.size(); i++) {
            String paramgeneric = attgeneric.get(i).getName();
            converterBody += "private boolean " + paramgeneric + "; \n"
                    + "\n @Autowired"
                    + "\n private " + StringFormatterUtil.upperCaseTheFirstLetter(attgeneric.get(i).getType()) + "Converter " + StringFormatterUtil.lowerCaseTheFirstLetter(attgeneric.get(i).getType()) + "Converter ; \n";
        }
        List<PojoItem> listgeneric = pojo.getFieldsListGenric();
        for (int i = 0; i < listgeneric.size(); i++) {
            PojoItem attribut = listgeneric.get(i);
            converterBody += "private boolean " + attribut.getField().getName() + "; \n"
                    + "\n @Autowired"
                    + "\n private " + StringFormatterUtil.upperCaseTheFirstLetter(attribut.getPojo().getPojoName()) + "Converter " + StringFormatterUtil.lowerCaseTheFirstLetter(attribut.getPojo().getPojoName()) + "Converter ; \n";
        }
        converterBody += "\n @Override \n " + generatetoItemMethode(pojo) + "\n";
        converterBody += "\n  @Override \n " + generatetoVoMethode(pojo) + "\n";
        return (converterBody);

    }

    @Override
    public String generatetoItemMethode(Pojo pojo) throws Exception {
        String item = "";
        List<Field> attgeneric = pojo.getFieldsGeneric();
        for (int i = 0; i < attgeneric.size(); i++) {
            String paramgeneric = attgeneric.get(i).getName();
            String attributName = StringFormatterUtil.upperCaseTheFirstLetter(paramgeneric);
            String lowattributclass = StringFormatterUtil.lowerCaseTheFirstLetter(attgeneric.get(i).getType());
            item += "\n if(" + paramgeneric + "&& vo.get" + attributName + "Vo() != null) {"
                    + "\n item.set" + attributName + "(" + lowattributclass + "Converter.toItem(vo.get" + attributName + "Vo()));\n"
                    + "} \n ";
        }
        List<Field> attStringorboolean = pojo.getFieldsSimpleStringOrBoolean();
        if(attStringorboolean!=null && !attStringorboolean.isEmpty())
        for (int i = 0; i < attStringorboolean.size(); i++) {
            String paramsimple = attStringorboolean.get(i).getName();
            String attributName = StringFormatterUtil.upperCaseTheFirstLetter(paramsimple);
            if (attStringorboolean.get(i).getType().getSimpleName().equals("String")) {
                item += "\n if (StringUtil.isNotEmpty(vo.get" + attributName + "())) {"
                        + "\n item.set" + attributName + "(vo.get" + attributName + "());\n"
                        + "} \n";
            } else {
                item += "\n if (vo.get" + attributName + "() != null) {"
                        + "\n item.set" + attributName + "(vo.get" + attributName + "());\n"
                        + "} \n";
            }

        }
        List<Field> attnumberordate = pojo.getFieldsSimpleNumberOrDate();
        for (int i = 0; i < attnumberordate.size(); i++) {
            String paramsimple2 = attnumberordate.get(i).getName();
            String paramName = StringFormatterUtil.upperCaseTheFirstLetter(paramsimple2);
            String util = "";
            switch (attnumberordate.get(i).getType().getSimpleName().toLowerCase()) {
                case "date":
                    util += "DateUtil.parse";
                    break;
                case "bigdecimal":
                    util += "NumberUtil.toBigDecimal";
                    break;
                case "double":
                    util += "NumberUtil.toDouble";
                    break;
                case "long":
                    util += "NumberUtil.toLong";
                    break;
                case "int":
                    util += "NumberUtil.toInt";
                    break;
                default:
                    break;
            }
            item += "\n if (vo.get" + paramName + "() != null) {"
                    + "\n item.set" + paramName + "(" + util + "(vo.get" + paramName + "()));\n"
                    + "} \n";
        }

        List<PojoItem> listgeneric = pojo.getFieldsListGenric();
        for (int i = 0; i < listgeneric.size(); i++) {
            PojoItem attribut = listgeneric.get(i);
            String attributname = attribut.getField().getName();
            String uppattributname = StringFormatterUtil.upperCaseTheFirstLetter(attribut.getField().getName());
            String lowattributclass = StringFormatterUtil.lowerCaseTheFirstLetter(attribut.getPojo().getPojoName());

            item += "\n if (ListUtil.isNotEmpty(vo.get" + StringFormatterUtil.upperCaseTheFirstLetter(attributname.substring(0, attributname.length() - 1)) + "Vos ()) && " + attributname + ") {"
                    + "\n item.set" + uppattributname + "(" + lowattributclass + "Converter.toItem(vo.get" + StringFormatterUtil.upperCaseTheFirstLetter(attributname.substring(0, attributname.length() - 1)) + "Vos())); \n"
                    + "} \n";

        }

        return "public " + pojo.getPojoName()
                + " toItem(" + pojo.getPojoName() + "Vo vo) {\n"
                + " if (vo == null) {\n"
                + "    return null;\n"
                + "      } else {\n"
                + pojo.getPojoName() + " item = new " + pojo.getPojoName() + "();\n"
                + item + "\n"
                + "return item;"
                + "\n }"
                + "\n }";
    }

    @Override
    public String generatetoVoMethode(Pojo pojo) throws Exception {
        String vo = "";
//        List<Field>params = pojo.getFields();
        List<Field> attgeneric = pojo.getFieldsGeneric();
        for (int i = 0; i < attgeneric.size(); i++) {
            String paramgeneric = attgeneric.get(i).getName();
            String attributName = StringFormatterUtil.upperCaseTheFirstLetter(paramgeneric);
            String lowattributclass = StringFormatterUtil.lowerCaseTheFirstLetter(attgeneric.get(i).getType());
            vo += "\n if(" + paramgeneric + "&& item.get" + attributName + "() != null) {"
                    + "\n vo.set" + attributName + "Vo(" + lowattributclass + "Converter.toVo(item.get" + attributName + "()));\n"
                    + "} \n ";
        }
        List<Field> attStringorboolean = pojo.getFieldsSimpleStringOrBoolean();
        if(attStringorboolean!=null)
        for (int i = 0; i < attStringorboolean.size(); i++) {
            String paramsimple = attStringorboolean.get(i).getName();
            String attributName = StringFormatterUtil.upperCaseTheFirstLetter(paramsimple);
            if (attStringorboolean.get(i).getType().getSimpleName().equals("String")) {
                vo += "\n if (StringUtil.isNotEmpty(item.get" + attributName + "())) {"
                        + "\n vo.set" + attributName + "(item.get" + attributName + "());\n"
                        + "} \n";
            } else {
                vo += "\n if (item.get" + attributName + "() != null) {"
                        + "\n vo.set" + attributName + "(item.get" + attributName + "());\n"
                        + "} \n";
            }
        }

        List<Field> attnumberordate = pojo.getFieldsSimpleNumberOrDate();
        for (int i = 0; i < attnumberordate.size(); i++) {
            String paramsimple2 = attnumberordate.get(i).getName();
            String paramName = StringFormatterUtil.upperCaseTheFirstLetter(paramsimple2);
            String util = "";
            switch (attnumberordate.get(i).getType().getSimpleName().toLowerCase()) {
                case "date":
                    util += "DateUtil.formateDate";
                    break;
                case "bigdecimal":
                    util += "NumberUtil.toString";
                    break;
                case "double":
                    util += "NumberUtil.toString";
                    break;
                case "long":
                    util += "NumberUtil.toString";
                    break;
                case "int":
                    util += "NumberUtil.toString";
                    break;
                case "integer":
                    util += "NumberUtil.toString";
                    break;
                default:
                    break;
            }
            vo += "\n if (item.get" + paramName + "() != null) {"
                    + "\n vo.set" + paramName + "(" + util + "(item.get" + paramName + "()));\n"
                    + "} \n";
        }

        List<PojoItem> listgeneric = pojo.getFieldsListGenric();
        for (int i = 0; i < listgeneric.size(); i++) {
            PojoItem attribut = listgeneric.get(i);
            String attributname = attribut.getField().getName();
            String uppattributname = StringFormatterUtil.upperCaseTheFirstLetter(attribut.getField().getName());
            String lowattributclass = StringFormatterUtil.lowerCaseTheFirstLetter(attribut.getPojo().getPojoName());
            vo += "\n if(ListUtil.isNotEmpty(item.get" + StringFormatterUtil.upperCaseTheFirstLetter(attributname.substring(0, attributname.length() - 1)) + "()) && " + attributname + ") {"
                    + "\n vo.set" + StringFormatterUtil.upperCaseTheFirstLetter(attributname.substring(0, attributname.length() - 1)) + "Vos(" + StringFormatterUtil.lowerCaseTheFirstLetter(attribut.getPojo().getPojoName()) + "Converter.toVo" + "(item.get" + StringFormatterUtil.upperCaseTheFirstLetter(attribut.getField().getName()) + "()));\n"
                    + "} \n";
        }

        return "public " + pojo.getPojoName() + "Vo toVo(" + pojo.getPojoName() + " item) {\n"
                + " if (item == null) {\n"
                + "    return null;\n"
                + "      } else {\n"
                + pojo.getPojoName() + "Vo vo = new " + pojo.getPojoName() + "Vo();\n"
                + vo + "\n"
                + "return vo;"
                + "\n }"
                + "\n }";
    }

    @Override
    public String generateinitMethod(Pojo pojo) throws Exception {
        String init = "public void init() { \n";
        List<Field> attgeneric = pojo.getFieldsGeneric();
        for (int i = 0; i < attgeneric.size(); i++) {
            String paramgeneric = attgeneric.get(i).getName();
            init += "\n" + paramgeneric + " = true; \n";
        }
        List<PojoItem> listgeneric = pojo.getFieldsListGenric();
        for (int i = 0; i < listgeneric.size(); i++) {
            PojoItem attribut = listgeneric.get(i);
            String attributname = attribut.getField().getName();
            init += "\n" + attributname + " = true; \n";
        }
        init += "}";
        return init;
    }

    @Override
    public String getPath() {
        return ProjetBusinessConfig.getProjectPathWithPackagePath() + ConverterConfig.getPackagePath();
    }

    @Override
    public String constructFileName(Pojo pojo) {
        return pojo.getPojoName() + "Converter.java";
    }

}
