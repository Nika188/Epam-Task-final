package edu.epam.fop.json.warehouse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.epam.fop.json.warehouse.item.Item;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WarehouseReaderJson implements WarehouseReader{
    @Override
    public Collection<Item> readItems(InputStream data) {
        if (data == null) return List.of();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<Item> items = mapper.readValue(data, new TypeReference<List<Item>>() {});
            if (items == null) return List.of();
            Collection<Item> filtered = new ArrayList<>();
            for (Item item : items) if (item != null) filtered.add(item);
            return filtered;
        } catch (Exception e) {
            return List.of();
        }
    }
}
