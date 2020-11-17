package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
                // "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
                continue;
            }

            if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                updateConcertTicket(item);
                continue;
            }

            if (!item.name.equals("Aged Brie")) {
                if (item.quality > 0) {
                    item.quality = item.quality - 1;
                }
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
            }

            item.sellIn = item.sellIn - 1;

            if (item.sellIn < 0) {
                if (!item.name.equals("Aged Brie")) {
                    if (item.quality > 0) {
                        item.quality = item.quality - 1;
                    }

                } else {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                }
            }
        }
    }

    private void updateConcertTicket(Item ticket) {
        if (ticket.sellIn <= 0) {
            ticket.sellIn -= 1;
            ticket.quality = 0; // ticket quality drops to zero after the concert
            return;
        }

        // Ticket quality increases 3x when less than 5
        if (ticket.sellIn <= 5) {
            ticket.sellIn -= 1;
            ticket.quality = Math.min(ticket.quality + 3, 50);
            return;
        }

        // Ticket quality increases twice when less than 10 days
        if (ticket.sellIn <= 10) {
            ticket.sellIn -= 1;
            ticket.quality = Math.min(ticket.quality + 2, 50);
            return;
        }

        ticket.sellIn -= -1;
        ticket.quality = Math.min(ticket.quality + 1, 50);
    }
}