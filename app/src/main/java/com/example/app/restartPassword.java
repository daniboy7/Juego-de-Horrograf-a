package com.example.app;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import at.markushi.ui.CircleButton;
import cz.msebera.android.httpclient.Header;


public class restartPassword extends AppCompatActivity {
    private CircleButton btnLogin;

    Session session;
    String destinatario;
    String mensaje;
    String correo;
    String contraseña;
    String asunto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart_password);



        final EditText correoT = (EditText)findViewById(R.id.restartEmail);
        final EditText contraseñaT = (EditText)findViewById(R.id.restartPassword);
        Button btnRestart = (Button)findViewById(R.id.btnRestartPassword);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinatario = correoT.getText().toString();
                contraseña = contraseñaT.getText().toString();
                asunto = "Restablecer contraseña";
                mensaje = "Estimado usuario se restableció su contraseña, su contraseña es "+contraseña;


                restart(destinatario,contraseña);
                new SendMailAsync(restartPassword.this,destinatario,asunto,mensaje).execute();

                Intent i = new Intent(restartPassword.this, MainActivity.class);
                restartPassword.this.startActivity(i);
                restartPassword.this.finish();
            }
        });

        btnLogin = (CircleButton)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(getApplicationContext(),MainActivity.class);
                restartPassword.this.startActivity(intentLogin);
                restartPassword.this.finish();
            }
        });
    }

    private void restart(String destinatario, String contraseña) {
        String url ="http://horrography.000webhostapp.com/restablecerContraseña.php?";
        String parametros ="email="+destinatario+"&password="+contraseña;
        StringRequest stringRequest = new StringRequest(url + parametros,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(restartPassword.this,"Contraseña cambiada",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue cola = Volley.newRequestQueue(restartPassword.this);
        cola.add(stringRequest);
    }


    public class SendMailAsync extends AsyncTask<Void,Void,Void>{
        private Context context;
        private Session session;
        private String email;
        private String subject;
        private String message;

        public SendMailAsync(Context context, String email, String subject, String message){
            this.context = context;
            this.email = email;
            this.subject = subject;
            this.message = message;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Properties props = new Properties();
            props.put("mail.smtp.host","smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port","465");
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth","true");
            props.put("mail.smtp.port","465");

            session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("horrografia.app@gmail.com","d(zgtg(MiqmtUzfTPjPY");
                }
            });

            try{
                MimeMessage mm = new MimeMessage(session);
                mm.setFrom(new InternetAddress("horrografia.app@gmail.com"));
                mm.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
                mm.setSubject(subject);
                mm.setText(message);
                Transport.send(mm);

            }catch (MessagingException e){
                e.printStackTrace();
            }
            return null;
        }
    }



}
