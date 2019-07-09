/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.utilEngine;

import java.io.IOException;
import ma.zs.generator.business.projet.ProjetBusinessConfig;
import ma.zs.generator.util.FileUtil;
import org.springframework.stereotype.Component;

/**
 *
 * @author A O
 */
@Component
public class UtilEngineImpl implements UtilEngine {

    @Override
    public void generate() throws IOException, Exception {
        generateDir();
        FileUtil.create(getDir() + "\\" + "SearchUtil.java", generatePackage() + generateSearchUtil());
        FileUtil.create(getDir() + "\\" + "NumberUtil.java", generatePackage() + generateNumberUtil());
        FileUtil.create(getDir() + "\\" + "DateUtil.java", generatePackage() + generateDateUtil());
        FileUtil.create(getDir() + "\\" + "ListUtil.java", generatePackage() + generateListUtil());
        FileUtil.create(getDir() + "\\" + "StringUtil.java", generatePackage() + generateStringUtil());

    }

    public static void generateDir() throws IOException {
        FileUtil.createDirectory(ProjetBusinessConfig.getProjectPath() + ProjetBusinessConfig.getProjectPackageBasePath() + UtilEngineConfig.getPackagePath());
    }

    public static String getDir() throws IOException {
        return (ProjetBusinessConfig.getProjectPath() + ProjetBusinessConfig.getProjectPackageBasePath() + UtilEngineConfig.getPackagePath());
    }

    @Override
    public String generatePackage() {
        return "package " + ProjetBusinessConfig.getProjectPackageBase() + "." + UtilEngineConfig.getPackageName() + ";\n \n \n \n";
    }

    @Override
    public String generateSearchUtil() {
        return "import java.text.ParseException;\n"
                + "import java.text.SimpleDateFormat;\n"
                + "import java.util.Date;\n"
                + "\n"
                + "public class SearchUtil {\n"
                + "\n"
                + "    public static Date convert(String date) {\n"
                + "        try {\n"
                + "            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(\"yyyy-MM-dd\");\n"
                + "            return simpleDateFormat.parse(date);\n"
                + "        } catch (ParseException ex) {\n"
                + "            return null;\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public static java.sql.Date converte(java.util.Date date) {\n"
                + "        if (date != null) {\n"
                + "            return new java.sql.Date(date.getTime());\n"
                + "        } else {\n"
                + "            return null;\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public static String addConstraint(String beanAbrev, String atributeName, String operator, Object value) {\n"
                + "        boolean condition = value != null;\n"
                + "        if (value != null && value.getClass().getSimpleName().equals(\"String\")) {\n"
                + "            condition = condition && !value.equals(\"\");\n"
                + "        }\n"
                + "        if (condition && operator.equals(\"LIKE\")) {\n"
                + "            return \" AND \" + beanAbrev + \".\" + atributeName + \" \" + operator + \" '%\" + value + \"%'\";\n"
                + "        } else if (condition) {\n"
                + "            return \" AND \" + beanAbrev + \".\" + atributeName + \" \" + operator + \" '\" + value + \"'\";\n"
                + "        }\n"
                + "\n"
                + "        return \"\";\n"
                + "    }\n"
                + "\n"
                + "    public static String addConstraintOr(String beanAbrev, String atributeName, String operator, Object value) {\n"
                + "        boolean condition = value != null;\n"
                + "        if (value != null && value.getClass().getSimpleName().equals(\"String\")) {\n"
                + "            condition = condition && !value.equals(\"\");\n"
                + "        }\n"
                + "        if (condition) {\n"
                + "            return \" OR \" + beanAbrev + \".\" + atributeName + \" \" + operator + \" '\" + value + \"'\";\n"
                + "        }\n"
                + "        return \"\";\n"
                + "    }\n"
                + "\n"
                + "    public static String addConstraintMinMax(String beanAbrev, String atributeName, Object valueMin, Object valueMax) {\n"
                + "        String requette = \"\";\n"
                + "        if (valueMin != null) {\n"
                + "            requette += \" AND \" + beanAbrev + \".\" + atributeName + \" >= '\" + valueMin + \"'\";\n"
                + "        }\n"
                + "        if (valueMax != null) {\n"
                + "            requette += \" AND \" + beanAbrev + \".\" + atributeName + \" <= '\" + valueMax + \"'\";\n"
                + "        }\n"
                + "        return requette;\n"
                + "    }\n"
                + "\n"
                + "    public static String addConstraintDate(String beanAbrev, String atributeName, String operator, Date value) {\n"
                + "        return addConstraint(beanAbrev, atributeName, operator, converte(value));\n"
                + "    }\n"
                + "\n"
                + "    public static String addConstraintMinMaxDate(String beanAbrev, String atributeName, Date valueMin, Date valueMax) {\n"
                + "        return addConstraintMinMax(beanAbrev, atributeName, converte(valueMin), converte(valueMax));\n"
                + "    }\n"
                + "}\n";

    }

