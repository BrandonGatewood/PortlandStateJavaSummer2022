Brandon Gatewood
Project 2

This project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill. Users are given an option to print a newly added phone call, print the README for the project, or add a phone bill and all its phone calls to a given .txt file.

To use run this project on the command line, [options] are optional and <args> are mandatory.

java -jar target/phonebill-2022.0.0.jar [options] <args>

[options] can be entered in any order, except when using the '-textFile' option, a path to a .txt file must be entered right after it.
    Supported [options] include:
    '-print', '-README', and "'-textFile' '.txt'"

    '-print' option will print out the customers Phone Bill entered from the command line.
    '-README' option will display the README file for this project.
    "'-textFile' '.txt'" option will parse through the given '.txt' file, create a new phone bill for the given customer, then add all calls from the given .txt file and the command line. It will then write back to the .txt with the new phone call.

<args> must be entered in order:
    customer name, caller number (nnn-nnn-nnnn), callee number (nnn-nnn-nnnn), begin date (dd/dd/dddd), begin time (dd:dd), end date (dd/dd/dddd), and end time (dd:dd).

    Customer name can contain characters and digits.
    Caller and Callee number can be any digit from 0-9.
    Begin and End date can be any digit from 00/00/0000 - 99/99/9999.
    Begin and End time can be any digit from 00:00 - 99:99.