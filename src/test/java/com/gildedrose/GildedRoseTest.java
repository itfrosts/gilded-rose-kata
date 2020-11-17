package com.gildedrose;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @ParameterizedTest
    @MethodSource("provideProductQualityExpectations")
    void shouldUpdateProductQualityAppropriately(String name, int sellIn, int quality, int expectedQuality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(items[0].quality, expectedQuality);
    }

    private static Stream<Arguments> provideProductQualityExpectations() {
        return Stream.of(
                Arguments.of("+5 Dexterity Vest", 10, 10, 9),
                Arguments.of("+5 Dexterity Vest", 0, 10, 8), // Once the sell by date has passed, Quality degrades twice as fast
                Arguments.of("+5 Dexterity Vest", 10, 0, 0), // Quality of an item is never negative
                Arguments.of("Aged Brie", 10, 10, 11), // Aged Brie quality should increase with time
                Arguments.of("Aged Brie", 10, 50, 50), // Quality of an item is never more than 50
                Arguments.of("Elixir of the Mongoose", 10, 10, 9),
                Arguments.of("Sulfuras, Hand of Ragnaros", 10, 80, 80), // Sulfuras quality never decreases
                Arguments.of("Backstage passes to a TAFKAL80ETC concert", 15, 10, 11), // Ticket quality increases with time
                Arguments.of("Backstage passes to a TAFKAL80ETC concert", 10, 10, 12), // Ticket quality increases twice when less than 10 days
                Arguments.of("Backstage passes to a TAFKAL80ETC concert", 5, 10, 13), // Ticket quality increases 3x when less than 5
                Arguments.of("Backstage passes to a TAFKAL80ETC concert", 0, 10, 0), // Ticket quality drops to 0 after the concert
                Arguments.of("Aged Brie", -1, 10, 12) // TODO: Figure out if quality should really increase by two after sellIn time
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductSellInExpectations")
    void shouldUpdateProductSellIn(String name, int sellIn, int expectedSellInValue) {
        Item[] items = new Item[] { new Item(name, sellIn, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(items[0].sellIn, expectedSellInValue);
    }

    private static Stream<Arguments> provideProductSellInExpectations() {
        return Stream.of(
                Arguments.of("+5 Dexterity Vest", 10, 9),
                Arguments.of("Aged Brie", 0, -1),
                Arguments.of("Elixir of the Mongoose", -1, -2),
                Arguments.of("Sulfuras, Hand of Ragnaros", 10, 10), // Sulfuras never has to be sold
                Arguments.of("Sulfuras, Hand of Ragnaros", -1, -1), // Sulfuras never has to be sold
                Arguments.of("Backstage passes to a TAFKAL80ETC concert", 1, 0)
        );
    }



}
