package brickingbad.ui.game;

import brickingbad.domain.game.*;
import brickingbad.domain.game.listeners.ErrorListener;
import brickingbad.domain.game.listeners.GameListener;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.components.UIGameObject;
import brickingbad.ui.components.containers.BrickCountPanel;
import brickingbad.ui.components.containers.BuildButtonPanel;
import brickingbad.ui.game.animation.Animator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BuildingModePanel extends JPanel implements GameListener, ErrorListener {

    private static BuildingModePanel instance;

    private ArrayList<UIGameObject> uiObjects;

    private BuildButtonPanel buildButtonPanel;
    private BrickCountPanel brickCountPanel;

    private BufferedImage background;

    private BuildingModePanel() {
        Animator.getInstance(this).start();
        setLayout(null);
        uiObjects = new ArrayList<>();
        initUI();
        loadBackgroundImage();
        Game.getInstance().addObjectListener(this);
        Game.getInstance().addErrorListener(this);
    }

    public static BuildingModePanel getInstance() {
        if (instance == null) {
            instance = new BuildingModePanel();
        }
        return instance;
    }

    private void initUI() {
        buildButtonPanel = new BuildButtonPanel();
        brickCountPanel = new BrickCountPanel();
        brickCountPanel.setSize(GameConstants.screenWidth, (int)GameConstants.paddleAreaHeight);

        JPanel upContainer = new JPanel(new BorderLayout());
        upContainer.setSize(GameConstants.screenWidth, (int)GameConstants.menuAreaHeight);
        upContainer.setOpaque(true);
        upContainer.setBackground(Color.darkGray);

        JPanel downContainer = new JPanel(new BorderLayout());
        downContainer.setSize(GameConstants.screenWidth, (int)GameConstants.paddleAreaHeight);
        downContainer.setLocation(0, (int)(GameConstants.screenHeight - GameConstants.paddleAreaHeight));
        downContainer.setOpaque(false);

        add(upContainer);
        add(downContainer);
        upContainer.add(buildButtonPanel, BorderLayout.LINE_START);
        downContainer.add(brickCountPanel, BorderLayout.LINE_END);
    }

    @Override
    public void addObject(GameObject object) {
        UIGameObject newUIObject = new UIGameObject(object);
        addMouseListener(newUIObject);
        addMouseMotionListener(newUIObject);
        uiObjects.add(newUIObject);
    }

    @Override
    public void removeObject(GameObject object) {
        uiObjects.removeIf(uiGameObject -> uiGameObject.containsObject(object));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        uiObjects.forEach((obj) -> obj.paintComponent(g));
    }

    private void loadBackgroundImage() {
        try {
            this.background = ImageIO.read(new File("resources/sprites/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(String errorMessage) {
      JOptionPane.showMessageDialog(BrickingBadFrame.getInstance(), errorMessage);
    }

    public void resetUI() {
        uiObjects = new ArrayList<>();
    }

}
