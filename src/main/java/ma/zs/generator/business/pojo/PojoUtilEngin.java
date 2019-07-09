/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.pojo;

import java.util.List;

/**
 *
 * @author YOUNES
 */
public interface PojoUtilEngin {

    public  List<Pojo> getPojos();
    
    public  List<Pojo> getPojosConfig();
    
    public  Pojo findPojoByClass(Class clazz);
    
}
