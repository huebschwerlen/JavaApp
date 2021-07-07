This is a README file!

Sam Huebschwerlen
Project 1

User can enter in a name, appointment description, begin time, and end time
into the command line and the program will create a new appointment book belonging
to the name entered, and then add an appointment to the book with the appointment
description, begin and end time entered.

The description of the appointment can be printed after entering
your information by prefacing your info with the -print option.

usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>
args are (in this order):

owner                The person who owns the appt book
description          A description of the appointment
begin                When the appt begins (24-hour time)
end                  When the appt ends (24-hour time)

options are (options may appear in any order):
-print              Prints a description of the new appointment
-README             Prints a README for this project and exits

Date and time should be in the format: mm/dd/yyyy hh:mm

Owner and Descriptions should be wrapped in quotes.

When using -README option the README.txt file will print to the screen and quit the application.
To use the application to add an appointment, please remove the -README option from the command line.

