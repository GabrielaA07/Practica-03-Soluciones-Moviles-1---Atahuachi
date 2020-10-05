package com.example.practica03_atahuachi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class SimpleFragment extends Fragment {
    private static final int ARTICLE01 = 0;
    private static final int ARTICLE02 = 1;

    private static final int NONE = 2;
    public int mRadioButtonChoice = NONE;

    private static final String CHOICE = "choice"; //constante

    OnFragmentInteractionListener mListener;

    interface  OnFragmentInteractionListener {
        //es un metodo qi¿ue recibe un entero, se va a a comunicar con la clase SimpleFragment
        void onRadioButtonChoice(int choice);
    }

    //para prevenir la instanciacion de varias veces el fragmento
    public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE, choice);
        fragment.setArguments(arguments);
        return  fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw  new ClassCastException(context.toString()
                    + getResources().getString(R.string.exception_message));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.activity_fragment_simple, container, false);
        final RadioGroup radioGroup = root.findViewById(R.id.radio_group);

        if (getArguments().containsKey(CHOICE)) {
            mRadioButtonChoice = getArguments().getInt(CHOICE);

            if (mRadioButtonChoice != NONE) {
                radioGroup.check(radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                TextView tv = root.findViewById(R.id.fragment_header);

                switch (index) {
                    case ARTICLE01:
                        mListener.onRadioButtonChoice(ARTICLE01);
                        break;
                    case ARTICLE02:
                        mListener.onRadioButtonChoice(ARTICLE02);
                        break;
                    default:
                        mListener.onRadioButtonChoice(NONE);
                        break;
                }
            }
        });

        return root;
    }
}
