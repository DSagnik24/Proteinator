package com.CalorieCalculator.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class NutritionController {

    private static final Map<String, Double> CALORIES = Map.ofEntries(
            Map.entry("chicken", 1.65),
            Map.entry("rice", 1.30),
            Map.entry("banana", 0.89),
            Map.entry("apple", 0.52),
            Map.entry("bread (white)", 2.65),
            Map.entry("milk (whole)", 0.61),
            Map.entry("cheese", 4.02),
            Map.entry("potato", 0.77),
            Map.entry("broccoli", 0.34),
            Map.entry("tofu", 0.76),
            Map.entry("peanut butter", 5.88),
            Map.entry("almonds", 5.79),
            Map.entry("oats", 3.89),
            Map.entry("yogurt (plain)", 0.59),
            Map.entry("beef (lean)", 2.50),
            Map.entry("fish (salmon)", 2.08),
            Map.entry("dal (lentils)", 1.16),
            Map.entry("maggi noodles", 3.85),
            Map.entry("egg", 78.0),             // 1 large egg (~50g)
            Map.entry("chapati (roti)", 120.0) // 1 roti (~40g)
    );



    private static final Map<String, Double> PROTEIN = Map.ofEntries(
            Map.entry("chicken", 0.31),
            Map.entry("rice", 0.027),
            Map.entry("banana", 0.011),
            Map.entry("apple", 0.003),
            Map.entry("bread (white)", 0.09),
            Map.entry("milk (whole)", 0.032),
            Map.entry("cheese", 0.25),
            Map.entry("potato", 0.02),
            Map.entry("broccoli", 0.028),
            Map.entry("tofu", 0.081),
            Map.entry("peanut butter", 0.25),
            Map.entry("almonds", 0.21),
            Map.entry("oats", 0.169),
            Map.entry("yogurt (plain)", 0.10),
            Map.entry("beef (lean)", 0.26),
            Map.entry("fish (salmon)", 0.20),
            Map.entry("dal (lentils)", 0.09),
            Map.entry("maggi noodles", 0.07),
            Map.entry("egg", 6.0),
            Map.entry("chapati (roti)", 3.0)
    );


    @PostMapping("/calculate")
    public NutritionResponse calculate(@RequestBody InputRequest inputRequest) {
        double totalCalories = 0;
        double totalProtein = 0;

        for (String raw : inputRequest.getItems()) {
            String rawLower = raw.toLowerCase();
            double quantity = 1;

            // Try to extract quantity as first number found
            String[] words = rawLower.split(" ");
            try {
                quantity = Double.parseDouble(words[0].replaceAll("[^\\d.]", "")); // remove non-numeric chars like 'g'
            } catch (NumberFormatException ignored) {}

            for (String item : CALORIES.keySet()) {
                if (rawLower.contains(item)) {
                    totalCalories += quantity * CALORIES.get(item);
                    totalProtein += quantity * PROTEIN.get(item);
                    break;
                }
            }
        }

        return new NutritionResponse(Math.round(totalCalories), Math.round(totalProtein));
    }

}
