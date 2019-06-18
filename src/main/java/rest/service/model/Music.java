package rest.service.model;

public class Music extends Item {
    private String artist;

    public Music() {
    }

    public Music(String title, String genre, String format, Integer year, String artist) {
        super(title, genre, format, year);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean compareTo(Item obj) {
        if (super.compareTo(obj)
                && artist.equals(((Music) obj).artist)) {
            return true;
        }
        return false;
    }
}

