# Hospital Emergency Ward Simulation Take Home Assignment

Problem Statement:

Simulate scheduler for hospital emergency ward. At a given time, the emergency ward has a
fixed number of doctors. All the incoming patients will be provided with a tag depending upon
the severity of the emergency.
P0: Needs immediate attention
P1: Not critical, can wait

A patient with a more critical problem will preempt the other patients even if they have been
getting attended by a doctor or waiting for a longer duration. In case, there are many patients
with the same priority, then they will be attended to in the order of their arrival time.
Example:
<Number of doctors>
N = 1
<Patient number, Severity, TimeTaken>
[1, P1, 5 minutes] -> Output: “Doctor: 1 is attending Patient: 1”
[2, P1, 2 minutes] -> Output: “Please wait, you are at waiting number: 1”

After 5 minutes, Output: “Doctor 1 is attending Patient: 2”
[3, P0, 10 minutes] -> Output: “Critical patient, Doctor: 1 is now attending Patient: 3”
[4, P1, 7 minutes] -> Output: “Please wait, you are at waiting number: 2”

After 10 minutes, Output: “Doctor: 1 is attending Patient: 2” (Re-examine from the beginning i.e.
for 2 minutes) 
