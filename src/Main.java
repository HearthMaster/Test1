import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        Thread t1 = new Thread();
        t1.start();
        TimeUnit time = TimeUnit.SECONDS;
        CrptAPI app = new CrptAPI(10000, 5);
        Object document = "{\"description\":\n" +
                "{ \"participantInn\": \"string\" }, \"doc_id\": \"string\", \"doc_status\": \"string\",\n" +
                "\"doc_type\": \"LP_INTRODUCE_GOODS\", \"importRequest\": true,\n" +
                "\"owner_inn\": \"string\", \"participant_inn\": \"string\", \"producer_inn\":\n" +
                "\"string\", \"production_date\": \"2020-01-23\", \"production_type\": \"string\",\n" +
                "\"products\": [ { \"certificate_document\": \"string\",\n" +
                "\"certificate_document_date\": \"2020-01-23\",\n" +
                "\"certificate_document_number\": \"string\", \"owner_inn\": \"string\",\n" +
                "\"producer_inn\": \"string\", \"production_date\": \"2020-01-23\",\n" +
                "\"tnved_code\": \"string\", \"uit_code\": \"string\", \"uitu_code\": \"string\" } ],\n" +
                "\"reg_date\": \"2020-01-23\", \"reg_number\": \"string\"}";
        String podpis = null;
        Scanner sc = new Scanner(System.in);
        int i = -1;
        while(i<1000000000){
                app.createDocument(document, podpis);
        }
    }
}