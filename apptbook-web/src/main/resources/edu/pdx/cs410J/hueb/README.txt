This is a README file!

Sam Huebschwerlen
Project 4

This Appointment Book client can perform several functions:

– Add an appointment to the server:
$ java -jar target/apptbook-client.jar -host localhost -port 12345 \
"Dave" "Teach Java Class" 10/19/2021 6:00 pm 10/19/2021 9:30 pm

– Search for appointments that begin between two times. The below command line should prettyprint all appointments that begin in the month of November. A message should be printed if there
is no appointment between those two times.
$ java -jar target/apptbook-client.jar -host localhost -port 12345 \
-search "Dave" 11/01/2021 12:00 am 11/30/2021 11:59 pm

– Pretty print all appointments in an appointment book.
$ java -jar target/apptbook-client.jar -host localhost -port 12345 "Dave"

usage: java -jar target/apptbook-2021.0.0.jar [options] <args>
args are (in this order):

owner                The person who owns the appt book
description          A description of the appointment
begin                When the appt begins (24-hour time)
end                  When the appt ends (24-hour time)

options are (options may appear in any order):
-host hostname       Host computer on which the server runs
-port port           Port on which the server is listening
-search              Appointments should be searched for
-print               Prints a description of the new appointment
-README              Prints a README for this project and exits

Date and time should be in the format: mm/dd/yyyy hh:mm am/pm
End Time Can Not Be Before Begin Time

Owner and Descriptions should be wrapped in quotes.

If the owner entered does not exist the application will attempt to
create a new appointment book for owner to save appointments to.

When using -README option the README.txt file will print to the screen and quit the application.
To use the application to add an appointment, please remove the -README option from the command line.