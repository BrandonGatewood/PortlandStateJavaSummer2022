package edu.pdx.cs410J.gatew2;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class Project4Test {
    @Test
    void checkForInputOptionsREADME() throws IOException, ParseException {
        //GIVEN that the '-README' option is entered
        ArrayList<String> args = new ArrayList<>();
        args.add("-host");
        args.add("localHost");
        args.add("-port");
        args.add("8081");
        args.add("-README");

        //WHEN '-README' option is entered
        //THEN output should be: README.txt
        assertThat(Project4.checkForInputOptions(args), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
    }



    @Test
    void checkForInputOptionsSearchAndPrint() throws ParseException, IOException {
        //GIVEN that the '-search' and '-print' option is entered
        ArrayList<String> args = new ArrayList<>();
        args.add("-host");
        args.add("localHost");
        args.add("-port");
        args.add("8081");
        args.add("-search");
        args.add("-print");

        //WHEN '-search'  and '-print' option is entered
        //THEN output should be: "'-search' and '-print' options cannot be used at the same time."
        assertThat(Project4.checkForInputOptions(args), equalTo("'-search' and '-print' options cannot be used at the same time."));
    }


    /*
        For validating argument length with '-search' option.
     */
    @Test
    void validateArgumentLengthSearch() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);
        // GIVEN that too many args are entered with '-search' option
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("12/21/2021");
        args.add("1:40");
        args.add("pm");
        args.add("12/21/2021");
        args.add("1:50");
        args.add("pm");
        args.add("bg");
        args.add("-host");

        // WHEN too many arguments are entered with '-search' option
        // THEN the output should be: "Too many arguments".
        assertThat(Project4.validateArgumentsLength(client, args, false, true), equalTo("Too many arguments."));
    }

    @Test
    void validateArgumentLengthSearch0() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 0 args are entered with '-search' option
        List<String> args = new ArrayList<>();

        // WHEN 0 arguments are entered with '-search' option
        // THEN the output should be: "Missing customer name."
        assertThat(Project4.validateArgumentsLength(client, args, false, true), equalTo("Missing customer name."));
    }

    @Test
    void validateArgumentLengthSearch1() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 1 args are entered with '-search' option
        List<String> args = new ArrayList<>();
        args.add("Brandon");

        // WHEN 1 arguments are entered with '-search' option
        // THEN the output should be: "Missing beginning date (mm/dd/yyyy)."
        assertThat(Project4.validateArgumentsLength(client, args, false, true), equalTo("Missing beginning date (mm/dd/yyyy)."));
    }

    @Test
    void validateArgumentLengthSearch2() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 2 args are entered with '-search' option
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("12/21/2021");

        // WHEN 2 arguments are entered with '-search' option
        // THEN the output should be: "Missing beginning time (hh:mm)."
        assertThat(Project4.validateArgumentsLength(client, args, false, true), equalTo("Missing beginning time (hh:mm)."));
    }

    @Test
    void validateArgumentLengthSearch3() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 3 args are entered with '-search' option
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("12/21/2021");
        args.add("1:40");

        // WHEN 3 arguments are entered with '-search' option
        // THEN the output should be: "Missing ending "am"/"pm"."
        assertThat(Project4.validateArgumentsLength(client, args, false, true), equalTo("Missing ending \"am\"/\"pm\"."));
    }

    @Test
    void validateArgumentLengthSearch4() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 4 args are entered with '-search' option
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("12/21/2021");
        args.add("1:40");
        args.add("pm");

        // WHEN 4 arguments are entered with '-search' option
        // THEN the output should be: "Missing ending date (mm/dd/yyyy)."
        assertThat(Project4.validateArgumentsLength(client, args, false, true), equalTo("Missing ending date (mm/dd/yyyy)."));
    }

    @Test
    void validateArgumentLengthSearch5() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 5 args are entered with '-search' option
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("12/21/2021");
        args.add("1:40");
        args.add("pm");
        args.add("12/21/2021");

        // WHEN 5 arguments are entered with '-search' option
        // THEN the output should be: "Missing ending time (hh:mm)."
        assertThat(Project4.validateArgumentsLength(client, args, false, true), equalTo("Missing ending time (hh:mm)."));
    }

    @Test
    void validateArgumentLengthSearch6() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 6 args are entered with '-search' option
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("12/21/2021");
        args.add("1:40");
        args.add("pm");
        args.add("12/21/2021");
        args.add("1:50");

        // WHEN 6 arguments are entered with '-search' option
        // THEN the output should be: "Missing ending "am"/"pm"."
        assertThat(Project4.validateArgumentsLength(client, args, false, true), equalTo("Missing ending \"am\"/\"pm\"."));
    }









    /*
        For validating argument length with no options and length > 1
     */
    @Test
    void validateArgumentLengthOf2() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 2 args are entered
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");

        // WHEN 2 arg is entered
        // THEN the output should be: "Missing callee number (nnn-nnn-nnnn)."
        assertThat(Project4.validateArgumentsLength(client, args, false, false), equalTo("Missing callee number (nnn-nnn-nnnn)."));
    }

    @Test
    void validateArgumentLengthOf3() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 3 args are entered
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");

        // WHEN 3 arg is entered
        // THEN the output should be: "Missing beginning date (mm/dd/yyyy)."
        assertThat(Project4.validateArgumentsLength(client, args, false, false), equalTo("Missing beginning date (mm/dd/yyyy)."));
    }

    @Test
    void validateArgumentLengthOf4() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 4 args are entered
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/2021");

        // WHEN 4 arg is entered
        // THEN the output should be: "Missing beginning time (hh:mm)."
        assertThat(Project4.validateArgumentsLength(client, args, false, false), equalTo("Missing beginning time (hh:mm)."));
    }

    @Test
    void validateArgumentLengthOf5() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 5 args are entered
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/2021");
        args.add("2:20");

        // WHEN 5 arg is entered
        // THEN the output should be: "Missing ending "am"/"pm"."
        assertThat(Project4.validateArgumentsLength(client, args, false, false), equalTo("Missing ending \"am\"/\"pm\"."));
    }

    @Test
    void validateArgumentLengthOf6() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 6 args are entered
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("am");

        // WHEN 6 arg is entered
        // THEN the output should be: "Missing ending date (mm/dd/yyyy)."
        assertThat(Project4.validateArgumentsLength(client, args, false, false), equalTo("Missing ending date (mm/dd/yyyy)."));
    }

    @Test
    void validateArgumentLengthOf7() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 7 args are entered
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("am");
        args.add("12/21/2021");

        // WHEN 7 arg is entered
        // THEN the output should be: "Missing ending time (hh:mm)."
        assertThat(Project4.validateArgumentsLength(client, args, false, false), equalTo("Missing ending time (hh:mm)."));
    }

    @Test
    void validateArgumentLengthOf8() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 8 args are entered
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("am");
        args.add("12/21/2021");
        args.add("2:50");

        // WHEN 8 arg is entered
        // THEN the output should be: "Missing ending "am"/"pm"."
        assertThat(Project4.validateArgumentsLength(client, args, false, false), equalTo("Missing ending \"am\"/\"pm\"."));
    }
    @Test
    void validateArgumentLengthOf10() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 8080;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that 10 or more args are entered
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("am");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("am");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("am");

        // WHEN 10 or more arg is entered
        // THEN the output should be: "Too many arguments."
        assertThat(Project4.validateArgumentsLength(client, args, false, false), equalTo("Too many arguments."));
    }


    /*
        For validating each argument
     */
    @Test
    void validateEachArgumentCaller() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that caller number is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123/456/7890");
        args.add("123-123-4567");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("am");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("am");

        // WHEN caller number is in an invalid format.
        // THEN the output should be: "Caller number can only be in the format: nnn-nnn-nnnn."
        assertThat(Project4.validateEachArgument(client, args, false), equalTo("Caller number can only be in the format: nnn-nnn-nnnn."));
    }

    @Test
    void validateEachArgumentCallee() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that callee number is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("13-123-4567");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("am");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("am");

        // WHEN callee number is in an invalid format.
        // THEN the output should be: "Callee number can only be in the format: nnn-nnn-nnnn."
        assertThat(Project4.validateEachArgument(client, args, false), equalTo("Callee number can only be in the format: nnn-nnn-nnnn."));
    }

    @Test
    void validateEachArgumentBeginDate() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that begin date is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/21");
        args.add("2:20");
        args.add("am");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("am");

        // WHEN begin date is in an invalid format.
        // THEN the output should be: "Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be "0"."
        assertThat(Project4.validateEachArgument(client, args, false), equalTo("Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\"."));
    }

    @Test
    void validateEachArgumentBeginTime() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that begin time is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/2021");
        args.add("59.20");
        args.add("am");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("am");

        // WHEN begin time is in an invalid format.
        // THEN the output should be: "Beginning time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."
        assertThat(Project4.validateEachArgument(client, args, false), equalTo("Beginning time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."));
    }

    @Test
    void validateEachArgumentBeginAmPm() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that begin ampm is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("dm");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("am");

        // WHEN begin ampm is in an invalid format.
        // THEN the output should be: "Beginning "am"/"pm" can only be in the format: "am"/"pm"."
        assertThat(Project4.validateEachArgument(client, args, false), equalTo("Beginning \"am\"/\"pm\" can only be in the format: \"am\"/\"pm\"."));
    }

    @Test
    void validateEachArgumentEndDate() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that end date is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("am");
        args.add("70/21/2021");
        args.add("2:50");
        args.add("am");

        // WHEN end date is in an invalid format.
        // THEN the output should be: "Ending date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be "0"."
        assertThat(Project4.validateEachArgument(client, args, false), equalTo("Ending date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\"."));
    }

    @Test
    void validateEachArgumentEndTime() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that end time is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("am");
        args.add("12/21/2021");
        args.add("2:70");
        args.add("am");

        // WHEN end time is in an invalid format.
        // THEN the output should be: "Ending time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."
        assertThat(Project4.validateEachArgument(client, args, false), equalTo("Ending time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."));
    }

    @Test
    void validateEachArgumentEndAmPm() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that end ampm is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("123-456-7890");
        args.add("123-123-4567");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("am");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("tm");

        // WHEN end ampm is in an invalid format.
        // THEN the output should be: "Ending "am"/"pm" can only be in the format: "am"/"pm"."
        assertThat(Project4.validateEachArgument(client, args, false), equalTo("Ending \"am\"/\"pm\" can only be in the format: \"am\"/\"pm\"."));
    }








    /*
        For validating each argument for searching dates
     */
    @Test
    void validateEachArgumentForDatesBeginDate() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that begin date is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("0/21/2021");
        args.add("2:20");
        args.add("am");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("am");

        // WHEN begin date is in an invalid format.
        // THEN the output should be: "Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be "0"."
        assertThat(Project4.validateEachArgumentForDates(client, args), equalTo("Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\"."));
    }

    @Test
    void validateEachArgumentForDatesBeginTime() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that begin time is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("12/21/2021");
        args.add("70:20");
        args.add("am");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("am");

        // WHEN begin time is in an invalid format.
        // THEN the output should be: "Beginning time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."
        assertThat(Project4.validateEachArgumentForDates(client, args), equalTo("Beginning time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."));
    }

    @Test
    void validateEachArgumentForDatesBeginAmPm() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that begin ampm is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("morning");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("am");

        // WHEN begin ampm is in an invalid format.
        // THEN the output should be: "Beginning "am"/"pm" can only be in the format: "am"/"pm"."
        assertThat(Project4.validateEachArgumentForDates(client, args), equalTo("Beginning \"am\"/\"pm\" can only be in the format: \"am\"/\"pm\"."));
    }

    @Test
    void validateEachArgumentForDatesEndDate() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that end date is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("pm");
        args.add("12/0/2021");
        args.add("2:50");
        args.add("pm");

        // WHEN end date is in an invalid format.
        // THEN the output should be: "Ending date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be "0"."
        assertThat(Project4.validateEachArgumentForDates(client, args), equalTo("Ending date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\"."));
    }

    @Test
    void validateEachArgumentForDatesEndTime() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that end time is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("pm");
        args.add("12/21/2021");
        args.add("2;50");
        args.add("pm");

        // WHEN end time is in an invalid format.
        // THEN the output should be: "Ending time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."
        assertThat(Project4.validateEachArgumentForDates(client, args), equalTo("Ending time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."));
    }

    @Test
    void validateEachArgumentForDatesEndAmPm() throws ParseException, IOException {
        String hostName = "localhost";
        int port = 12345;
        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // GIVEN that end ampm is in an invalid format.
        List<String> args = new ArrayList<>();
        args.add("Brandon");
        args.add("12/21/2021");
        args.add("2:20");
        args.add("pm");
        args.add("12/21/2021");
        args.add("2:50");
        args.add("night");

        // WHEN end ampm is in an invalid format.
        // THEN the output should be: "Ending "am"/"pm" can only be in the format: "am"/"pm"."
        assertThat(Project4.validateEachArgumentForDates(client, args), equalTo("Ending \"am\"/\"pm\" can only be in the format: \"am\"/\"pm\"."));
    }








    /*
        For checking if end date is before begin date
     */
    @Test
    void checkDates1() throws ParseException {
        // GIVEN that end date is before begin date
        String beginDate = "12/21/2021";
        String beginTime = "2:20";
        String beginAmPm = "pm";
        String endDate = "12/21/2021";
        String endTime = "1:50";
        String endAmPm = "pm";

        // WHEN end date is before begin date
        // THEN the output should be: "Call end time cannot be before begin time."
        assertThat(Project4.checkDates(beginDate, beginTime, beginAmPm, endDate, endTime, endAmPm), equalTo("Call end time cannot be before begin time."));
    }


    @Test
    void checkDates2() throws ParseException {
        // GIVEN that end date is before begin date
        String beginDate = "12/21/2021";
        String beginTime = "2:20";
        String beginAmPm = "pm";
        String endDate = "12/21/2021";
        String endTime = "1:50";
        String endAmPm = "pm";

        // WHEN end date is before begin date
        // THEN the output should be: "Call end time cannot be before begin time."
        assertThat(Project4.checkDates(endDate, endTime, endAmPm, beginDate, beginTime, beginAmPm), equalTo(null));
    }
}