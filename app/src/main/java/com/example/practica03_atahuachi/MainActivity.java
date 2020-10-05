package com.example.practica03_atahuachi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SimpleFragment.OnFragmentInteractionListener {
    private Button mButton;
    private boolean isFragmentDisplayed = false;
    static final String FRAGMENT_STATE = "state of Fragment";
    private int mRadioButtonChoice = 2;
    private int votos[] = {R.string.title1, R.string.title2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(FRAGMENT_STATE);

            if (isFragmentDisplayed) {
                mButton.setText(R.string.close);
            }
        }

        mButton = (Button)findViewById(R.id.open_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    displayNewFragment();
                } else {
                    closeFragment();
                }
            }
        });
    }

    public void displayNewFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance(mRadioButtonChoice);
        //TODO: Obtener el FragmentManager e iniciar la transacción
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction
                = fragmentManager.beginTransaction();

        //TODO: Agregar el Fragment
        fragmentTransaction.add(R.id.fragment_container,
                simpleFragment)
                .addToBackStack(null)
                .commit();
        mButton.setText(R.string.close);
        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        SimpleFragment simpleFragment = (SimpleFragment)fragmentManager
                .findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            fragmentManager.beginTransaction()
                    .remove(simpleFragment)
                    .commit();
            mButton.setText(R.string.open);
            isFragmentDisplayed = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putBoolean(FRAGMENT_STATE,
                isFragmentDisplayed);
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public void onRadioButtonChoice(int choice) {
        mRadioButtonChoice = choice;

        if(mRadioButtonChoice == 1) {
            votos[1]++;
        } else {
            votos[0]++;
        }

        if (Integer.parseInt(mayor(votos)) == 0) {
            //Toast.makeText(this,getResources().getString(R.string.title2)+" Tiene "+votos[1]+" votos",Toast.LENGTH_LONG).show();
            //Toast.makeText(this, getResources().getString(R.string.title1)+" Tiene"+votos[0]+"votos",Toast.LENGTH_LONG).show();
            Toast.makeText(this, "El mas votado es: "+getResources().getString(R.string.title1)+" con "+votos[0]+" votos",Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this,getResources().getString(R.string.title2)+" Tiene "+votos[1]+" votos",Toast.LENGTH_LONG).show();
            //Toast.makeText(this, getResources().getString(R.string.title1)+" Tiene"+votos[0]+"votos",Toast.LENGTH_LONG).show();
            Toast.makeText(this, "El mas votado es: "+getResources().getString(R.string.title2)+" con "+votos[1]+" votos",Toast.LENGTH_LONG).show();
        }
    }

    private String mayor(int[] votos) {
        int numayor = votos[0];
        int posicion = 0;

        for (int i = 0; i < 2; i++) {
            if (votos[i] > numayor) {
                posicion = i;
                numayor = votos[i];
            }
        }

        return String.valueOf(posicion);
    }
}