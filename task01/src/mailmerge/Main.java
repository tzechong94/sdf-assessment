package mailmerge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    static String csvFile;
    static String templateFile;
    static List<List<String>> fileList = new ArrayList<List<String>>();
    static List<String> lineList = new ArrayList<String>();
    static List<String> mapValueList = new ArrayList<String>();
    static String line;
    static List<String> keywords = new ArrayList<String>();
    static Map<String, List<String>> peopleMap = new HashMap<String, List<String>>();

    public static void main(String[] args) throws Exception {

        csvFile = args[0];
        templateFile = args[1];
        
        ReadCsv(csvFile, templateFile);

    }        

    public static void ReadCsv(String csvFileName, String templateFileName) throws Exception {
        File file = new File(csvFileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        fileList = br.lines()
                    .map(l -> Arrays.stream(l.split(","))
                    .collect(Collectors.toList()))
                    .collect(Collectors.toList());

        br.close();
        
        System.out.println(fileList + "\n");

        // List to map to store key: value pairs

        for (Integer i = 0; i < fileList.get(0).size();i++) {
            for (Integer j = 1; j < fileList.size(); j++){
                mapValueList.add(fileList.get(j).get(i));
            }
            peopleMap.put(fileList.get(0).get(i), mapValueList);
            mapValueList = new ArrayList<String>();
        }

        System.out.println("database: \n" + peopleMap);

        File templatefile = new File(templateFileName);
        FileReader tfr = new FileReader(templatefile);
        BufferedReader tbr = new BufferedReader(tfr);
        String templateLine;
        String pattern1 = "<<";
        String pattern2 = ">>";
        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));

        // look for keywords bounded by << >> 

        while ((templateLine = tbr.readLine()) != null) {
            Matcher m = p.matcher(templateLine);
            while(m.find()) {
                keywords.add(m.group(1));
                // System.out.println("Test: " + m.group(1));
            }
        }
        tbr.close();

        System.out.println("\nkeywords: " + keywords);
        System.out.println("\npeoplemap: " + peopleMap);
        System.out.println("\nfilelist: " + fileList);
        // matching key with template

        for (Integer j = 0; j<fileList.size()-1;j++){
            String email = "";

            File templatefile1 = new File(templateFileName);
            FileReader tfr1 = new FileReader(templatefile1);
            BufferedReader tbr1 = new BufferedReader(tfr1);   
            while ((templateLine = tbr1.readLine()) != null) {
                if (templateLine.isEmpty()){
                    email += "\r\n\r\n";
                }
                for (Integer i = 0; i<keywords.size();i++) {
                    if (templateLine.contains(keywords.get(i))) {
                        // System.out.println(peopleMap.get("address").get(i));
                        templateLine = templateLine.replaceAll("<<" + keywords.get(i) + ">>", (peopleMap.get(keywords.get(i)).get(j)).replace("\\n",System.getProperty("line.separator")));
                }
                
            }
            email += templateLine;
        } 
        System.out.println(email+"\n");
        tbr1.close();
    }
        // }

        // replacing key with template 
    }
    
}