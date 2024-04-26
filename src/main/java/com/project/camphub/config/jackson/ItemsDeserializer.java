package com.project.camphub.config.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemsDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        OpenApiResponse.Items items = new OpenApiResponse.Items();

        if (node.has("item") && !node.get("item").isEmpty()) {
            List<OpenApiResponse.Item> itemList = Arrays.asList(p.getCodec().treeToValue(node.get("item"), OpenApiResponse.Item[].class));
            items.setItem(itemList);
        } else {
            items.setItem(new ArrayList<>());
        }

        return items;
    }
}
