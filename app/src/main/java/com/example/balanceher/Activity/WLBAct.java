package com.example.balanceher.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.balanceher.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WLBAct extends AppCompatActivity {
    String[] BMIList = {"18.5-24.9", "25.0-29.9", "30.0 or above"};
    String[] IncomeList = {"Below 30000", "30000-100000", ">100000"};
    String[] AgeList = {"Less than 20", "21 to 35", "36 to 50", "51 or more"};

    // vegetables
    // stress
    // newPlace
    // people
    // helpedPeople
    // interaction
    // achievements
    // donations
    // bmi
    // workCompletion
    // actions
    // stepsWalks
    // liveVision
    // sleepHours
    // vacation
    // anger
    // income
    // awards
    // passionHours
    // meditaionsHours
    // age
    // gender
    EditText vegetablesET,stressET,newPlaceET,peopleET,helpedPeopleET,interactionET,achievementsET,donationsET,workCompletionET,actionsET,stepsWalksET,liveVisionET,sleepHoursET,vacationET,angerET,awardsET,passionHoursET,meditaionsHoursET;
    AutoCompleteTextView bmiACTV, incomeACTV, ageACTV;

    ArrayAdapter<String>bmiACTVAda, incomeACTVAda, ageACTVAda;

    String vegetablesStr,stressStr,newPlaceStr,peopleStr,helpedPeopleStr,interactionStr,achievementsStr,donationsStr,bmiStr,workCompletionStr,actionsStr,stepsWalksStr,liveVisionStr,sleepHoursStr,vacationStr,angerStr,incomeStr,awardsStr,passionHoursStr,meditaionsHoursStr,ageStr, genderStr;

    String url = "https://balanceher.up.railway.app/predict";

    Button predictBtn;

    static Dialog success_dia, failed_dia;
    @Override
    protected void onCreate(Bundle savedInstanceState) { // lifecycle builtin
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlb);

        //Initialize
        Initialize();

        predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(WLBAct.this, "clicked", Toast.LENGTH_SHORT).show();
                processPrediction();
            }
        });
    }

    private void processPrediction() {
        LoadAllStr();
        if(TextUtils.isEmpty(vegetablesStr)  || TextUtils.isEmpty(stressStr) || TextUtils.isEmpty(newPlaceStr)
                || TextUtils.isEmpty(peopleStr) || TextUtils.isEmpty(helpedPeopleStr)
                || TextUtils.isEmpty(interactionStr) || TextUtils.isEmpty(achievementsStr)
                || TextUtils.isEmpty(donationsStr) || TextUtils.isEmpty(bmiStr)
                || TextUtils.isEmpty(workCompletionStr) || TextUtils.isEmpty(actionsStr)
                || TextUtils.isEmpty(stepsWalksStr) || TextUtils.isEmpty(liveVisionStr)
                || TextUtils.isEmpty(sleepHoursStr) || TextUtils.isEmpty(vacationStr)
                || TextUtils.isEmpty(angerStr) || TextUtils.isEmpty(incomeStr)
                || TextUtils.isEmpty(awardsStr) || TextUtils.isEmpty(passionHoursStr)
                || TextUtils.isEmpty(meditaionsHoursStr) || TextUtils.isEmpty(ageStr)){
            Toast.makeText(this, "Some field are empty", Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(this, vegetablesStr+" "+stressStr+" "+newPlaceStr+" "+peopleStr+" "+helpedPeopleStr+" "+interactionStr+" "+achievementsStr+" "+donationsStr+" "+bmiStr+" "+workCompletionStr+" "+actionsStr+" "+stepsWalksStr+" "+liveVisionStr+" "+sleepHoursStr+" "+vacationStr+" "+angerStr+" "+incomeStr+" "+awardsStr+" "+passionHoursStr+" "+meditaionsHoursStr+" "+ageStr+" "+genderStr, Toast.LENGTH_SHORT).show();
            SendRequest();
        }
    }

    private void SendRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String data = jsonObject.getString("result");

                    if(data.equals("1")){
                        PredictionResultYesDialog();
                        //Toast.makeText(WLBAct.this, "Congratulations!!!", Toast.LENGTH_SHORT).show();
                    }else{
                        PredictionResultNoDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WLBAct.this, "Error in sending request", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("vegetables",    vegetablesStr);
                params.put("stress",    stressStr);
                params.put("newPlace",    newPlaceStr);
                params.put("people",    peopleStr);
                params.put("helpedPeople",    helpedPeopleStr);
                params.put("interaction",    interactionStr);
                params.put("achievements",    achievementsStr);
                params.put("donations",    donationsStr);
                params.put("bmi",    bmiStr);
                params.put("workCompletion",    workCompletionStr);
                params.put("actions",    actionsStr);
                params.put("stepsWalks",    stepsWalksStr);
                params.put("liveVision",    liveVisionStr);
                params.put("sleepHours",    sleepHoursStr);
                params.put("vacation",    vacationStr);
                params.put("anger",    angerStr);
                params.put("income",    incomeStr);
                params.put("awards",    awardsStr);
                params.put("passionHours",    passionHoursStr);
                params.put("meditaionsHours",    meditaionsHoursStr);
                params.put("age",    ageStr);
                params.put("gender",    genderStr);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(WLBAct.this);
        queue.add(stringRequest);
    }


    private void ResetAllStr() {
        vegetablesStr = ""; stressStr = ""; newPlaceStr = ""; peopleStr = ""; helpedPeopleStr = ""; interactionStr = ""; achievementsStr = ""; donationsStr = ""; bmiStr = ""; workCompletionStr = ""; actionsStr = ""; stepsWalksStr = ""; liveVisionStr = ""; sleepHoursStr = ""; vacationStr = ""; angerStr = ""; incomeStr = ""; awardsStr = ""; passionHoursStr = ""; meditaionsHoursStr = ""; ageStr = ""; genderStr = "";
    }

    private void PredictionResultYesDialog(){
        success_dia.setContentView(R.layout.dialog_success);
        success_dia.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button btn_doagain = success_dia.findViewById(R.id.btn_doagain);
        Button btn_go_back = success_dia.findViewById(R.id.btn_go_back);

        btn_doagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WLBAct.this, "clicked do again", Toast.LENGTH_SHORT).show();
                ResetAllStr();
                success_dia.dismiss();
            }
        });
        btn_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WLBAct.this, "clicked go back", Toast.LENGTH_SHORT).show();
                success_dia.dismiss();
                finish();
            }
        });
        success_dia.show();
    }

    private void PredictionResultNoDialog() {
        failed_dia.setContentView(R.layout.dialog_failed);
        failed_dia.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button btn_doagain = failed_dia.findViewById(R.id.btn_doagain);
        Button btn_go_back = failed_dia.findViewById(R.id.btn_go_back);

        btn_doagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WLBAct.this, "clicked do again", Toast.LENGTH_SHORT).show();
                ResetAllStr();
                failed_dia.dismiss();
            }
        });
        btn_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WLBAct.this, "clicked go back", Toast.LENGTH_SHORT).show();
                failed_dia.dismiss();
                finish();
            }
        });
        failed_dia.show();
    }

    public void LoadAllStr(){
        vegetablesStr = vegetablesET.getText().toString().trim();
        stressStr = stressET.getText().toString().trim();
        newPlaceStr = newPlaceET.getText().toString().trim();
        peopleStr = peopleET.getText().toString().trim();
        helpedPeopleStr = helpedPeopleET.getText().toString().trim();
        interactionStr = interactionET.getText().toString().trim();
        achievementsStr = achievementsET.getText().toString().trim();
        donationsStr = donationsET.getText().toString().trim();
        workCompletionStr = workCompletionET.getText().toString().trim();
        actionsStr = actionsET.getText().toString().trim();
        stepsWalksStr = stepsWalksET.getText().toString().trim();
        liveVisionStr = liveVisionET.getText().toString().trim();
        sleepHoursStr = sleepHoursET.getText().toString().trim();
        vacationStr = vacationET.getText().toString().trim();
        angerStr = angerET.getText().toString().trim();
        awardsStr = awardsET.getText().toString().trim();
        passionHoursStr = passionHoursET.getText().toString().trim();
        meditaionsHoursStr = meditaionsHoursET.getText().toString().trim();

        bmiStr = bmiACTV.getText().toString().trim();  //"18.5-24.9", "25.0-29.9", "30.0 or above"
        if(bmiStr == "18.5-24.9")  bmiStr = "1";
        else if(bmiStr == "25.0-29.9") bmiStr = "2";
        else bmiStr = "3";

        incomeStr = incomeACTV.getText().toString().trim();  //{"Below 30000", "30000-100000", ">100000"};
        if(incomeStr == "Below 30000")  incomeStr = "1";
        else if(incomeStr == "30000-100000") incomeStr = "2";
        else incomeStr = "3";

        ageStr = ageACTV.getText().toString().trim();  // {"Less than 20", "21 to 35", "36 to 50", "51 or more"};
        if(ageStr == "Less than 20")  incomeStr = "0";
        else if(ageStr == "21 to 35") incomeStr = "1";
        else if(ageStr == "36 to 50") ageStr = "2";
        else ageStr = "3";

        genderStr = "1";

        //TestData
        /*vegetablesStr = "2";
        stressStr = "3";
        newPlaceStr = "4";
        peopleStr = "3";
        helpedPeopleStr = "8";
        interactionStr = "10";
        achievementsStr="5";
        donationsStr="2";
        bmiStr="2";
        workCompletionStr="5";
        actionsStr="2";
        stepsWalksStr="5";
        liveVisionStr="5";
        sleepHoursStr="8";
        vacationStr="2";
        angerStr="2";
        incomeStr="2";
        awardsStr="3";
        passionHoursStr="2";
        meditaionsHoursStr="6";
        ageStr="2";
        genderStr="1";*/
    }
    public void Initialize(){
        vegetablesET = findViewById(R.id.vegetablesET);
        stressET = findViewById(R.id.stressET);
        newPlaceET = findViewById(R.id.newPlaceET);
        peopleET = findViewById(R.id.peopleET);
        helpedPeopleET = findViewById(R.id.helpedPeopleET);
        interactionET = findViewById(R.id.interactionET);
        achievementsET = findViewById(R.id.achievementsET);
        donationsET = findViewById(R.id.donationsET);
        workCompletionET = findViewById(R.id.workCompletionET);
        actionsET = findViewById(R.id.actionsET);
        stepsWalksET = findViewById(R.id.stepsWalksET);
        liveVisionET = findViewById(R.id.liveVisionET);
        sleepHoursET = findViewById(R.id.sleepHoursET);
        vacationET = findViewById(R.id.vacationET);
        angerET = findViewById(R.id.angerET);
        awardsET = findViewById(R.id.awardsET);
        passionHoursET = findViewById(R.id.passionHoursET);
        meditaionsHoursET = findViewById(R.id.meditaionsHoursET);

        bmiACTV = findViewById(R.id.BMIACTV);
        incomeACTV = findViewById(R.id.incomeACTV);
        ageACTV = findViewById(R.id.ageACTV);

        predictBtn = findViewById(R.id.predictBtn);

        //Initializing ACTV adapters
        bmiACTVAda = new ArrayAdapter<>(this, R.layout.auto_comp_item, BMIList);
        incomeACTVAda = new ArrayAdapter<>(this, R.layout.auto_comp_item, IncomeList);
        ageACTVAda = new ArrayAdapter<>(this, R.layout.auto_comp_item, AgeList);

        //Setting ACTV adapters
        bmiACTV.setAdapter(bmiACTVAda);
        incomeACTV.setAdapter(incomeACTVAda);
        ageACTV.setAdapter(ageACTVAda);

        success_dia = new Dialog(this);
        failed_dia = new Dialog(this);
    }
}

/*
                params.put("vegetables",   vegetablesStr);
                params.put("stress",   stressStr    );
                params.put("newPlace",   newPlaceStr    );
                params.put("people",   peopleStr);
                params.put("helpedPeople",   helpedPeopleStr);
                params.put("interaction",   interactionStr);
                params.put("achievements",   achievementsStr);
                params.put("donations",   donationsStr);
                params.put("bmi",   bmiStr);
                params.put("workCompletion",   workCompletionStr);
                params.put("actions",   actionsStr);
                params.put("stepsWalks",   stepsWalksStr);
                params.put("liveVision",   liveVisionStr);
                params.put("sleepHours",   sleepHoursStr);
                params.put("vacation",   vacationStr);
                params.put("anger",   angerStr);
                params.put("income,",   incomeStr);
                params.put("awards,",   awardsStr);
                params.put("passionHours,",   passionHoursStr);
                params.put("meditaionsHours,",   meditaionsHoursStr);
                params.put("age,",   ageStr);
                params.put("gender,",   genderStr);

 */