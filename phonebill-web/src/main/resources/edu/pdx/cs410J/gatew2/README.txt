Brandon Gatewood
Project 4

This project supports a phone bill server and provides REST-ful web services to a phone bill client.
The server will keep track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.
Users are given an option to print the README for the project, print a newly added phone call, or search a customers phone calls between a beginning and ending time.

To use run this project on the command line, [options] are optional and <args> are mandatory.

java -jar target/phonebill-2022.0.0.jar -host hostName -port port[options] <args>

[options] can be entered in any order
    Supported [options] include:
    -README', '-print', '-search'

    '-README' option will display the README file for this project.
    '-print' option will print out the customers Phone Bill entered from the command line.
    '-search' option will search for phone calls between a beginning and ending date.

<args> must be entered in order:
    customer name, caller number (nnn-nnn-nnnn), callee number (nnn-nnn-nnnn), begin date (dd/dd/dddd), begin time (dd:dd), am/pm, end date (dd/dd/dddd), and end time (dd:dd), am/pm.

    FOR Customer name: can contain characters and digits.
    FOR Caller and Callee number: can be any digit from 0-9.
    FOR Begin and End Date: month and day can be from 1-69 or 01-69 BUT cannot be "0".
    FOR Begin and End Time: hour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69.
    FOR am/pm: can only be the String "am" or "pm".