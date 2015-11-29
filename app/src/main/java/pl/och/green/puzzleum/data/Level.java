package pl.och.green.puzzleum.data;

import android.graphics.Bitmap;

public class Level {
    private final int imageId;
    private final Bitmap image;
    private final int tiles;
    private final String title;
    private final int descriptionId;

    public Level(Bitmap image, int tiles, int imageId, String title, int descriptionId) {
        this.image = image;
        this.imageId = imageId;
        this.tiles = tiles;
        this.title = title;
        this.descriptionId = descriptionId;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getImageId() {
        return imageId;
    }

    public int getTiles() {
        return tiles;
    }

    public String getTitle() {
        return title;
    }

    public int getDescriptionId() {
        return descriptionId;
    }
}
