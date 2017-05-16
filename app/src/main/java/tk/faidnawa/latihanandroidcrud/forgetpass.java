package tk.faidnawa.latihanandroidcrud;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tk.faidnawa.latihanandroidcrud.app.AppConfig;
import tk.faidnawa.latihanandroidcrud.app.AppController;
public class forgetpass extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btcengpass;
    private EditText inputEmail;
    private EditText  inputToken;
    private EditText inputnewpass;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        inputEmail = (EditText) findViewById(R.id.emailfoegetpass);
        btcengpass = (Button) findViewById(R.id.cengpass);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // Login button Click Event
        btcengpass.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                // Check for empty data in the form
                if (!email.isEmpty()) {
                    // login user
                    cekemail(email);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "From tidak boleh kosong !", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

    }



    private void cekemail(final String email) {


        pDialog.setMessage("cek ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_CEKEMAIL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Cek_email Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // Launch main activity
                       // Intent intent = new Intent(forgetpass.this,Crud.class);
                        //startActivity(intent);
                        //finish();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(forgetpass.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_resetpass, null);
                        inputToken = (EditText) mView.findViewById(R.id.ettoken);
                        inputnewpass = (EditText) mView.findViewById(R.id.etnewPassword);
                       // final EditText mToken = (EditText) mView.findViewById(R.id.ettoken);
                        //final EditText mPassword = (EditText) mView.findViewById(R.id.etnewPassword);
                        Button mreset = (Button) mView.findViewById(R.id.btnreset);
                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();
                        mreset.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String seToken = inputToken.getText().toString().trim();
                                String setPassword = inputnewpass.getText().toString().trim();

                                // Check for empty data in the form
                                if (!seToken.isEmpty() && !setPassword.isEmpty()) {
                                    // login user
                                    sereset(seToken, setPassword);
                               // if(!mToken.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()){
                                    //Toast.makeText(forgetpass.this,
                                      //      "login sukses",
                                        //    Toast.LENGTH_SHORT).show();
                                 //   cekreset(mToken, mPassword);
                                   // dialog.dismiss();
                                }else{
                                    Toast.makeText(forgetpass.this,
                                            "isi semua field",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void sereset(final String seToken,final String setPassword) {

        pDialog.setMessage(" loding ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_RESETPASS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "reset pass Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session


                        // Launch main activity
                        Intent intent = new Intent(forgetpass.this,
                                Login2Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("setToken", seToken);
                params.put("updatepassword", setPassword);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    //@Override
    //public void onBackPressed() {


    //}
}
