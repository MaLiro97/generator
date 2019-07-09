/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.pojo;

import java.lang.reflect.Field;

/**
 *
 * @author A O
 */
public class PojoItem {

    private Field field;
    private Pojo pojo;
    private String pojoItemType;
    private boolean primitif;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Pojo getPojo() {
        return pojo;
    }

    public void setPojo(Pojo pojo) {
        this.pojo = pojo;
    }

    public boolean isPrimitif() {
        return primitif;
    }

    public void setPrimitif(boolean isPrimitif) {
        this.primitif = isPrimitif;
    }

    public String getPojoItemType() {
        return pojoItemType;
    }

    public void setPojoItemType(String pojoType) {
        this.pojoItemType = pojoType;
    }

    @Override
    public String toString() {
        return "PojoItem{" + "field=" + field + ", pojo=" + pojo + ", pojoItemType=" + pojoItemType + ", primitif=" + primitif + '}';
    }
    
    

}
