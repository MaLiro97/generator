/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.bean;

import java.io.IOException;
import java.util.List;
import ma.zs.generator.business.pojo.Pojo;

/**
 *
 * @author ismail
 */
public interface BeanEngine {
    
    public String generateFields(Pojo myPojo);
    public String generateSimpleFields(Pojo myPojo);
    public String generateGenericFields(Pojo myPojo);
    public String generateListFields(Pojo myPojo);
    public String generateClass(Pojo myPojo , String content);
    public String generate(Pojo myPojo);
    public String generateConstructor(Pojo myPojo);
    public void generateBeanFile() throws IOException;
    public String generateGetterSimple(Pojo myPojos);
    public String generateSetterSimple(Pojo myPojos);
    public String generateGetterGeneric(Pojo myPojos);
    public String generateSetterGeneric(Pojo myPojos);
    public String generateGetterList(Pojo myPojos);
    public String generateSetterList(Pojo myPojos);
    public String generateImports(Pojo myPojo);
    public String hashCodeAndEquals(Pojo myPojo);
    public String getPath();
    public void generateBeans() throws IOException;
    public void generateBeanDir() throws IOException;
    
    
}
