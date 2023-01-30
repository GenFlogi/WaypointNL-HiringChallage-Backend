package nl.waypoint.brombauer.bl;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.waypoint.brombauer.pojo.Container;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class DataGenerator {


    private String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    private String generateCurrentDateTime() {
        return new SimpleDateFormat("dd MMMM yyyy HH:mm").format(new Date());
    }

    private Double generateRandomDouble() {
        Random r = new Random();
        Double decimal = r.nextDouble(100) + 1;
        return roundToDecimals(decimal, 4);
    }

    private Double divideBySmaller(Double a, Double b) {
        if (a > b) {
            return a / b;
        } else {
            return b / a;
        }
    }

    private Double roundToDecimals(Double value, Integer places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private String generateMD5Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        StringBuilder hashtext = new StringBuilder(number.toString(16));
        while (hashtext.length() < 32) {
            hashtext.insert(0, "0");
        }
        return hashtext.toString();
    }

    public static Container generateContainer() throws NoSuchAlgorithmException {
        DataGenerator dataGenerator = new DataGenerator();
        String uuid = dataGenerator.generateUUID();
        String currentDateTime = dataGenerator.generateCurrentDateTime();
        Double a = dataGenerator.generateRandomDouble();
        Double b = dataGenerator.generateRandomDouble();
        Double c = dataGenerator.divideBySmaller(a, b);
        Double roundedDecimal = dataGenerator.roundToDecimals(c, 2);
        StringBuilder calculationResult = new StringBuilder();
        calculationResult.append(String.format("%.4f", a));
        calculationResult.append(" / ");
        calculationResult.append(String.format("%.4f", b));
        calculationResult.append(" = ");
        calculationResult.append(String.format("%.2f", roundedDecimal));

        String hash = dataGenerator.generateMD5Hash(uuid + currentDateTime + roundedDecimal);
        return new Container(uuid, currentDateTime, roundedDecimal, calculationResult, hash);
    }

    public static void main(String[] args) {
        try {
            generateAndWriteToJsonFile(Path.of("src/main/resources/data.json"));
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateAndWriteToJsonFile(Path path) throws IOException, NoSuchAlgorithmException {
        Container container = DataGenerator.generateContainer();
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            ObjectMapper mapper = new ObjectMapper();
            writer.write(mapper.writeValueAsString(container));
        }
    }
}
