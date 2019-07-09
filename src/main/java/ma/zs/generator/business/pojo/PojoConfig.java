/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.pojo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gouss
 */
public class PojoConfig {

    private static final String reference = "reference";
    private static final String idDefaultName = "id";
    private static final String idDefaultType = "Long";

    public static String getReference() {
        return reference;
    }

    public static String getIdDefaultName() {
        return idDefaultName;
    }

    public static String getIdDefaultType() {
        return idDefaultType;
    }
    
    

}
