/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.vo;

import ma.zs.generator.business.pojo.Pojo;

/**
 *
 * @author abdou
 */
public interface VoEngine {

    public String generateFields(Pojo pojo);

    public String generateAttributeSimple(Pojo pojo);

    public String generateAttributeGeneric(Pojo pojo);

    public String generateAttributeList(Pojo pojo);

    public String generateGetterAndSetterSimple(Pojo pojo);

    public String generateGetterAndSetterGeneric(Pojo pojo);

    public String generateGetterAndSetterList(Pojo pojo);

    public String generateClassHeader(Pojo pojo);

    public String generateImport();

    public String generateGetter(String type, String fieldName);

    public String generateSetter(String type, String fieldName);

    public String generateGetterList(String fieldName);

    public String generateSetterList(String fieldName);
    
    public void generateVo();
    
    public String constructFileName(Pojo pojo);

}
