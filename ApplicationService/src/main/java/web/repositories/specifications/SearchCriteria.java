package web.repositories.specifications;

import lombok.Getter;
import web.dto.request.filter.Item;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchCriteria {

    private final List<Item> params;

    public SearchCriteria() {
        this.params = new ArrayList<>();
    }

    public void add(Item item) {
        params.add(item);
    }

    public void addAll(List<Item> items) {
        params.addAll(items);
    }

    public void remove(String field) {
        params.removeIf(item -> item.getField().equals(field));
    }
}
