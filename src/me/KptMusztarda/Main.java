package me.KptMusztarda;

import me.KptMusztarda.GUI.GUI;
import me.KptMusztarda.GUI.Log;
import me.KptMusztarda.GUI.Renderer;
import me.KptMusztarda.GUI.Window;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    static Log log;
    static GUI gui;
    static Cube cube;

    static final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();


    public static void main(String[] args) {
        Window w = new Window();

        cube = new Cube();

        Renderer r = new Renderer(5, 5);
        cube.setRenderer(r);
        r.setCube(cube);

        w.add(r);

        gui = new GUI(575, 5);
        w.add(gui);

        log = new Log(5, r.getY()+r.getHeight()+5, r.getX()+r.getWidth() - 2, 200);
        w.add(log.getScroll());

        NetworkData.getInstance().setCube(cube);

        w.setDefaultCloseOperation(Window.EXIT_ON_CLOSE);
        w.setVisible(true);

        synchronized (queue) {
            while(true)  {
                try {
                    queue.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
