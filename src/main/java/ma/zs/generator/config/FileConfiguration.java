/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.config;

import ma.zs.generator.config.enumeration.LANGUAGE;
import ma.zs.generator.database.DataBaseUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.zs.generator.reflexivity.ReflexivityGetterAndSetter;
import ma.zs.generator.util.StringFormatterUtil;


/**
 *
 * @author YOUNES
 */
public class FileConfiguration {

    private static String configFile;
    private static String configFileName = "config.php";
    private static String utilServiceConfig;
    private static String utilServiceConfigName = "utilService.php";

    private static String utilControlerConfig;
    private static String utilControlerConfigName = "utilControler.php";
    private static String sessionUtil;
    private static String sessionUtilName = "sessionUtil.php";

    private static String handlePrefix = "handle";
    private static String initPrefix = "init";
    private static String frontControlerName = "frontControler.php";
    private static String frontControler;

    static {
        try {
            initConfigFile();
            initSessionUtil();
            initUtilServiceConfigFile();
            initUtilControlerConfigFile();
            initUtilFrontControlerFile();
        } catch (Exception ex) {
            Logger.getLogger(FileConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void initUtilServiceConfigFile() {
        utilServiceConfig = "<?php\n"
                + "\n"
                + "function " + RequestConfiguration.getAddAttributeMinMax() + "($attribute_name, $min_value, $max_value) {\n"
                + "    $query = \"\";\n"
                + "    if ($min_value != NULL) {\n"
                + "        $query .= \" AND $attribute_name >= '$min_value'\";\n"
                + "    }\n"
                + "    if ($max_value != NULL) {\n"
                + "        $query .= \" AND $attribute_name <= '$max_value'\";\n"
                + "    }\n"
                + "    return $query;\n"
                + "}\n"
                + "\n"
                + "function " + RequestConfiguration.getAddAttribute() + "($attribute_name, $attribute_value,$opeartor) {\n"
                + "    $query = \"\";\n"
                + "    if ($attribute_value != NULL && $attribute_value != \"\") {\n"
                + "      if($opeartor=='AND'){"
                + "        $query .= \" AND $attribute_name = '$attribute_value'\";\n"
                + "         }else{\n"
                + "        $query .= \" AND  $attribute_name LIKE '%$attribute_value%'\";\n"
                + "        }\n"
                + "    }\n"
                + "    return $query;\n"
                + "}\n"
                + "\n"
                + "function addAttributeForUpdate($attribute_name, $attribute_value) {\n"
                + "    $query = \"\";\n"
                + "    if ($attribute_value != NULL && $attribute_value != \"\") {\n"
                + "        $query .= \" , $attribute_name = '$attribute_value'\";\n"
                + "    }\n"
                + "    return $query;\n"
                + "}\n"
                + "\n"
                + "function " + RequestConfiguration.getWrapParamName() + "($param, $tab) {\n"
                + "    if (isset($tab[$param]) && $tab[$param] != NULL && $tab[$param] != \"\") {\n"
                + "        return $tab[$param];\n"
                + "    }\n"
                + "    return NULL;\n"
                + "}\n"
                + "\n"
                + "function addOrConstraint($data, $attributRequestName, $attributeName) {\n"
                + "    $query = \"\";\n"
                + "    $myData = wrapParam($attributRequestName, $data);\n"
                + "    $i = 0;\n"
                + "    if ($myData != NULL) {\n"
                + "        $query = \" AND (\";\n"
                + "        $extractDatas = explode(',', $myData);\n"
                + "        foreach ($extractDatas as $extractData) {\n"
                + "            if ($i == 0) {\n"
                + "                $query .= \"$attributeName = '$extractData'\";\n"
                + "            } else {\n"
                + "                $query .= \" OR $attributeName = '$extractData'\";\n"
                + "            }\n"
                + "            $i++;\n"
                + "        }\n"
                + "        $query .= \")\";\n"
                + "    }\n"
                + "    return $query;\n"
                + "}\n"
                + "\n"
                + "function printQuery($query) {\n"
                + "    echo \"<br></br>$query<br></br>\";\n"
                + "}\n"
                + "\n"
                + "function printDataAndQuery($data, $query) {\n"
                + "    printQuery($query);\n"
                + "    echo json_encode(($data), JSON_UNESCAPED_UNICODE);\n"
                + "    echo \"<br></br><br></br>\";\n"
                + "    return 1;\n"
                + "}\n"
                + "";
    }

    private static void initConfigFile() {
        configFile = "<?php\n"
                + "\n"
                + "\n"
                + "function getDbHost($local) {\n"
                + "    return $local == 1 ? \"" + DataBaseConfiguration.getDbLocalServer() + "\" : \"" + DataBaseConfiguration.getDbRemoteServer() + "\";\n"
                + "}\n"
                + "\n"
                + "function getDbName($local) {\n"
                + "    return $local == 1 ? \"" + DataBaseConfiguration.getDbLocalName() + "\" : \"" + DataBaseConfiguration.getDbRemoteName() + "\";\n"
                + "}\n"
                + "\n"
                + "function getDbUser($local) {\n"
                + "    return $local == 1 ? \"" + DataBaseConfiguration.getDbLocalUser() + "\" : \"" + DataBaseConfiguration.getDbRemoteUser() + "\";\n"
                + "}\n"
                + "\n"
                + "function getDbPass($local) {\n"
                + "    return $local == 1 ? \"" + DataBaseConfiguration.getDbLocalPass() + "\" : \"" + DataBaseConfiguration.getDbRemotePass() + "\";\n"
                + "}\n"
                + "\n"
                + "function connect() {\n"
                + "\n"
                + "    $local = " + DataBaseConfiguration.getLocal() + ";\n"
                + "    $dbhost = getDbHost($local);\n"
                + "    $dbname = getDbName($local);\n"
                + "    $dbuser = getDbUser($local);\n"
                + "    $dbpswd = getDbPass($local);\n"
                + "\n"
                + "\n"
                + "    try {\n"
                + "        $db = new PDO('mysql:dbname=' . $dbname . ';host=' . $dbhost, $dbuser, $dbpswd, array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));\n"
                + "        $db->exec(\"SET CHARACTER SET utf8\");\n"
                + "        $db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_OBJ);\n"
                + "        return $db;\n"
                + "    } catch (PDOexception $e) {\n"
                + "        die(\"2 Une erreur est survenue lors de la connexion a la base de donnees \".$e);\n"
                + "    }\n"
                + "}\n"
                + "\n"
                + "function loadMultiple($query) {\n"
                + "    $db = connect();\n"
                + "    $columns = $db->query($query);\n"
                + "    $datas = [];\n"
                + "    $i = 0;\n"
                + "    while ($item = $columns->fetch()) {\n"
                + "        $datas[] = $item;\n"
                + "        $i++;\n"
                + "    }\n"
                + "    if ($i == 0) {\n"
                + "        return NULL;\n"
                + "    }\n"
                + "    return $datas;\n"
                + "}\n"
                + "\n"
                + "function loadOne($query) {\n"
                + "    $db = connect();\n"
                + "    $req = $db->query($query);\n"
                + "    while ($fe = $req->fetch()) {\n"
                + "        return $fe;\n"
                + "    }\n"
                + "    return NULL;\n"
                + "}\n"
                + "\n"
                + "function execRequest($query) {\n"
                + "    $db = connect();\n"
                + "    return $db->query($query);\n"
                + "}\n"
                + "\n"
                + "function generateMax($beanName,$atributeName){\n"
                + "$myMax = loadOne(\"SELECT MAX($atributeName) AS myMax FROM $beanName\")->myMax ;\n"
                + "if($myMax == NULL){\n"
                + "return 1;\n"
                + "}\n"
                + "return $myMax+1;\n"
                + "}\n"
                + "";
    }

    private static void initUtilControlerConfigFile() {
        utilControlerConfig = "<?php\n"
                + "function encode($data) {\n"
                + "    return json_encode(($data), JSON_UNESCAPED_UNICODE);\n"
                + "}\n"
                + "\n"
                + "function printEncode($data) {\n"
                + "    echo encode($data);\n"
                + "}\n"
                + "\n"
                + "function decode($local) {\n"
                + "    if ($local == 1)\n"
                + "        return $_GET;\n"
                + "    else if ($local == 2)\n"
                + "        return $_POST;\n"
                + "    else\n"
                + "        return json_decode(file_get_contents('php://input'), true);\n"
                + "}\n"
                + "function forward($pageToForward) {\n"
                + "    header(\"location:$pageToForward\");\n"
                + "    exit();\n"
                + "}\n"
                + "\n"
                + "?>\n";
    }

    private static void initSessionUtil() {
        sessionUtil = "<?php\n"
                + "if (session_id() == '') {\n"
                + "    session_start();\n"
                + "}?>";
    }

    private static void initUtilFrontControlerFile() throws Exception {
        List<Class> myClasses = DataBaseUtil.getPojos();
        frontControler = "<?php\n";
        //frontControler += Include.generateIncludeSessionForFrontControler(LANGUAGE.JAVA,myClasses);
        frontControler += contrucHandelRequest(myClasses);
        frontControler += contructInitMethode(myClasses);
        frontControler += contructHandleMethode(myClasses);
        frontControler += "?>\n";

    }

    private static String contrucHandelRequest(List<Class> myClasses) {
        String resultat = "function " + FileConfiguration.getHandlePrefix() + "(){\n";
        String pageNameVariable = "$pageName";
        resultat += pageNameVariable + "=$_GET['page'];\n";
        String branch = "";
        for (int i = 0; i < myClasses.size(); i++) {
            Class myClass = myClasses.get(i);
            String pageName = StringFormatterUtil.lowerCaseTheFirstLetter(myClass);
            if (i == 0) {
                branch = "if";
            } else {
                branch = "else if";
            }
            resultat += branch + "(" + pageNameVariable + " ==" + " '" + pageName + "'){\n";
            resultat += "forward(\"" + pageName + ".php\");\n";
            resultat += "}\n";
        }
        resultat += "}\n";
        return resultat;
    }

    private static String contructInitMethode(List<Class> myClasses) throws Exception {
        String resultat = "";
        for (int i = 0; i < myClasses.size(); i++) {
            Class myClass = myClasses.get(i);
            resultat += contructHandleMethode(myClass);
        }
        return resultat;
    }

    private static String contructInitMethode(Class myClass) {
        String scopedAttribute = "$_SESSION['" + StringFormatterUtil.lowerCaseTheFirstLetter(myClass) + "']";
        String resultat = "function " + FileConfiguration.getInitPrefix()
                + StringFormatterUtil.upperCaseTheFirstLetter(myClass) + "(){\n"
                + "if (!isset(" + scopedAttribute + ") || " + scopedAttribute + " == NULL) {\n"
                + scopedAttribute + " = " + RequestConfiguration.getFindAllPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(myClass) + "();\n"
                + "}\n"
                + "}\n";
        return resultat;
    }

    private static String contructHandleMethode(List<Class> myClasses) throws Exception {
        String resultat = "";
        for (int i = 0; i < myClasses.size(); i++) {
            Class myClass = myClasses.get(i);
            resultat += contructInitMethode(myClass);
        }
        return resultat;
    }

    private static String contructHandleMethode(Class myClass) throws Exception {
        String resultat = "function " + FileConfiguration.getHandlePrefix() + StringFormatterUtil.upperCaseTheFirstLetter(myClass) + "(){\n";
        resultat += FileConfiguration.getInitPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(myClass) + "();\n";
        List<String> headerParam = ReflexivityGetterAndSetter.getObjectGetterList(myClass);
        for (String myHeader : headerParam) {
            resultat += FileConfiguration.getInitPrefix() + StringFormatterUtil.upperCaseTheFirstLetter(myHeader) + "();\n";
        }
        resultat += "}\n";
        return resultat;
    }

    public static String getConfigFile() {
        return configFile;
    }

    public static String getUtilServiceConfig() {
        return utilServiceConfig;
    }

    public static String getUtilControlerConfig() {
        return utilControlerConfig;
    }

    public static String getConfigFileName() {
        return configFileName;
    }

    public static String getUtilServiceConfigName() {
        return utilServiceConfigName;
    }

    public static String getSessionUtil() {
        return sessionUtil;
    }

    public static String getSessionUtilName() {
        return sessionUtilName;
    }

    public static String getHandlePrefix() {
        return handlePrefix;
    }

    public static String getInitPrefix() {
        return initPrefix;
    }

    public static String getFrontControlerName() {
        return frontControlerName;
    }

    public static String getFrontControler() {
        return frontControler;
    }

    public static String getUtilControlerConfigName() {
        return utilControlerConfigName;
    }

}
