/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.reflexivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import ma.zs.generator.business.pojo.Pojo;
import ma.zs.generator.util.StringFormatterUtil;

/**
 *
 * @author YOUNES
 */
public class ReflexivityGetterAndSetter {

    public static String getParameterName(Method methodGetter) {
        return (methodGetter.getName().substring(3).charAt(0) + "").toLowerCase() + methodGetter.getName().substring(4);
    }

    public static List<String> getParameterName(Class myClass) throws Exception {
        return getParameterName(getGetterListIncludingId(myClass, true, 3));
    }

    public static List<String> getParameterName(List<Method> methodGetters) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < methodGetters.size(); i++) {
            Method method = methodGetters.get(i);
            results.add(getParameterName(method));
        }
        return results;
    }

    public static List<String> getGetterAsString(Class myClass) throws Exception {
        List<Method> methodGetters = getGetterList(myClass);
        List<String> results = new ArrayList<>();
        for (int i = 0; i < methodGetters.size(); i++) {
            Method method = methodGetters.get(i);
            results.add("public " + method.getName());
        }
        return results;
    }

    public static List<String> getTypeParams(List<Method> methodGetters) throws Exception {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < methodGetters.size(); i++) {
            Method method = methodGetters.get(i);
            String typeRetour = method.getReturnType().getSimpleName();
            if (!ReflexivityUtil.typeJavaToSql(typeRetour).equals("")) {
                results.add(ReflexivityUtil.typeJavaToSql(typeRetour));
            } else if (!typeRetour.equals("List") && !typeRetour.equals("ArrayList")) {
                Class retourClass = Class.forName(method.getReturnType().getName());
                results.add(ReflexivityUtil.typeJavaToSql(getId(retourClass).getReturnType().getSimpleName()));
            }
        }
        return results;
    }

    public static List<String> getListOrArrayListElement(List<Method> methodGetters) throws Exception {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < methodGetters.size(); i++) {
            Method method = methodGetters.get(i);
            String typeRetour = method.getReturnType().getSimpleName();
            System.out.println("haa typeRetour ==> "+typeRetour);
            if (typeRetour.startsWith("List") || typeRetour.startsWith("ArrayList")) {
                System.out.println("haa typeRetour ==> "+typeRetour);
            }
        }
        return results;
    }

    public static List<Class> getParameterType(List<Method> methodGetters) {
        List<Class> results = new ArrayList<>();
        for (int i = 0; i < methodGetters.size(); i++) {
            Class type = methodGetters.get(i).getParameterTypes()[0];
            results.add(type);
        }
        return results;
    }

    public static List<Method> getGetterListExceptId(Class myClass) throws Exception {
        return getGetterListIncludingId(myClass, false, 3);
    }

    public static List<Method> getGetterList(Class myClass) throws Exception {
        return getGetterListIncludingId(myClass, true, 3);
    }

    public static List<String> getPrimitifGetterList(Class myClass) throws Exception {
        List<Method> mothods = getGetterListIncludingId(myClass, true, 1);
        return getParameterName(mothods);
    }

    public static List<String> getObjectGetterList(Class myClass) throws Exception {
        List<Method> mothods = getGetterListIncludingId(myClass, true, 2);
        return getParameterName(mothods);

    }

    public static List<String> getParamList(Class myClass) throws Exception {
        List<Method> mothods = getGetterList(myClass);
        return getParameterName(mothods);
    }

    public static List<String> getPrimitifParamList(Class myClass) throws Exception {
        List<Method> mothods = getGetterList(myClass);
        return getParameterName(mothods);
    }

    private static List<Method> getGetterListIncludingId(Class myClass, boolean includeId, int paramType) throws Exception {
        Method[] methods = myClass.getMethods();
        List<Method> getters = new ArrayList<Method>();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (!method.getName().equals("getClass") && method.getName().startsWith("get")) {
                if (paramType == 3) { // all
                    getters.add(method);
                } else if (!method.getReturnType().getName().equals("java.util.List")) {
                    if (paramType == 1) { // primitif
                        if (ReflexivityUtil.isGenericType(method.getReturnType().getSimpleName())) {
                            System.out.println("hana f primitif ==> " + method.getName());
                            getters.add(method);
                        }
                    } else if (paramType == 2) { // objet
                        System.out.println("hana f object non list ==> " + method.getName());
                        if (!ReflexivityUtil.isGenericType(method.getReturnType().getSimpleName())) {
                            getters.add(method);
                        }
                    }
                }
            }
        }
        return getters;

//            if (!includeId) {
//                boolean isId = method.getName().toLowerCase().equals(DataBaseMapping.findMappedIdGetter(myClass.getName()));
//                if (isId) {
//                    getters.remove(method);
//                }
//            }
    }

    public static List<Method> getSetterList(Class myClass) throws Exception {
        Method[] methods = myClass.getMethods();
        List<Method> getters = new ArrayList<>();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("set") && !method.getParameterTypes()[0].getName().equals("java.util.List")) {
                getters.add(method);
            }
        }

        return getters;
    }
  
    @Deprecated
    public static String getIdName(Class myClass) throws Exception {
        Method myMethod = getId(myClass);
        return myMethod.getName().substring(3, myMethod.getName().length());
    }
    
    public static String getIdName(Pojo pojo) throws Exception {
        Method myMethod = getId(pojo);
        return myMethod.getName().substring(3, myMethod.getName().length());
    }
   @Deprecated
    public static Method getId(Class myClass) throws Exception {
        List<Method> methods = getGetterList(myClass);
        for (int i = 0; i < methods.size(); i++) {
            Method method = methods.get(i);
            if (method.getName().equals("getId")) {
                return method;
            }
        }
        return null;
    }
   @Deprecated
    public static Method setId(Object obj) throws Exception {
        List<Method> methods = getSetterList(obj.getClass());
        for (int i = 0; i < methods.size(); i++) {
            Method method = methods.get(i);
            if (method.getName().equals("setId")) {
                return method;
            }
        }
        return null;
    }
     public static Method getId(Pojo pojo) throws Exception {
        List<Method> methods = getGetterList(pojo.getClazz());
        for (int i = 0; i < methods.size(); i++) {
            Method method = methods.get(i);
            if (method.getName().equals("get"+StringFormatterUtil.upperCaseTheFirstLetter(pojo.getIdName()))) {
                return method;
            }
        }
        return null;
    }

    public static Method setId(Pojo pojo) throws Exception {
        List<Method> methods = getSetterList(pojo.getClazz());
        for (int i = 0; i < methods.size(); i++) {
            Method method = methods.get(i);
            if (method.getName().equals("set"+StringFormatterUtil.upperCaseTheFirstLetter(pojo.getIdName()))) {
                return method;
            }
        }
        return null;
    }
    
    

}
