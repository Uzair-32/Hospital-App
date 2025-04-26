package com.example.project.Health_Data_Handling;

import com.example.project.User_Management.Administrator;

public class VitalSign {

    // atttributes
    private String userID;
    private int heartRate;
    private int oxygenLevel;
    private int bloodPressure;
    private double temperature;
    private int respiratoryRate;
    private double glucoseLevel;
    private double cholesterolLevel;
    private double bmi;
    private double hydrationLevel;
    private int stressLevel;
    
    // constructors
    public VitalSign() {

    }

    public VitalSign(
        String userID, int heartRate, int oxygenLevel, int bloodPressure, double temperature, 
        int respiratoryRate, double glucoseLevel, double cholesterolLevel, double bmi, 
        double hydrationLevel, int stressLevel, Administrator admin
    ) {
        if(admin.findPatientbyID(userID) == null) {
            throw new IllegalArgumentException("Patient with User ID " + userID + " not found!");
        }
        setUserID(userID);
        setHeartRate(heartRate);
        setOxygenLevel(oxygenLevel);
        setBloodPressure(bloodPressure);
        setTemperature(temperature);
        setRespiratoryRate(respiratoryRate);
        setGlucoseLevel(glucoseLevel);
        setCholesterolLevel(cholesterolLevel);
        setBmi(bmi);
        setHydrationLevel(hydrationLevel);
        setStressLevel(stressLevel);
    }

    // setters with validations
    public void setUserID(String userID) {
        if (userID == null || userID.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        this.userID = userID;
    }
    
    public void setHeartRate(int heartRate) {
        if (heartRate < 30 || heartRate > 200) { 
            throw new IllegalArgumentException("Heart rate must be between 30 and 200 bpm.");
        }
        this.heartRate = heartRate;
    }
    
    public void setOxygenLevel(int oxygenLevel) {
        if (oxygenLevel < 50 || oxygenLevel > 100) { 
            throw new IllegalArgumentException("Oxygen level must be between 50 and 100%.");
        }
        this.oxygenLevel = oxygenLevel;
    }
    
    public void setBloodPressure(int bloodPressure) {
        if (bloodPressure < 50 || bloodPressure > 200) { 
            throw new IllegalArgumentException("Blood pressure must be between 50 and 200 mmHg.");
        }
        this.bloodPressure = bloodPressure;
    }
    
    public void setTemperature(double temperature) {
        if (temperature < 30.0 || temperature > 45.0) { 
            throw new IllegalArgumentException("Temperature must be between 30.0 and 45.0°C.");
        }
        this.temperature = temperature;
    }
    
    public void setRespiratoryRate(int respiratoryRate) {
        if (respiratoryRate < 5 || respiratoryRate > 50) { 
            throw new IllegalArgumentException("Respiratory rate must be between 5 and 50 breaths per minute.");
        }
        this.respiratoryRate = respiratoryRate;
    }
    
    public void setGlucoseLevel(double glucoseLevel) {
        if (glucoseLevel < 50 || glucoseLevel > 300) { 
            throw new IllegalArgumentException("Glucose level must be between 50 and 300 mg/dL.");
        }
        this.glucoseLevel = glucoseLevel;
    }
    
    public void setCholesterolLevel(double cholesterolLevel) {
        if (cholesterolLevel < 50 || cholesterolLevel > 400) { 
            throw new IllegalArgumentException("Cholesterol level must be between 50 and 400 mg/dL.");
        }
        this.cholesterolLevel = cholesterolLevel;
    }
    
    public void setBmi(double bmi) {
        if (bmi < 10.0 || bmi > 50.0) {
            throw new IllegalArgumentException("BMI must be between 10.0 and 50.0.");
        }
        this.bmi = bmi;
    }
    
    public void setHydrationLevel(double hydrationLevel) {
        if (hydrationLevel < 0 || hydrationLevel > 100) { 
            throw new IllegalArgumentException("Hydration level must be between 0 and 100%.");
        }
        this.hydrationLevel = hydrationLevel;
    }
    
    public void setStressLevel(int stressLevel) {
        if (stressLevel < 0 || stressLevel > 10) {
            throw new IllegalArgumentException("Stress level must be between 0 and 10.");
        }
        this.stressLevel = stressLevel;
    }

    // getters 
    public String getUserID() {  return userID; }
    public int getHeartRate() { return heartRate; }
    public int getOxygenLevel() { return oxygenLevel; }
    public int getBloodPressure() { return bloodPressure; }
    public double getTemperature() { return temperature; }
    public int getRespiratoryRate() { return respiratoryRate; }
    public double getGlucoseLevel() { return glucoseLevel; }
    public double getCholesterolLevel() { return cholesterolLevel; }
    public double getBmi() { return bmi; }
    public double getHydrationLevel() { return hydrationLevel; }
    public int getStressLevel() { return stressLevel; }

    @Override
    public String toString() {
        return String.format(
            "User ID: %s\n" +
            "Heart Rate: %d bpm\n" +
            "Oxygen Level: %d%%\n" +
            "Blood Pressure: %d mmHg\n" +
            "Temperature: %.1f °C\n" +
            "Respiratory Rate: %d breaths/min\n" +
            "Glucose Level: %.2f mg/dL\n" +
            "Cholesterol Level: %.2f mg/dL\n" +
            "BMI: %.2f kg/m²\n" +
            "Hydration Level: %.2f%%\n" +
            "Stress Level: %d/10",
            this.userID, this.heartRate, this.oxygenLevel, this.bloodPressure, this.temperature,
            this.respiratoryRate, this.glucoseLevel, this.cholesterolLevel, this.bmi,
            this.hydrationLevel, this.stressLevel
        );
    }    
}

