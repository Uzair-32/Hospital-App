package com.example.project.Health_Data_Handling;

import java.util.*;
public class VitalsDatabase {
    private List<VitalSign> vitalList; // a list that will store all the vital signs of patients

    public VitalsDatabase() {
        this.vitalList = new ArrayList<>();
    }

    public void addVitalSign(VitalSign record) {
        vitalList.add(record); // adds the vitals sign of patient naming record in the list 
    }

    /*  so this is a method that will return a record or list user will give it a userID and it will iterate through the 
    the existed list that stores all the vitals and at each vital record it check where the userID matches or not and if 
    it matches with someone it will return the list storing the record*/
    public List<VitalSign> findPatientViatls(String PatientUserID) {
        List<VitalSign> patientVitals = new ArrayList<>();

        for(VitalSign vital : vitalList) {
            if(vital.getUserID().equals(PatientUserID)) {
                patientVitals.add(vital);
            }
        }
        return patientVitals;
    }

    /* a method which will take the userID and iterate through the list and if the userID matches with some record it will
    update it with the new record passed by the user */
    public void updateVitalRecord(String PatientUserID, VitalSign newVitalRecord) {
        for(int i = 0; i < vitalList.size(); i++) {
            if(vitalList.get(i).getUserID().equals(PatientUserID)) {
                vitalList.set(i, newVitalRecord);
                return;
            }
        }
    }

    // a method that will remove the vital record from the vital list by taking ID
    /*why i use lamda expression here instead of simple loop so two main reason first: if use we for loop and try to 
        remove the record lets say at index 2 and then imediately use return then no other record will be check and it is 
        possible to have multilple record for one patient and if i dont use return and then it will iterate through whole list 
        but lets see a thing here say the userID matches at 1 index then it will remove it then list will be shifted to the 
        left the record at 2 index will be on 1 index and our iterator i will go to the 2 index so the record will miss
     */
    public void deleteVitalRecord(String PatientUserID) {
        vitalList.removeIf(v -> v.getUserID().equals(PatientUserID));
    }
}
