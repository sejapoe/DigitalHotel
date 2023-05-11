package ru.sejapoe.digitalhotel.ui.survey;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.user.SurveyFormState;
import ru.sejapoe.digitalhotel.data.repository.UserRepository;

@HiltViewModel
public class SurveyViewModel extends ViewModel {
    private static final Pattern PHONE_REGEX = Pattern.compile("^\\+7\\d{10}$");

    private final UserRepository repository;
    private final MutableLiveData<LocalDate> date = new MutableLiveData<>();

    private final MutableLiveData<SurveyFormState> formState = new MutableLiveData<>(new SurveyFormState());

    @Inject
    public SurveyViewModel(UserRepository repository) {
        this.repository = repository;
    }


    public LiveData<Boolean> sendSurvey(String firstName, String lastName, String patronymic, String phoneNumber, boolean isMale) {
        if (validate(firstName, lastName, phoneNumber, isMale))
            return repository.sendSurvey(firstName, lastName, patronymic, phoneNumber, date.getValue(), isMale);
        else return new MutableLiveData<>(false);
    }

    public boolean validate(String firstName, String lastName, String phoneNumber, boolean isSexSelected) {
        SurveyFormState newState = new SurveyFormState(
                firstName == null || firstName.isEmpty() ? R.string.error_field_required : android.R.string.ok,
                lastName == null || lastName.isEmpty() ? R.string.error_field_required : android.R.string.ok,
                android.R.string.ok,
                phoneNumber == null || phoneNumber.isEmpty() ? R.string.error_field_required : (
                        PHONE_REGEX.matcher(phoneNumber).matches() ? android.R.string.ok : R.string.error_invalid_phone_number
                ),
                date.getValue() == null ? R.string.error_field_required : android.R.string.ok,
                isSexSelected);
        System.out.println(newState);
        formState.postValue(newState);
        return newState.isOk();
    }

    public LiveData<SurveyFormState> getFormState() {
        return formState;
    }

    public void setDate(LocalDate date) {
        this.date.postValue(date);
    }

    public LiveData<LocalDate> getDate() {
        return date;
    }
}
