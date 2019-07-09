/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.config;

import ma.zs.generator.config.enumeration.PAGE_TYPE;
import ma.zs.generator.config.enumeration.FILE_TYPE;
import ma.zs.generator.util.StringFormatterUtil;

/**
 *
 * @author YOUNES
 */
public class AppConfiguration {

    private static String serviceSuffix = "Service.php";

    private static String controlerSuffix = "Controler.php";
    private static String jsonControlerSuffix = "JsonControler.php";
    //private static String projectPath = "C:\\Program Files (x86)\\EasyPHP-DevServer-14.1VC9\\data\\localweb\\projects\\User";
    private static String projectPath = "C:\\Users\\ismail\\Documents\\NetBeansProjects\\samaka\\src";

    private static String createViewSuffix = "New.jsp";
    private static String jQuerySuffix = "JQuery.js";
    private static String editViewSuffix = "Edit.jsp";
    private static String detailViewSuffix = "View.jsp";
    private static String listViewSuffix = "Search.jsp";
    private static String removeViewSuffix = "Remove.php";

    private static String createViewIdSuffix = "Create";
    private static String createViewPopUpIdSuffix = "CreateView";
    private static String editViewIdSuffix = "Edit";
    private static String detailViewIdSuffix = "Detail";
    private static String listViewIdSuffix = "List";
    private static String searchViewIdSuffix = "Search";
    private static String removeViewIdSuffix = "Remove";

    private static String buttonIdSuffix = "Button";
    private static String removeIdSuffix = "Entry";

    private static String editButtonPrefix = "e";
    private static String removeButtonPrefix = "d";
    private static String trPrefix = "tr";

    private static String beanPath = "main\\java\\ma\\zs\\generator\\bean";
    private static String jqueryEditViewSelector = ".btn-primary";
    private static String jqueryRemoveViewSelector = ".btn-danger";

    private static String searchMinLabelSuffix = " Min";
    private static String searchMinSuffix = "Min";
    private static String searchMaxLabelSuffix = " Max";
    private static String searchMaxSuffix = "Max";

    public static String constructFileName(Class param, FILE_TYPE file_type) {
        if (null != file_type) {
            switch (file_type) {
                case SERVICE:
                    return constructFileNameService(param);
                case CONTROLER:
                    return constructFileNameControler(param);
                case JSON_CONTROLER:
                    return constructFileNameJsonControler(param);
                case EDIT:
                    return constructFileNameEditView(param);
                case LIST:
                    return constructFileNameListView(param);
                case REMOVE:
                    return constructFileNameRemoveView(param);
                case CREATE:
                    return constructFileNameCreateView(param);
                case JQUERY:
                    return constructFileNameJQuery(param);
                default:
                    break;
            }
        }
        return null;
    }

    public static String constructId(Class param, PAGE_TYPE page_type) {
        if (null != page_type) {
            switch (page_type) {
                case CREATE:
                    return constructIdCreate(param);
                case DETAIL:
                    return constructIdCreateView(param);
                case EDIT:
                    return constructIdEdit(param);
                case LIST:
                    return constructIdList(param);
                case REMOVE:
                    return constructIdRemove(param);
                case SEARCH:
                    return constructIdSearch(param);
                default:
                    break;
            }
        }
        return null;
    }

    public static String constructId(String param, PAGE_TYPE page_type) {
        if (null != page_type) {
            switch (page_type) {
                case CREATE:
                    return constructIdCreate(param);
                case EDIT:
                    return constructIdEdit(param);
                case DETAIL:
                    return constructIdDetail(param);
                case LIST:
                    return constructIdList(param);
                case SEARCH:
                    return constructIdSearch(param);
                case REMOVE:
                    return constructIdRemove(param);
                default:
                    break;
            }
        }
        return null;
    }

    private static String constructFileNameService(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getServiceSuffix();
    }

    private static String constructFileNameControler(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getControlerSuffix();
    }

