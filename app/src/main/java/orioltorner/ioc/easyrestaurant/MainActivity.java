package orioltorner.ioc.easyrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private EditText mTextEmail;
    private EditText mPassword;
    Button buttonLogin;
    boolean isValid;

    //Test ping
    Button bttnPing;

    String userName = "admin";
    String userPassword = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextEmail = findViewById(R.id.textEmail);
        mPassword = findViewById(R.id.textPassword);
        buttonLogin = findViewById(R.id.button_login);

        bttnPing = findViewById(R.id.bttnPing);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoEmail = mTextEmail.getText().toString();
                String txtPassword = mPassword.getText().toString();

                if (textoEmail.isEmpty() || txtPassword.isEmpty()){

                    Toast.makeText(MainActivity.this, "Siusplau introdueix totes les dades", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    isValid = validate(textoEmail,txtPassword);

                    if(!isValid)
                    {
                        Toast.makeText(MainActivity.this, "Dades incorrectes", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Login correcte", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, orioltorner.ioc.easyrestaurant.VPC.class);
                        startActivity(intent);
                    }
                }
            }
        });

        bttnPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String host = "localhost";

                try
                {
                    InetAddress direccion = InetAddress.getLocalHost();
                    boolean alcanzable = direccion.isReachable(10000);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    //Metode boto login
    public boolean validate(String userId, String password) {

        if(userId.equals(userName) || password.equals(userPassword))
        {
            return true;
        }
        return false;
    }
}