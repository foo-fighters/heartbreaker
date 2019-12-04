package brickingbad.controller;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.persistence.Save;
import brickingbad.domain.game.persistence.SaveAssembler;
import brickingbad.services.Adapter;
import brickingbad.services.persistence.SaveRepository;
import brickingbad.ui.BrickingBadFrame;

import java.util.List;

public class SaveController {

  private static SaveController instance;
  private static SaveRepository saveRepository;

  private SaveController() {
    saveRepository = SaveRepository.getInstance();
  }

  public static SaveController getInstance() {
    if (instance == null) {
      instance = new SaveController();
    }
    return instance;
  }

  public void saveGame(String name, boolean inRunningMode) {
    Game game = Game.getInstance();
    Save save = SaveAssembler.assemble(game, name);
    save.inRunningMode = inRunningMode;
    saveRepository.save(save);
  }

  public void loadGame(String name) {
    Save save = saveRepository.getSaveByName(name);
    if (save.inRunningMode) {
      BrickingBadFrame.getInstance().showRunningModePanel();
    } else {
      BrickingBadFrame.getInstance().showBuildingModePanel();
    }
    SaveAssembler.disassemble(save);
    Game.getInstance().play();
  }

  public List<String> getSaveNames() {
    return saveRepository.getSaveNames();
  }

  public SaveController adapt(Adapter adapter) {
    saveRepository.adapt(adapter);
    return this;
  }

}
