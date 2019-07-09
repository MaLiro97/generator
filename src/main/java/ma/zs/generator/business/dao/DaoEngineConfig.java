/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.dao;

import org.springframework.stereotype.Component;

/**
 *
 * @author Mohcine
 */
@Component
public class DaoEngineConfig {

    private String importHader = "import org.springframework.data.jpa.repository.JpaRepository;\n"
            + "import org.springframework.stereotype.Repository;\n\n\n";

    private String packagePath = "domain\\model\\dao";
    private String packageName = "domain.model.dao";
    private String suffix = "Dao";

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
