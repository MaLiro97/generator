/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.app;

import ma.zs.generator.config.AppConfiguration;
import ma.zs.generator.config.FileStructureModuleConfiguration;
import ma.zs.generator.database.DatabaseEngine;
import ma.zs.generator.database.OrmEngine;
import java.io.IOException;
import java.util.List;
import ma.zs.generator.util.FileUtil;

/**
 *
 * @author YOUNES
 */
public class AppGenerator {

    public static void generate(Class key, String createView, String editView, String detailView, String listView, String removeView,
            String boFile, List<String> bllFile, String daoFile, String brFile, String beanFile,
            String module,String vo) throws Exception {

        String directoryPath = FileUtil.constructFilePath(key);
     FileUtil.createDirectory(directoryPath);
//        generatePages(key, createView, directoryPath, editView, detailView, listView, removeView);
//      generateDalFiles(key, boFile, daoFile, brFile, module);
//        generateBllFiles(key, bllFile, module);
//        generateBeanFile(key, beanFile, module);
   generateVo(key, vo, module);

    }

    private static void generateDalFiles(Class key, String boFile, String daoFile, String brFile, String module) throws IOException {
    	FileUtil.create(FileStructureModuleConfiguration.findDalBo(key, module), boFile);
        FileUtil.create(FileStructureModuleConfiguration.findDalDao(key, module), daoFile);
        FileUtil.create(FileStructureModuleConfiguration.findDalBr(key, module), brFile);

    }
    
    private static void generateVo(Class key, String voFile ,String module) throws IOException {
        FileUtil.create(FileStructureModuleConfiguration.findWsVo(key, module), voFile);
    }

   

    private static void generatePages(Class key, String createView, String directoryPath, String editView, String detailView, String listView, String removeView) throws IOException {
        FileUtil.generateFile(key, createView, directoryPath, AppConfiguration.getCreateViewSuffix());
        FileUtil.generateFile(key, editView, directoryPath, AppConfiguration.getEditViewSuffix());
        FileUtil.generateFile(key, detailView, directoryPath, AppConfiguration.getDetailViewSuffix());
        FileUtil.generateFile(key, listView, directoryPath, AppConfiguration.getListViewSuffix());
        FileUtil.generateFile(key, removeView, directoryPath, AppConfiguration.getRemoveViewSuffix());
    }

    public static void generateMappingFile(String module, String moduleAbreveation) throws Exception {
        generateSqlFile();
        generateOrmFiles(module, moduleAbreveation);
    }

    public static void generateBeanFile(Class key, String beanContent, String module) throws Exception {
        FileUtil.create(FileStructureModuleConfiguration.findBean(key, module), beanContent);
    }

    private static void generateSqlFile() throws Exception {
        String path = AppConfiguration.getProjectPath();
        String sqlFile = DatabaseEngine.generateTablesCode();
        FileUtil.create(path + "\\db.sql", sqlFile);

    }

    private static void generateOrmFiles(String module, String moduleAbreveation) throws Exception {
        String path = AppConfiguration.getProjectPath();
        String ormFileContent = OrmEngine.generateFileContent(module, moduleAbreveation);
        FileUtil.create(path + "\\orm.xml", ormFileContent);
    }

}
