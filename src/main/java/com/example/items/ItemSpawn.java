package com.example.items;

/**
 * ItemSpawn.
 */
public class ItemSpawn {
    /**
     *
     */
    private Item item;
    /**
     *
     */
    private int waitTime;

    /**
     *
     * @param item
     * @param waitTime
     */
    public ItemSpawn(Item item, int waitTime) {
        this.item = item;
        this.waitTime = waitTime;
    }

    /**
     *
     * @return
     */
    public Item getItem() {
        return item;
    }

    /**
     *
     * @param item
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     *
     * @return
     */
    public int getWaitTime() {
        return waitTime;
    }

    /**
     *
     */
    public void decreaseWaitTime() {
        waitTime -= 1;
    }

    /**
     *
     * @param waitTime
     */
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
}
