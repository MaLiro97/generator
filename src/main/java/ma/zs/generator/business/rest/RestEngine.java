/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.rest;

import java.io.IOException;
import ma.zs.generator.business.pojo.Pojo;

/**
 *
 * @author Anas
 */
public interface RestEngine {

    public String getPath();

    public void generateRest() throws IOException;

    public void generateRestDir() throws IOException;

    public void generateRestContent() throws IOException;

    public String constructRestHeader(Pojo p);

    public String constructRestBody(Pojo p);

    public String generateCreateMethode(Pojo p);

    public String generateDeleteByIdMethode(Pojo p);

    public String generateRemoveByReference(Pojo pojo);



    public String generateFindAllMethode(Pojo p);

    public String generateFindByCriteriaMethode(Pojo p);

    public String generateImport(Pojo p);

    public boolean isSupportMinMax(String typeName);

    public String constructRestFooter(Pojo p);
}
