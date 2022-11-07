package orioltorner.ioc.easyrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private EditText mTextEmail;
    private EditText mPassword;
    Button buttonLogin;
    private TextView mRegisterText;
    boolean isValid;

    //Test ping
    Button bttnPing;

    String userEmail, userPassword;

    final String HOST = "http://192.168.56.1:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextEmail = findViewById(R.id.textEmail);
        mPassword = findViewById(R.id.textPassword);
        buttonLogin = findViewById(R.id.button_login);
        mRegisterText = findViewById(R.id.registerText);

        bttnPing = findViewById(R.id.bttnPing);

        mRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userEmail = mTextEmail.getText().toString();
                userPassword = mPassword.getText().toString();

                boolean loginOk = false;
                loginOk = loginRequest();
                System.out.println(loginOk);

                if (loginOk) {

                }
            }
        });

    }

    public boolean loginRequest() {
        String URL = HOST.concat("/user/login");
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", userEmail);
            jsonBody.put("password", userPassword);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_RESPONSE", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String token = (String) jsonObject.get("token");
                        String role = (String) jsonObject.get("role");
                        System.out.println(isValid);
//                    isValid = true;
//                    System.out.println(isValid);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(MainActivity.this, VPC.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_RESPONSE", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    String json = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    try {
                        json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        System.out.println(json);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isValid;
    }
}