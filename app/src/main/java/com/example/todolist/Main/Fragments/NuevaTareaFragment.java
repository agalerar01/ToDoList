package com.example.todolist.Main.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolist.Main.Arquitectura.ImageUtils;
import com.example.todolist.Main.Arquitectura.ViewModel;
import com.example.todolist.Main.SharedPreferencesHelper;
import com.example.todolist.Main.Objetos.Tarea;
import com.example.todolist.databinding.FragmentNuevaTareaBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NuevaTareaFragment extends Fragment {

    FragmentNuevaTareaBinding binding;
    ViewModel viewModel;
    SharedPreferencesHelper helper;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    boolean error;
    String titulo = "", categoria = "", descripcion = "", fecha = "";
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private byte[] fotoBlob;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNuevaTareaBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        helper = new SharedPreferencesHelper(getContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fechaLimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        (view1, selectedYear, selectedMonth, selectedDay) -> {
                            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                            binding.fechaLimite.setText(selectedDate);
                        },
                        year, month, day);

                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

                datePickerDialog.show();
            }
        });
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri imageUri = result.getData().getData();
                            fotoBlob = ImageUtils.uriToBlob(requireContext().getContentResolver(), imageUri);

                            binding.imagenTarea.setImageBitmap(ImageUtils.blobToBitmap(fotoBlob));
                            binding.eliminar.setVisibility(View.VISIBLE);
                            binding.imagenTarea.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

        binding.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fotoBlob = null;

                binding.eliminar.setVisibility(View.GONE);
                binding.imagenTarea.setVisibility(View.GONE);
            }
        });


        binding.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePickerLauncher.launch(intent);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error = false;

                if (!String.valueOf(binding.titulo.getText()).equalsIgnoreCase("")) {
                    titulo = String.valueOf(binding.titulo.getText());
                } else {
                    binding.titulo.setError("El campo no puede estar vacio");
                    error = true;
                }

                if (!String.valueOf(binding.categoria.getText()).equalsIgnoreCase("")) {
                    categoria = String.valueOf(binding.categoria.getText());
                } else {
                    binding.categoria.setError("El campo no puede estar vacio");
                    error = true;
                }

                if (!String.valueOf(binding.descripcion.getText()).equalsIgnoreCase("")) {
                    descripcion = String.valueOf(binding.descripcion.getText());
                } else {
                    binding.descripcion.setError("El campo no puede estar vacio");
                    error = true;
                }

                if (!String.valueOf(binding.fechaLimite.getText()).equalsIgnoreCase("")) {
                    fecha = String.valueOf(binding.fechaLimite.getText());
                } else {
                    binding.fechaLimite.setError("El campo no puede estar vacio");
                    error = true;
                }

                if (!error) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date;
                    try {
                        date = dateFormat.parse(fecha);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    viewModel.buscarIgual(titulo, categoria, date).observe(getViewLifecycleOwner(), new Observer<Tarea>() {
                        @Override
                        public void onChanged(Tarea tarea) {
                            if (tarea != null) {
                                binding.errorIgual.setText("No pueden existir dos tareas iguales");
                            } else {
                                viewModel.insertar(new Tarea(titulo, categoria,mAuth.getCurrentUser().getEmail().toString(), descripcion, date, fotoBlob));
                                helper.actualizarCuenta();
                                getActivity().getSupportFragmentManager().popBackStack();
                                if(helper.devolverNoMostrar() == false&&helper.devolverCuenta() == 2){
                                    helper.reiniciarCuenta();
                                    new AlertDialog.Builder(getContext())
                                            .setTitle("Valorar")
                                            .setMessage("¿Deseas valorar nuestra aplicacion?")
                                            .setPositiveButton("Mas tarde", (dialog, which) -> {
                                                dialog.dismiss();
                                            })
                                            .setNegativeButton("No volver a mostrar", (dialog, which) -> {
                                                helper.guardarNoMostrar(true);
                                            })
                                            .show();
                                }
                            }
                        }
                    });
                }
            }
        });

    }
}