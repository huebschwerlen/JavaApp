package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class TextParser implements AppointmentBookParser {

    protected final String fileName;
    private final String absolutePath = new File("").getAbsolutePath();
    private String filePath = absolutePath+"/src/main/resources/edu/pdx/cs410J/hueb/";
//    private String filePath = "/Users/sam/Desktop/PortlandStateJavaSummer2021/apptbook/src/main/resources/edu/pdx/cs410J/hueb/";

    /**
     * Creates a new <code>TextParser</code>
     *
     *@param file
     *      The name of the file that is meant to be parsed
     */
    public TextParser(String file) {
        this.fileName = file;
    }


    /**
     * Parses file at fileName and returns
     *      an appointment book with appointments added from
     *      external file
     */
    @Override
    public AppointmentBook parse() throws ParserException {

        try {
//            InputStream file = Project1.class.getResourceAsStream(this.fileName);
////            InputStream file = Project1.class.getResourceAsStream("TEST2.txt");
//
//            if (file == null) {
//                System.out.println("File: " + file);
//                file = getClass().getClassLoader().getResourceAsStream(filePath+this.fileName);
//
////                String test = getClass().getClassLoader().getResourceAsStream(this.fileName);
//                System.out.println("File: " + file);
//                if (file == null) {
//                    System.out.println("File Still: " + file);
////                    file = getResourceAsStream(this.fileName);
//                }
//
//            }
//
//
//
//            InputStreamReader inputStream = new InputStreamReader(file);
////            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
//            BufferedReader reader = new BufferedReader(inputStream);
//
//            FileReader fr = new FileReader();



            BufferedReader br = new BufferedReader(new FileReader(filePath+this.fileName));





            String line;
            String owner=null;
            ArrayList<String> data = new ArrayList<>();
            ArrayList<String> ownerlist = new ArrayList<>();
            AppointmentBook aptBook = new AppointmentBook();


//            while ((line = reader.readLine()) != null) {
//                data.add(line);
//            }


            while ((line = br.readLine()) != null) {
                data.add(line);
            }


//            System.out.println(data);


            int count = 0;
            for (String row : data) {
                String[] values = row.split("--");

                count++;
//                System.out.println(values);

                if(values.length==6) {
                    owner = values[0];
                    ownerlist.add(owner);
                    String beginTime = values[2] + " " + values[3];
                    String endTime = values[4] + " " + values[5];
                    Appointment apt = new Appointment(values[1], beginTime, endTime);
                    aptBook.addAppointment(apt);
                } else {
//                    System.out.println("Line from External Storage File May Be Malformatted and was Not Loaded to Apt Book: " + Arrays.toString(values));
                    System.err.println("\nLine " + count + " in your external appointment book storage file: " + this.fileName + " may be malformatted.\nPlease Check and Try Again"+
                            "\nRemember each line in your storage file represents an appointment in the format:" +
                            "\nowner--description--begin date<mm/dd/yyyy>--begin time<hh:mm>--end date<mm/dd/yyyy>--end time<hh:mm>" +
                            "\nexample) Buffy Summers--Slay Vampires--07/31/1992--11:30--12/11/2001--16:30"
                            +"\nExiting...\n");
                    System.exit(1);
                }

            }
            //citation: https://stackoverflow.com/questions/22989806/find-the-most-common-string-in-arraylist
            String mostRepeatedWord = ownerlist.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting())).entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();

            owner = mostRepeatedWord;

//            System.out.println("\nOwner that appears the most: "+owner);

            aptBook.setOwnerName(owner);

            return aptBook;

        } catch (FileNotFoundException e) {
            System.err.println("FileNotFound: " + this.fileName + "\nPlease try again with new file name\n");
//            System.exit(1);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.err.println("UnsupportedEncoding found in TextParser");
//            System.exit(1);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOexception found in TextParser");
//            System.exit(1);
            e.printStackTrace();
        }

        return new AppointmentBook();

    }


}
