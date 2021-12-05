package com.project.homework.model;

import java.time.LocalDateTime;

import com.project.homework.utils.Utils;

public class Metadata {
    private String url;
    private LocalDateTime lastFetch;
    private int links;
    private int assets;
    private int images;
    private int imports;

    public Metadata(String url) {
        this.url = url;
        this.lastFetch = LocalDateTime.now();
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getLastFetch() {
        return this.lastFetch;
    }

    public void setLastFetch(LocalDateTime lastFetch) {
        this.lastFetch = lastFetch;
    }

    public int getLinks() {
        return this.links;
    }

    public void setLinks(int links) {
        this.links = links;
    }

    public int getAssets() {
        return this.assets;
    }

    public void setAssets(int assets) {
        this.assets = assets;
    }

    public int getImages() {
        return this.images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public int getImports() {
        return this.imports;
    }

    public void setImports(int imports) {
        this.imports = imports;
    }

    @Override
    public String toString() {
        return "site: " + this.url + "\n" +
                "last_fetch: " + Utils.formatLocalDate(this.lastFetch) + "\n" +
                "links: " + this.links + "\n" +
                "assets: " + this.assets + " (" + this.images + " Images)" + "\n" +
                "imports: " + this.imports + "\n";
    }

}
