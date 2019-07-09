/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.config;

/**
 *
 * @author YOUNES
 */
public class RequestConfiguration {

    private static String addAttributeMinMax = "addAttributeMinMax";
    private static String addAttribute = "addAttribute";
    private static String wrapParamName = "wrapParam";
    private static String suffixMax = "Max";
    private static String suffixMin = "Min";
    private static String query = "$query";
    private static String suffixValue = "Value";
    private static String findPrefix = "find";
    private static String findAllPrefix = "findAll";
    private static String updatePrefix = "update";
    private static String createPrefix = "create";
    private static String findDetailPrefix = "findDetail";
    private static String deletePrefix = "delete";
    private static String findByCriteriaPrefix = "findByCriteria";
    private static String formatDataToTablePrefix = "formatDataToTable";

    public static String getAddAttributeMinMax() {
        return addAttributeMinMax;
    }

    public static String getAddAttribute() {
        return addAttribute;
    }

    public static String getWrapParamName() {
        return wrapParamName;
    }

    public static String getSuffixMax() {
        return suffixMax;
    }

    public static String getSuffixMin() {
        return suffixMin;
    }

    public static String getQuery() {
        return query;
    }

    public static String getSuffixValue() {
        return suffixValue;
    }

    public static String getFindPrefix() {
        return findPrefix;
    }

    public static String getFindAllPrefix() {
        return findAllPrefix;
    }

    public static String getUpdatePrefix() {
        return updatePrefix;
    }

    public static String getCreatePrefix() {
        return createPrefix;
    }

    public static String getFindDetailPrefix() {
        return findDetailPrefix;
    }

    public static String getDeletePrefix() {
        return deletePrefix;
    }

    public static String getFindByCriteriaPrefix() {
        return findByCriteriaPrefix;
    }

    public static String getFormatDataToTablePrefix() {
        return formatDataToTablePrefix;
    }

}
