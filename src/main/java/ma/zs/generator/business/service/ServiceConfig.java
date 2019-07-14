/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.service;

import java.io.File;

/**
 *
 * @author El Massafi
 */
public class ServiceConfig {

    private static final String serviceSuffix = "Service";
    private static final String saveMethodeName = "save";
    private static final String updateMethodeName = "update";
    private static final String saveWithItemsMethodePrefix = "saveWith";
    private static final String deleteMethodeName = "delete";
    private static final String findByCriteriaMethodeName = "findByCriteria";
    private static final String findAllMethodeName = "findAll";
    private static final String deleteByPrefix = "deleteBy";

    private static final String packageName = "domain.model.service";
    private static final String packagePath = "domain"+File.separator+"model"+File.separator+"service";

    public static String getPackageName() {
        return packageName;
    }

    public static String getPackagePath() {
        return packagePath;
    }

    public static String getServiceSuffix() {
        return serviceSuffix;
    }

    public static String getSaveMethodeName() {
        return saveMethodeName;
    }

    public static String getUpdateMethodeName() {
        return updateMethodeName;
    }

    public static String getSaveWithItemsMethodePrefix() {
        return saveWithItemsMethodePrefix;
    }

    public static String getDeleteMethodeName() {
        return deleteMethodeName;
    }

    public static String getFindByCriteriaMethodeName() {
        return findByCriteriaMethodeName;
    }

    public static String getFindAllMethodeName() {
        return findAllMethodeName;
    }

    public static String getDeleteByPrefix() {
        return deleteByPrefix;
    }

}
