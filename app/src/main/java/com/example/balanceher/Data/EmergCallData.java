package com.example.balanceher.Data;

import com.example.balanceher.EmergCallModel;
import com.example.balanceher.R;

import java.util.ArrayList;
import java.util.List;

public class EmergCallData {
    static EmergCallData instance;
    String[] nameList = {"Dr Anwara Begum", "Prof. Dr. Brig. Gen. Md. Azizul Islam", "Dr. Mohiuddin A. Sikder", "Prof. Dr. Md. Gias Uddin Sagor", "Dr. Surajit Roy Chowdhury"};
    String[] designationList={"MBBS, M.Phil (Psychiatry)","MBBS, FCPS, FRCP (UK), FACP (USA)", "MBBS, M.PHIL (Psychiatry)", "MBBS, M.Phil (Psychiatry)", "MBBS, MD (Psychiatry)"};
    String[] workPlaceList = {"Mental Health, Brain Disorders, Headache & Epilepsy Specialist, National Hospital, Chittagong","Psychiatry Specialist, Consultant, Square Hospitals Ltd.", "Mental Diseases Specialist & Psychiatrist, Epic Healthcare, Chittagong", "Mental Health, Brain Disorders, Headache & Epilepsy Specialist, National Hospital, Chittagong", "Mental Health Specialist & Psychotherapist, Popular Diagnostic Center, Chittagong"};
    String[] contactList = {"01715-228152", "01713141447", "+8801984499600", "+8801822685066", "+8809613787810"};
    Integer[] imgList = {R.drawable.anwara_begum, R.drawable.azizul_islam, R.drawable.mohiuddin, R.drawable.sagor, R.drawable.surajit};

    List<EmergCallModel>list = new ArrayList<>();
    private EmergCallData(){
        for(int i=0;i<5;i++){
            list.add(new EmergCallModel(imgList[i], nameList[i], designationList[i], workPlaceList[i], contactList[i]));
        }
    }
    public static EmergCallData getInstance(){
        if(instance == null)
            instance = new EmergCallData();
        return instance;
    }
    public List<EmergCallModel> getEmergCallData(){
        return list;
    }
}
