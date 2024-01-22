package moneyCalculator.exchangerateAPI;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import moneyCalculator.Currency;
import moneyCalculator.CurrencyLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class ExchangerateCurrencyLoader implements CurrencyLoader {
    @Override
    public List<Currency> load() {
        try {
            return toList(loadJson());
        } catch (IOException e) {
            return emptyList();
        }
    }

    private List<Currency> toList(String json) {
        List<Currency> list = new ArrayList<>();
        List<JsonElement> symbols = new Gson().fromJson(json, JsonObject.class)
                .get("supported_codes").getAsJsonArray().asList();
        for (JsonElement symbol : symbols)
            list.add(new Currency(symbol.getAsJsonArray().asList().get(0).getAsString(),symbol.getAsJsonArray().asList().get(1).getAsString()));
        return list;
    }

    private String loadJson() throws IOException {
        URL url = new URL("https://v6.exchangerate-api.com/v6/6ea3787c99e31d55eb4378b4/codes");
        try (InputStream is = url.openStream()) {
            return new String(is.readAllBytes());
        }
    }
}
