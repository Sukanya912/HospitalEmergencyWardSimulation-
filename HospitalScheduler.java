import java.util.*;

/*
// severity: P0 -> Needs immediate attention, P1 -> Not very critical, Can wait
// timeTaken: in minutes
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
// HospitalScheduler class
// doctorAttendingPatient: default null, means not attending
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
 
    // adds new patient
    void addNewPatient(Patient patient){
        if(patientArrived.contains(patient.patientNumber)){
            System.out.println("Patient: #" +patient.patientNumber + " Already Taken into Consideration");
            return;
        }
        if(patient.severity==0){
            if(criticalPatients>0){
                System.out.println("Please wait, you are at waiting number #" + (criticalPatients + 1));
            }
            patientsQueue.add(patient);
            criticalPatients++;
        }
        else{
            if(patientsQueue.size() > 0 || (findDoctorAccToSeverity(-1)==-1)){
                System.out.println("Patient: #"+ patient.patientNumber + ", Please wait, you are at waiting number #" + (patientsQueue.size() + 1));
            }
            patientsQueue.add(patient);
            nonCriticalPatients++;
        }
        patientArrived.add(patient.patientNumber);
        visitDoctor();
    }
 
    // visit Doctor
    void visitDoctor(){
        Patient patient = patientsQueue.poll();
        if(patient.severity==0){
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
 
    // assign Doctor
    void assignDoctor(Patient patient, int doctor){
        if(patient.severity == 0 && doctorAttendingPatient[doctor]!=null && doctorAttendingPatient[doctor].severity==1){
            patientsQueue.add(doctorAttendingPatient[doctor]);
            System.out.println("Critical patient, Doctor: #" + (doctor+1) + " is now attending Patient: #" + patient.patientNumber);
            visitDoctor();
        }
        else{
            System.out.println("Doctor: #" + (doctor+1) + " is attending Patient: #" + patient.patientNumber);
        }
        doctorAttendingPatient[doctor] = patient;
    }
    
    // Find Next available doctor
    int findNextAvailableDoctor(Patient patient){
        int potentialDoctor = findDoctorAccToSeverity(-1);
        if(potentialDoctor!=-1){
            return potentialDoctor;
        }
        if(patient.severity==0){
            potentialDoctor = findDoctorAccToSeverity(1);
            if(potentialDoctor!=-1){
                return potentialDoctor;
            }
        }
 
        potentialDoctor = findDoctorAccToSeverity(0);
        return potentialDoctor;
    }
 
    //utility for Find doctor according to Severity
    int findDoctorAccToSeverity(int severity){
        int potentialDoctor = -1, waitTime =  Integer.MAX_VALUE;
        for(int doctor = 0; doctor< numberOfDoctors; doctor++){
            if(severity == -1 ){
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

// Scheduler class
public class Scheduler{
    public static void main(String[] args) {
        HospitalScheduler scheduler =  new Hospital Scheduler(1);
        scheduler.addNewPatient(new Patient(1, 1, 5));
        scheduler.addNewPatient(new Patient(2, 1, 2));
        scheduler.addNewPatient(new Patient(3, 0, 10));
        scheduler.addNewPatient(new Patient(4, 1, 7));
    }
}
