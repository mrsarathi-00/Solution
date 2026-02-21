import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static int convertToDecimal(String value, int base) {
        return Integer.parseInt(value, base);
    }

    public static double findConstant(List<Integer> x, List<Integer> y, int k) {

        double result = 0;

        for (int i = 0; i < k; i++) {

            double term = y.get(i);

            for (int j = 0; j < k; j++) {

                if (i != j) {

                    term *= (double)(0 - x.get(j)) / (x.get(i) - x.get(j));

                }
            }

            result += term;
        }

        return result;
    }

    public static void main(String[] args) {

        try {

            String content = new String(Files.readAllBytes(Paths.get("input.json")));

            JSONObject obj = new JSONObject(content);

            JSONObject keys = obj.getJSONObject("keys");

            int k = keys.getInt("k");

            List<Integer> x = new ArrayList<>();
            List<Integer> y = new ArrayList<>();

            for (String key : obj.keySet()) {

                if (key.equals("keys")) continue;

                int xi = Integer.parseInt(key);

                JSONObject root = obj.getJSONObject(key);

                int base = Integer.parseInt(root.getString("base"));

                String value = root.getString("value");

                int yi = convertToDecimal(value, base);

                x.add(xi);
                y.add(yi);
            }

            Collections.sort(x);

            List<Integer> ySorted = new ArrayList<>();

            for (int xi : x) {
                JSONObject root = obj.getJSONObject(String.valueOf(xi));
                int base = Integer.parseInt(root.getString("base"));
                String value = root.getString("value");
                ySorted.add(convertToDecimal(value, base));
            }

            List<Integer> useX = x.subList(0, k);
            List<Integer> useY = ySorted.subList(0, k);

            double c = findConstant(useX, useY, k);

            System.out.println("Constant term (c) = " + (int)Math.round(c));

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
}
