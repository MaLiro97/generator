/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.database;

/**
 *
 * @author ismail
 */
public class DataBaseUtilConfig {

    private static String beanPath = "main\\java\\ma\\zs\\generator\\bean";
    private static String packageRoot = "ma.zs.generator";
    private static String packageBean = packageRoot+".bean";

     
    public static String getBeanPath() {
        return beanPath;
    }

    public static void setBeanPath(String beanPath) {
        DataBaseUtilConfig.beanPath = beanPath;
    }

    public static String getPackageBean() {
        return packageBean;
    }

    public static void setPackageBean(String packageBean) {
        DataBaseUtilConfig.packageBean = packageBean;
    }

}
