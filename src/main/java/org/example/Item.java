package org.example;
/*
  @author   soniakk
  @project   gilded-rose
  @class  GlidedRose
  @version  1.0.1

*/

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    public String name;
    public int sellIn;
    public int quality;

}
