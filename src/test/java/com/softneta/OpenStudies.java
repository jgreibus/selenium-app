package com.softneta;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class OpenStudies {

    private String studyUID;
    private static final int TIMEOUT = 6; // seconds
    private static StringBuffer verificationErrors = new StringBuffer();
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    static TestProperties testConf = new TestProperties();

    public OpenStudies(String studyUID) {
        this.studyUID = studyUID;
    }

    @Parameterized.Parameters
    public static Collection<Object []> getStudiesUID () throws FileNotFoundException {

        File testData = new File(testConf.getListPath());
        Scanner scanner = new Scanner(testData);
        Collection<Object []> studies = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String testDataArray[] = line.split(",");
            studies.add(new Object[] {testDataArray[0]});
        }
        return studies;
    }

    private static ArrayList<Study> getStudies() throws FileNotFoundException {
        File testData = new File(testConf.getListPath());
        Scanner scanner  = new Scanner(testData);

        ArrayList<Study> studies = new ArrayList<Study>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String studyData[] = line.split(",");
            studies.add(new Study(studyData[0], studyData[1], studyData[2], studyData[3], studyData[4]));
        }
        return studies;
    }

    private static Study getStudy(String uid) throws FileNotFoundException {
        ArrayList<Study> studyList = new ArrayList<Study>();
        studyList = getStudies();
        for(Study study : studyList){
            if(study.getUid().equals(uid)) return study;
        }
        return null;
    }

    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setupTest() throws IOException, InterruptedException {
        driver = new ChromeDriver();
        driver.get(testConf.getUrl());
        driver.manage().window().maximize();
        System.out.println("Login");
        driver.findElement(By.name("user")).clear();
        driver.findElement(By.name("user")).sendKeys(testConf.getUsername());
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(testConf.getPassword());
        driver.findElement(By.name("Submit")).click();
        if (driver instanceof JavascriptExecutor) {
            js = (JavascriptExecutor)driver;
        }
        wait = new WebDriverWait(driver, TIMEOUT);
    }
    @After
    public void teardown()
    {
        if (driver != null) {
            driver.quit();
        }
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {

            fail(verificationErrorString);
        }
    }
    private void Logoff(){
        try{
            driver.get(testConf.getUrl() +"/Logoff.php");
        }
        catch(Exception e){
            System.err.println("Log off was not successful");
        }
    }

    @Test
    public void imageOpenTest() throws InterruptedException, IOException {

        driver.get(testConf.getUrl() +"/md5/index.html?study="+studyUID);
        Thread.sleep(13000);

        if(getStudy(studyUID)!= null) {
            Study study = getStudy(studyUID);
            System.out.println("Study metadata:");
            System.out.println("SOP class: " + study.getSopClass());
            System.out.println("Transfer syntax: " + study.getTransferSyntax());
            System.out.println("Photometric interpretation: " + study.getPhotometric());
        }

        WebElement canvas = driver.findElement(By.id("container-1-1"));
        saveCanvas("Study_"+studyUID,canvas);
        Logoff();
    }
    private void saveCanvas(String name, WebElement canvas) {
        try {
            new File("C:\\Target\\").mkdirs();
            FileUtils.copyFile(WebElementExtender.captureElementBitmap(canvas), new File("C:\\Target\\" + name + ".png"));
            System.out.println("Canvas saved (" + name + ".png).");
        } catch (Exception e) {
            System.err.println("Failed to save canvas (" + name + ".png).");
        }
    }

    @Test
    public void compareImages() throws IOException {
        File source = new File("C:\\Target\\study_"+studyUID+".png");
        File target = new File("C:\\Target\\image.jpg");
        System.out.println(getSimiliarityProcentage(source, target));
    }

    public float getSimiliarityProcentage(File source, File target) throws IOException {

        float procentage = 0;

        BufferedImage bufferedSource = ImageIO.read(source);
        DataBuffer dataBufferSource = bufferedSource.getData().getDataBuffer();
        int sourceSize = dataBufferSource.getSize();

        BufferedImage bufferedTarget = ImageIO.read(target);
        DataBuffer dataBufferTarget = bufferedTarget.getData().getDataBuffer();
        int targetSize = dataBufferSource.getSize();
        double count = 0;
        System.out.println("Source size "+sourceSize);
        System.out.println("Target size "+targetSize);
        if(sourceSize == targetSize){
            for(int i=0; i < sourceSize; i++){
                if(dataBufferSource.getElem(i) == dataBufferTarget.getElem(i)) count = count+1;
            }
            System.out.println("count "+(count*100));
            System.out.println("source size "+sourceSize);
            procentage = (float) ((count * 100)/sourceSize);
            System.out.println("Procentage "+procentage);
        }
        else{
            System.out.println("Images are not of the same size");
        }
        return procentage;
    }


}
