package com.CalorieCalculator.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class NutritionController {

    private static final Map<String, Double> CALORIES = Map.of(
            "egg", 78.0,
            "chicken", 165.0,
            "rice", 130.0
    );

    private static final Map<String, Double> PROTEIN = Map.of(
            "egg", 6.0,
            "chicken", 31.0,
            "rice", 2.7
    );

    @PostMapping("/calculate")
    public NutritionResponse calculate(@RequestBody InputRequest inputRequest) {
        double totalCalories = 0;
        double totalProtein = 0;

        for (String raw : inputRequest.getItems()) {
            String[] words = raw.toLowerCase().split(" ");
            double quantity = 1;
            try {
                quantity = Double.parseDouble(words[0]);
            } catch (NumberFormatException ignored) {}

            for (String word : words) {
                for (String item : CALORIES.keySet()) {
                    if (word.contains(item)) {
                        totalCalories += quantity * CALORIES.get(item);
                        totalProtein += quantity * PROTEIN.get(item);
                        break;
                    }
                }
            }
        }

        return new NutritionResponse(Math.round(totalCalories), Math.round(totalProtein));
    }
}
