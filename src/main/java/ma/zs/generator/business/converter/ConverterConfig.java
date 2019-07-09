/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.converter;

import ma.zs.generator.business.projet.ProjetBusinessConfig;

/**
 *
 * @author Anas
 */
public class ConverterConfig {

    private static final String packageName = "ws.rest.converter";
    private static final String packagePath = "ws\\rest\\converter\\";
    private static final String suffix = "Converter";
    private static final String itemName = "item";
    private static final String VoName = "vo";

    private static final String converterClassCode = "package " + ProjetBusinessConfig.getProjectPackageBase() + "." + ConverterConfig.getPackageName() + ";\n \n"
            + "import java.util.ArrayList;\n"
            + "import java.util.List;\n"
            + "\n"
            + "/**\n"
            + " *\n"
            + " */\n"
            + "public abstract class AbstractConverter<T, VO> {\n"
            + "\n"
            + "    public abstract T toItem(VO vo);\n"
            + "\n"
            + "    public abstract VO toVo(T item);\n"
            + "\n"
            + "    public List<T> toItem(List<VO> vos) {\n"
            + "        if (vos == null || vos.isEmpty()) {\n"
            + "            return null;\n"
            + "        } else {\n"
            + "            List<T> items = new ArrayList();\n"
            + "            for (VO vo : vos) {\n"
            + "                items.add(toItem(vo));\n"
            + "            }\n"
            + "            return items;\n"
            + "        }\n"
            + "    }\n"
            + "\n"
            + "    public List<VO> toVo(List<T> items) {\n"
            + "        if (items == null || items.isEmpty()) {\n"
            + "            return null;\n"
            + "        } else {\n"
            + "            List<VO> vos = new ArrayList();\n"
            + "            for (T item : items) {\n"
            + "                vos.add(toVo(item));\n"
            + "            }\n"
            + "            return vos;\n"
            + "        }\n"
            + "    }\n"
            + "}";

    public static String getPackageName() {
        return packageName;
    }

    public static String getPackagePath() {
        return packagePath;
    }

    public static String getConverterClassCode() {
        return converterClassCode;
    }

    public static String getSuffix() {
        return suffix;
    }

    public static String getItemName() {
        return itemName;
    }

    public static String getVoName() {
        return VoName;
    }

}
