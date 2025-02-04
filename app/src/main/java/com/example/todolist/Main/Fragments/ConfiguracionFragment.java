package com.example.todolist.Main.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.todolist.Main.Arquitectura.ViewModel;
import com.example.todolist.Main.SharedPreferencesHelper;
import com.example.todolist.databinding.FragmentConfiguracionBinding;

public class ConfiguracionFragment extends Fragment {

    FragmentConfiguracionBinding binding;
    SharedPreferencesHelper helper;
    ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentConfiguracionBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        helper = new SharedPreferencesHelper(getContext());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(helper.devolverActivaAlerta()){
            binding.activacionColores.setChecked(true);
            binding.diasMedia.setEnabled(true);
            binding.colorMedia.setEnabled(true);
            binding.diasUrgente.setEnabled(true);
            binding.colorUrgente.setEnabled(true);
        }else{
            binding.activacionColores.setChecked(false);
            binding.diasMedia.setEnabled(false);
            binding.colorMedia.setEnabled(false);
            binding.diasUrgente.setEnabled(false);
            binding.colorUrgente.setEnabled(false);
        }

        binding.diasUrgente.setText(String.valueOf(helper.devolverUrgente()));
        binding.colorUrgente.setText(helper.devolverColorUrgente());

        binding.diasMedia.setText(String.valueOf(helper.devolverMedia()));
        binding.colorMedia.setText(helper.devolverColorMedia());

        binding.guardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.activacionColores.isChecked()){
                    helper.guardarActivaAlerta(true);
                }else{
                    helper.guardarActivaAlerta(false);
                }

                if(binding.activacionColores.isChecked()){
                    helper.guardarUrgente(Integer.parseInt(String.valueOf(binding.diasUrgente.getText())));
                    helper.guardarMedia(Integer.parseInt(String.valueOf(binding.diasMedia.getText())));
                    helper.guardarColorUrgente(String.valueOf(binding.colorUrgente.getText()));
                    helper.guardarColorMedia(String.valueOf(binding.colorMedia.getText()));
                }
            }
        });

        binding.activacionColores.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binding.diasMedia.setEnabled(true);
                    binding.colorMedia.setEnabled(true);
                    binding.diasUrgente.setEnabled(true);
                    binding.colorUrgente.setEnabled(true);
                }else{
                    binding.diasMedia.setEnabled(false);
                    binding.colorMedia.setEnabled(false);
                    binding.diasUrgente.setEnabled(false);
                    binding.colorUrgente.setEnabled(false);
                }
            }
        });
    }
}