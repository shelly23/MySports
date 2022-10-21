package persistence;

import android.provider.BaseColumns;

public class DBCommands {

    static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS users (\n" +
            "_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
            "prename varchar(255),\n" +
            "surname varchar(255),\n" +
            "username varchar(255),\n" +
            "password varchar(255),\n" +
            "deleted int\n" +
            ")";

    static final String SQL_INSERT_USER = "INSERT INTO user VALUES(nextval('id_user')," +
            "prename,surname,username,password";


}
