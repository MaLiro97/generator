/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.database;

import ma.zs.generator.config.AppConfiguration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.zs.generator.util.FileUtil;

/**
 *
 * @author YOUNES
 */
public class DataBaseUtil {

    private static List<Class> pojos = new ArrayList();

    static {
        try {
            pojos = findPojo();
        } catch (Exception ex) {
            Logger.getLogger(DataBaseUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static List<Class> findPojo() throws Exception {
        List<String> myClasses = findPojoName();
        List<Class> res = new ArrayList();
        for (int i = 0; i < myClasses.size(); i++) {
            res.add(Class.forName(myClasses.get(i)));
        }
        return res;
    }

    public static List<String> findPojoName() throws Exception {
        List<String> myClasses = new ArrayList();
        String beanPath = DataBaseUtilConfig.getBeanPath();
        String packageBean = DataBaseUtilConfig.getPackageBean();
        File file = new File("src/" + beanPath);
        String[] files = file.list();
        for (int i = 0; i < files.length; i++) {
            String myFile = files[i];
            myFile = myFile.replace(".java", "");
            myClasses.add(packageBean+"." + myFile);
        }
        return myClasses;


    }

    public static void generateFile(Map<Class, String> resultats, String rootPath, String fileSuffix, String separator) throws Exception {
        for (Map.Entry<Class, String> entry : resultats.entrySet()) {
            Class key = entry.getKey();
            String content = entry.getValue();
            FileUtil.generateFile(key, content, rootPath, fileSuffix);
        }
    }
      
    public static List<Class> getPojos() {
        return pojos;
    }

}
