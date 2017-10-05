package com.softneta;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static String sourcePath = "D:\\TestDicom";
    private static String destPath = "D:\\TestDicom";

    public static void main(String[] args) throws IOException {

        generateTestDataFile(readFileList(sourcePath),destPath);
    }

    public static ArrayList<TestDataEntry> readFileList(String sourcePath) throws IOException {
        ArrayList<TestDataEntry> testDataList = new ArrayList<>();
        List<File> listOfFiles = Files.walk(Paths.get(sourcePath))
                .filter(Files::isRegularFile)
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        for (File file : listOfFiles) {
            if (file.getName().contains(".dcm")) {
                testDataList.add(readMetaData(file));
            }
        }
        return testDataList;
    }

    private static TestDataEntry readMetaData(File file) {
        DicomObject dcmObj;
        DicomInputStream din = null;
        TestDataEntry dataEntry;
        try {
            din = new DicomInputStream(file);
            dcmObj = din.readDicomObject();

            dataEntry = new TestDataEntry(dcmObj.getString(Tag.StudyInstanceUID),
                    dcmObj.getString(Tag.StudyInstanceUID),
                    dcmObj.getString(Tag.SOPClassUID),
                    dcmObj.getString(Tag.TransferSyntaxUID),
                    dcmObj.getString(Tag.PhotometricInterpretation),
                    dcmObj.getString(Tag.BitsStored),
                    dcmObj.getString(Tag.SeriesDescription));

            return dataEntry;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                din.close();
            }
            catch (IOException ignore) {
            }
        }
    }

    private static void generateTestDataFile(ArrayList<TestDataEntry> testDataList, String path) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        String line;
        for(TestDataEntry entry : testDataList){
            line = entry.getStudyUID()+","
                    +entry.getInstanceUID()+","
                    +entry.getSopClass()+","
                    +entry.getTransferSyntax()+","
                    +entry.getPhotometricIntepretation()+","
                    +entry.getBitsSotred()+","
                    +entry.getInstanceDescrition();
            sb.append(line);
            sb.append("\n");
        }
        FileOutputStream out = new FileOutputStream(path+"testDataList.txt");
        try {
            System.out.println(sb.toString());
            out.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
