package com.harium.etyl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.harium.etyl.commons.module.Module;
import com.harium.etyl.core.GDXCore;
import com.harium.etyl.gdx.GDXWindow;
import com.harium.etyl.loader.Loader;
import com.harium.etyl.util.PathHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseEngine<T extends GDXCore> {

    protected int w;
    protected int h;
    protected String icon;

    protected GDXCore core;
    private Lwjgl3ApplicationConfiguration configuration;

    protected List<Loader> loaders;

    public BaseEngine(int w, int h) {
        super();

        this.w = w;
        this.h = h;

        loaders = new ArrayList<>();

        configuration = buildConfiguration();
        core = initCore();
        core.getSession().put(Etyl.WINDOW, new GDXWindow());
    }

    public void init() {
        configuration.setWindowedMode(w, h);

        new Lwjgl3Application(core, configuration);

        initLoaders();
    }

    protected void initialSetup(String suffix) {
        String path = PathHelper.currentDirectory() + "assets" + File.separator + suffix;

        for (Loader loader : loaders) {
            loader.setUrl(path);
        }
    }

    protected void initLoaders() {
        for (Loader loader : loaders) {
            loader.setAssets(core.getAssets());
        }
    }

    protected GDXCore initCore() {
        return new GDXCore(w, h);
    }

    public GDXCore getCore() {
        return core;
    }

    protected Lwjgl3ApplicationConfiguration buildConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        return configuration;
    }

    public void setTitle(String title) {
        configuration.setTitle(title);
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void enableFullScreen() {
        Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
        configuration.setFullscreenMode(currentMode);
    }

    public void disableFullScreen() {
        configuration.setWindowedMode(w, h);
    }

    public void addLoader(Loader loader) {
        loaders.add(loader);
    }

    public void addModule(Module module) {
        core.addModule(module);
    }
}
