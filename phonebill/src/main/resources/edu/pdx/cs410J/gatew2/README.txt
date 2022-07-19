Brandon Gatewood
Project 3

This project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill. Users are given an option to print a newly added phone call, print the README for the project, or add a phone bill and all its phone calls to a given .txt file.

To use run this project on the command line, [options] are optional and <args> are mandatory.

java -jar target/phonebill-2022.0.0.jar [options] <args>

[options] can be entered in any order, except when using the '-textFile' and '-pretty' option, a path to a .txt file must be entered right after it.
    Supported [options] include:
    '-print', '-README', "'-textFile' '.txt'", and "'-pretty' '.txt'"

    '-print' option will print out the customers Phone Bill entered from the command line.
    '-README' option will display the README file for this project.
    "'-textFile' '.txt'" option will parse through the given '.txt' file, create a new phone bill for the given customer, then add all calls from the given .txt file and the command line. It will then write back to the .txt with the new phone call.
    "'-pretty' '.txt'" option will dump a PhoneBill and its PhoneCalls into the '.txt' file in a pretty format, and then read the '.txt' file to Standard Output.

    NOTE: Each Customer will have their own .txt file and will not check if each customer name in the file are the same.

<args> must be entered in order:
    customer name, caller number (nnn-nnn-nnnn), callee number (nnn-nnn-nnnn), begin date (dd/dd/dddd), begin time (dd:dd), am/pm, end date (dd/dd/dddd), and end time (dd:dd), am/pm.

    FOR Customer name: can contain characters and digits.
    FOR Caller and Callee number: can be any digit from 0-9.
    FOR Begin and End Date: month and day can be from 1-69 or 01-69 BUT cannot be "0".
    FOR Begin and End Time: hour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69.
    FOR am/pm: can only be the String "am" or "pm".