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
public class CssConfiguration {

    private static String cssInputForm = "form-control";
    private static String cssLabelForm = "col-sm-3";
    private static String cssLabelSearch = "sr-only";

    public static String getCssInputForm() {
        return cssInputForm;
    }

    public static String getCssLabelForm() {
        return cssLabelForm;
    }

    public static String getCssLabelSearch() {
        return cssLabelSearch;
    }

    public static void setCssLabelSearch(String cssLabelSearch) {
        CssConfiguration.cssLabelSearch = cssLabelSearch;
    }

}
