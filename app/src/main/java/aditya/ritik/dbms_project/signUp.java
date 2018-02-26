package aditya.ritik.dbms_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signUp extends AppCompatActivity {

    Button signUp;
    EditText id,pass,pass_con;
    LoginDataBaseAdapter loginDataBaseAdapter;
    @Override
    public void onBackPressed() {
        finish();overridePendingTransition(R.anim.fade_in,0);
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        id=(EditText)findViewById(R.id.id);id.setText(getIntent().getStringExtra("id"));
        pass=(EditText)findViewById(R.id.pass);pass.setText(getIntent().getStringExtra("pass"));
        pass_con=(EditText)findViewById(R.id.pass_con);
        signUp=(Button)findViewById(R.id.signIN);
        signUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:signUp.setBackground(getResources().getDrawable(R.drawable.button));break;
                    case MotionEvent.ACTION_UP:signUp.setBackground(getResources().getDrawable(R.drawable.button_pressed));
                        String userName=id.getText().toString();
                        String password=pass.getText().toString();
                        String confirmPassword=pass_con.getText().toString();
                        if(userName.equals("")||password.equals("")||confirmPassword.equals(""))
                        {Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();}
                        else if(!password.equals(confirmPassword))
                        {Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();}
                        else
                        {
                            loginDataBaseAdapter.insertEntry(userName, password);
                            Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        break;
                }return false;}});
    }
}
