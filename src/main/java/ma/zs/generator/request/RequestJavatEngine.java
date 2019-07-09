/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.request;

import ma.zs.generator.reflexivity.ReflexivityGetterAndSetter;
import java.lang.reflect.Method;
import java.util.List;
import ma.zs.generator.database.DataBaseUtil;
import ma.zs.generator.util.FileUtil;
import ma.zs.generator.reflexivity.ReflexivityLuncher;
import ma.zs.generator.util.StringFormatterUtil;

/**
 *
 * @author moulaYounes
 */
public class RequestJavatEngine {

    public static String constructSaveRequette(Object obj) throws Exception {
        String requette = "INSERT INTO " + StringFormatterUtil.lowerCaseTheFirstLetter(obj.getClass()) + "(";
        List<Method> mothods = ReflexivityGetterAndSetter.getGetterList(obj.getClass());
        List<String> params = ReflexivityGetterAndSetter.getParameterName(mothods);
        requette += RequestUtil.assembleRequestItem(params, false);
        requette += ") VALUES(";
        requette += RequestUtil.assembleRequestItem(params, true);
        requette += ")";
        System.out.println("ha reekkete php ==> " + requette);
        return requette;
    }

    public static String constructUpdateRequette(Object obj, Object id) throws Exception {
        String requette = "UPDATE  " + StringFormatterUtil.lowerCaseTheFirstLetter(obj.getClass()) + " SET ";
        List<Method> mothods = ReflexivityGetterAndSetter.getGetterList(obj.getClass());
        List<String> params = ReflexivityGetterAndSetter.getParameterName(mothods);
        requette += RequestUtil.assembleRequestItemForUpdate(params, ReflexivityLuncher.lunchGetters(obj));
        requette += " WHERE id='" + id + "'";

        return requette;
    }

    public static String constructDeleteRequette(Object obj) throws Exception {
        String requette = "DElETE FROM " + StringFormatterUtil.lowerCaseTheFirstLetter(obj.getClass()) + " WHERE id='" + ReflexivityLuncher.lunchGetter(obj, ReflexivityGetterAndSetter.getId(obj.getClass())) + "'";
        return requette;
    }

}
