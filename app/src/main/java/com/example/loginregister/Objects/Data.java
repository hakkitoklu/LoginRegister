package com.example.loginregister.Objects;

public class Data {
    public static final String TABLE_NAME="users";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_USERNAME="username";
    public static final String COLUMN_PASSWORD="password";
    public static final String COLUMN_SCKEY="sckey";
    public static final String COLUMN_IVKEY="ivkey";
    public static final String COLUMN_PLATFORM="platform";

    private int id;
    private String platform;
    private String username;
    private String password;
    private String secretkey;
    private String iv;

    public static final String CREATE_TABLE="CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PLATFORM + " TEXT, "
            + COLUMN_USERNAME + " TEXT, "
            + COLUMN_PASSWORD + " TEXT, "
            + COLUMN_SCKEY + " TEXT, "
            + COLUMN_IVKEY + " TEXT " + ")";

    public Data(){
    }

    public Data(int id, String platform, String username, String password, String secretkey, String iv){
        super();
        this.id=id;
        this.platform=platform;
        this.username=username;
        this.password=password;
        this.secretkey=secretkey;
        this.iv=iv;
    }
    public int getId(){
        return id;
    }
    public String getPlatform(){
        return platform;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getSecretkey(){
        return secretkey;
    }
    public String getIv(){
        return iv;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setPlatform(String platform){
        this.platform=platform;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setSecretkey(String secretkey){
        this.secretkey=secretkey;
    }
    public void setIv(String iv){
        this.iv=iv;
    }
}
