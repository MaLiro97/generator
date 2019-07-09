
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.converter;

import ma.zs.generator.business.pojo.Pojo;

/**
 *
 * @author BOSS
 */
public interface ConverterEngine {

    public void generateConverter() throws Exception;

    public void generateConverterDir() throws Exception;

    public String generateconverterContent(Pojo pojo) throws Exception;

    public String generateImport(Pojo pojo) throws Exception;

    public String constructConverterHeader(Pojo pojo) throws Exception;

    public String constructConverterBody(Pojo pojo) throws Exception;

    public String generatetoItemMethode(Pojo pojo) throws Exception;

    public String generatetoVoMethode(Pojo pojo) throws Exception;
    
    public String generateinitMethod (Pojo pojo) throws Exception;

    public String getPath();

    public String constructFileName(Pojo pojo);

}
