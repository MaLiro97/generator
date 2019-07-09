/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.projet;

/**
 *
 * @author ismail
 */
public class ProjetBusinessConfig {

    private static final String projectName = "easy-faculte";
    private static final String projectPath = System.getProperty("user.home")+"\\Documents\\NetBeansProjects\\generatedProject\\src\\main\\java\\";

    private static final String projectPackageBase = "ma.zs.generatedProject";
    private static final String projectPackageBasePath = "ma\\zs\\generatedProject\\";
    private static final String projectPathWithPackagePath = projectPath + projectPackageBasePath;

    private static final String port = "8092";
    private static final String dbUser = "root";
    private static final String dbPassword = "";
    private static final String dbName = "7rira";

    public static String getProjectName() {
        return projectName;
    }

    public static String getProjectPath() {
        return projectPath;
    }

    public static String getProjectPackageBase() {
        return projectPackageBase;
    }

    public static String getProjectPackageBasePath() {
        return projectPackageBasePath;
    }

    public static String getPort() {
        return port;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static String getDbName() {
        return dbName;
    }

    public static String getProjectPathWithPackagePath() {
        return projectPathWithPackagePath;
    }

}
