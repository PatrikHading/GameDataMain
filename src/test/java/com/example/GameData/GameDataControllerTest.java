package com.example.GameData;

import com.example.GameData.model.GameStats;
import com.example.GameData.model.Player;
import com.example.GameData.service.GameStatsService;
import com.example.GameData.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
@SpringBootTest
@AutoConfigureMockMvc

public class GameDataControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlayerService playerService;
    @MockBean
    private GameStatsService gameStatsService;

    @Test
    public void testRemoveAllFromTeam1() throws Exception {
        MockHttpSession session = new MockHttpSession();
        List<Player> team1Players = new ArrayList<>();
        team1Players.add(new Player("John", "Doe", 99));
        team1Players.add(new Player("Jane", "Smith", 88));
        session.setAttribute("team1Players", team1Players);
        mockMvc.perform(post("/removeAllFromTeam1").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game"));
        List<Player> updatedTeam1Players = (List<Player>) session.getAttribute("team1Players");
        assert updatedTeam1Players != null;
        assert updatedTeam1Players.isEmpty();
    }

    @Test
    public void testEndSession() throws Exception {
        List<Integer> team1PlayerIds = List.of(1, 2);
        List<Integer> team1Goals = List.of(2, 1);
        List<Integer> team1Assists = List.of(1, 2);
        List<Integer> team1Pims = List.of(0, 1);
        List<Integer> team2PlayerIds = List.of(3, 4);
        List<Integer> team2Goals = List.of(1, 0);
        List<Integer> team2Assists = List.of(0, 1);
        List<Integer> team2Pims = List.of(2, 0);
        when(playerService.getPlayerById(1)).thenReturn(new Player("John", "Doe", 99));
        when(playerService.getPlayerById(2)).thenReturn(new Player("Jane", "Smith", 88));
        when(playerService.getPlayerById(3)).thenReturn(new Player("Jim", "Beam", 77));
        when(playerService.getPlayerById(4)).thenReturn(new Player("Jill", "Valentine", 66));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("team1Players", new ArrayList<>());
        session.setAttribute("team2Players", new ArrayList<>());
        mockMvc.perform(post("/endSession")
                        .param("team1PlayerIds", team1PlayerIds.stream().map(String::valueOf).toArray(String[]::new))
                        .param("team1Goals", team1Goals.stream().map(String::valueOf).toArray(String[]::new))
                        .param("team1Assists", team1Assists.stream().map(String::valueOf).toArray(String[]::new))
                        .param("team1Pims", team1Pims.stream().map(String::valueOf).toArray(String[]::new))
                        .param("team2PlayerIds", team2PlayerIds.stream().map(String::valueOf).toArray(String[]::new))
                        .param("team2Goals", team2Goals.stream().map(String::valueOf).toArray(String[]::new))
                        .param("team2Assists", team2Assists.stream().map(String::valueOf).toArray(String[]::new))
                        .param("team2Pims", team2Pims.stream().map(String::valueOf).toArray(String[]::new))
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game"));
        verify(gameStatsService, times(4)).saveGameStats(any(GameStats.class));
        assert session.getAttribute("team1Players") == null;
        assert session.getAttribute("team2Players") == null;
    }

}
