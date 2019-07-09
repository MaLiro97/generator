/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.rest;

import ma.zs.generator.business.converter.ConverterConfig;
import ma.zs.generator.business.projet.ProjetBusinessConfig;
import ma.zs.generator.business.service.ServiceConfig;

/**
 *
 * @author Anas
 */
public class RestConfig {

    private static final String saveMethodeName = ServiceConfig.getSaveMethodeName();
    private static final String updateMethodeName = ServiceConfig.getUpdateMethodeName();
    private static final String deleteByPrefix = ServiceConfig.getDeleteByPrefix();
    private static final String findAllName = ServiceConfig.getFindAllMethodeName();
    private static final String ProjectName = ProjetBusinessConfig.getProjectName();
    private static final String findByCriteriaMethodeName = ServiceConfig.getFindByCriteriaMethodeName();
    private static final String ConverterSuffix = ConverterConfig.getSuffix();
    private static final String ServiceSuffix = ServiceConfig.getServiceSuffix();
    private static final String packageName = "ws.rest";
    private static final String packagePath = "ws\\rest\\";
    private static final String CrossOrigin = "@CrossOrigin(origins = {\"http://localhost:4200\"})";
    private static final String VoSuffix = "Vo";
        private static final String ItemName = "Item";

    private static final String SearchVoSuffix = "SerchVo";

    public static String getItemName() {
        return ItemName;
    }

    public static String getSearchVoSuffix() {
        return SearchVoSuffix;
    }

    public static String getVoSuffix() {
        return VoSuffix;
    }

    public static String getCrossOrigin() {
        return CrossOrigin;
    }

    public static String getSaveMethodeName() {
        return saveMethodeName;
    }

    public static String getUpdateMethodeName() {
        return updateMethodeName;
    }

    public static String getDeleteByPrefix() {
        return deleteByPrefix;
    }

  
    public static String getFindAllName() {
        return findAllName;
    }

    public static String getProjectName() {
        return ProjectName;
    }

    public static String getFindByCriteriaMethodeName() {
        return findByCriteriaMethodeName;
    }

    public static String getConverterSuffix() {
        return ConverterSuffix;
    }

    public static String getServiceSuffix() {
        return ServiceSuffix;
    }

    public static String getPackageName() {
        return packageName;
    }

    public static String getPackagePath() {
        return packagePath;
    }

}
