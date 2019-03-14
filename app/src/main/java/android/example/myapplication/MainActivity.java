package android.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private EditText Email;
    private SharedPreferences sharedPreferences;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.layout_register);
        setContentView(R.layout.layout_login);
        //setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("data",Context.MODE_PRIVATE);
    }
    public  void  btnLogin(View view){
        String email = ((TextView)findViewById(R.id.email)).getText().toString();
        String password = ((TextView)findViewById(R.id.password)).getText().toString();
        int number = sharedPreferences.getInt("number",-1);
        for(int i=0;i<=number;i++){
            if(email.equals(sharedPreferences.getString("email"+i,""))){
                if(password.equals(sharedPreferences.getString("pass"+i,""))){
                    setContentView(R.layout.activity_main);
                    return;
                }else {
                    Toast.makeText(this,"Wrong password",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        Toast.makeText(this,"No exist",Toast.LENGTH_LONG).show();
    }
    public  void btnLogout(View view){
        setContentView(R.layout.layout_login);
    }
    public void btnLinkclick(View view){
        setContentView(R.layout.layout_login);
    }
    public void btnRegister(View view){
        setContentView(R.layout.layout_register);
    }
    public void btnRegisterclick(View view){
        MyTask t= new MyTask();
        t.execute();
    }


    public class MyTask extends AsyncTask<String, Integer, String> {
        String name;
        String pass;
        String email;
        Button link;

        @Override
        protected void onPreExecute() {
            Name=(EditText) findViewById(R.id.name);
            Password=(EditText)findViewById(R.id.password);
            Email=(EditText)findViewById(R.id.email);
            name=Name.getText().toString().trim();
            pass=Password.getText().toString().trim();
            email = Email.getText().toString().trim();
            link = (Button)findViewById(R.id.btnLinkToLoginScreen);
        }

        @Override
        protected String doInBackground(String... params) {
            if(name.equals("")|pass.equals("")|email.equals("")){
                return "Nothing";
            } else {
                if(sharedPreferences.getString("email0","").equals("")){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("name"+0,name);
                    editor.putString("pass"+0,pass);
                    editor.putString("email"+0,email);
                    editor.putInt("number",0);
                    editor.commit();
                    return "victory";
                }else {
                    int number = sharedPreferences.getInt("number", -1);
                    int number1=number+1;
                    for (int i = 0; i <= number; i++) {
                        if (email.equals(sharedPreferences.getString("email" + i, ""))) {
                            return "exist";
                        }
                    }
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("name"+number1,name);
                    editor.putString("pass"+number1,pass);
                    editor.putString("email"+number1,email);
                    editor.putInt("number",number1);
                    editor.commit();
                    return "victory";
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            link.setText(result);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    link.setText("Log into your account");
                }
            },1000);
        }
    }

}
