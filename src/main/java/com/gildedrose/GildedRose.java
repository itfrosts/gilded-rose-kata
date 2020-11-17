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
            
            item.sellIn = item.sellIn - 1;
            
            if (item.name.equals("Aged Brie")) {
                // not explicitly defined in the requirements yet previously it was coded that
                // Aged Brie increases twice as fast after the sellIn time.
                item.quality = item.sellIn < 0 ? item.quality + 2 : item.quality + 1;
            } else {
                decreaseItemQuality(item);
            }

            item.quality = Math.max(item.quality, 0); // The Quality of an item is never negative
            item.quality = Math.min(item.quality, 50); // The Quality of an item is never more than 50
        }
    }
    
    private void decreaseItemQuality(Item item) {
        // Once the sell by date has passed, Quality degrades twice as fast
        int decreaseBy = item.sellIn < 0 ? 2 : 1;
        
        // "Conjured" items degrade in Quality twice as fast as normal items
        if (item.name.equals("Conjured Mana Cake")) {
            decreaseBy *= 2;
        }
        
        item.quality -= decreaseBy;
    }

    private void updateConcertTicket(Item ticket) {
        ticket.sellIn -= 1;
        
        if (ticket.sellIn < 0) {
            ticket.quality = 0; // ticket quality drops to zero after the concert
            return;
        }
    
        // Ticket quality increases twice when less than 10 days
        if (ticket.sellIn < 10) {
            // Ticket quality increases by an extra 1 when less than 5 days left
            ticket.quality += ticket.sellIn < 5 ? 2 : 1;
        }

        ticket.quality = Math.min(ticket.quality + 1, 50);
    }
}