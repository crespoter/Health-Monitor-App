package com.crespoter.healthangel;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {

    Sessions sessions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessions = new Sessions(getApplicationContext());
        if(sessions.isInitialised())
        {
            //TODO
            //DIRECTLY LOG USER IN
        }
        setContentView(R.layout.activity_main);
        FrameLayout pages = (FrameLayout) findViewById(R.id.pageFrames);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout.Tab patientLoginTab = tabs.newTab();
        patientLoginTab.setText("Patient Login");
        patientLoginTab.setIcon(R.drawable.patient);
        tabs.addTab(patientLoginTab);

        TabLayout.Tab doctorLoginTab = tabs.newTab();
        doctorLoginTab.setText("Doctor Login");
        doctorLoginTab.setIcon(R.drawable.doctor);
        tabs.addTab(doctorLoginTab);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment pageToDisplay = null;
                switch (tab.getPosition()) {
                    case 0:
                        pageToDisplay = new PatientLogin();
                        break;
                    case 1:
                        pageToDisplay = new DoctorLogin();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.pageFrames, pageToDisplay);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabs.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }
}
