package com.liverpool.exam.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class OrderService {
    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String PEDIDOS_URL = "https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/pedidos";
    private static final String ITEMS_URL = "https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/items";

    public List<JsonNode> fetchPedidos() {
        ResponseEntity<String> r = rest.getForEntity(PEDIDOS_URL, String.class);
        try {
            return mapper.readTree(r.getBody()).findValues(null);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public JsonNode[] fetchPedidosRaw() {
        try {
            String json = rest.getForObject(PEDIDOS_URL, String.class);
            return mapper.readValue(json, JsonNode[].class);
        } catch (Exception e) {
            return new JsonNode[0];
        }
    }

    public JsonNode[] fetchItemsRaw() {
        try {
            String json = rest.getForObject(ITEMS_URL, String.class);
            return mapper.readValue(json, JsonNode[].class);
        } catch (Exception e) {
            return new JsonNode[0];
        }
    }

    private String normalize(String s) {
        if (s == null) return "";
        String n = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return n.replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase().trim();
    }

    private boolean fuzzyMatch(String text, String query) {
        String a = normalize(text);
        String b = normalize(query);
        if (a.contains(b)) return true;
        LevenshteinDistance ld = new LevenshteinDistance();
        int dist = ld.apply(a, b);
        int threshold = Math.max(1, b.length() / 6 + 1);
        return dist <= threshold;
    }

    public List<JsonNode> search(String q) {
        List<JsonNode> out = new ArrayList<>();
        JsonNode[] pedidos = fetchPedidosRaw();
        JsonNode[] items = fetchItemsRaw();

        for (JsonNode p : pedidos) {
            String orderRef = p.path("orderRef").asText("");
            String orderStatus = p.path("orderStatus").asText("");
            String storeName = p.path("storeName").asText("");

            boolean matchPedido = fuzzyMatch(orderRef, q) || fuzzyMatch(orderStatus, q) || fuzzyMatch(storeName, q);

            // check items for this order
            boolean matchItem = false;
            String pedidoId = p.path("orderRef").asText("");
            for (JsonNode it : items) {
                String itemOrderRef = it.path("orderRef").asText("");
                if (!itemOrderRef.equals(pedidoId)) continue;
                String displayName = it.path("displayName").asText("");
                if (fuzzyMatch(displayName, q)) { matchItem = true; break; }
            }

            if (matchPedido || matchItem) {
                out.add(p);
            }
        }
        return out;
    }
}
