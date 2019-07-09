/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.projet;

import java.io.IOException;

/**
 *
 * @author Anas
 */
public interface ProjetBusinessEngine {

    public void generateProjectDir() throws IOException;

    public void generateProject() throws IOException, Exception;
    

}
