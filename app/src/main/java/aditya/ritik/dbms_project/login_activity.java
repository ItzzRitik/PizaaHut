package aditya.ritik.dbms_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class login_activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Button signIN,google;
    EditText id,pass;
    TextView create;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 100;
    LoginDataBaseAdapter loginDataBaseAdapter;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        id=(EditText)findViewById(R.id.id);
        pass=(EditText)findViewById(R.id.pass);
        signIN=(Button)findViewById(R.id.signIN);
        signIN.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:signIN.setBackground(getResources().getDrawable(R.drawable.button));break;
                    case MotionEvent.ACTION_UP:signIN.setBackground(getResources().getDrawable(R.drawable.button_pressed));
                        overridePendingTransition(R.anim.fade_in,0);
                        String userName=id.getText().toString();
                        String password=pass.getText().toString();
                        String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);
                        if(password.equals(storedPassword))
                        {
                            Intent userdesk = new Intent(login_activity.this, user_desk.class);
                            login_activity.this.startActivity(userdesk);
                        }
                        else
                        {
                            Toast.makeText(login_activity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                        }
                        break;
                }return false;}});


        google=(Button)findViewById(R.id.google);
        google.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:google.setBackground(getResources().getDrawable(R.drawable.button));break;
                    case MotionEvent.ACTION_UP:google.setBackground(getResources().getDrawable(R.drawable.button_google));signIn();break;
                }return false;}});

        id=(EditText) findViewById(R.id.id);
        pass=(EditText)findViewById(R.id.pass);

        create=(TextView)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(login_activity.this, signUp.class);
                signUpIntent.putExtra("id",id.getText().toString());
                signUpIntent.putExtra("pass",pass.getText().toString());
                login_activity.this.startActivity(signUpIntent);
                overridePendingTransition(R.anim.fade_in,0);
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this ,this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}
    private void signIn()
    {Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);startActivityForResult(signInIntent, RC_SIGN_IN);}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {GoogleSignInAccount acct = result.getSignInAccount();
                String valid_ID=loginDataBaseAdapter.getSinlgeEntry(acct.getEmail());
                if(!valid_ID.equals("NOT EXIST"))
                {
                    Intent userdesk = new Intent(login_activity.this, user_desk.class);
                    login_activity.this.startActivity(userdesk);
                }
                else
                {
                    Toast.makeText(login_activity.this, "User Account Doesn't Exists", Toast.LENGTH_LONG).show();
                }
            } else {Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();}
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        }
    }
}
