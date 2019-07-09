/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.util;

/**
 *
 * @author Mohcine
 */
public class ProjetUtil {

    private static final String projectPackageBase = "ma.zs.generatedProject";
    private static final String projectPackageBasePath = "ma\\zs\\generatedProject\\";
    private static String packageRoot = "ma.zs.generator";
    private static String packageBean = projectPackageBase + ".bean";

    public static String getPackageRoot() {
        return packageRoot;
    }

    public static void setPackageRoot(String packageRoot) {
        ProjetUtil.packageRoot = packageRoot;
    }

    public static String getPackageBean() {
        return packageBean;
    }

    public static void setPackageBean(String packageBean) {
        ProjetUtil.packageBean = packageBean;
    }

}
