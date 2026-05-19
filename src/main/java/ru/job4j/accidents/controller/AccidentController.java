package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.service.AccidentService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/accidents")
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;

    @GetMapping("/list")
    public String getAllAccidents(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        model.addAttribute("user", "Dmitrii");
        return "accidents/list";
    }
}
