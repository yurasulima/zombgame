package io.mblueberry.model.packet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SpawnPlayer {
    public String username;
    public int x;
    public int y;
}
