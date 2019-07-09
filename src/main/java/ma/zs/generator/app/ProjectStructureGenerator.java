/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.app;

import ma.zs.generator.config.FileStructureModuleConfiguration;
import ma.zs.generator.config.enumeration.FILE_STRUCTURE_STYLE;
import ma.zs.generator.config.enumeration.PAGE_TYPE;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ma.zs.generator.util.FileUtil;

/**
 *
 * @author YOUNES
 */
public class ProjectStructureGenerator {

    public static String getPageSuffix(PAGE_TYPE page_type) {
        switch (page_type) {
            case SEARCH:
                return "Search";
            case CREATE:
                return "New";
            case DETAIL:
                return "View";
            case EDIT:
                return "Edit";
        }
        return null;
    }

    

    public static void generateProjectStructure(List<Class> keyList, FILE_STRUCTURE_STYLE file_structure_style) throws IOException {
        generateViewFolder(keyList);
        if (file_structure_style == FILE_STRUCTURE_STYLE.MVC) {
            Map<String, String> mvcFiles = FileStructureModuleConfiguration.getMvcDirectories();
            List<String> mvcFilesKey = new ArrayList(mvcFiles.keySet());
            for (int i = 0; i < mvcFilesKey.size(); i++) {
                String filePath = FileUtil.constructFilePath(mvcFiles.get(mvcFilesKey.get(i)));
                FileUtil.createDirectory(filePath);
            }

        } else if (file_structure_style == FILE_STRUCTURE_STYLE.ENTITY) {
            List<String> modules = FileStructureModuleConfiguration.getModules();
            for (int i = 0; i < modules.size(); i++) {
                String filePath = FileUtil.constructFilePath(modules.get(i));
                FileUtil.createDirectory(filePath);
            }
        }

    }

    private static void generateViewFolder(List<Class> keyList) throws IOException {
        for (int i = 0; i < keyList.size(); i++) {
            Class key = keyList.get(i);
            FileUtil.createDirectory(FileUtil.constructFilePath(key));
        }
    }
}
