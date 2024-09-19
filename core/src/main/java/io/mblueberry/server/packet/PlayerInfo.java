package io.mblueberry.server.packet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerInfo {
    public String username;
    public int x;
    public int y;
}
