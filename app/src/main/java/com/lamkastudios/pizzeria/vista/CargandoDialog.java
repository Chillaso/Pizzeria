package com.lamkastudios.pizzeria.vista;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.lamkastudios.pizzeria.R;

public class CargandoDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_cargando,null));

        return builder.create();
    }
}
