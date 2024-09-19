package io.mblueberry.core.model;

import lombok.Data;

import java.util.List;

@Data
public class WorldInfo {
    public String name;
    public int size;
    public List<String> blocks;
    public List<Integer> map;
}
