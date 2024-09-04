package io.mblueberry.model;

import lombok.Data;

import java.util.List;

@Data
public class WorldInfo {
    String name;
    int size;
    List<String> blocks;
    List<Integer> map;
}
