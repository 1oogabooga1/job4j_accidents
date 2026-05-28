package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import org.springframework.ui.Model;
import ru.job4j.accidents.service.AccidentTypeService;

@Controller
@RequestMapping("/accidents")
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;

    private final AccidentTypeService typeService;

    @GetMapping("/list")
    public String getAllAccidents(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        model.addAttribute("user", "Dmitrii");
        return "accidents/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("user", "Dmitrii");
        model.addAttribute("types", typeService.findAll());
        return "accidents/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Accident accident, Model model) {
        try {
            accidentService.create(accident);
            return "redirect:/accidents/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        var acc = accidentService.findById(id);
        if (acc.isEmpty()) {
            model.addAttribute("message", "The accident was not found, incorrect id");
            return "errors/404";
        }
        model.addAttribute("user", "Dmitrii");
        model.addAttribute("accident", acc.get());
        model.addAttribute("types", typeService.findAll());
        return "accidents/accident";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Accident accident, Model model) {
        try {
            var result = accidentService.edit(accident);
            if (!result) {
                model.addAttribute("message", "Some error occurred during edition");
                return "errors/404";
            }
            return "redirect:/accidents/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var result = accidentService.delete(id);
        if (!result) {
            model.addAttribute("message", "Some error occurred during deleting this accident");
            return "errors/404";
        }
        return "redirect:/accidents/list";
    }
}
