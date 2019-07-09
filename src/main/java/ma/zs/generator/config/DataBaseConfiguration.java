/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.config;

/**
 *
 * @author YOUNES
 */
public class DataBaseConfiguration {

    private static String dbLocalName = "covoiturage";
    private static String dbRemoteName = "";
    private static String dbLocalServer = "127.0.0.1";
    private static String dbRemoteServer = "";
    private static String dbLocalUser = "root";
    private static String dbRemoteUser = "";
    private static String dbLocalPass = "";
    private static String dbRemotePass = "";
    private static int local = 1;

    public static String getDbLocalName() {
        return dbLocalName;
    }

    public static String getDbRemoteName() {
        return dbRemoteName;
    }

    public static String getDbLocalServer() {
        return dbLocalServer;
    }

    public static String getDbRemoteServer() {
        return dbRemoteServer;
    }

    public static String getDbLocalUser() {
        return dbLocalUser;
    }

    public static String getDbRemoteUser() {
        return dbRemoteUser;
    }

    public static String getDbLocalPass() {
        return dbLocalPass;
    }

    public static String getDbRemotePass() {
        return dbRemotePass;
    }

    public static int getLocal() {
        return local;
    }

}
