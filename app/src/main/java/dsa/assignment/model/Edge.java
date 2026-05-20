package dsa.assignment.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Edge {
    int v, u;
    int weight;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Edge)) return false;

        Edge edge = (Edge) obj;
        return edge.getU() == u && edge.getV() == v;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v, u);
    }
}
