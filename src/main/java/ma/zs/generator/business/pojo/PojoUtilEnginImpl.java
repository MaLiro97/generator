/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.pojo;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.zs.generator.database.DataBaseUtilConfig;
import ma.zs.generator.reflexivity.ReflexivityGetterAndSetter;
import ma.zs.generator.reflexivity.ReflexivityUtil;
import ma.zs.generator.util.FileUtil;
import org.springframework.stereotype.Service;

/**
 *
 * @author gouss
 */
@Service
public class PojoUtilEnginImpl implements PojoUtilEngin {

    private List<Pojo> pojos = new ArrayList<>();
    private List<Pojo> pojosConfig = new ArrayList<>();

    public PojoUtilEnginImpl() {

        try {
            loadConfigPojo();
            pojos = findPojo();
            updatePojo();
        } catch (Exception ex) {
            Logger.getLogger(PojoUtilEngin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadConfigPojo() throws Exception {
//        pojosConfig.add(new Pojo(Compte.class.getSimpleName(), "rib", "String", Compte.class));
    }

    public Class findClass(String className) throws Exception {
        List<Object[]> myClasses = findPojoName();
        for (int i = 0; i < myClasses.size(); i++) {
            final Object[] myClass = myClasses.get(i);
            Class clazz = (Class) myClass[1];
            if (clazz.getSimpleName().equals(className)) {
                return (Class) myClass[1];
            }
        }

        return null;
    }

    private List<Pojo> findPojo() throws Exception {
        List<Object[]> myClasses = findPojoName();
        List<Pojo> res = new ArrayList();
        for (int i = 0; i < myClasses.size(); i++) {
            final Object[] myClass = myClasses.get(i);
            Pojo pojo = findPojoInConfig((Class) myClass[1]);
            Pojo myPojo = null;
            if (pojo == null) {
                myPojo = new Pojo(pojoName((String) myClass[0]), PojoConfig.getIdDefaultName(), PojoConfig.getIdDefaultType(), (Class) myClass[1]);
            } else {
                myPojo = pojo;
            }
            findFields(myPojo);
            findFieldsSimple(myPojo);
            setRefenceAndTypeReference(myPojo);
            findFieldsGeneric(myPojo);
            findFieldList(myPojo);
            findGetterAndSetterMethods(myPojo);
            setFieldsSimpleMinMaxAndSimple(myPojo);

            res.add(myPojo);
        }
        return res;
    }

    private String pojoName(String chaine) {
        int lastPoint = LastPointIndex(chaine);
       
        return chaine.substring(lastPoint+1, chaine.toCharArray().length);
    }
    
    private int LastPointIndex(String chaine){
        int lastPoint = 0;
        for (int i = 0; i < chaine.toCharArray().length; i++) {
            char c = chaine.toCharArray()[i];
            if (c=='.') {
                lastPoint = i;
            }
        }
        return lastPoint;
    }

    public Pojo findPojoByClass(Class clazz) {
        for (Pojo pojo : pojos) {
            if (pojo.getClazz() == clazz) {
                return pojo;
            }
        }
        return null;
    }

    public List<Object[]> findPojoName() throws Exception {
        List<Object[]> myClasses = new ArrayList();
        String beanPath = DataBaseUtilConfig.getBeanPath();
        String packageBean = DataBaseUtilConfig.getPackageBean();
        File file = new File("src/" + beanPath);
        String[] files = file.list();
        for (int i = 0; i < files.length; i++) {
            String myFile = files[i];
            myFile = myFile.replace(".java", "");
            final String myClass = packageBean + "." + myFile;
            myClasses.add(new Object[]{myClass, Class.forName(myClass)});
        }
        return myClasses;

    }

    public List<String> findPojoNameOussama() throws Exception {
        List<String> myClasses = new ArrayList();
        String beanPath = DataBaseUtilConfig.getBeanPath();
        String packageBean = DataBaseUtilConfig.getPackageBean();
        File file = new File("src/" + beanPath);
        String[] files = file.list();
        for (int i = 0; i < files.length; i++) {
            String myFile = files[i];
            myFile = myFile.replace(".java", "");
            myClasses.add(packageBean + "." + myFile);
        }
        return myClasses;

    }

    public void generateFile(Map<Class, String> resultats, String rootPath, String fileSuffix, String separator) throws Exception {
        for (Map.Entry<Class, String> entry : resultats.entrySet()) {
            Class key = entry.getKey();
            String content = entry.getValue();
            FileUtil.generateFile(key, content, rootPath, fileSuffix);
        }
    }

//    public  List<Class> getPojos() {
//        return pojos;
//    }
    private Pojo findPojoInConfig(Class clazz) {
        for (int i = 0; i < pojosConfig.size(); i++) {
            if (pojosConfig.get(i).getClazz() == clazz) {
                return pojosConfig.get(i);
            }

        }
        return null;
    }

    private void findFields(Pojo pojo) throws Exception {
        List<Field> fields = new ArrayList<>();

        for (Field field : pojo.getClazz().getDeclaredFields()) {
            fields.add(field);
        }
        pojo.setFields(fields);
    }

    private void findFieldsSimple(Pojo pojo) throws Exception {
        List<Field> fields = new ArrayList<>();
        for (Field field : pojo.getClazz().getDeclaredFields()) {
            String type = field.getType().getSimpleName();
            if (ReflexivityUtil.isGenericType(type)) {
                fields.add(field);
            }
        }
        pojo.setFieldsSimple(fields);
    }

    private void findFieldsGeneric(Pojo pojo) throws Exception {
        List<Field> fields = new ArrayList<>();
        for (Field field : pojo.getClazz().getDeclaredFields()) {
            String type = field.getType().getSimpleName();
            if (!ReflexivityUtil.isGenericType(type) && !type.equals("List")) {
                fields.add(field);
            }
        }
        pojo.setFieldsGeneric(fields);
    }

    private void findFieldList(Pojo pojo) throws Exception {
        List<PojoItem> pojoItems = new ArrayList<>();
        List<PojoItem> pojoItemsSimple = new ArrayList<>();
        Field[] fields = pojo.getClazz().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.getType().getSimpleName().equals("List")) {
                PojoItem pojoItem = new PojoItem();
                pojoItem.setField(field);
                String type = listNameType(field);
                pojoItem.setPojoItemType(type);
                if (isPrimitif(type)) {
                    pojoItem.setPrimitif(true);
                    pojoItemsSimple.add(pojoItem);
                } else {
                    pojoItem.setPrimitif(false);
                }
                pojoItems.add(pojoItem);
            }
        }
        pojo.setFieldsList(pojoItems);
        pojo.setFieldsListSimple(pojoItemsSimple);
    }

    private void updatePojo() {
        for (Pojo pojo : pojos) {
            updateFieldList(pojo);
        }
    }

    private void updateFieldList(Pojo pojo) {
        List<PojoItem> pojoItemsSimple = new ArrayList<>();
        List<PojoItem> pojoItemsGenric = new ArrayList<>();

        for (PojoItem pojoItem : pojo.getFieldsList()) {
            if (!pojoItem.isPrimitif()) {
                Pojo fildPojo = findPojo(pojoItem.getPojoItemType());
                if (fildPojo != null) {
                    pojoItem.setPojo(fildPojo);
                    pojoItemsGenric.add(pojoItem);
                } else {
                    pojoItemsSimple.add(pojoItem);
                    pojoItem.setPrimitif(true);
                    pojoItemsSimple.add(pojoItem);
                    pojoItem.setPojoItemType(pojoItem.getPojoItemType());
                }
            }
        }
        pojo.getFieldsListSimple().addAll(pojoItemsSimple);
        pojo.setFieldsListGenric(pojoItemsGenric);
    }

    private Pojo findPojo(String className) {
        Pojo pojo = null;
        List<Pojo> pojos = getPojos();
        for (int i = 0; i < pojos.size(); i++) {
            if (pojos.get(i).getPojoName().equals(className)) {
                pojo = pojos.get(i);
            }
        }
        return pojo;
    }

    private String listNameType(Field field) {
        String chaine = field.getGenericType().getTypeName();
        int insex = LastPointIndex(chaine);
       
        if (field.getType().getSimpleName().equals("List")) {
            return chaine.substring(insex+1, chaine.toCharArray().length - 1);
        }
        return chaine.substring(insex+1, chaine.toCharArray().length);
    }

    private void findGetterAndSetterMethods(Pojo pojo) throws Exception {

        List<Method> getterMethods = ReflexivityGetterAndSetter.getGetterList(pojo.getClazz());
        List<Method> setterMethods = ReflexivityGetterAndSetter.getSetterList(pojo.getClazz());

        pojo.setGetters(getterMethods);
        pojo.setSetters(setterMethods);

    }

    private void setRefenceAndTypeReference(Pojo pojo) {
        List<Field> fields = pojo.getFieldsSimple();
        for (Field field : fields) {
            if (field.getName().equals(PojoConfig.getReference())) {
                pojo.setReferenceName(field.getName());
                pojo.setReferenceType(field.getType().getSimpleName());
            }

        }
    }

    private void setFieldsSimpleMinMaxAndSimple(Pojo pojo) {
        List<Field> fields = pojo.getFieldsSimple();
        List<Field> fieldsSimpleMinMax = new ArrayList<>();
        List<Field> fieldsSimpleStringBolean = new ArrayList<>();
        for (Field field : fields) {
            if (isNumberOrDate(field.getType().getSimpleName())) {
                fieldsSimpleMinMax.add(field);
            } else {
                fieldsSimpleStringBolean.add(field);
            }
        }
        if (!fieldsSimpleMinMax.isEmpty()) {
            pojo.setFieldsSimpleNumberOrDate(fieldsSimpleMinMax);
        }
        if (!fieldsSimpleStringBolean.isEmpty()) {
            pojo.setFieldsSimpleStringOrBoolean(fieldsSimpleStringBolean);
        }

    }

    @Override
    public List<Pojo> getPojos() {
        return pojos;
    }

    @Override
    public List<Pojo> getPojosConfig() {
        return pojosConfig;
    }

    private boolean isNumberOrDate(String typeName) {
        List<String> types = Arrays.asList("int", "Integer", "float", "Float", "double", "Double", "long", "Long", "BigDecimal", "Date");
        return types.contains(typeName);
    }

    private boolean isPrimitif(String typeName) {
        List<String> types = Arrays.asList("boolean", "Boolean", "String", "int", "Integer", "float", "Float", "double", "Double", "long", "Long", "BigDecimal", "Date");
        return types.contains(typeName);
    }

}
