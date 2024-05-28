package org.example.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.imagecomparison.SimilarityMatchingOptions;
import io.appium.java_client.imagecomparison.SimilarityMatchingResult;
import org.apache.commons.codec.binary.Base64;
import org.example.interactions.ContactInteractions;
import org.example.utils.DriverUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ImageComparisonTest {
    AppiumDriver driver;
    ContactInteractions contactInteractions;

    public static byte[] getImageBytes(String imagePath) {
        try {
            Path path = Path.of(imagePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @BeforeEach
    public void init() {
        DriverUtils.initDriver();
        driver = DriverUtils.getDriver();
        contactInteractions = new ContactInteractions(driver);
    }

    private void saveImage(byte[] imageBytes, String fileName) {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(Base64.decodeBase64(imageBytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void compareImages() throws InterruptedException, IOException {
        Thread.sleep(500);
        byte[] originalImg = Base64.encodeBase64(getImageBytes("originalImage.png"));

        byte[] screenshot = Base64.encodeBase64(driver.getScreenshotAs(OutputType.BYTES));
        saveImage(screenshot, "screenshot.png");

//        FeaturesMatchingResult result = driver
//                .matchImagesFeatures(screenshot, originalImg, new FeaturesMatchingOptions()
//                        .withDetectorName(FeatureDetector.ORB)
//                        .withGoodMatchesFactor(98)
//                        .withMatchFunc(MatchingFunction.BRUTE_FORCE_HAMMING)
//                        .withEnabledVisualization());
//        assertThat(result.getVisualization().length, is(greaterThan(0)));
//        System.out.println(result.getVisualization().length);
//        System.out.println(result.getCount());
//        System.out.println(result.getTotalCount());
//        assertThat(result.getCount(), is(greaterThan(98)));
//        assertThat(result.getTotalCount(), is(greaterThan(0)));
//        assertFalse(result.getPoints1().isEmpty());
//        assertNotNull(result.getRect1());
//        assertFalse(result.getPoints2().isEmpty());
//        assertNotNull(result.getRect2());
        SimilarityMatchingResult result = driver
                .getImagesSimilarity(originalImg, screenshot, new SimilarityMatchingOptions()
                        .withEnabledVisualization());
        assertThat(result.getVisualization().length, is(greaterThan(0)));
        System.out.println(result.getVisualization().length);
        System.out.println(result.getScore());
        assertThat(result.getScore(), is(greaterThan(0.97)));
    }

    @Test
    public void compareImagesWithOpenCV() throws Exception {
        // Take a screenshot
        Thread.sleep(500);
        byte[] screenshot = Base64.encodeBase64(driver.getScreenshotAs(OutputType.BYTES));
        saveImage(screenshot, "screenshot.png");
        BufferedImage img1 = ImageIO.read(new File("originalImage.png"));
        BufferedImage img2 = ImageIO.read(new File("screenshot.png"));
        int w1 = img1.getWidth();
        int w2 = img2.getWidth();
        int h1 = img1.getHeight();
        int h2 = img2.getHeight();
        if ((w1 != w2) || (h1 != h2)) {
            System.out.println("Both images should have same dimwnsions");
        } else {
            long diff = 0;
            for (int j = 0; j < h1; j++) {
                for (int i = 0; i < w1; i++) {
                    //Getting the RGB values of a pixel
                    int pixel1 = img1.getRGB(i, j);
                    Color color1 = new Color(pixel1, true);
                    int r1 = color1.getRed();
                    int g1 = color1.getGreen();
                    int b1 = color1.getBlue();
                    int pixel2 = img2.getRGB(i, j);
                    Color color2 = new Color(pixel2, true);
                    int r2 = color2.getRed();
                    int g2 = color2.getGreen();
                    int b2 = color2.getBlue();
                    //sum of differences of RGB values of the two images
                    long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                    diff = diff + data;
                }
            }
            double avg = diff / ((long) w1 * h1 * 3);
            double percentage = (avg / 255) * 100;
            System.out.println("Difference: " + percentage);
            assertThat((long) percentage, is(lessThan(1L)));
        }
    }

    @AfterEach
    public void destroy() {
        DriverUtils.stopDriver();
    }
}
