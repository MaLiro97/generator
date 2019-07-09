package ma.zs.generator.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author YOUNES
 */
public class AppEngine {

//    public static void main(String[] args) throws Exception {
//        generate(LANGUAGE.JAVA, "\n\n");
//    }

//    public static void generate(LANGUAGE language, String separator) throws Exception {
//        String module = "flotte";
//        String moduleAbreviation = "FL";
//        String path = AppConfiguration.getProjectPath();
//        Map<Class, String> createView = Page.generateCreate(language, PAGE_STYLE.NAORMAL_PAGE);
//        Map<Class, String> detailView = Page.generateDetail(language, PAGE_STYLE.NAORMAL_PAGE);
//        Map<Class, String> editView = Page.generateEdit(language, PAGE_STYLE.NAORMAL_PAGE);
//        Map<Class, String> listView = Page.generateList(language, PAGE_STYLE.NAORMAL_PAGE);
//        Map<Class, String> removeView = Page.generateRemove(language, PAGE_STYLE.NAORMAL_PAGE);
//        Map<Class, String> boFile = BoEngine.generateTypeMethode(language, module);
//        Map<Class, List<String>> bllFile = BllEngine.generate(language, module);
//        Map<Class, String> daoFile = DaoEngine.generate(language);
//        Map<Class, String> brFile = BrEngine.generate(language, module);
//        Map<Class, String> beanFile = BeanEngine.generate(language, module);
//        Map<Class, String> vos = VoEngine.generate(language, module);
//
//
//        List<Class> keyList = new ArrayList(boFile.keySet());
//        ProjectStructureGenerator.generateProjectStructure(keyList, FILE_STRUCTURE_STYLE.MVC);
//        for (int i = 0; i < keyList.size(); i++) {
//            Class key = keyList.get(i);
//            AppGenerator.generate(key, createView.get(key), editView.get(key), detailView.get(key), listView.get(key),
//                    removeView.get(key), boFile.get(key), bllFile.get(key), daoFile.get(key),
//                    brFile.get(key), beanFile.get(key),vos.get(key), module);
//        }
//        AppGenerator.generateMappingFile(module, moduleAbreviation);
//        FacesConfigEngine.generateFacesFile(module);
//    }

}
