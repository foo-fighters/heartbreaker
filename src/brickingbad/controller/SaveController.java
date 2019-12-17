package brickingbad.controller;

import brickingbad.domain.game.Level;
import brickingbad.domain.game.authentication.User;
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
    User user = AuthenticationController.getInstance().getCurrentUser();
    Level level = Level.getInstance();
    Save save = SaveAssembler.assemble(level, name);
    save.inRunningMode = inRunningMode;
    save.username = AuthenticationController.getInstance().getCurrentUser().name;
    saveRepository.save(save, user);
  }

  public void loadGame(String name) {
    User user = AuthenticationController.getInstance().getCurrentUser();
    Save save = saveRepository.getSaveByName(name, user);
    if (save.inRunningMode) {
      BrickingBadFrame.getInstance().showRunningModePanel();
    } else {
      BrickingBadFrame.getInstance().showBuildingModePanel();
    }
    SaveAssembler.disassemble(save);
    GameController.getInstance().resumeGameIfPaused();
  }

  public List<String> getSaveNames() {
    User user = AuthenticationController.getInstance().getCurrentUser();
    return saveRepository.getSaveNames(user);
  }

  public SaveController adapt(Adapter adapter) {
    saveRepository.adapt(adapter);
    return this;
  }

}
