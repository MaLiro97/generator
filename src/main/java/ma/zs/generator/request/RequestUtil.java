/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.request;

import java.util.List;

/**
 *
 * @author YOUNES
 */
public class RequestUtil {

    public static String assembleRequestItemForUpdate(List<String> params, List<String> execMethods) {
        String requette = "";
        for (int i = 0; i < params.size(); i++) {
            requette += "" + params.get(i) + "='\" ." + execMethods.get(i) + ". \"'";
            //. " '" . wrapParam('message', $data) . "' , '" .
            if (i != params.size() - 1) {
                requette += ", ";
            }
        }
        return requette;
    }

    public static String assembleRequestItem(List<String> params, boolean beforeValueInInsertRequest) {
        String requette = "";
        for (int i = 0; i < params.size(); i++) {
            if (beforeValueInInsertRequest) {
                requette += "'\" ." + params.get(i) + ". \"'";
            } else {
                requette += "" + params.get(i) + "";
            }
            if (i != params.size() - 1) {
                requette += ", ";
            }
        }
        return requette;
    }

}
