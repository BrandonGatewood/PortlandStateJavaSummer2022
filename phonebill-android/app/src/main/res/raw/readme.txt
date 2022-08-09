Project 5
Brandon Gatewood

This project represents a phone bill application that keeps track of its customers and their phone calls. Each phone call is saved into a .txt for their phone bill.

Users are given an option to print the README for the project, add a phone call into a new or an existing phone bill and search through a customers phone bill.

The "HELP" button will bring the user to a new page, where it will display this README file.


The "AddToPhoneBill" button will bring the user to a new page, where the user must fill out the appropriate information.
    It will ask for the customer name, caller number, callee number, begin date and end date. Once the information has been filled out, user may press the submit button to submit the new phone call or the reset button to reset all the input fields.

    If all inputs are valid information, it will bring the user to the main menu and a popup will show the newly added phone call.
    If an input is invalid, a pop up will show explaining which information was invalid.

    NOTE:
        Customer name can contain characters and digits.
        Caller number can be any digit from 0-9. But must be in the format: (nnn-nnn-nnnn).
        Callee number can be any digit from 0-9. But must be in the format: (nnn-nnn-nnnn).
        Begin time must be in the format: "dd/dd/dddd dd:dd". Month and day can be from 1-69 or 01-69 BUT cannot be "0". Hour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69.
        End date must be in the format: "dd/dd/dddd dd:dd". Month and day can be from 1-69 or 01-69 BUT cannot be "0". Hour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69.

        For both begin and end date, you MUST select the "am" or "pm" option.


The "pretty_print_phone_bill" button will bring the user to a new page, where the user must enter a customer name.
    The user may press the submit button to pretty print or the reset button to reset the input field.

    If a customer exist, it will pretty print all of its phone calls.
    If a customer doesn't exist, a pop up will appear stating that the customer wasnt found.

    NOTE:
    Customer name can contain characters and digits.


The "search_a_phone_bill", button will bring the user to a new page, where the user must enter the customer name, a beginning date and an ending date.
    It will ask for the customer name, a beginning date, and an ending date. Once the information has been filled out, user may press the submit button to pretty print or the reset button to reset the input fields.

    If all inputs are valid information, it will display all of the customers phone calls within the beginning and ending date.
        If a customers phone bill is empty or has no phone calls within the specified time, it will display "'name' has no phone calls within the specified time".
    If an input is invalid, a pop up will show explaining which information was invalid.

    NOTE:
        Customer name can contain characters and digits.
        Begin time must be in the format: "dd/dd/dddd dd:dd". Month and day can be from 1-69 or 01-69 BUT cannot be "0". Hour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69.
        End date must be in the format: "dd/dd/dddd dd:dd". Month and day can be from 1-69 or 01-69 BUT cannot be "0". Hour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69.

        For both begin and end date, you MUST select the "am" or "pm" option.