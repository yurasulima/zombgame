package io.mblueberry.model.packet;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerUpdate {
    public int x, y;
    public String username;
    public String animState;
}