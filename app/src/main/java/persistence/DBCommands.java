package persistence;

public class DBCommands {

    static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE IF NOT EXISTS users (_id BIGINT PRIMARY KEY,prename varchar(255),surname varchar(255),username varchar(255),password varchar(255))";

    static final String SQL_CREATE_MOCKDATA1 =
            "INSERT INTO users VALUES ('0','Carina','Arnberger','carina.arnberger@gmail.com','3dd28c5a23f780659d83dd99981e2dcb82bd4c4bdc8d97a7da50ae84c7a7229a6dc0ae8ae4748640a4cc07ccc2d55dbdc023a99b3ef72bc6ce49e30b84253dae');";
    static final String SQL_CREATE_MOCKDATA0 =
            "INSERT INTO users VALUES ('1','Franz','Sagaischek','f.sagaischek@gmx.at','3dd28c5a23f780659d83dd99981e2dcb82bd4c4bdc8d97a7da50ae84c7a7229a6dc0ae8ae4748640a4cc07ccc2d55dbdc023a99b3ef72bc6ce49e30b84253dae');";
    static final String SQL_CREATE_MOCKDATA7 =
            "INSERT INTO days VALUES ('5','2023-03-04','123','0','0','1','0');";
    static final String SQL_CREATE_MOCKDATA2 =
            "INSERT INTO days VALUES ('0','2023-03-03','122','0','0','1','0');";
    static final String SQL_CREATE_MOCKDATA3 =
            "INSERT INTO days VALUES ('1','2023-03-02','1235','0','0','1','0');";
    static final String SQL_CREATE_MOCKDATA4 =
            "INSERT INTO days VALUES ('2','2023-03-01','4245','0','1','0','0');";
    static final String SQL_CREATE_MOCKDATA5 =
            "INSERT INTO days VALUES ('3','2023-01-28','345','0','0','0','0');";
    static final String SQL_CREATE_MOCKDATA6 =
            "INSERT INTO days VALUES ('4','2023-02-17','2340','0','1','0','0');";

    static final String SQL_CREATE_ENTRIES_DAYS =
            "CREATE TABLE IF NOT EXISTS days (_id BIGINT AUTO_INCREMENT PRIMARY KEY,actual_date date,steps BIGINT,steps_start BIGINT ,active bool,attack bool,user_id BIGINT,FOREIGN KEY(user_id) REFERENCES users(id))";

    static final String SQL_INSERT_USER = "INSERT INTO user VALUES(nextval('id_user')," +
            "prename,surname,username,password";


}
