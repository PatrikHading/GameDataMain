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

    // Lägg till spelare
    @GetMapping("/addPlayers")
    public String addPlayers() {
        return "addPlayers";
    }

    @PostMapping("/addPlayers")
    public String addPlayer(@RequestParam("firstname") String firstName,
                            @RequestParam("lastname") String lastName,
                            @RequestParam("jerseynumber") int jerseyNumber,
                            Model model) {
        Player player = new Player(firstName, lastName, jerseyNumber);
        playerService.savePlayer(player);
        model.addAttribute("message", "Player added successfully");
        return "redirect:/addPlayers";
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
