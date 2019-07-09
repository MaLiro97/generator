/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.projet.view;

import java.io.IOException;
import ma.zs.generator.util.FileUtil;

/**
 *
 * @author Anas
 */
public class ProjetWebEngine {

    public static void generateProjectWebDir() throws IOException {
        FileUtil.createDirectory(ProjetWebConfig.getProjectPath() );
    }

    public static void generateProject() throws IOException {
        generateProjectWebDir();
    }

}
