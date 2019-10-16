package jj.app.dynamicform;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jj.app.dynamicform.models.Constants;
import jj.app.dynamicform.models.MyControl;
import jj.app.dynamicform.models.MyOptions;

public class MainActivity extends AppCompatActivity {
    List<MyControl> myControlList;
    LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getControls();
        setContentView(prepareView());
    }

    private void init() {
        myControlList = new ArrayList<>();
    }

    private void getControls() {
        String data = readFile();
        JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
        if (jsonObject.has("controls")) {
            Type type = new TypeToken<List<MyControl>>() {
            }.getType();
            myControlList = new Gson().fromJson(jsonObject.getAsJsonArray("controls"), type);
            if (myControlList == null) myControlList = new ArrayList<>();
        }
    }

    private String readFile() {
        String json = null;
        try {
            InputStream is = getAssets().open("dynamic_form.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            int a = is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private LinearLayout prepareView() {
        llMain = new LinearLayout(this);
        llMain.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 20, 30, 20);
        llMain.setLayoutParams(params);

        for (int i = 0; i < myControlList.size(); i++) {
            MyControl control = myControlList.get(i);
            switch (control.getControlType()) {
                case Constants.EDIT_TEXT:
                    addEditText(control);
                    break;
                case Constants.SPINNER:
                    addSpinner(control);
                    break;
                case Constants.RADIO_BUTTON:
                    addRadioButtons(control);
                    break;
                case Constants.CHECK_BOX:
                    addCheckBox(control);
                    break;
                case Constants.DATE_PICKER:
                    addDatePicker(control);
                    break;
                case Constants.SUBMIT_BUTTON:
                    addSubmitButton(control);
                    break;
            }
        }

        return llMain;
    }


    private void addCaption(String title) {
        TextView textTitle = new TextView(this);
        textTitle.setText(title);
        textTitle.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        textTitle.setTextSize(18f);
        llMain.addView(textTitle);
    }

    private void addEditText(MyControl control) {
        addCaption(control.getTitle());
        EditText editText = new EditText(this);
        editText.setId(control.getControlId());
        editText.setHint("Enter Value");
        editText.setText(control.getValue());
        editText.setLayoutParams(getLayoutParam());
        llMain.addView(editText);
    }

    private LinearLayout.LayoutParams getLayoutParam() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 15);
        return params;
    }

    private List<String> getStringOptions(List<MyOptions> myOptionsList) {
        List<String> temp = new ArrayList<>();
        temp.add("SELECT");
        for (int i = 0; i < myOptionsList.size(); i++)
            temp.add(myOptionsList.get(i).getValue());
        return temp;
    }

    private void addSpinner(MyControl control) {
        addCaption(control.getTitle());
        Spinner spinner = new Spinner(this);
        spinner.setId(control.getControlId());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getStringOptions(control.getOptions()));
        spinner.setAdapter(adapter);
        spinner.setLayoutParams(getLayoutParam());
        llMain.addView(spinner);
    }

    public void addRadioButtons(MyControl control) {
        addCaption(control.getTitle());

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setId(control.getControlId());
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);

        List<MyOptions> myOptionsList = control.getOptions();
        for (int i = 0; i < myOptionsList.size(); i++) {
            MyOptions myOptions = myOptionsList.get(i);
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(myOptions.getId());
            radioButton.setText(myOptions.getValue());
            if (control.getValue() != null && control.getValue().equals(myOptions.getValue())) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
        radioGroup.setLayoutParams(getLayoutParam());

        llMain.addView(radioGroup);
    }

    public void addCheckBox(MyControl control) {
        addCaption(control.getTitle());

        List<MyOptions> myOptionsList = control.getOptions();
        for (int i = 0; i < myOptionsList.size(); i++) {
            MyOptions myOptions = myOptionsList.get(i);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(myOptions.getId());
            checkBox.setText(myOptions.getValue());
            if (control.getValue() != null && control.getValue().equals(myOptions.getValue())) {
                checkBox.setChecked(true);
            }

            if (i == myOptionsList.size() - 1) {
                checkBox.setLayoutParams(getLayoutParam());
            }
            llMain.addView(checkBox);
        }


    }

    private void addDatePicker(MyControl control) {
        addCaption(control.getTitle());

        final EditText editText = new EditText(this);
        editText.setHint("Choose Date");
        editText.setId(control.getControlId());
        //editText.setText(control.getValue());
        editText.setFocusable(false);
        editText.setClickable(true);
        editText.setLayoutParams(getLayoutParam());

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new MyDatePickerDialog(editText);
                newFragment.show(getSupportFragmentManager(), "date_picker");
            }
        });
        llMain.addView(editText);
    }

    private void addSubmitButton(MyControl control) {
        Button button = new Button(this);
        button.setText(control.getValue());

        button.setLayoutParams(getLayoutParam());
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        button.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClick();
            }
        });

        llMain.addView(button);
    }

    public static class MyDatePickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        EditText editText;

        public MyDatePickerDialog(EditText editText) {
            this.editText = editText;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getContext(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = MessageFormat.format("{0}/{1}/{2}", String.valueOf(dayOfMonth), String.valueOf(month + 1), String.valueOf(year));
            editText.setText(date);
        }
    }


    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void onSubmitClick() {
        Map<String, String> data = new HashMap<>();
        List<String> stringData = new ArrayList<>();


        for (int i = 0; i < myControlList.size(); i++) {
            MyControl control = myControlList.get(i);
            if (control.getControlType().equals(Constants.EDIT_TEXT)) {
                EditText editText = llMain.findViewById(control.getControlId());
                String value = String.valueOf(editText.getText());
                if (control.isRequired() && TextUtils.isEmpty(value)) {
                    toast(control.getTitle() + " is required.");
                    return;
                }
                data.put(control.getTitle(), value);
                stringData.add(control.getTitle() + ":" + value);
            } else if (control.getControlType().equals(Constants.SPINNER)) {
                Spinner spinner = llMain.findViewById(control.getControlId());

                int index = spinner.getSelectedItemPosition();
                if (control.isRequired() && (index == -1 || index == 0)) {
                    toast("Select " + control.getTitle());
                    return;
                }
                String value = "";
                if (index > 0)
                    value = control.getOptions().get(index - 1).getValue();
                data.put(control.getTitle(), value);
                stringData.add(control.getTitle() + ":" + value);
            } else if (control.getControlType().equals(Constants.RADIO_BUTTON)) {
                RadioGroup radioGroup = llMain.findViewById(control.getControlId());

                RadioButton radioButton = llMain.findViewById(radioGroup.getCheckedRadioButtonId());
                if (control.isRequired() && radioButton == null) {
                    toast("Select " + control.getTitle());
                    return;
                }
                String value = "";
                if (radioButton != null)
                    value = String.valueOf(radioButton.getText());
                data.put(control.getTitle(), value);
                stringData.add(control.getTitle() + ":" + value);
            } else if (control.getControlType().equals(Constants.CHECK_BOX)) {

                String value = "";
                List<MyOptions> myOptionsList = control.getOptions();
                for (int j = 0; j < myOptionsList.size(); j++) {
                    CheckBox checkBox = llMain.findViewById(myOptionsList.get(j).getId());
                    if (checkBox != null && checkBox.isChecked()) {
                        if (!TextUtils.isEmpty(value))
                            value += ",";
                        value += checkBox.getText();
                    }
                }

                if (control.isRequired() && TextUtils.isEmpty(value)) {
                    toast("Select " + control.getTitle());
                    return;
                }
                data.put(control.getTitle(), value);
                stringData.add(control.getTitle() + ":" + value);
            } else if (control.getControlType().equals(Constants.DATE_PICKER)) {
                EditText editText = llMain.findViewById(control.getControlId());
                String value = String.valueOf(editText.getText());
                if (control.isRequired() && TextUtils.isEmpty(value)) {
                    toast(control.getTitle() + " is required.");
                    return;
                }
                data.put(control.getTitle(), value);
                stringData.add(control.getTitle() + ":" + value);
            }
        }

        display(stringData);
        //toast(data.toString());
        //setLog(data.toString());


    }

    public void display(List<String> stringList) {
        try {

            String data[] = new String[stringList.size()];
            for (int j = 0; j < stringList.size(); j++) {
                data[j] = stringList.get(j);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Information");
            builder.setItems(data, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("OK", null);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
