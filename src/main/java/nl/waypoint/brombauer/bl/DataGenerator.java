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
        Double decimal = null;
        /*while(decimal == null || decimal%4 != 0) {
            decimal = r.nextDouble(100)+1;
        }*/
        decimal = r.nextDouble(100) + 1;
        return decimal;
    }

    private Double divideBySmaller(Double a, Double b) {
        if (a > b) {
            return a / b;
        } else {
            return b / a;
        }
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

    public Container generateContainer() throws NoSuchAlgorithmException {
        String uuid = generateUUID();
        String currentDateTime = generateCurrentDateTime();
        Double a = generateRandomDouble();
        Double b = generateRandomDouble();
        Double c = divideBySmaller(a, b);
        Double roundedDecimal = Math.round(c * 100.0) / 100.0;
        StringBuilder calculationResult = new StringBuilder();
        calculationResult.append(a);
        calculationResult.append(" / ");
        calculationResult.append(b);
        calculationResult.append(" = ");
        calculationResult.append(roundedDecimal);
        calculationResult.append(" (");
        String hash = generateMD5Hash(uuid + currentDateTime + roundedDecimal);
        calculationResult.append(hash);
        calculationResult.append(")");
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
        Container container = new DataGenerator().generateContainer();
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            ObjectMapper mapper = new ObjectMapper();
            writer.write(mapper.writeValueAsString(container));
        }
    }
}
