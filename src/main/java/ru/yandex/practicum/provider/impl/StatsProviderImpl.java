package ru.yandex.practicum.provider.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.event.client.EventClient;
import ru.yandex.practicum.event.client.dto.ViewStats;
import ru.yandex.practicum.provider.StatsProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatsProviderImpl implements StatsProvider {

    private final EventClient client;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Map<String, ViewStats> viewStats = new HashMap<>();

    @Override
    public Map<String, ViewStats> getViewStats(String start, String end,
                                               List<String> uris, Boolean unique) {
        ResponseEntity<Object> response = client.getStats(start, end, uris, false);
        try {
            Object body = response.getBody();
            String json = mapper.writeValueAsString(body);
            List<ViewStats> set =  mapper.readValue(json, new TypeReference<>() {});
            set.forEach(element -> viewStats.put(element.getUri(), element));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json not valid", e);
        }
        return viewStats;
    }
}
