/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.util;

import java.util.List;

/**
 *
 * @author Mohcine
 */
public class ListUtil {

    public static boolean isNull(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotNull(List list) {
        return !isNull(list);
    }

}
