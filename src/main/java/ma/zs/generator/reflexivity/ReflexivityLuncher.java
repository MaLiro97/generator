/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.reflexivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YOUNES
 */
public class ReflexivityLuncher {

    public static List<String> lunchGetters(Object obj) throws Exception {
        List<Method> methods = ReflexivityGetterAndSetter.getGetterList(obj.getClass());
        List<String> res = new ArrayList<>();
        for (int i = 0; i < methods.size(); i++) {
            Method method = methods.get(i);
            Object object = ReflexivityLuncher.lunchGetter(obj, method);
            if (object != null) {
                res.add(object.toString());
            } else {
                res.add(new String(""));
            }

        }
        return res;
    }

    public static Object lunchGetter(Object obj, int getterNumber) throws Exception {
        List<Method> methods = ReflexivityGetterAndSetter.getGetterList(obj.getClass());
        if (getterNumber < methods.size()) {
            return lunchGetter(obj, methods.get(getterNumber));
        }

        return null;
    }

    public static Method lunchGetterByName(Object obj, String name) throws Exception {
        Class[] params = new Class[1];
        params[0] = Class.forName("java.lang.String");
        return obj.getClass().getMethod(name, params);
    }

    public static Object lunchGetterByParamName(Object obj, String paramName) throws Exception {
        List<Method> getters = ReflexivityGetterAndSetter.getGetterList(obj.getClass());
        for (int i = 0; i < getters.size(); i++) {
            Method method = getters.get(i);
            if (method.getName().toLowerCase().equals(("get" + paramName).toLowerCase())) {

                return lunchGetter(obj, method);
            }

        }
        return null;
    }

    public static Object lunchGetter(Object objet, Method methodGetter) throws Exception {
        String type = methodGetter.getReturnType().getSimpleName();

        if (ReflexivityUtil.isGenericType(type)) {
            return methodGetter.invoke(objet, null);
        } else if (!type.equals("List") && !type.equals("ArrayList")) {
            Object resultatExec = methodGetter.invoke(objet, null);
            return ReflexivityGetterAndSetter.getId(resultatExec.getClass()).invoke(resultatExec, null);
        }
        return null;
    }

    public static Object lunchGetterWithParams(Object objet, Method methodGetter, String param) throws Exception {
        Object[] params = new Object[1];
        params[0] = param;
        return methodGetter.invoke(objet, params);
    }

    public static void lunchSetter(Object objet, Method methodSetter, Object value) throws Exception {
        Object[] objects = new Object[1];
        objects[0] = value;
        methodSetter.invoke(objet, objects);
    }
}
