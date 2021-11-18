package com.example.PPKWU_4.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.xembly.Directives;
import org.xembly.Xembler;

import java.util.Arrays;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@RestController
@RequestMapping("format_controller/")
public class FormatController {

    private final String lowerCaseAPI = "http://localhost:8080/string_analyzer/lower_case/";
    private final String upperCaseAPI = "http://localhost:8080/string_analyzer/upper_case/";
    private final String numbersAPI = "http://localhost:8080/string_analyzer/numbers/";
    private final String specialCharactersAPI = "http://localhost:8080/string_analyzer/special_characters/";

    @GetMapping("analyze_string/{format}/{text}")
    public String getStringStatistics(@PathVariable("text") String text,
                                      @PathVariable("format") String format) {
        StringBuilder textStats = new StringBuilder();
        RestTemplate restTemplate = new RestTemplate();

        if (format.equals("csv")) {
            textStats.append("lowerCase,upperCase,numbers,specialCharacters\n");

            textStats.append(restTemplate.getForObject(lowerCaseAPI + text, String.class)).append(",");
            textStats.append(restTemplate.getForObject(upperCaseAPI + text, String.class)).append(",");
            textStats.append(restTemplate.getForObject(numbersAPI + text, String.class)).append(",");
            textStats.append(restTemplate.getForObject(specialCharactersAPI + text, String.class));
            return String.valueOf(textStats);
        } else if (format.equals("json")) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("lowerCase", restTemplate.getForObject(lowerCaseAPI + text, String.class));
            jsonObject.put("upperCase", restTemplate.getForObject(upperCaseAPI + text, String.class));
            jsonObject.put("numbers", restTemplate.getForObject(numbersAPI + text, String.class));
            jsonObject.put("specialCharacters", restTemplate.getForObject(specialCharactersAPI + text, String.class));

            return jsonObject.toJSONString();
        } else if (format.equals("txt")) {
            textStats.append("lowerCase: ").append(restTemplate.getForObject(lowerCaseAPI + text, String.class)).append("\n");
            textStats.append("upperCase: ").append(restTemplate.getForObject(upperCaseAPI + text, String.class)).append("\n");
            textStats.append("numbers: ").append(restTemplate.getForObject(numbersAPI + text, String.class)).append("\n");
            textStats.append("specialCharacters: ").append(restTemplate.getForObject(specialCharactersAPI + text, String.class));

            return textStats.toString();
        } else if (format.equals("xml")) {

            try {
                String xml = new Xembler(
                        new Directives()
                                .add("stringStats")
                                .add("lowerCase")
                                .set(restTemplate.getForObject(lowerCaseAPI + text, String.class))
                                .up()
                                .add("upperCase")
                                .set(restTemplate.getForObject(upperCaseAPI + text, String.class))
                                .up()
                                .add("numbers")
                                .set(restTemplate.getForObject(numbersAPI + text, String.class))
                                .up()
                                .add("specialCharacters")
                                .set(restTemplate.getForObject(specialCharactersAPI + text, String.class))
                ).xml();
                return xml;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        } else {
            return "Cannot analyze string.";
        }
    }

    @GetMapping("format_data/{input_format}/{output_format}/{text}")
    public String convertFormat(@PathVariable("text") String text,
                                @PathVariable("input_format") String inputFormat,
                                @PathVariable("output_format") String outputFormat) throws ParseException {

        if (inputFormat.equals(outputFormat)) return text;
        System.out.println(text);
        StringBuilder textStats = new StringBuilder();

        int[] data = new int[4];
        String[] temp;

        if (inputFormat.equals("txt")) {
            String[] splitData = text.split("\n");
            for (int i = 0; i < splitData.length; i++) {
                temp = splitData[i].split(" ");
                data[i] = Integer.parseInt(temp[1]);
                System.out.println(data[i]);
            }
        } else if (inputFormat.equals("json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(text);

            data[0] = Integer.parseInt(jsonObject.get("lowerCase").toString());
            data[1] = Integer.parseInt(jsonObject.get("upperCase").toString());
            data[2] = Integer.parseInt(jsonObject.get("numbers").toString());
            data[3] = Integer.parseInt(jsonObject.get("specialCharacters").toString());
        }


        if (outputFormat.equals("csv")) {
            textStats.append("lowerCase,upperCase,numbers,specialCharacters\n");

            textStats.append(data[0]).append(",");
            textStats.append(data[1]).append(",");
            textStats.append(data[2]).append(",");
            textStats.append(data[3]);
            return String.valueOf(textStats);
        } else if (outputFormat.equals("json")) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("lowerCase", data[0]);
            jsonObject.put("upperCase", data[1]);
            jsonObject.put("numbers", data[2]);
            jsonObject.put("specialCharacters", data[3]);

            return jsonObject.toJSONString();
        } else if (outputFormat.equals("txt")) {
            textStats.append("lowerCase: ").append(data[0]).append("\n");
            textStats.append("upperCase: ").append(data[1]).append("\n");
            textStats.append("numbers: ").append(data[2]).append("\n");
            textStats.append("specialCharacters: ").append(data[3]);

            return textStats.toString();
        } else if (outputFormat.equals("xml")) {

            try {
                String xml = new Xembler(
                        new Directives()
                                .add("stringStats")
                                .add("lowerCase")
                                .set(data[0])
                                .up()
                                .add("upperCase")
                                .set(data[1])
                                .up()
                                .add("numbers")
                                .set(data[2])
                                .up()
                                .add("specialCharacters")
                                .set(data[3])
                ).xml();
                return xml;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        } else {
            return "Cannot analyze string.";
        }
    }
}
