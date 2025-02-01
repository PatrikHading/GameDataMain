package com.example.GameData.controller;


import com.example.GameData.model.Player;
import com.example.GameData.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/addPlayers")
    public String addPlayers() {
        return "addPlayers";
    }

    @PostMapping("/addPlayers")
    public String addPlayer(@RequestParam("name") String firstName,
                            @RequestParam("lastname") String lastName,
                            @RequestParam("jerseynumber") Integer jerseyNumber,
                            Model model) {

        if (firstName == null || firstName.trim().isEmpty()) {
            model.addAttribute("errorMessage", "First name is required");
            return returnWithFields(model, firstName, lastName, jerseyNumber);
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            model.addAttribute("errorMessage", "Last name is required");
            return returnWithFields(model, firstName, lastName, jerseyNumber);
        }

        if (jerseyNumber == null) {
            model.addAttribute("errorMessage", "Jersey number is required");
            return returnWithFields(model, firstName, lastName, jerseyNumber);
        }

        if (jerseyNumber <= 0 || jerseyNumber > 100) {
            model.addAttribute("errorMessage", "Jersey number must be between 1 and 100");
            return returnWithFields(model, firstName, lastName, jerseyNumber);
        }

        try {
            Player player = new Player(firstName.trim(), lastName.trim(), jerseyNumber);
            playerService.savePlayer(player);
            return "redirect:/addPlayers?success=true";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to save player. Please try again.");
            return returnWithFields(model, firstName, lastName, jerseyNumber);
        }
    }

    private String returnWithFields(Model model, String firstName, String lastName, Integer jerseyNumber) {
        model.addAttribute("firstname", firstName);
        model.addAttribute("lastname", lastName);
        model.addAttribute("jerseynumber", jerseyNumber);
        return "addPlayers";
    }

    // Visa registrerade spelare
    @GetMapping("/registeredPlayers")
    public String getAllPlayers(Model model) {
        List<Player> players = playerService.getAllPlayers();
        model.addAttribute("players", players);
        return "registeredPlayers";
    }

    // Uppdatera spelare
    @GetMapping("updatePlayers/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        Player player = playerService.getPlayerById(id);
        model.addAttribute("player", player);
        return "updatePlayers";
    }

    @PostMapping("/updatePlayers/{id}")
    public String updatePlayer(@PathVariable Integer id, @ModelAttribute Player player, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "updatePlayers";
        }
        playerService.updatePlayer(id, player);
        return "redirect:/registeredPlayers";
    }

    // Ta bort spelare
    @GetMapping("/deletePlayer/{id}")
    public String deletePlayer(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Player player = playerService.getPlayerById(id);
            if (player == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Player not found");
                return "redirect:/registeredPlayers";
            }
            playerService.deletePlayer(id);
            redirectAttributes.addFlashAttribute("successMessage", "Player deleted");
            return "redirect:/registeredPlayers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting player: " + e.getMessage());
            return "redirect:/registeredPlayers";
        }
    }
}
