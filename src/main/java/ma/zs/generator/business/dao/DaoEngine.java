/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.dao;

import ma.zs.generator.business.pojo.Pojo;

/**
 *
 * @author Mohcine
 */
public interface DaoEngine {

    public void generete();
    public String generateFile(Pojo pojo);
    public String generateNextIdMethode(Pojo pojo);
    public String constructFileName(Pojo pojo);
    public String generateMethodsForReference(Pojo pojo);
    public String generateMethodsForObjects(Pojo pojo);
    public String generateImport(Pojo pojo);
    public String getPath();

}