    private static String constructFileNameJsonControler(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getJsonControlerSuffix();
    }

    private static String constructFileNameCreateView(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getCreateViewSuffix();
    }

    private static String constructFileNameEditView(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getEditViewSuffix();
    }

    private static String constructFileNameListView(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getListViewSuffix();
    }

    private static String constructFileNameRemoveView(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getRemoveViewSuffix();
    }

    private static String constructFileNameJQuery(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getjQuerySuffix();
    }

    private static String constructIdCreate(String param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getCreateViewIdSuffix();
    }

    private static String constructIdCreate(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getCreateViewIdSuffix();
    }

    private static String constructIdCreateView(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getCreateViewPopUpIdSuffix();
    }

    private static String constructIdEdit(String param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getEditViewIdSuffix();
    }

    private static String constructIdDetail(String param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getDetailViewIdSuffix();
    }

    private static String constructIdEdit(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getEditViewIdSuffix();
    }

    private static String constructIdList(String param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getListViewIdSuffix();
    }

    private static String constructIdSearch(String param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getSearchViewIdSuffix();
    }

    private static String constructIdSearch(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getSearchViewIdSuffix();
    }

    private static String constructIdList(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getListViewIdSuffix();
    }

    private static String constructIdRemove(String param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getRemoveViewIdSuffix();
    }

    private static String constructIdRemove(Class param) {
        return StringFormatterUtil.lowerCaseTheFirstLetter(param) + AppConfiguration.getRemoveViewIdSuffix();
    }

    public static String getServiceSuffix() {
        return serviceSuffix;
    }

    public static String getControlerSuffix() {
        return controlerSuffix;
    }

    public static String getJsonControlerSuffix() {
        return jsonControlerSuffix;
    }

    public static String getProjectPath() {
        return projectPath;
    }

    public static String getBeanPath() {
        return beanPath;
    }

    public static String getCreateViewSuffix() {
        return createViewSuffix;
    }

    public static String getEditViewSuffix() {
        return editViewSuffix;
    }

    public static String getDetailViewSuffix() {
        return detailViewSuffix;
    }

    public static String getListViewSuffix() {
        return listViewSuffix;
    }

    public static String getjQuerySuffix() {
        return jQuerySuffix;
    }

    public static String getRemoveViewSuffix() {
        return removeViewSuffix;
    }

    public static String getCreateViewIdSuffix() {
        return createViewIdSuffix;
    }

    public static String getEditViewIdSuffix() {
        return editViewIdSuffix;
    }

    public static String getDetailViewIdSuffix() {
        return detailViewIdSuffix;
    }

    public static String getListViewIdSuffix() {
        return listViewIdSuffix;
    }

    public static String getRemoveViewIdSuffix() {
        return removeViewIdSuffix;
    }

    public static String getButtonIdSuffix() {
        return buttonIdSuffix;
    }

    public static String getRemoveIdSuffix() {
        return removeIdSuffix;
    }

    public static String getEditButtonPrefix() {
        return editButtonPrefix;
    }

    public static String getRemoveButtonPrefix() {
        return removeButtonPrefix;
    }

    public static String getTrPrefix() {
        return trPrefix;
    }

    public static String getJqueryEditViewSelector() {
        return jqueryEditViewSelector;
    }

    public static String getJqueryRemoveViewSelector() {
        return jqueryRemoveViewSelector;
    }

    public static String getSearchViewIdSuffix() {
        return searchViewIdSuffix;
    }

    public static String getSearchMinLabelSuffix() {
        return searchMinLabelSuffix;
    }

    public static String getSearchMaxLabelSuffix() {
        return searchMaxLabelSuffix;
    }

    public static String getSearchMinSuffix() {
        return searchMinSuffix;
    }

    public static String getSearchMaxSuffix() {
        return searchMaxSuffix;
    }

    public static String getCreateViewPopUpIdSuffix() {
        return createViewPopUpIdSuffix;
    }

}
