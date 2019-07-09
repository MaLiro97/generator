/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.pojo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author ismail
 */
public class Pojo {

    private String pojoName;
    private String idName;
    private String idType;
    private String referenceName;
    private String referenceType;
    private Class clazz;
    private List<Method> getters;
    private List<Method> setters;
    private List<Field> fields;
    private List<Field> fieldsGeneric;
    private List<Field> fieldsSimple;
    private List<Field> fieldsSimpleNumberOrDate;
    private List<Field> fieldsSimpleStringOrBoolean;
    private List<PojoItem> fieldsList;
    private List<PojoItem> fieldsListSimple;
    private List<PojoItem> fieldsListGenric;

    public Pojo() {
    }

    public Pojo(String pojoName, String idName, String idType, Class clazz) {
        this.pojoName = pojoName;
        this.idName = idName;
        this.idType = idType;
        this.clazz = clazz;
    }

    public Pojo(String pojoName, String idName, String idType, String referenceName, String referenceType) {
        this.idName = idName;
        this.idType = idType;
        this.referenceName = referenceName;
        this.referenceType = referenceType;
        this.pojoName = pojoName;
    }

//    public Pojo(String pojoName, String idName, String idType) {
//        this.idName = idName;
//        this.idType = idType;
//        this.pojoName = pojoName;
//    }
    public List<Field> getFieldsGeneric() {
        return fieldsGeneric;
    }

    public void setFieldsGeneric(List<Field> fieldsGeneric) {
        this.fieldsGeneric = fieldsGeneric;
    }

    public List<Field> getFieldsSimple() {
        return fieldsSimple;
    }

    public void setFieldsSimple(List<Field> fieldsSimple) {
        this.fieldsSimple = fieldsSimple;
    }

    public List<PojoItem> getFieldsList() {
        return fieldsList;
    }

    public void setFieldsList(List<PojoItem> fieldsList) {
        this.fieldsList = fieldsList;
    }

    public List<Method> getGetters() {
        return getters;
    }

    public void setGetters(List<Method> getters) {
        this.getters = getters;
    }

    public List<Method> getSetters() {
        return setters;
    }

    public void setSetters(List<Method> setters) {
        this.setters = setters;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getPojoName() {
        return pojoName;
    }

    public void setPojoName(String pojoName) {
        this.pojoName = pojoName;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public List<Field> getFieldsSimpleNumberOrDate() {
        return fieldsSimpleNumberOrDate;
    }

    public void setFieldsSimpleNumberOrDate(List<Field> fieldsSimpleNumberOrDate) {
        this.fieldsSimpleNumberOrDate = fieldsSimpleNumberOrDate;
    }

    public List<Field> getFieldsSimpleStringOrBoolean() {
        return fieldsSimpleStringOrBoolean;
    }

    public void setFieldsSimpleStringOrBoolean(List<Field> fieldsSimpleStringOrBoolean) {
        this.fieldsSimpleStringOrBoolean = fieldsSimpleStringOrBoolean;
    }

    public List<PojoItem> getFieldsListSimple() {
        return fieldsListSimple;
    }

    public void setFieldsListSimple(List<PojoItem> fieldsListSimple) {
        this.fieldsListSimple = fieldsListSimple;
    }

    public List<PojoItem> getFieldsListGenric() {
        return fieldsListGenric;
    }

    public void setFieldsListGenric(List<PojoItem> fieldsListGenric) {
        this.fieldsListGenric = fieldsListGenric;
    }

    @Override
    public String toString() {
        return "Pojo{" + "pojoName=" + pojoName + ", idName=" + idName + ", idType=" + idType + ", referenceName=" + referenceName + ", referenceType=" + referenceType + ", clazz=" + clazz + ", getters=" + getters + ", setters=" + setters + ", fields=" + fields + ", fieldsGeneric=" + fieldsGeneric + ", fieldsSimple=" + fieldsSimple + ", fieldsSimpleNumberOrDate=" + fieldsSimpleNumberOrDate + ", fieldsSimpleStringOrBoolean=" + fieldsSimpleStringOrBoolean + ", fieldsList=" + fieldsList + ", fieldsListSimple=" + fieldsListSimple + ", fieldsListGenric=" + fieldsListGenric + '}';
    }

}
