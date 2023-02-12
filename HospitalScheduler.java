import java.util.*;

/*
// `Patient` class
// `patientNumber`: An integer representing the patient number.
// `severity`: An integer representing the patient's severity, where 0 represents critical and 1 represents non-critical.
// `timeTaken`: An integer representing the time taken by the doctor to attend the patient.
*/

class Patient{
    int patientNumber;
    int severity;  
    int timeTaken;
 
    Patient(int patientNumber, int severity, int timeTaken){
        this.patientNumber =  patientNumber;
        this.severity = severity;
        this.timeTaken = timeTaken;    
    }
}
 
/*
// `HospitalScheduler` class
// `numberOfDoctors`: An integer representing the number of doctors in the hospital.
// `doctorAttendingPatient`: An array of Patient objects, representing the patients that are being attended by each doctor.
// `patientsQueue`: A priority queue of Patient objects, representing the patients waiting to be attended in the order of their severity and patient number.
// `patientArrived`: A set of integers representing the patient numbers of patients who have already arrived at the hospital.
// `criticalPatients`: An integer representing the number of critical patients waiting in the queue.
// `nonCriticalPatients`: An integer representing the number of non-critical patients waiting in the queue.
*/
class HospitalScheduler{
 
    int numberOfDoctors;
    Patient[] doctorAttendingPatient;
    PriorityQueue<Patient> patientsQueue;
    Set<Integer> patientArrived;
    int criticalPatients, nonCriticalPatients;
 
    // Constructor
    HospitalScheduler(int numberOfDoctors){
        this.numberOfDoctors = numberOfDoctors;
        this.doctorAttendingPatient = new Patient[numberOfDoctors];
        this.patientsQueue = new PriorityQueue<>( (a, b) -> {
            if(a.severity != b.severity){
                return Integer.compare(a.severity, b.severity);
            }
            return Integer.compare(a.patientNumber, b.patientNumber);
        });
        this.patientArrived = new HashSet<>();
        this.criticalPatients = 0;
        this.nonCriticalPatients = 0;
    }
 
    // `addNewPatient`: Adds a new patient to the hospital and updates the waiting queue and patient list.
    void addNewPatient(Patient patient){
        if(patientArrived.contains(patient.patientNumber)){
            System.out.println("Patient: #" +patient.patientNumber + " Already Taken into Consideration");
            return;
        }
        if(patient.severity == 0){
            if(criticalPatients>0){
                System.out.println("Please wait, you are at waiting number #" + (criticalPatients + 1));
            }
            patientsQueue.add(patient);
            criticalPatients++;
        }
        else{
            if(patientsQueue.size() > 0 || (findDoctorAccToSeverity(-1) == -1)){
                System.out.println("Patient: #"+ patient.patientNumber + ", Please wait, you are at waiting number #" + (patientsQueue.size() + 1));
            }
            patientsQueue.add(patient);
            nonCriticalPatients++;
        }
        patientArrived.add(patient.patientNumber);
        visitDoctor();
    }
 
    // `visitDoctor`: Assigns the next patient in the queue to a doctor.
    void visitDoctor(){
        Patient patient = patientsQueue.poll();
        if(patient.severity == 0){
            int potentialDoctor = findNextAvailableDoctor(patient);
            assignDoctor(patient, potentialDoctor);
            criticalPatients--;
        }
        else{
            int potentialDoctor = findNextAvailableDoctor(patient);
            assignDoctor(patient, potentialDoctor);
            nonCriticalPatients--;
        }
    }
 
    // `assignDoctor`: Assigns a patient to a specific doctor.
    void assignDoctor(Patient patient, int doctor){
        if(patient.severity == 0 && doctorAttendingPatient[doctor] != null && doctorAttendingPatient[doctor].severity == 1){
            patientsQueue.add(doctorAttendingPatient[doctor]);
            System.out.println("Critical patient, Doctor: #" + (doctor+1) + " is now attending Patient: #" + patient.patientNumber);
            visitDoctor();
        }
        else{
            System.out.println("Doctor: #" + (doctor + 1) + " is attending Patient: #" + patient.patientNumber);
        }
        doctorAttendingPatient[doctor] = patient;
    }
    
    // `findNextAvailableDoctor`: Finds the next available doctor based on the patient's severity.
    int findNextAvailableDoctor(Patient patient){
        int potentialDoctor = findDoctorAccToSeverity(-1);
        if(potentialDoctor != -1){
            return potentialDoctor;
        }
        if(patient.severity == 0){
            potentialDoctor = findDoctorAccToSeverity(1);
            if(potentialDoctor != -1){
                return potentialDoctor;
            }
        }
 
        potentialDoctor = findDoctorAccToSeverity(0);
        return potentialDoctor;
    }
 
    //`findDoctorAccToSeverity`: A utility method that finds a doctor with a specific severity, if available.
    int findDoctorAccToSeverity(int severity){
        int potentialDoctor = -1, waitTime =  Integer.MAX_VALUE;
        for(int doctor = 0; doctor< numberOfDoctors; doctor++){
            if(severity == -1){
                if(doctorAttendingPatient[doctor]==null){
                    return doctor;
                }
            }
            else if(severity == 1){
                if(doctorAttendingPatient[doctor].severity == 1 && doctorAttendingPatient[doctor].timeTaken<waitTime){
                    waitTime = doctorAttendingPatient[doctor].timeTaken;
                    potentialDoctor = doctor;
                }
            }
            else{
                if(doctorAttendingPatient[doctor].timeTaken<waitTime){
                    waitTime = doctorAttendingPatient[doctor].timeTaken;
                    potentialDoctor = doctor;
                }
            }
        }
 
        return potentialDoctor;
    }
}

// HospitalEmergencyWardSimulation
public class HospitalEmergencyWardSimulation {
    public static void main(String[] args) {
        HospitalScheduler scheduler = new HospitalScheduler(1);
        scheduler.addNewPatient(new Patient(1, 1, 5));
        scheduler.addNewPatient(new Patient(2, 1, 2));
        scheduler.addNewPatient(new Patient(3, 0, 10));
        scheduler.addNewPatient(new Patient(4, 1, 7));
    }
}
