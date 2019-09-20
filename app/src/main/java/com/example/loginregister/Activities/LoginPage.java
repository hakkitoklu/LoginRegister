package com.example.loginregister.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.loginregister.Database.DBHelper;
import com.example.loginregister.Functions.decoder;
import com.example.loginregister.R;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPage extends Activity {
    @BindView(R.id.loginid)
    EditText loginid;
    @BindView(R.id.loginpass)
    EditText loginpass;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.gotoregister)
    Button gotoregister;
    @BindView(R.id.logradioFB)
    RadioButton loginradioFB;
    @BindView(R.id.logradioIG)
    RadioButton logradioIG;
    @BindView(R.id.logradioTW)
    RadioButton logradioTW;

    String decryptedPass,SPID,SPdecryptedPass;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ButterKnife.bind(this);
        dbHelper=new DBHelper(this);

        loginid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((loginid.getText().toString().trim().isEmpty())) {
                            ArrayList<String> names=new ArrayList<>();
                            for (int k=1;k<dbHelper.getDataCount();k++){
                                names.add(dbHelper.getData(k).getUsername());
                            }
                            loginid.setText(SPID);
                            loginpass.setText(SPdecryptedPass);
                }
            }
        });

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texid=loginid.getText().toString().trim();
                String textpass=loginpass.getText().toString().trim();
                String checkedradio=checkRadio();
                boolean x=false;

//Veri tabanı içerisindeki verileri id bazlı gezerek kontrol işlemine sokuyor.
                for(int k=1;k<dbHelper.getDataCount();k++){
//Her girdiye ait verileri sırasıyla alarak decrypt işlemine sokuyot ve login için girilecek veri ile karşılaştırmaya hazır hale getiriyor
                    decryptedPass= decoder.dec(dbHelper.getData(k).getPassword(),dbHelper.getData(k).getSecretkey(),dbHelper.getData(k).getIv());
//Decrypt edilen verilel girdi olarak alınan veriler ile karşılaştırılıyor.
                    if((dbHelper.getData(k).getUsername().equals(texid)) && (decryptedPass.equals(textpass) && dbHelper.getData(k).getPlatform().equals(checkedradio))){
                        Toast.makeText(getApplicationContext(),"Login Successfull!",Toast.LENGTH_SHORT).show();
                        x=true;//Uyuşma sağlanıyorsa başarılı uyarısı verilir ve true parametresi başarısız girişte karşılaştırmaya sokmak için atanır
                    }
                }
                if (x==false)  // giriş başarısız olduğu durumda failed uyarısı verilir.
                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.gotoregister)
    public void gotoregisterClick() {
        Intent go = new Intent(LoginPage.this, RegisterPage.class);
        startActivity(go);
    }

    public String checkRadio(){
        if (loginradioFB.isChecked()){
            return new String("Facebook");
        }
        if (logradioIG.isChecked()){
            return new String("Instagram");
        }
        if (logradioTW.isChecked()){
            return new String("Twitter");
        }
        return null;
    }


}