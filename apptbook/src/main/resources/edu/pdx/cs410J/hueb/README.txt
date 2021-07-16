This is a README file!

Sam Huebschwerlen
Project 2

User can enter in a name, appointment description, begin time, and end time
into the command line and the program will create a new appointment book belonging
to the name entered, and then add an appointment to the book with the appointment
description, begin and end time entered.

The description of the appointment can be printed after entering
your information by prefacing your info with the -print option.

Users can also read and write appointments to and from an external
file of their choice.

If owner name on external file appointments doesn't match owner name on
new added appointment the appointment will not be added to the file.

usage: java -jar target/apptbook-2021.0.0.jar [options] <args>
args are (in this order):

owner                The person who owns the appt book
description          A description of the appointment
begin                When the appt begins (24-hour time)
end                  When the appt ends (24-hour time)

options are (options may appear in any order):
-textFile file      Where to read/write the appointment book
-print              Prints a description of the new appointment
-README             Prints a README for this project and exits

Date and time should be in the format: mm/dd/yyyy hh:mm

Owner and Descriptions should be wrapped in quotes.

If the file entered does not exist the application will attempt to
create a new external file of the same name entered to save appointments to.

When using -README option the README.txt file will print to the screen and quit the application.
To use the application to add an appointment, please remove the -README option from the command line.

