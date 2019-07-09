/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.vo;

/**
 *
 * @author abdou
 */
public class VoConfig {

    private static final String packageName = "ws.rest.vo";
    private static final String packagePath = "ws\\rest\\vo\\";
    private static final String voPrefix = "Vo";
    private static final String voPrefixList = "sVo";

    public static String getPackageName() {
        return packageName;
    }

    public static String getPackagePath() {
        return packagePath;
    }

    public static String getVoPrefix() {
        return voPrefix;
    }

    public static String getVoPrefixList() {
        return voPrefixList;
    }

    
}
