package moneyCalculator.exchangerateAPI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import moneyCalculator.Currency;
import moneyCalculator.ExchangeRate;
import moneyCalculator.ExchangeRateLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ExchangerateExchangeRateLoader implements ExchangeRateLoader {

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        try {
            URL url = new URL("https://v6.exchangerate-api.com/v6/6ea3787c99e31d55eb4378b4/latest/"+from.code());
            InputStream input = url.openStream();
            JsonObject js = new Gson().fromJson(new String(input.readAllBytes()), JsonObject.class);
            return new ExchangeRate(from,to,
                    LocalDate.parse(js.get("time_last_update_utc").getAsString(), DateTimeFormatter.RFC_1123_DATE_TIME),
                    js.get("conversion_rates").getAsJsonObject().asMap().get(to.code()).getAsDouble());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}