    @Override
    public String generateNumberUtil() {
        return "public class NumberUtil {\n"
                + "\n"
                + "    private static final String CHAINE_VIDE = \"\";\n"
                + "\n"
                + "    public static double toDecimal(String value) {\n"
                + "        if (value == null || value.isEmpty()) {\n"
                + "            return 0D;\n"
                + "        } else {\n"
                + "            return Double.parseDouble(value);\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public static int toInt(String value) {\n"
                + "        if (value == null || value.isEmpty()) {\n"
                + "            return 0;\n"
                + "        } else {\n"
                + "            return Integer.parseInt(value);\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public static String toString(Double value) {\n"
                + "        if (value == null) {\n"
                + "            return CHAINE_VIDE;\n"
                + "        } else {\n"
                + "            return String.valueOf(value);\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public static String toString(Integer value) {\n"
                + "        if (value == null) {\n"
                + "            return CHAINE_VIDE;\n"
                + "        } else {\n"
                + "            return String.valueOf(value);\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public static String toString(Long value) {\n"
                + "        if (value == null) {\n"
                + "            return CHAINE_VIDE;\n"
                + "        } else {\n"
                + "            return String.valueOf(value);\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public static Long toLong(String value) {\n"
                + "        if (value == null || value.isEmpty()) {\n"
                + "            return 0L;\n"
                + "        } else {\n"
                + "            return Long.parseLong(value);\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "}\n"
                + "";
    }

    @Override
    public String generateDateUtil() {
        return "import java.sql.Timestamp;\n"
                + "import java.text.ParseException;\n"
                + "import java.text.SimpleDateFormat;\n"
                + "import java.util.Date;"
                + "public class DateUtil {\n"
                + "\n"
                + "    public static String formateDate(Date date) {\n"
                + "        return formateDate(\"yyyy-MM-dd hh:mm:ss.SSS\", date);\n"
                + "    }\n"
                + "\n"
                + "    public static String formateDate(String pattern, Date date) {\n"
                + "        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);\n"
                + "        if (date != null) {\n"
                + "            return simpleDateFormat.format(date);\n"
                + "        } else {\n"
                + "            return \"\";\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public static Date parse(String date) {\n"
                + "        if (date == null || date.isEmpty()) {\n"
                + "            return null;\n"
                + "        } else {\n"
                + "            try {\n"
                + "                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(\"yyyy-MM-dd\");\n"
                + "                return simpleDateFormat.parse(date);\n"
                + "            } catch (Exception ex) {\n"
                + "                return null;\n"
                + "            }\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public static Date parseTimestamp(String date) {\n"
                + "\n"
                + "        try {\n"
                + "            SimpleDateFormat dateFormat = new SimpleDateFormat(\"yyyy-MM-dd hh:mm:ss.SSS\");\n"
                + "            Date parsedDate = dateFormat.parse(date);\n"
                + "            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());\n"
                + "            return timestamp;\n"
                + "        } catch (Exception e) {\n"
                + "            return null;\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public static java.sql.Date convertFormUtilToSql(java.util.Date date) {\n"
                + "        if (date != null) {\n"
                + "            return new java.sql.Date(date.getTime());\n"
                + "        } else {\n"
                + "            return null;\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public static java.sql.Timestamp convertFormUtilToTimestamp(java.util.Date date) {\n"
                + "        if (date != null) {\n"
                + "            return new java.sql.Timestamp(date.getTime());\n"
                + "        } else {\n"
                + "            return null;\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "}\n"
                + "";
    }

    private String generateListUtil() {
        return "import java.util.List;\n"
                + "\n"
                + "/**\n"
                + " *\n"
                + " * @author A O\n"
                + " */\n"
                + "public class ListUtil {\n"
                + "\n"
                + "    public static boolean isEmpty(List objects) {\n"
                + "        return objects == null || objects.isEmpty();\n"
                + "    }\n"
                + "\n"
                + "    public static boolean isNotEmpty(List objects) {\n"
                + "        return !isEmpty(objects);\n"
                + "    }\n"
                + "}\n"
                + "";
    }

    private String generateStringUtil() {
        return "/**\n"
                + " *\n"
                + " * @author ELMassafi\n"
                + " */\n"
                + "public class StringUtil {\n"
                + "\n"
                + "    public static boolean isEmpty(String string) {\n"
                + "        return string == null || string.isEmpty();\n"
                + "    }\n"
                + "\n"
                + "    public static boolean isNotEmpty(String string) {\n"
                + "        return !isEmpty(string);\n"
                + "    }\n"
                + "}"
                + "";
    }

}
