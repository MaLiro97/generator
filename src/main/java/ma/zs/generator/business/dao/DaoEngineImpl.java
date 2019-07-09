/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.dao;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.zs.generator.business.pojo.Pojo;
import ma.zs.generator.business.pojo.PojoUtilEngin;
import ma.zs.generator.business.projet.ProjetBusinessConfig;
import ma.zs.generator.util.FileUtil;
import ma.zs.generator.util.ProjetUtil;
import ma.zs.generator.util.StringFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mohcine
 */
@Service
public class DaoEngineImpl implements DaoEngine {

    @Autowired
    private DaoEngineConfig daoEngineConfig;
    @Autowired
    private PojoUtilEngin pojoUtil;

    @Override
    public void generete() {
        try {
            FileUtil.createDirectory(getPath());
        } catch (IOException ex) {
            Logger.getLogger(DaoEngineImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Pojo> pojos = pojoUtil.getPojos();
        for (Pojo pojo : pojos) {
            try {
                FileUtil.generateFile(constructFileName(pojo), generateFile(pojo), getDir());
            } catch (IOException ex) {
                Logger.getLogger(DaoEngineImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String generateFile(Pojo pojo) {
        //generer les import
        String resultat = generateImport(pojo);
        //generer le corps de la class
        resultat += generateNextIdMethode(pojo) + "\n";
        resultat += "}\n";
        return (resultat);
    }

    @Override
    public String generateNextIdMethode(Pojo pojo) {
        String res = "@Repository\n"
                + " public interface " + pojo.getPojoName() + daoEngineConfig.getSuffix() + " extends JpaRepository<" + pojo.getPojoName() + "," + pojo.getIdType() + "> {\n";
        try {
            //generer les method pour le reference
            res += generateMethodsForReference(pojo);
        } catch (Exception ex) {
            Logger.getLogger(DaoEngineImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //generer des method poud des object
            res += generateMethodsForObjects(pojo);
        } catch (Exception ex) {
            Logger.getLogger(DaoEngineImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        res += "\n";
        return res;

    }

    @Override
    public String constructFileName(Pojo pojo) {
        return pojo.getPojoName() + daoEngineConfig.getSuffix() + ".java";

    }

    @Override
    public String generateMethodsForReference(Pojo pojo) {
        String res = "";
        if (pojo.getReferenceName() != null) {
            String ref = StringFormatterUtil.upperCaseTheFirstLetter(pojo.getReferenceName());
            res += "\t public " + pojo.getPojoName() + " findBy" + ref + "(" + pojo.getReferenceType() + " " + pojo.getReferenceName() + ");\n";
            res += "\t public int deleteBy" + ref + "(" + pojo.getReferenceType() + " " + pojo.getReferenceName() + ");\n";
        }

        res += "\n";
        return res;

    }

    @Override
    public String generateMethodsForObjects(Pojo pojo) {
        String upperclasse = StringFormatterUtil.upperCaseTheFirstLetter(pojo.getPojoName());
        String res = "";
        for (Field field : pojo.getFieldsGeneric()) {
            String paramName = field.getName();
            String paramType = field.getType().getSimpleName();
            res += "\t public " + upperclasse + " findBy" + StringFormatterUtil.upperCaseTheFirstLetter(paramName) + "(" + paramType + ' ' + (paramName) + ");\n";
            res += "\t public int deleteBy" + StringFormatterUtil.upperCaseTheFirstLetter(paramName) + "(" + paramType + ' ' + (paramName) + ");\n";
        }
        res += "\n";
        return res;

    }

    @Override
    public String generateImport(Pojo pojo) {

        String resultat = "package " + ProjetBusinessConfig.getProjectPackageBase() + "." + daoEngineConfig.getPackageName() + ";\n";

        for (Field field : pojo.getFieldsGeneric()) {
            String param = field.getType().getSimpleName();
            resultat += "import " + ProjetUtil.getPackageBean() + "." + StringFormatterUtil.upperCaseTheFirstLetter(param) + ";\n";

        }

        resultat += "import " + ProjetUtil.getPackageBean() + "." + (pojo.getPojoName()) + ";\n";
        resultat += "\n" + daoEngineConfig.getImportHader();
        return resultat;
    }

    @Override
    public String getPath() {
        return ProjetBusinessConfig.getProjectPathWithPackagePath() + daoEngineConfig.getPackagePath();

    }

    public DaoEngineConfig getDaoEngineConfig() {
        return daoEngineConfig;
    }

    public void setDaoEngineConfig(DaoEngineConfig daoEngineConfig) {
        this.daoEngineConfig = daoEngineConfig;
    }

    public PojoUtilEngin getPojoUtil() {
        return pojoUtil;
    }

    public void setPojoUtil(PojoUtilEngin pojoUtil) {
        this.pojoUtil = pojoUtil;
    }

    public String getDir() {
        return (ProjetBusinessConfig.getProjectPath() + ProjetBusinessConfig.getProjectPackageBasePath() + daoEngineConfig.getPackagePath());
    }

}
