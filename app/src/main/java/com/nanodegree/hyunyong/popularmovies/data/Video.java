package com.nanodegree.hyunyong.popularmovies.data;

/**
 * "id": "5a37e67d0e0a264cd01ede50",
 *             "iso_639_1": "en",
 *             "iso_3166_1": "US",
 *             "key": "QvHv-99VfcU",
 *             "name": "Behind the Scenes with James Cameron and Robert Rodriguez",
 *             "site": "YouTube",
 *             "size": 1080,
 *             "type": "Featurette"
 */
public class Video {
    public Video(String id, String iso_639_1, String iso_3166_1, String key, String name, String site, int size, String type) {
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    private String id;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;
}
