package brickingbad.ui;

import brickingbad.ui.components.BBButton;

public interface DefaultActionable {
    default BBButton getDefaultButton(){
        return null;
    };
}
