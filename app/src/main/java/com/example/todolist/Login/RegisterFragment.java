package com.example.todolist.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.todolist.Main.MainActivity;
import com.example.todolist.R;
import com.example.todolist.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    NavController navController;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireView());

        binding.volverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        binding.registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.correo.getText().toString().equalsIgnoreCase("")) {
                    binding.correo.setError("El campo no puede estar vacio");
                    return;
                }
                String email = binding.titulo.getText().toString();

                if (binding.contrasena.getText().toString().equalsIgnoreCase("")) {
                    binding.contrasena.setError("El campo no puede estar vacio");
                    return;
                }
                String contrasena = binding.contrasena.getText().toString();

                if (binding.repetirContrasena.getText().toString().equalsIgnoreCase("")) {
                    binding.repetirContrasena.setError("El campo no puede estar vacio");
                    return;
                }
                String reContra = binding.repetirContrasena.getText().toString();

                if (contrasena.equalsIgnoreCase(reContra)) {
                    binding.repetirContrasena.setError("La contraseña no coinciden");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, contrasena)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser usuario = mAuth.getCurrentUser();
                                    Toast.makeText(getContext(), "Inicio de sesión exitoso: " + usuario.getEmail(), Toast.LENGTH_SHORT).show();
                                    ActivityLogin.iniciarActivityMain();
                                } else {
                                    Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}