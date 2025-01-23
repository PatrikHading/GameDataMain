package com.example.GameData;

import com.example.GameData.model.GameStats;
import com.example.GameData.model.Player;
import com.example.GameData.service.GameStatsService;
import com.example.GameData.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GameDataController {

    private final PlayerService playerService;


    @Autowired
    public GameDataController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/addPlayers")
    public String addPlayers() {
        return "addPlayers";
    }

    @PostMapping("/addPlayers")
    public String addPlayer(
            @RequestParam("firstname") String firstName,
            @RequestParam("lastname") String lastName,
            @RequestParam("jerseynumber") int jerseyNumber,
            Model model) {

        Player player = new Player(firstName, lastName, jerseyNumber);
        playerService.savePlayer(player);
        model.addAttribute("message", "Player added successfully");
        return "redirect:/addPlayers";

    }

    @GetMapping("/archive")
    public String archive() {
        return "archive";
    }

    @GetMapping("/registeredPlayers")
    public String getAllPlayers(Model model) {
        List<Player> players = playerService.getAllPlayers();
        model.addAttribute("players", players);
        return "registeredPlayers";
    }

    @GetMapping("/updatePlayers")
    public String updatePlayers(Model model) {
        List<Player> players = playerService.getAllPlayers();
        model.addAttribute("players", players);
        return "updatePlayers";
    }



    @GetMapping("/game")
    public String game(Model model, HttpSession session) {
        List<Player> team1Players = (List<Player>) session.getAttribute("team1Players");
        if (team1Players == null) {
            team1Players = new ArrayList<>();
            session.setAttribute("team1Players", team1Players);
        }
        List<Player> team2Players = (List<Player>) session.getAttribute("team2Players");
        if (team2Players == null) {
            team2Players = new ArrayList<>();
            session.setAttribute("team2Players", team2Players);
        }

        List<Player> availablePlayers = playerService.getAllPlayers();
        availablePlayers.removeAll(team1Players);

        model.addAttribute("team1Players", team1Players);
        model.addAttribute("team2Players", team2Players);
        model.addAttribute("availablePlayers", availablePlayers);

        return "game";
    }

    @PostMapping("/addToTeam1")
    public String addToTeam1(@RequestParam("playerId") Integer playerId, HttpSession session, Model model) {

        Player playerToAdd = playerService.getAllPlayers().stream().filter(player -> player.getId() == playerId).findFirst().orElse(null);

        if (playerToAdd != null) {
            List<Player> team1Players = (List<Player>) session.getAttribute("team1Players");
            if (team1Players == null) {
                team1Players = new ArrayList<>();
            }
            if (team1Players.size() < 20) {
                // Check if player isn't already in the team
                if (!team1Players.contains(playerToAdd)) {
                    team1Players.add(playerToAdd);
                    session.setAttribute("team1Players", team1Players);
                }
            }
        }
         return "redirect:/game";
    }

    @PostMapping("/addToTeam2")
    public String addToTeam2(@RequestParam("playerId") Integer playerId, HttpSession session, Model model) {

        Player playerToAdd = playerService.getAllPlayers().stream().filter(player -> player.getId() == playerId).findFirst().orElse(null);

        if (playerToAdd != null) {
            List<Player> team2Players = (List<Player>) session.getAttribute("team2Players");
            if (team2Players == null) {
                team2Players = new ArrayList<>();
            }
            if (team2Players.size() < 20) {
                // Check if player isn't already in the team
                if (!team2Players.contains(playerToAdd)) {
                    team2Players.add(playerToAdd);
                    session.setAttribute("team2Players", team2Players);
                }
            }
        }
        return "redirect:/game";
    }

    @PostMapping("/removeAllFromTeam1")
    public String removeAllFromTeam1(HttpSession session, Model model) {
        List<Player> team1Players = (List<Player>) session.getAttribute("team1Players");
        if (team1Players != null) {
            team1Players.clear();
            session.setAttribute("team1Players", team1Players);
        }
        return "redirect:/game";
    }

    @PostMapping("/removeAllFromTeam2")
    public String removeAllFromTeam2(HttpSession session, Model model) {
        List<Player> team2Players = (List<Player>) session.getAttribute("team2Players");
        if (team2Players != null) {
            team2Players.clear();
            session.setAttribute("team2Players", team2Players);
        }
        return "redirect:/game";
    }

    @PostMapping("/saveGameStats")
    public String saveGameStats(
            @RequestParam("playerId") Long playerId,
            @RequestParam("goals") int goals,
            @RequestParam("assists") int assists,
            @RequestParam("pim") int pim) {


        return "redirect:/game";
    }

    @Autowired
    private GameStatsService gameStatsService;  // Add this field


    @PostMapping("/updateStats")
    @ResponseBody
    public String updateStats(
            @RequestParam("playerId") Integer playerId,
            @RequestParam("statType") String statType,
            @RequestParam("value") Integer value,
            HttpSession session) {

        Map<Integer, Integer> statMap;
        String mapAttribute;

        switch(statType) {
            case "goals":
                mapAttribute = "playerGoals";
                break;
            case "assists":
                mapAttribute = "playerAssists";
                break;
            case "pim":
                mapAttribute = "playerPIM";
                break;
            default:
                return "Invalid stat type";
        }

        statMap = (Map<Integer, Integer>) session.getAttribute(mapAttribute);
        if (statMap == null) {
            statMap = new HashMap<>();
        }

        statMap.put(playerId, value);
        session.setAttribute(mapAttribute, statMap);

        return "Success";
    }

    @PostMapping("/endSession")
    public String endSession(@RequestParam List<Integer> team1PlayerIds,
                             @RequestParam List<Integer> team1Goals,
                             @RequestParam List<Integer> team1Assists,
                             @RequestParam List<Integer> team1Pims,
                             @RequestParam List<Integer> team2PlayerIds,
                             @RequestParam List<Integer> team2Goals,
                             @RequestParam List<Integer> team2Assists,
                             @RequestParam List<Integer> team2Pims,
                             HttpSession session) {

        String gameId = generateGameId();

        // Save Team 1 stats
        for (int i = 0; i < team1PlayerIds.size(); i++) {
            Player player = playerService.getPlayerById(team1PlayerIds.get(i));

            GameStats stats = new GameStats();
            stats.setPlayer(player);
            stats.setGoals(team1Goals.get(i));
            stats.setAssists(team1Assists.get(i));
            stats.setPim(team1Pims.get(i));
            stats.setGameId(gameId);

            gameStatsService.saveGameStats(stats);
        }

        // Save Team 2 stats
        for (int i = 0; i < team2PlayerIds.size(); i++) {
            Player player = playerService.getPlayerById(team2PlayerIds.get(i));

            GameStats stats = new GameStats();
            stats.setPlayer(player);
            stats.setGoals(team2Goals.get(i));
            stats.setAssists(team2Assists.get(i));
            stats.setPim(team2Pims.get(i));
            stats.setGameId(gameId);

            gameStatsService.saveGameStats(stats);
        }

        // Clear session
        session.removeAttribute("team1Players");
        session.removeAttribute("team2Players");

        return "redirect:/";
    }

    private String generateGameId() {
        return "Game-" +System.currentTimeMillis();
    }

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