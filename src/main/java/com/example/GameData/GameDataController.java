package com.example.GameData;

import com.example.GameData.model.GameStats;
import com.example.GameData.model.Player;
import com.example.GameData.service.GameStatsService;
import com.example.GameData.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

    //Visar startsidan
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Visar formuläret för att lägga till spelare
    @GetMapping("/addPlayers")
    public String addPlayers() {
        return "addPlayers";
    }

    // Lägger till en spelare i databasen
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

    // Visar arkivsidan
    @GetMapping("/archive")
    public String archive() {
        return "archive";
    }

    // Hämtar alla registrerade spelare och visar dem på sidan
    @GetMapping("/registeredPlayers")
    public String getAllPlayers(Model model) {
        List<Player> players = playerService.getAllPlayers();
        model.addAttribute("players", players);
        return "registeredPlayers";
    }

    //Controller för att uppdatera spelare ska flyttas ut ur GameDataController
    @Controller
    @RequestMapping("/updatePlayers")
    public class PlayerController {

        @Autowired
        private PlayerService playerService;

        // Visar formuläret för att uppdatera en spelares data
        @GetMapping("/{id}")
        public String showUpdateForm(@PathVariable Integer id, Model model) {
            Player player = playerService.getPlayerById(id);
            model.addAttribute("player", player);
            return "updatePlayers";
        }

        // Uppdaterar en spelares data
        @PostMapping("/{id}")
        public String updatePlayer(@PathVariable Integer id, @ModelAttribute Player player, BindingResult bindingResult, Model model) {
            if (bindingResult.hasErrors()) {
                return "updatePlayers";
            }

            playerService.updatePlayer(id, player);
            return "redirect:/registeredPlayers";
        }
    }

    // Visar spelarsidan där användare kan lägga till spelare i lag
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

    // Lägger till spelare i lag 1
    @PostMapping("/addToTeam1")
    public String addToTeam1(@RequestParam("playerId") Integer playerId, HttpSession session, Model model) {

        Player playerToAdd = playerService.getAllPlayers().stream().filter(player -> player.getId() == playerId).findFirst().orElse(null);

        if (playerToAdd != null) {
            List<Player> team1Players = (List<Player>) session.getAttribute("team1Players");
            if (team1Players == null) {
                team1Players = new ArrayList<>();
            }
            if (team1Players.size() < 20) {
                if (!team1Players.contains(playerToAdd)) {
                    team1Players.add(playerToAdd);
                    session.setAttribute("team1Players", team1Players);
                }
            }
        }
         return "redirect:/game";
    }

    // Lägger till spelare i lag 2
    @PostMapping("/addToTeam2")
    public String addToTeam2(@RequestParam("playerId") Integer playerId, HttpSession session, Model model) {

        Player playerToAdd = playerService.getAllPlayers().stream().filter(player -> player.getId() == playerId).findFirst().orElse(null);

        if (playerToAdd != null) {
            List<Player> team2Players = (List<Player>) session.getAttribute("team2Players");
            if (team2Players == null) {
                team2Players = new ArrayList<>();
            }
            if (team2Players.size() < 20) {
                if (!team2Players.contains(playerToAdd)) {
                    team2Players.add(playerToAdd);
                    session.setAttribute("team2Players", team2Players);
                }
            }
        }
        return "redirect:/game";
    }

    // Tar bort alla spelare från lag 1
    @PostMapping("/removeAllFromTeam1")
    public String removeAllFromTeam1(HttpSession session, Model model) {
        List<Player> team1Players = (List<Player>) session.getAttribute("team1Players");
        if (team1Players != null) {
            team1Players.clear();
            session.setAttribute("team1Players", team1Players);
        }
        return "redirect:/game";
    }

    // Tar bort alla spelare från lag 2
    @PostMapping("/removeAllFromTeam2")
    public String removeAllFromTeam2(HttpSession session, Model model) {
        List<Player> team2Players = (List<Player>) session.getAttribute("team2Players");
        if (team2Players != null) {
            team2Players.clear();
            session.setAttribute("team2Players", team2Players);
        }
        return "redirect:/game";
    }

    // Sparar spelets statistik
    @PostMapping("/saveGameStats")
    public String saveGameStats(
            @RequestParam("playerId") Long playerId,
            @RequestParam("goals") int goals,
            @RequestParam("assists") int assists,
            @RequestParam("pim") int pim) {


        return "redirect:/game";
    }

    @Autowired
    private GameStatsService gameStatsService;

    // Uppdaterar spelarnas statistik för mål, assist eller PIM i sessionen
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

    // Avslutar spelet, sparar statistik och rensar laguppställningar från sessionen
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

        session.removeAttribute("team1Players");
        session.removeAttribute("team2Players");

        return "redirect:/";
    }

    // Genererar ett unikt Game ID baserat på tidsstämpel
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

    // Visar en lista över alla spelade matcher
    @GetMapping("/games")
    public String listGames(Model model) {
        List<String> gameIds = gameStatsService.findUniqueGameIds();
        model.addAttribute("games", gameIds);
        return "games";
    }

    // Visar detaljerad statistik för ett specifikt spel baserat på Game ID
    @GetMapping("/games/{gameId}")
    public String gameDetails(@PathVariable String gameId, Model model) {
        List<GameStats> gameStats = gameStatsService.findByGameId(gameId);
        if (!gameStats.isEmpty()) {
            model.addAttribute("gameStatsList", gameStats);  // Lägg till alla stats för matchen
        } else {
            model.addAttribute("gameStatsList", null);  // Ingen statistik för den matchen
        }
        return "games";
    }


}