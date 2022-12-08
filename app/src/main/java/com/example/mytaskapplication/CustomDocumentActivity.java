package com.example.mytaskapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CustomDocumentActivity extends AppCompatActivity {

    String token;
    Button attachment ;
    TextView name;
    TextView date ;
    EditText editText1,editText2,editText3;
    String label;
    List<String> labels;
    final Calendar myCalendar= Calendar.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayoutParams.setMargins(15, 15, 15, 15);
        linearLayout.setGravity(0);
        attachment = new Button(this);
        name = new TextView(this);
        date = new TextView(this);
        editText1=new EditText(this);
        editText2=new EditText(this);
        editText3=new EditText(this);

        name.setTextSize(20);
        date.setTextSize(20);

        Drawable img = getResources().getDrawable(R.drawable.ic_baseline_attachment_24);
        attachment.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

        editText1.setBackground(getResources().getDrawable(R.drawable.editbox));
        editText2.setBackground(getResources().getDrawable(R.drawable.editbox));

        linearLayout.addView(name);
        linearLayout.addView(editText1);
        linearLayout.addView(date);
        linearLayout.addView(editText2);
        linearLayout.addView(attachment);

        labels=new ArrayList<>();

        setContentView(linearLayout, linearLayoutParams);
        getDocumentInfo();
        getSupportActionBar().setTitle("Custom Document");


        DatePickerDialog.OnDateSetListener date1 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CustomDocumentActivity.this,date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CustomDocumentActivity.this,date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editText2.setText(dateFormat.format(myCalendar.getTime()));
    }


    public void getDocumentInfo(){

        token="eyJ0eXBlIjoiQlMiLCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaXRlaWQiOiJkbFZQV0V0aEwzcG1SMkpFUVc1NFpsYzJaREpSWnowOSIsImRldmljZW5vIjoiWm1OQmVtOTZVVEJNT0RWc1NqSmhSRFl4V0c4dlppc3h" +
                "TSEJNVjI5NE1FZEtjblpzYlZaTGVrNWFPRDA9IiwiY2xpZW50bmFtZSI6ImF6RXZOU3Q0VG1aSk1rTTBSR1V5ZUVKMk1GUkJUakZTWnpsMVNubGhNRUphTlZWdk9HczVlVko2T0QwPSIsInVzZXJpZCI6Il" +
                "JuUnhhbVI0U1hkUVdrdElja1ZMVWt0eVMyRjBRVDA5In0.Fg5JbggGjDSqu4L3xlD7XofiKCnOon_MlCeRzU1UWQI";
        String url= "https://development-mobile.builderstorm.com/mobileapp/ddoctemplate/2854?pageno=0&docid=" +
                "0&issue_id=&pid=329&recurrence=&clone=0";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Toast.makeText(getApplicationContext(), ""+response.toString(), Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        JSONArray rowColums=jsonObject1.getJSONArray("rowcolumns");
                        JSONObject objectdata=rowColums.getJSONObject(0);
                        JSONObject columndata=objectdata.getJSONObject("column_data");
                        label=columndata.getString("label");
                        labels.add(columndata.getString("label"));
                        //Toast.makeText(getApplicationContext(),""+label,Toast.LENGTH_LONG).show();

                    }
                    name.setText(labels.get(0));
                    date.setText(labels.get(1));
                    attachment.setText(labels.get(2));

                }
                catch (JSONException e){
                    Toast.makeText(getApplicationContext(),"c"+ e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),""+ error.toString(),Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("authToken", token);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
