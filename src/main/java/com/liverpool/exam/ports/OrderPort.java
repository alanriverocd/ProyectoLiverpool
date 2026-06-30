package com.liverpool.exam.ports;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/**
 * Port interface for order-related operations (external API access, search).
 */
public interface OrderPort {
    JsonNode[] fetchPedidosRaw();
    JsonNode[] fetchItemsRaw();
    List<JsonNode> search(String q);
}
