package com.example.lastprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CodeVerf extends AppCompatActivity {

    private EditText txtLoginId;
    private String verificationCode;
    private Student student;
    private String bzuID;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verf);
        txtLoginId = findViewById(R.id.txtLoginId);
        bzuID=getIntent().getStringExtra("id");
        readUser(bzuID);
    }

    public void buttonSendEmail(View view){
        String stringReceiverEmail = email;
        if (!stringReceiverEmail.isEmpty()) {
            verificationCode = generateVerificationCode();
            sendEmailInBackground(stringReceiverEmail, verificationCode);
        } else {
            Toast.makeText(this, "Please enter a valid receiver email", Toast.LENGTH_SHORT).show();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private void sendVerificationCode(String receiverEmail, String verificationCode) {
        final String stringSenderEmail = "securesurf.asscsurance@gmail.com";
        final String stringPasswordSenderEmail = "utcclwfcwppcwkux";

        String stringHost = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", stringHost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(stringSenderEmail));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            mimeMessage.setSubject("Verification Code");
            mimeMessage.setText("Your verification code is: " + verificationCode);

            Transport.send(mimeMessage);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private void sendEmailInBackground(String receiverEmail, String verificationCode) {
        new SendEmailTask(receiverEmail, verificationCode).execute();
    }

    private class SendEmailTask extends AsyncTask<Void, Void, Void> {
        private String receiverEmail;
        private String verificationCode;

        SendEmailTask(String receiverEmail, String verificationCode) {
            this.receiverEmail = receiverEmail;
            this.verificationCode = verificationCode;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sendVerificationCode(receiverEmail, verificationCode);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Verification code sent to your email", Toast.LENGTH_LONG).show();
        }
    }

    public void buttonValidateCode(View view) {
        String enteredCode = txtLoginId.getText().toString();

        if (enteredCode.equals(verificationCode)) {
            Intent intent = new Intent(this, ResetPass.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Verification code is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    private void readUser(String loginId) {
        String url = "http://10.0.2.2:5000/student/" + loginId;

        RequestQueue queue = Volley.newRequestQueue(CodeVerf.this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = response;

                            String result = jsonArray.getString(0);

                            if (!result.equals(null)) {
                                student=new Student(Integer.parseInt(jsonArray.getString(0)),Integer.parseInt(jsonArray.getString(1)),jsonArray.getString(2),jsonArray.getString(3),jsonArray.getString(4),jsonArray.getString(5));
                                email=student.getEmail();
                                verificationCode = generateVerificationCode();
                                sendEmailInBackground(email, verificationCode);
                            } else {
                                Toast.makeText(CodeVerf.this, "invalid ID", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                    }
                }
        );
        queue.add(request);
    }
}