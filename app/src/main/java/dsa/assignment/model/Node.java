package dsa.assignment.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Node {
    int vertex, weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;
        return vertex == node.vertex &&
            weight == node.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex, weight);
    }
}
