/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.bean;

/**
 *
 * @author ismail
 */
public class BeanEngineConfig {

    private static final String annotationMappingListOpen = "@OneToMany(mappedBy =\"";
    private static final String annotationMappingListClose = "\")";
    private static final String annotationMappingObject = "@ManyToOne";
    private static final String annotationId = "@Id";
    private static final String autoIncrement = "@GeneratedValue(strategy = GenerationType.AUTO)\n";
    private static final String annotationClass = "@Entity";
    private static final String packageName = "bean";
    private static final String packagePath = "bean\\";
    private static final String annotationDate = "@Temporal(javax.persistence.TemporalType.DATE)";

    public static String getAnnotationMappingListOpen() {
        return annotationMappingListOpen;
    }

    public static String getAutoIncrement() {
        return autoIncrement;
    }

    public static String getAnnotationMappingListClose() {
        return annotationMappingListClose;
    }

    public static String getAnnotationMappingObject() {
        return annotationMappingObject;
    }

    public static String getAnnotationId() {
        return annotationId;
    }

    public static String getAnnotationClass() {
        return annotationClass;
    }

    public static String getPackageName() {
        return packageName;
    }

    public static String getPackagePath() {
        return packagePath;
    }

    public static String getAnnotationDate() {
        return annotationDate;
    }

    
    
}
