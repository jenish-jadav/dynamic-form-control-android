package jj.app.dynamicform;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import jj.app.dynamicform.models.Constants;
import jj.app.dynamicform.newmodel.Schema;

public class DemoActivity extends AppCompatActivity {
    List<Schema> myControlList;
    LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        llMain = findViewById(R.id.layout_main);
        myControlList = new ArrayList<>();
        getControls();
        prepareView();

    }


    private void prepareView() {
        for (int i = 0; i < myControlList.size(); i++) {
            Schema data = myControlList.get(i);
            switch (data.getType()){

                case Constants.header:
                    if (data.getNgModel() == null){
                        addText(data);
                    }
                    break;
            }
        }
    }

    private void addText(Schema schema) {
        String title = schema.getLabel().toString();
        String[] split = title.split("nbsp;");
        String secondString = split[1];

        String[] split2 = secondString.split("<");
        String firstString = split2[0];
        addCaption(firstString);
    }

    private void addCaption(String title) {
        TextView textTitle = new TextView(this);
        textTitle.setText(title);
        textTitle.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        textTitle.setTextSize(18f);
        textTitle.setLayoutParams(getLayoutParam());
        llMain.addView(textTitle);
    }

    private LinearLayout.LayoutParams getLayoutParam() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 15);
        return params;
    }

    private void getControls() {
        String data = readFile();
        data = data.replace("\n  ","");
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(data);
            JSONObject jsonObject = jsonObj.getJSONObject("form_data");
            JSONArray jsonArray = jsonObject.getJSONArray("schemas");
            Type type = new TypeToken<List<Schema>>() {}.getType();
            myControlList = new Gson().fromJson(String.valueOf(jsonArray), type);
            if (myControlList == null) myControlList = new ArrayList<>();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String readFile() {
        String json = null;
        try {
            InputStream is = getAssets().open("dynamic_form_2.json");
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
}