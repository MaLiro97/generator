/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.service.impl;

import java.io.File;


/**
 *
 * @author Anas
 */
public class ServiceImplConfig {

    private static final String serviceImplSuffix = "ServiceImpl";
    private static final String packageName = "domain.model.service.impl";
    private static final String packagePath = "domain"+File.separator+"model"+File.separator+"service"+File.separator+"impl";
    
    private static final String serviceSuffix = "Service";
    private static final String servicePackage = "domain.model.service";
    private static final String editMethodeName = "edit";
    private static final String daoSuffix = "Dao";
    private static final String daoPackage = "domain.model.dao";
    private static final String ejb = "\n @EJB \n";
    private static final String autowired = "\n @Autowired \n";
    private static final String override = "\n @Override \n";
    private static final String serviceAnnotation = "\n @Service  \n";

    private static final String saveMethodeName = "save";
    private static final String updateMethodeName = "update";
    private static final String saveWithItemsMethodePrefix = "saveWith";
    private static final String findByPrefix = "findBy";
    private static final String deleteByPrefix = "deleteBy";
    private static final String deleteMethodeName = "delete";
    private static final String findByCriteriaMethodeName = "findByCriteria";
    private static final String findByAllMethodeName = "findAll";

    public static String getSaveMethodeName() {
        return saveMethodeName;
    }
    

    public static String getUpdateMethodeName() {
        return updateMethodeName;
    }

    public static String getDeleteMethodeName() {
        return deleteMethodeName;
    }

    public static String getDeleteByPrefix() {
        return deleteByPrefix;
    }

    public static String getEjb() {
        return ejb;
    }

    public static String getEditMethodeName() {
        return editMethodeName;
    }
    

    public static String getFindByCriteriaMethodeName() {
        return findByCriteriaMethodeName;
    }

    public static String getPackageName() {
        return packageName;
    }

    public static String getPackagePath() {
        return packagePath;
    }

    public static String getFindByAllMethodePrefix() {
        return findByAllMethodeName;
    }

    public static String getSaveWithItemsMethodePrefix() {
        return saveWithItemsMethodePrefix;
    }

    public static String getServicePackage() {
        return servicePackage;
    }

    public static String getServiceSuffix() {
        return serviceSuffix;
    }

    public static String getServiceImplSuffix() {
        return serviceImplSuffix;
    }

    public static String getAutowired() {
        return autowired;
    }

    public static String getDaoPackage() {
        return daoPackage;
    }

    public static String getOverride() {
        return override;
    }

    public static String getFindByAllMethodeName() {
        return findByAllMethodeName;
    }

    public static String getDaoSuffix() {
        return daoSuffix;
    }

    public static String getServiceAnnotation() {
        return serviceAnnotation;
    }

    public static String getFindByPrefix() {
        return findByPrefix;
    }

}
