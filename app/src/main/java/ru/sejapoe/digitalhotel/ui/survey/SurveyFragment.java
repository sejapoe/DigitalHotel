package ru.sejapoe.digitalhotel.ui.survey;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.time.LocalDate;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.databinding.FragmentSurveyBinding;


@AndroidEntryPoint
public class SurveyFragment extends Fragment {
    private FragmentSurveyBinding binding;
    private SurveyViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSurveyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SurveyViewModel.class);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validate();
            }
        };

        binding.firstNameEditText.addTextChangedListener(watcher);
        binding.lastNameEditText.addTextChangedListener(watcher);
        binding.phoneNumberEditText.addTextChangedListener(watcher);
        binding.patronymicEditText.addTextChangedListener(watcher);

        binding.datePickerBtn.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext());
            datePickerDialog.setOnDateSetListener((view1, year, month, dayOfMonth) -> {
                viewModel.setDate(LocalDate.of(year, month, dayOfMonth));
            });
            datePickerDialog.show();
        });

        viewModel.getDate().observe(getViewLifecycleOwner(), localDate -> {
            binding.datePickerBtn.setText(localDate.toString());
            validate();
        });

        viewModel.getFormState().observe(getViewLifecycleOwner(), surveyFormState -> {
            System.out.println(surveyFormState);
            if (surveyFormState == null) {
                return;
            }
            binding.submitButton.setEnabled(surveyFormState.isOk());
            binding.firstNameEditText.setError(surveyFormState.hasFirstNameError() ? getString(surveyFormState.getFirstNameError()) : null);
            binding.lastNameEditText.setError(surveyFormState.hasLastNameError() ? getString(surveyFormState.getLastNameError()) : null);
            binding.patronymicEditText.setError(surveyFormState.hasPatronymicError() ? getString(surveyFormState.getPatronymicError()) : null);
            binding.phoneNumberEditText.setError(surveyFormState.hasPhoneNumberError() ? getString(surveyFormState.getPhoneNumberError()) : null);
        });

        binding.sexRadio.setOnCheckedChangeListener((group, checkedId) -> validate());

        binding.submitButton.setOnClickListener(v -> viewModel.sendSurvey(
                binding.firstNameEditText.getText().toString(),
                binding.lastNameEditText.getText().toString(),
                binding.patronymicEditText.getText().toString(),
                binding.phoneNumberEditText.getText().toString(),
                binding.sexRadio.getCheckedRadioButtonId() == binding.maleSex.getId()
        ).observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                NavHostFragment.findNavController(this).navigate(R.id.action_surveyFragment_to_loadingFragment);
        }));
    }

    private void validate() {
        System.out.println("text watcher");
        viewModel.validate(
                binding.firstNameEditText.getText().toString(),
                binding.lastNameEditText.getText().toString(),
                binding.phoneNumberEditText.getText().toString(),
                binding.sexRadio.getCheckedRadioButtonId() != -1);
    }
}
