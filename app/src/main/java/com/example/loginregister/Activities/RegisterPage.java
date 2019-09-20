package com.example.loginregister.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.loginregister.Database.DBHelper;
import com.example.loginregister.Functions.Encrypt;
import com.example.loginregister.Objects.Data;
import com.example.loginregister.R;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterPage extends Activity {
    @BindView(R.id.inputRegid)
    EditText inputRegid;
    @BindView(R.id.inputRegpass)
    EditText inputRegpass;
    @BindView(R.id.radioFB)
    RadioButton radioFB;
    @BindView(R.id.radioIG)
    RadioButton radioIG;
    @BindView(R.id.radioTW)
    RadioButton radioTW;

    KeyGenerator keyGenerator;
    SecretKey secretKey;
    byte[] IV = new byte[16];
    byte[] cipherid;
    byte[] cipherpass;
    SecureRandom random;
    String ID, encryptedPass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DBHelper databaseHelper;

   // public static final String MY_SHARED = "MySharedPreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        ButterKnife.bind(this);

        //sharedPreferences = getSharedPreferences(MY_SHARED, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        databaseHelper=new DBHelper(this);
    }

    @OnClick(R.id.regBtn)
    public void regBtnClick() {
                try {
                    keyGenerator = KeyGenerator.getInstance("AES");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                keyGenerator.init(256);
                secretKey = keyGenerator.generateKey();
                byte[] encodedSecretKey = secretKey.getEncoded();
                String strSecretKey = encoderfun(encodedSecretKey);
                random = new SecureRandom();
                random.nextBytes(IV);
                String strIV = encoderfun(IV);
                try {
                    cipherpass = Encrypt.encrypt(inputRegpass.getText().toString().trim().getBytes(), secretKey, IV);//Girilen metin şifrenin şifrelenmesi

                } catch (Exception e) {
                    e.printStackTrace();
                }
                ID = inputRegid.getText().toString().trim();
                encryptedPass = encoderfun(cipherpass);
                String checkedRadio=checkRadio();
                boolean x=false;
//Veri tabanı indeksi 1 den başladığı için sayacı 1 den başlatarak içerisindeki kullanıcı adı ve platforma göre eşleşme sağlaması durumunda "update" aksi durumda yeni kayıt işlemi yapılıyor.
                for (int k=1;k<databaseHelper.getDataCount();k++){
                    if(ID.equals(databaseHelper.getData(k).getUsername()) && checkedRadio.equals(databaseHelper.getData(k).getPlatform())){
                        updateData(Integer.toString(k),encryptedPass,strSecretKey,strIV);
                        x=true;
                        Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        x=false;//update işlemi yapılmaz ise false değer alarak yeni ekleme işlemi yapılacak.
                    }
                }
                if (x==false){
                    dataToDB(Data.COLUMN_PLATFORM,Data.COLUMN_USERNAME,Data.COLUMN_PASSWORD,Data.COLUMN_SCKEY,Data.COLUMN_IVKEY,
                            checkedRadio,ID,encryptedPass,strSecretKey,strIV);
                    Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_SHORT).show();
                }
    }

    public void dataToDB(String column1,String column2,String column3,String column4,String column5,
                         String entry1,String entry2,String entry3,String entry4,String entry5){
        long addData=databaseHelper.addData(column1,column2,column3,column4,column5,
                entry1,entry2,entry3,entry4,entry5);
        }

//http://www.codebind.com/android-tutorials-and-examples/android-sqlite-tutorial-example/
     public boolean updateData(String s1,String s2,String s3,String s4){
        boolean isUpdate=databaseHelper.updateData(s1,s2,s3,s4);
        if (isUpdate==true)
            return true;
        else
            return false;
     }
//Seçili platforma göre veritabanına kaydetmek için kontrol.
     public String checkRadio(){
        if (radioFB.isChecked()){
            return new String("Facebook");
        }
        if (radioIG.isChecked()){
            return new String("Instagram");
        }
        if (radioTW.isChecked()){
            return new String("Twitter");
        }
        return null;
     }

    public static String encoderfun(byte[] decval) {
        String conVal= Base64.encodeToString(decval,Base64.DEFAULT);
        return conVal;
    }
}

