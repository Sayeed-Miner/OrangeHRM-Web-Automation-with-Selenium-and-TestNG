package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import page.UserModel;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static int generateRandomId(int min, int max) {
        double rand = Math.random() * (max - min) + min;
        return (int) rand;
    }
    public static void saveInfo(UserModel model) throws IOException, ParseException {
        String filepath = "./src/test/resources/Employees.json";
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filepath));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", model.getFirstname());
        jsonObject.put("lastName", model.getLastname());
        jsonObject.put("employeeid", model.getEmployeeid());
        jsonObject.put("userName", model.getUsername());
        jsonObject.put("password", model.getPassword());
        jsonArray.add(jsonObject);
        System.out.println(jsonArray);
        FileWriter writer = new FileWriter(filepath);
        writer.write(jsonArray.toJSONString());
        writer.flush();
        writer.close();
    }
    public static JSONArray readJSONArray(String url) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray empArray = (JSONArray) parser.parse(new FileReader(url));
        return empArray;
    }
    public static void doScroll(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,600)");
    }
}