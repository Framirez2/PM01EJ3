package com.example.pm01ej3.Tablas;

public class Transacciones {
    //Nombre de la base de datos
    public static final String NameDatabase = "PM01DB";

    /* Creacion de las tablas de la BD */
    public static final String TbPersonas = "personas";

    /* Campos de la tabla personas */
    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = "edad";
    public static final String correo = "correo";
    public static final String dir = "dir";

    // DDL
    public static final String CTPersonas = "CREATE TABLE personas (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " nombres TEXT, apellidos TEXT, edad INTEGER, correo TEXT, dir TEXT)";

    public static final String GetPersonas = "SELECT * FROm " + Transacciones.TbPersonas;

    public static final String DropTPersona = "DROP TABLE IF EXISTS personas";

}
