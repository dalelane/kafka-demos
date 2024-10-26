package uk.co.dalelane.demos.ibmmq;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Year;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.github.javafaker.Commerce;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.PhoneNumber;

public class Putter extends App {

    private static final Faker faker = new Faker(Locale.UK);
    private static final Random rng = new Random();
    private static DateTimeFormatter dtFormatter = null;

    public static void main(String[] args) {
        try {
            final String MESSAGE_TEMPLATE = new String(Files.readAllBytes(Paths.get(getStringEnv("MESSAGE_TEMPLATE_PATH"))));
            dtFormatter = DateTimeFormatter.ofPattern(getStringEnv("TIMESTAMP_FORMAT"));
            int messageInterval = getIntEnv("MSG_INTERVAL");

            Connection connection = createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(getStringEnv("MQ_QUEUE"));
            MessageProducer producer = session.createProducer(destination);
            connection.start();

            while (true) {
                producer.send(session.createTextMessage(generateMessage(MESSAGE_TEMPLATE)));
                Thread.sleep(messageInterval);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String generateMessage(String template) {
        Name name = faker.name();
        Commerce commerce = faker.commerce();
        PhoneNumber phonenumber = faker.phoneNumber();
        boolean useCellPhone = rng.nextBoolean();
        return template
            .replace("1_TEMPLATE_ORDER_ID", UUID.randomUUID().toString())
            .replace("2_TEMPLATE_CUSTOMER_ID", UUID.randomUUID().toString())
            .replace("3_TEMPLATE_CUSTOMER_FIRSTNAME", name.firstName())
            .replace("4_TEMPLATE_CUSTOMER_LASTNAME", name.lastName())
            .replace("5_TEMPLATE_PHONE_TYPE", useCellPhone ? "landline" : "mobile")
            .replace("6_TEMPLATE_PHONE_NUMBER", useCellPhone ? phonenumber.phoneNumber() : phonenumber.cellPhone())
            .replace("7_TEMPLATE_CREDITCARD_NUMBER", faker.finance().creditCard())
            .replace("8_TEMPLATE_CREDITCARD_EXPIRY", getCreditCardExpiry())
            .replace("9_TEMPLATE_PRODUCT_ID", UUID.randomUUID().toString())
            .replace("10_TEMPLATE_PRODUCT_DESCRIPTION", commerce.productName())
            .replace("11_TEMPLATE_PRODUCT_COST", commerce.price())
            .replace("12_TEMPLATE_PRODUCT_QUANTITY", Integer.toString(1 + rng.nextInt(2)))
            .replace("13_TEMPLATE_ORDER_TIME", dtFormatter.format(ZonedDateTime.now()));
    }

    private static String getCreditCardExpiry() {
        int month = rng.nextInt(12) + 1;
        int year = Year.now().getValue() + 1 + rng.nextInt(3) - 2000;
        return (month < 10 ? "0" : "") + month + "/" + year;
    }
}
