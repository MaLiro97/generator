/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.config;

import ma.zs.generator.config.enumeration.SERVICE_TYPE;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author YOUNES
 */
public class FileStructureModuleConfiguration {

	private static Map<String, String> mvcDirectories;
	private static List<String> entityDirectories;
	private static List<String> modules;

	private static void initModules() {
		modules.add("flotte");
	}

	private static void initEntityDirectories() {
		entityDirectories.add("bean");
		entityDirectories.add("controller");
		entityDirectories.add("service");
		entityDirectories.add("vo");
		entityDirectories.add("controller.util");
		entityDirectories.add("service.util");
	}

	

	private static String findDal(Class key, String module, String suffix) {
		return findPath(module, "dal") + "\\" + key.getSimpleName() + suffix;
	}

	private static String findVo(Class key, String module, String suffix) {
		return findPath(module, "vo") + "\\" + key.getSimpleName() + suffix;
	}

	public static String findDalBo(Class key, String module) {
		return findDal(key, module, "Bo.java");
	}

	public static String findWsVo(Class key, String module) {
		return findVo(key, module, "Vo.java");
	}

	public static String findDalBr(Class key, String module) {
		return findDal(key, module, "BR.java");
	}

	public static String findDalDao(Class key, String module) {
		return findDal(key, module, "Dao.java");
	}

	public static String findBean(Class key, String module) {
		return findPath(module, "pl") + "\\" + key.getSimpleName() + "Bean.java";
	}

	private static String findPath(String module, String packageType) {
	
		System.out.println(getMvcDirectories());
		System.out.println("Search  :"+packageType + "." + module);
		return AppConfiguration.getProjectPath() + "\\" + getMvcDirectories().get(packageType + "." + module);
	}

	private static void initMvcDirecroties(String module) {
		mvcDirectories.put("pl." + module, "samac2\\" + module + "\\production\\pl");
		mvcDirectories.put("dal." + module, "samac2\\" + module + "\\production\\dal");
		mvcDirectories.put("bll." + module, "samac2\\" + module + "\\production\\bll");
		mvcDirectories.put("vo." + module, "samac2\\" + module + "\\production\\vo");
	}

	public static Map<String, String> getMvcDirectories() {
		if (mvcDirectories == null) {
			mvcDirectories = new HashMap();
			List<String> myModules = getModules();
			for (String module : myModules) {
				initMvcDirecroties(module);
			}
		}
		return mvcDirectories;
	}

	public static List<String> getEntityDirectories() {
		if (entityDirectories == null) {
			entityDirectories = new ArrayList();
			initEntityDirectories();
		}

		return entityDirectories;
	}

	public static List<String> getModules() {
		if (modules == null) {
			modules = new ArrayList();
			initModules();
		}
		return modules;
	}

}
