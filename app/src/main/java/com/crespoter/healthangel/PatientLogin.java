package com.crespoter.healthangel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static java.net.URLEncoder.encode;

public class PatientLogin extends Fragment implements View.OnClickListener{

    Sessions sessions;

    @Override
    public void onClick(View v) {
        final View fg = getView();
        assert fg != null;
        String name = ((EditText) fg.findViewById(R.id.patientEmail)).getText().toString();
        String password = ((EditText)fg.findViewById(R.id.patientPassword)).getText().toString();
        ValidateEmail val = new ValidateEmail();
        val.execute(name,password);
    }

    private class ValidateEmail extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPostExecute(String s) {
            if("username invalid".equals(s))
            {
                Toast.makeText(getContext(), "Invalid Username", Toast.LENGTH_LONG).show();
            }
            else if("password invalid".equals(s))
            {
                Toast.makeText(getContext(),"Invalid Password",Toast.LENGTH_LONG).show();
            }
            else
            {
                sessions.initialise(s,"patient");
                Intent i = new Intent(getContext(),PatientMain.class);
                getContext().startActivity(i);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            String sendingUrl = NetworkDetails.patientValidation + "?name=" + encode(name).replace("+","%20") +
                    "&password=" + encode(password).replace("+","%20") ;

            String inputLine = null;
            try {
                URL url = new URL(sendingUrl);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                inputLine = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return inputLine;
        }
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        sessions = new Sessions(getContext());
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.patient_login,container,false);
        Button patientSignIn = (Button) x.findViewById(R.id.patientSignInButton);
        patientSignIn.setOnClickListener(this);
        return x;
    }
}
