package com.bramkov.exhangerate_service.exchangerate;

import org.springframework.stereotype.Component;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExchangeRate {

    private List<Currency> currencyList;

    public ExchangeRate() {
        this.currencyList = fetchData();
    }

    private ArrayList<Currency> fetchData() {

        final String url = "https://www.cbr.ru/currency_base/daily/";
        ArrayList<Currency> listCurrency = new ArrayList<>();

        try {

            URL obj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Document doc = Jsoup.parse(response.toString());
            Elements elementsTr = doc.getElementsByTag("tr");


            for (Element elementTr : elementsTr) {
                Elements elementsTd = elementTr.getElementsByTag("td");

                /*
                    036
                    AUD
                    1
                    Австралийский доллар
                    61,5560
                 */

                if (elementsTd.size() > 0 ) {
                    int code = Integer.parseInt(elementsTd.get(0).text());
                    String lateCode = elementsTd.get(1).text();
                    String nameRate = elementsTd.get(3).text();
                    Double course = Double.parseDouble(elementsTd.get(4).text().replace(",","."));

                    listCurrency.add(new Currency(code, lateCode, nameRate, course));
                }

            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listCurrency;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }


}