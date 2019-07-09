/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.utilEngine;

import java.io.IOException;
import java.util.Date;

/**
 *
 * @author A O
 */
public interface UtilEngine {

    public void generate() throws IOException, Exception;

    public String generatePackage();

    public String generateSearchUtil();

    public String generateNumberUtil();

    public String generateDateUtil();

}
