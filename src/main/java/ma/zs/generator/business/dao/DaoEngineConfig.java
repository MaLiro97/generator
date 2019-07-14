/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.dao;

import java.io.File;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mohcine
 */
@Component
public class DaoEngineConfig {

    private String importHader = "import org.springframework.data.jpa.repository.JpaRepository;\n"
            + "import org.springframework.stereotype.Repository;\n\n\n";

    private String packagePath = "domain"+File.separator+"model"+File.separator+"dao";
    private String packageName = "domain.model.dao";
    private String suffix = "Dao";
    private static final String persistenceContext = "@PersistenceContext(unitName=\"";
    private static final String persistenceName = "persistence";
    
    
    
    public static String getPersistenceContext() {
        return persistenceContext+persistenceName+"\");";
    }

    public String getImportHader() {
        return importHader;
    }

    public void setImportHader(String importHader) {
        this.importHader = importHader;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
