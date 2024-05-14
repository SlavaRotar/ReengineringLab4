package org.example;
/*
  @author   soniakk
  @project   gilded-rose
  @class  GlidedRose
  @version  1.0.1

*/

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GlidedRoseTest {


    @Test
    public void updateQualityShouldThrowException_WhenItemArrayIsNull() {
        Item[] items = null;
        GlidedRose app = new GlidedRose(items);
        Assertions.assertThrows(NullPointerException.class, app::updateQuality,
                "Expected a NullPointerException when the item array is null");
    }

    @Test
    public void updateQualityShouldThrowException_WhenItemNameIsNull() {
        Item[] items = new Item[]{
                new Item(null, 5, 10)
        };
        GlidedRose app = new GlidedRose(items);
        Assertions.assertThrows(NullPointerException.class, app::updateQuality,
                "Expected an IllegalArgumentException when an item has a null name");
    }

    @Test
    public void qualityShouldStayAtZero_WhenItemHasNoQualityAndIsOutOfSellByDate() {
        Item[] items = new Item[]{
                new Item("+5 Dexterity Vest", 0, 0)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 0 && items[0].sellIn == -1,
                "Quality should remain at 0 when sell-in is 0");
    }

    @Test
    public void agedBrieShouldNotExceedMaxQuality_WhenUpdateQualityIsCalled() {
        Item[] items = new Item[]{
                new Item("Aged Brie", 50, 50)  // Already at max quality
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 50 && items[0].sellIn == 49,
                "Quality of 'Aged Brie' should not exceed 50");
    }

    @Test
    public void backstagePassesQualityShouldIncreaseByOne_WhenSellInIsGreaterThan10() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 30, 30)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 31 && items[0].sellIn == 29,
                "Quality of backstage passes should increase by 1 when sell-in is greater than 10");
    }
    @Test
    public void backstagePassesQualityShouldNotExceedMax_WhenSellInIs5OrLess() {
        // Arrange
        int initialSellIn = 5;
        int initialQuality = 48;  // Starting close to max quality
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", initialSellIn, initialQuality)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        int expectedQuality = Math.min(50, initialQuality + 3);  // Quality should not exceed 50
        int expectedSellIn = initialSellIn - 1;
        Assertions.assertEquals(expectedQuality, items[0].quality,
                "Backstage passes quality should not exceed 50, even with a 3-point increase");
        Assertions.assertEquals(expectedSellIn, items[0].sellIn,
                "Sell-in should decrease by 1");
    }

    @Test
    public void backstagePassesQualityShouldNotExceedMax_WhenAlreadyAtMaxQuality() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 30, 50)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 50 && items[0].sellIn == 29,
                "Quality should not exceed 50 even for backstage passes");
    }

    @Test
    public void backstagePassesQualityShouldDropToZero_WhenSellByDatePassed() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 0, 30)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 0 && items[0].sellIn == -1,
                "Backstage passes quality should drop to zero when sell-in is 0");
    }

    @Test
    public void qualityShouldDecreaseForConjuredItems_WhenUpdateQualityIsCalled_PreSellByDate() {
        Item[] items = new Item[]{
                new Item("Conjured Mana Cake", 10, 10)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 9 && items[0].sellIn == 9,
                "Conjured item quality should decrease by 1 pre-sell-by date");
    }

    @Test
    public void qualityShouldDecreaseFasterForConjuredItems_WhenSellByDatePassed() {
        Item[] items = new Item[]{
                new Item("Conjured Mana Cake", -1, 10)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 8 && items[0].sellIn == -2,
                "Conjured item quality should decrease by 2 after the sell-by date");
    }

    @Test
    public void qualityShouldNotExceedMaximum_WhenQualityIsAlreadyAtMaximum() {
        Item[] items = new Item[]{
                new Item("Aged Brie", 10, 50)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 50,
                "Quality should not exceed 50 even if item is 'Aged Brie'");
    }

    @Test
    public void qualityShouldNotGoBelowZero_WhenUpdateQualityIsCalled() {
        Item[] items = new Item[]{
                new Item("Elixir of the Mongoose", 5, 0)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 0,
                "Quality should not go below zero");
    }

    @Test
    public void sulfurasShouldNotChange_WhenUpdateQualityIsCalled() {
        Item[] items = new Item[]{
                new Item("Sulfuras, Hand of Ragnaros", 5, 80)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 80 && items[0].sellIn == 5,
                "Quality and sell-in of 'Sulfuras' should remain unchanged");
    }

    @Test
    public void backstagePassesQualityShouldIncreaseByTwo_WhenSellInIs10OrLess() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 30)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 32 && items[0].sellIn == 9,
                "Backstage passes quality should increase by 2 when sell-in is 10 or less");
    }

    @Test
    public void backstagePassesQualityShouldIncreaseByThree_WhenSellInIs5OrLess() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 30)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 33 && items[0].sellIn == 4,
                "Backstage passes quality should increase by 3 when sell-in is 5 or less");
    }

    @Test
    public void conjuredItemsQualityShouldDecreaseTwiceAsFast_AfterSellByDate() {
        Item[] items = new Item[]{
                new Item("Conjured Mana Cake", -1, 4)
        };
        GlidedRose app = new GlidedRose(items);
        app.updateQuality();
        Assertions.assertTrue(items[0].quality == 2 && items[0].sellIn == -2,
                "Conjured items should decrease in quality twice as fast after the sell-by date");
    }
}