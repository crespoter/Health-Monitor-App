package com.crespoter.healthangel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class DoctorMain extends AppCompatActivity {
    public Sessions sessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sessions = new Sessions(getApplicationContext());
        final DrawerLayout dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        loadDetails load = new loadDetails();
        load.execute(sessions.getId());
        setContentView(R.layout.doctor_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(sessions.getName());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });

    }
    private class loadDetails extends AsyncTask<String,String,String>
    {
        ArrayList<Patient> patients = new ArrayList<>();

        @Override
        protected void onPostExecute(String s) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
            recyclerView.setLayoutManager(gridLayoutManager);
            PatientViewerAdapter patientAdapter = new PatientViewerAdapter(patients,getApplicationContext());
            recyclerView.setAdapter(patientAdapter);
        }

        @Override
        protected String doInBackground(String... params) {
            String sendingUrl =NetworkDetails.getDoctorsPatientsDetails + "?doc_id="+params[0];
            String inputLine = null;
            String jsonResponse = "";
            try {
                URL url = new URL(sendingUrl);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                while((inputLine = reader.readLine()) != null)
                {
                    jsonResponse += inputLine;
                };
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Log.d("JSON", "onPostExecute: \n" +jsonResponse);
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray jsonPatientList = jsonObject.getJSONArray("patients");
                for(int i=0;i<jsonPatientList.length();i++)
                {
                    JSONObject row = jsonPatientList.getJSONObject(i);
                    patients.add(new Patient(row.getString("name"),row.getString("age"),row.getString("sex"),
                            row.getString("temperature"),row.getString("pulse"),row.getString("id")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
